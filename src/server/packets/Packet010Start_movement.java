package server.packets;

//direction contains a value representative of East, North-east, North, etc...
//0: E
//1: NE
//2: N
//3: NW
//4: W
//5: SW
//6: S
//7: SE

public class Packet010Start_movement extends Packet {

	int identifier_id;
	byte direction;
	
	public Packet010Start_movement(byte[] data) {
		super(10);
		String data_parts[] = readData(data);
		if (data_parts.length < 2 || data_parts[1].length() > 1) {
			return;
		}
		this.identifier_id = parseToInt(data_parts[0]);
		this.direction = (byte) data_parts[1].charAt(0);
 	}
	
	public Packet010Start_movement(int identifier_id, byte direction) {
		super(10);
		this.identifier_id = identifier_id;
		this.direction = direction;
	}
	
	public byte[] getData() {
		return (intToString(10) + sep + intToString(identifier_id) + sep + direction + end).getBytes();
	}
	
	public int getIdentifierID() {
		return this.identifier_id;
	}
	
	public byte getDirection() {
		return this.direction;
	}

	public boolean isValid() {
		return (direction >= 0 && direction <= 7);
	}
}
