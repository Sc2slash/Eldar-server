package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import server.entities.player.PlayerMP;
import server.game.Game;
import server.packets.Packet;
import server.packets.Packet000Login;
import server.packets.Packet.PacketTypes;
import server.packets.Packet001Login_confirm;
import server.packets.Packet003Ping;

public class Server {
	
	public String SERVER_ADDRESS = new String("localhost");

	public int PACKET_SIZE = 1024;
	public int SERVER_PORT = 8124;
	
	private Game game;
	private DatagramSocket socket;
	
	private List<PlayerMP> connectedPlayers = new ArrayList<PlayerMP>();
	
	public Server(Game game) {
		this.game = game;
		try {
			this.socket = new DatagramSocket(new InetSocketAddress(SERVER_ADDRESS, SERVER_PORT));
		} catch (SocketException e) {
			e.printStackTrace();
		}
		System.out.println(socket.getInetAddress());
		System.out.println(socket.getLocalAddress());
		System.out.println(socket.getLocalPort());
	}
	public void run() {
		while (true) {
			byte[] data = new byte[PACKET_SIZE];
			DatagramPacket packet = new DatagramPacket(data, data.length);
			try {
				socket.receive(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("CLIENT > " + new String(packet.getData()));
			System.out.println("ClientAddress = " + packet.getSocketAddress());
			parsePacket(packet.getData(), packet.getAddress(), packet.getPort());
		}
 	}
	
	public void parsePacket(byte[] data, InetAddress client_ip, int client_port) {
		String message = (new String(data)).trim();
		PacketTypes type = Packet.lookupPacket(message.substring(0, 3));
		switch (type) {
		default:
		case INVALID:
			break;
		case LOGIN:
		{
			Packet000Login packet = new Packet000Login(data);
			Packet001Login_confirm response = new Packet001Login_confirm(0);
			PlayerMP player = PlayerMP.newPlayerMP(packet.getUsername(), packet.getPassword(), client_ip, client_port);
			if (player != null && !clientIsConnected(packet.getUsername(), client_ip, client_port)) {
				this.connectedPlayers.add(player);
				System.out.println("["+ client_ip.getHostAddress()+":"+client_port+"] "+packet.getUsername()+" has connected");
				response.change_validity();
			}
			sendData(response.getData(), client_ip, client_port);
		}
			break;
		case DISCONNECT:
			break;
		case PING:
		{
			Packet003Ping packet = new Packet003Ping();
			sendData(packet.getData(), client_ip, client_port);
		}
		}
	}
	
	public void sendData(byte[] data, InetAddress client_ip, int client_port) {
		DatagramPacket packet = new DatagramPacket(data, data.length, client_ip, client_port);
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendDataToAllClients(byte[] data) {
		for (PlayerMP p : connectedPlayers) {
			sendData(data, p.player_ip, p.port);
		}
	}
	
	public boolean clientIsConnected(String username, InetAddress client_ip, int port) {
		for (PlayerMP p : connectedPlayers) {
			if (p.username.equals(username) && p.player_ip == client_ip && p.port == port) {
				return true;
			}
		}
		return false;
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		Server server = new Server(game);
		
		server.run();
	}
}
