package server;

import java.util.HashMap;

import server.entities.connected.Connected;
import server.game.entities.Entity;
import server.packets.Packet008Update_entity;
import server.utilities.Timer;

public class UpdateClients extends Thread {
	
	boolean is_running;
	HashMap<Integer, Packet008Update_entity.UpdateData> changed_entities;
	HashMap<String, Integer> feature_str_to_int = new HashMap<String, Integer>();
	//key = connection_id of player.
	HashMap<Integer, Packet008Update_entity> update_packets = new HashMap<Integer, Packet008Update_entity>();
 	Server server;
	
	public UpdateClients(Server server) {
		this.server = server;
		fillMap();
	}
	
	public void run() {
		is_running = true;
		changed_entities = new HashMap<Integer, Packet008Update_entity.UpdateData>();
		while (is_running) {
			Timer t = new Timer();
			t.start();
			while (t.getTimeS() < server.UPDATE_CLIENT_TIME);
			for (int entity_id : changed_entities.keySet()) {
				Packet008Update_entity.UpdateData update = changed_entities.get(entity_id);
				//first, add the value of the changed features to the UpdateData struct
				for (int i = 0; i < update.feature_is_updated.length; i++) {
					if (update.feature_is_updated[i]) {
						addValue(entity_id, i, update);
					}
				}
				build_update_packets(entity_id);
				changed_entities.remove(entity_id);
			}
			//now send all the packets
			for (int connection_id : update_packets.keySet()) {
				server.sendData(update_packets.get(connection_id).getData(), 
					server.connectedPlayers[connection_id].client_ip, 
						server.connectedPlayers[connection_id].client_port);
				update_packets.remove(connection_id);
			}
		}
	}
	
	public void addChange(int entity_id, int changed_feature) {
		Packet008Update_entity.UpdateData update;
		if (changed_entities.containsKey(entity_id)) {
			update = changed_entities.get(entity_id);
		} else {
			update = new Packet008Update_entity.UpdateData();
		}
		update.feature_is_updated[changed_feature] = true;
		changed_entities.put(entity_id, update);
	}
	
	
	private void fillMap() {
		feature_str_to_int.put("x_loc", 0);
		feature_str_to_int.put("y_loc", 1);
		feature_str_to_int.put("animation", 2);
	}
	
	public int feature_str_to_int(String feature) {
		return feature_str_to_int.get(feature);
	}
	
	private void build_update_packets(int entity_id) {
		Packet008Update_entity.UpdateData update = changed_entities.get(entity_id);
		for (Connected player : server.connectedPlayers) {
			if (player.playerMP.contains_entity(entity_id)) {
				addEntityToPacket(update, player.identifier_id, entity_id);
			}
		}
	}

	private void addEntityToPacket(Packet008Update_entity.UpdateData update, int client_id, int entity_id) {
		Packet008Update_entity packet;
		if (update_packets.containsKey(client_id)) {
			packet = update_packets.get(client_id);
		} else {
			packet = new Packet008Update_entity();
		}
		packet.update_new_entity(entity_id, update);
		update_packets.put(client_id, packet);
	}
	
	private void addValue(int entity_id, int index, Packet008Update_entity.UpdateData update) {
		Entity e = server.game.entities.get(entity_id);
		switch(index) {
		case 0:
			update.x_loc = (int) e.getLocation().x;
			break;
		case 1:
			update.y_loc = (int) e.getLocation().y;
			break;
		case 2:
			//TODO
			break;
		}
	}
	
}
