package server.packets;

public class Packet005Connection_succeeded extends Packet {

	int identifier_id;
	
	public Packet005Connection_succeeded(byte[] data) {
		super(5);
		String data_parts[] = readData(data);
		identifier_id = parseToInt(data_parts[0]);
	}
	
	public Packet005Connection_succeeded(int identifier_id) {
		super(5);
		this.identifier_id = identifier_id;
	}
	
	public byte[] getData() {
		return (intToString(5) + sep + intToString(identifier_id) + end).getBytes();
	}

	public boolean isValid() {
		return true;
	}
	
}
