package server.packets;

import server.Server;

public class Packet000Login extends Packet {

	private String username;
	private String password;
	
	public Packet000Login(byte[] data) {
		super(000);
		String data_parts[] = readData(data);
		if (data_parts.length < 2)
			return;
		this.username = data_parts[0];
		this.password = data_parts[1];
	}
	
	public Packet000Login(String username, String password ) {
		super(000);
		this.username = username;
		this.password = password;
	}
	
	public byte[] getData() {
		return ("000" + this.username + "&" + this.password).getBytes();
	}
	
	//Still left to implement
	public boolean isValid() {
		return true;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
}