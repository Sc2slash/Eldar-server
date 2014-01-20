package server.entities.player;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.TreeSet;

import server.game.entities.Player;
import server.utilities.Rectangle.Rect2f;
import server.validify.Validify;

public class PlayerMP extends Player {
	
	public InetAddress player_ip;
	public int port;
	public int connection_id;
	
	//Tree of all entities currently being held on client side for a particular player.
	TreeSet<Integer> client_entities = new TreeSet<Integer>();
	
	public static PlayerMP newPlayerMP(int connection_id, String username, String password, InetAddress player_ip, int port) {
		if (!Validify.valid_username(username) || !Validify.valid_password(password)) {
			return null;
		}
		//check that the username exists, and the password is correct
		if (!correct_password(username, password)) {
			return null;
		}
		LoadPlayer load = new LoadPlayer(username);
		return new PlayerMP(connection_id, load.getResourcesID(), load.getLocation(), load.getSpeed(), username, password, player_ip, port);
	}
	
	public PlayerMP(int connection_id, String resources_id, Rect2f location, float speed, String username, String password, InetAddress player_ip, int port) {
		super(username, resources_id, location, speed);
		this.player_ip = player_ip;
		this.port = port;
		this.connection_id = connection_id;
	}
	
	//check that the username exists, and the password is correct
	public static boolean correct_password(String username, String password) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(new File("Users")));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			return false;
		}
		String line;
		try {
			while ((line = br.readLine()) != null) {
				if (line.split(" - ")[0].equalsIgnoreCase(username) && line.split(" - ")[1].equals(password)) {
				   try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				   return true;
			   }
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean contains_entity(int entity_id) {
		return client_entities.contains(entity_id);
	}
	
}