package server.packets;

public class Packet003Ping extends Packet {
	
	public Packet003Ping() {
		super(003);
	}

	public byte[] getData() {
		return ("003").getBytes();
	}
	
	public boolean isValid() {
		return true;
	}
}

