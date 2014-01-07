package server.packets;

public class Packet000Login extends Packet {

	private String username;
	private String password;
	public int connection_id;
	
	public Packet000Login(byte[] data) {
		super(0);
		String data_parts[] = readData(data);
		if (data_parts.length < 3)
			return;
		this.username = data_parts[0];
		this.password = data_parts[1];
		this.connection_id = parseToInt(data_parts[2]);
	}
	
	public Packet000Login(String username, String password, int connection_id) {
		super(0);
		this.username = username;
		this.password = password;
		this.connection_id = connection_id;
	}
	
	public byte[] getData() {
		return (intToString(0) + sep + this.username + sep + this.password + sep + intToString(this.connection_id) + end).getBytes();
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
	
	public int getConnectionID() {
		return connection_id;
	}
	
}