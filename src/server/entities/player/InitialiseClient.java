package server.entities.player;

import server.Server;
import server.game.entities.Entity;
import server.packets.Packet007New_entity;

public class InitialiseClient {

	Server server;
	int connection_id;
	
	public InitialiseClient(Server server, int connection_id) {
		this.server = server;
		this.connection_id = connection_id;
	}
	
	public void initialise() {
		Entity player = server.game.entities.get(server.connectedPlayers[connection_id].playerMP.getID());
		Packet007New_entity packet = new Packet007New_entity(player);
		server.sendData(packet.getData(), server.connectedPlayers[connection_id].client_ip, 
				server.connectedPlayers[connection_id].client_port);
	}
	
}
