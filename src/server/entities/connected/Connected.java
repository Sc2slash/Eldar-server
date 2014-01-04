package server.entities.connected;

import java.net.InetAddress;

import server.entities.player.PlayerMP;

public class Connected {

	public int identifier_id;
	public InetAddress client_ip;
	public int client_port;
	PlayerMP playerMP;
	
	public int num_failed_ping = 0;
	
	public Connected(int identifier_id, InetAddress client_ip, int client_port) {
		this.identifier_id = identifier_id;
		this.client_ip = client_ip;
		this.client_port = client_port;
	}
	
	public void addPlayerMP(PlayerMP p) {
		this.playerMP = p;
	}
	
}
