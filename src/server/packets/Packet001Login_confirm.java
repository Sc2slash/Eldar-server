package server.packets;

public class Packet001Login_confirm extends Packet {
	
	private int is_valid;
	
	public Packet001Login_confirm(byte[] data) {
		super(1);
		String data_parts[] = readData(data);
		this.is_valid = parseToInt(data_parts[0]);
	}
	
	public Packet001Login_confirm(int is_valid) {
		super(1);
		this.is_valid = is_valid;
	}
	
	public byte[] getData() {
		return (intToString(1) + sep + intToString(this.is_valid) + end).getBytes();
	}
	
	public boolean isValid() {
		return (is_valid == 1 || is_valid == 0);
	}
	
	public int get_is_valid() {
		return is_valid;
	}

	public void change_validity() {
		is_valid ^= 1;
	}
}