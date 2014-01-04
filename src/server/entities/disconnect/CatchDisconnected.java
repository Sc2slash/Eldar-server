package server.entities.disconnect;

import server.Server;
import server.packets.Packet006Check_connection;
import server.utilities.Timer;

public class CatchDisconnected extends Thread {

	Server server;
	boolean[] received;
	
	public CatchDisconnected(Server server) {
		this.server = server;
		received = new boolean[server.MAX_NUM_PLAYERS];
	}
	
	public void run() {
		Timer t = new Timer();
		while (true) {
			for (int i = 0; i < server.MAX_NUM_PLAYERS; i++) {
				received[i] = false;
				if (server.connectedPlayers[i] != null) {
					Packet006Check_connection packet = new Packet006Check_connection(i);
					server.sendData(packet.getData(), server.connectedPlayers[i].client_ip, server.connectedPlayers[i].client_port);
				}
			}
			t.start();
			while (t.getTimeS() < 2);
			for (int i = 0; i < server.MAX_NUM_PLAYERS; i++) {
				if (server.connectedPlayers[i] != null) {
					if (received[i]) {
						server.connectedPlayers[i].num_failed_ping = 0;
					} else {
						server.connectedPlayers[i].num_failed_ping++;
						if (server.connectedPlayers[i].num_failed_ping >= 5) {
							System.out.println("client with id:" + i + " has failed " + server.connectedPlayers[i].num_failed_ping + " times");
							server.removeConnectedClient(i);
						}
					}
				}
			}
			
		}
	}
	
	public void markReceived(int client_id) {
		received[client_id] = true;
	}
	
}
