package server.cmd;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Scanner;

import server.Server;
import server.packets.Packet003Ping;
import server.packets.Packet007New_entity;

//Usage:
//Each line of stdin corresponds to one command
//(should also be able to take in the name of the packet)
//The first word in the line corresponds to the code of the packet to be sent
//Each of the following words correspond to the data in the packet,
//in the same order as they appear on the sent packet
//
//The last word corresponds to the client_ip and client_port,
//separated by a semicolon. eg '127.0.0.1:9213'
//(should also be able to take in the connection_id)


public class CMD extends Thread {

	public Server server;
	public boolean cmd_running = false;
	
	public CMD(Server server) {
		super("CMD");
		this.server = server;
	}
	
	public void run() {
		Scanner scanner = new Scanner(System.in);
		cmd_running = true;
		while (cmd_running) {
			String line_parts[] = scanner.nextLine().split(" ");
			if (line_parts.length < 2) {
				System.out.println("Invalid command");
				continue;
			}
			String packet_id = line_parts[0];
			if (line_parts[line_parts.length-1].split(":").length != 2) {
				System.out.println("Invalid client ip/port");
				continue;
			}
			InetAddress client_ip;
			try {
				client_ip = InetAddress.getByName(line_parts[line_parts.length-1].split(":")[0]);
			} catch (UnknownHostException e1) {
				System.out.println("Invalid client ip/port");
				continue;
			}
			int client_port = -1;
			try {
				client_port = Integer.parseInt(line_parts[line_parts.length-1].split(":")[1]);
			} catch (NumberFormatException e) {
				System.out.println("Invalid client ip/port");
				continue;
			}
			
			String arguments[] = Arrays.copyOfRange(line_parts, 1, line_parts.length-1);
			
			switch(Integer.parseInt(packet_id)) {
			//Ping
			case 3:
			{
				Packet003Ping packet = new Packet003Ping();
				server.sendData(packet.getData(), client_ip, client_port);
			}
				break;
			//Update Entity
			case 7:
			{
				Packet007New_entity packet = new Packet007New_entity();
				server.sendData(packet.getData(), client_ip, client_port);
			}
			break;
			default:
				System.out.println("Invalid packet id");
				break;
			}
			
		}
		scanner.close();
	}
}
