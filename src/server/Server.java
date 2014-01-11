package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

import server.cmd.CMD;
import server.entities.connected.Connected;
import server.entities.disconnect.CatchDisconnected;
import server.entities.player.PlayerMP;
import server.game.Game;
import server.game.entities.Entity;
import server.packets.Packet;
import server.packets.Packet000Login;
import server.packets.Packet.PacketTypes;
import server.packets.Packet001Login_confirm;
import server.packets.Packet003Ping;
import server.packets.Packet004Connect;
import server.packets.Packet005Connection_succeeded;
import server.packets.Packet006Check_connection;


public class Server {
	
	//refers to the period of time between updating information about entities on the server
	public final float SERVER_FRAME_TIME = 1/60;
	//refers to the period of time between sending information about entities to the client 
	public final float UPDATE_CLIENT_TIME = 1/30;
	
	public static HashMap<String, Entity> entities = new HashMap<String, Entity>();
	public static TreeSet<Integer> used_entity_ids = new TreeSet<Integer>();
	
	public String SERVER_ADDRESS = new String("25.155.82.122");

	public int PACKET_SIZE = 1024;
	public int SERVER_PORT = 8124;
	
	private Game game;
	private DatagramSocket socket;
	CatchDisconnected c;
	
	public int MAX_NUM_PLAYERS = 100;
	boolean used_id[] = new boolean[MAX_NUM_PLAYERS];
	public Connected[] connectedPlayers = new Connected[MAX_NUM_PLAYERS];
	
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
		c = new CatchDisconnected(this);
		c.start();
		CMD cmd = new CMD(this);
		cmd.start();
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
//			System.out.println("CLIENT > " + new String(packet.getData()));
//			System.out.println("ClientAddress = " + packet.getSocketAddress());
			parsePacket(packet.getData(), packet.getAddress(), packet.getPort());
		}
 	}
	
	public void parsePacket(byte[] data, InetAddress client_ip, int client_port) {
		PacketTypes type = Packet.getPrefix(data);
		switch (type) {
		default:
		case INVALID:
			break;
		case LOGIN:
		{
			Packet000Login packet = new Packet000Login(data);
			Packet001Login_confirm response = new Packet001Login_confirm(0);
			PlayerMP player = PlayerMP.newPlayerMP(packet.getConnectionID(), packet.getUsername(), packet.getPassword(), client_ip, client_port);
			System.out.print("["+ client_ip.getHostAddress()+":"+client_port+"] ");
			if (player != null) {
				connectedPlayers[packet.getConnectionID()].addPlayerMP(player);
				System.out.println(packet.getUsername()+" has connected");
				response.change_validity();
			} else {
				System.out.println("Client("+packet.getConnectionID()+") has failed to login to " + packet.getUsername());
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
			break;
		case CONNECT:
		{
			Packet004Connect packet = new Packet004Connect();
			if (!packet.isValid() || clientIsConnected(client_ip, client_port)) {
				break;
			}
			//The client is now valid, and can connect
			int client_id = -1;
			for (int i = 0; i < MAX_NUM_PLAYERS; i++)
				if (!used_id[i]) {
					client_id = i;
					connectedPlayers[i] = new Connected(i, client_ip, client_port);
					used_id[i] = true;
					break;
				}
			if (client_id == -1) {
				//TODO --> Should send a packet saying the server is full, or create a queue.
				break;
			}
			System.out.println("["+ client_ip.getHostAddress()+":"+client_port+"] " + "has connected with id: " + client_id);
			Packet005Connection_succeeded response = new Packet005Connection_succeeded(client_id);
			sendData(response.getData(), client_ip, client_port);
		}
			break;
		case CHECK_CONNECTION:
			Packet006Check_connection packet = new Packet006Check_connection(data);
			c.markReceived(packet.getIdentifierID());
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
		for (Connected p : connectedPlayers) {
			if (p != null) {
				sendData(data, p.client_ip, p.client_port);
			}
		}
	}
	
	public boolean clientIsConnected(InetAddress client_ip, int port) {
		for (Connected p : connectedPlayers) {
			if (p != null && p.client_ip == client_ip && p.client_port == port) {
				return true;
			}
		}
		return false;
	}
	
	public void removeConnectedClient(int identifier_id) {
		System.out.println("Removing client with id:" + identifier_id);
		//save data about the player
		
		//
		used_id[identifier_id] = false;
		connectedPlayers[identifier_id] = null;
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		Server server = new Server(game);
		
		server.run();
	}
}
