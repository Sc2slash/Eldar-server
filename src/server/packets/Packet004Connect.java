package server.packets;

public class Packet004Connect extends Packet {
	
	public Packet004Connect() {
		super(004);
	}
	
	public byte[] getData() {
		return ("004").getBytes();
	}

	public boolean isValid() {
		return true;
	}
	
}
