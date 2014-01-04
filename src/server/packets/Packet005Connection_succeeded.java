package server.packets;

public class Packet005Connection_succeeded extends Packet {

	int identifier_id;
	
	public Packet005Connection_succeeded(byte[] data) {
		super(005);
		String data_parts[] = readData(data);
		identifier_id = parseToInt(data_parts[0]);
	}
	
	public Packet005Connection_succeeded(int identifier_id) {
		super(005);
		this.identifier_id = identifier_id;
	}
	
	public byte[] getData() {
		char end = (char)255;
		return ("005" + intToString(identifier_id) + end).getBytes();
	}

	public boolean isValid() {
		return true;
	}
	
}
