package server.entities.player;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;

import server.validify.Validify;

public class PlayerMP extends Player {
	
	public InetAddress player_ip;
	public int port;
	
	private int num_failed = 0;
	
	public static PlayerMP newPlayerMP(String username, String password, InetAddress player_ip, int port) {
		if (!Validify.valid_username(username) || !Validify.valid_password(password)) {
			return null;
		}
		//check that the username exists, and the password is correct
		if (!correct_password(username, password)) {
			return null;
		}
		return new PlayerMP(username, password, player_ip, port);
	}
	
	public PlayerMP(String username, String password, InetAddress player_ip, int port) {
		super(username);
		this.player_ip = player_ip;
		this.port = port;
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
	
}