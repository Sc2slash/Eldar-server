package server.packets;

public class Packet001Login_confirm extends Packet {
	
	private String is_valid;
	
	public Packet001Login_confirm(byte[] data) {
		super(001);
		String data_parts[] = readData(data);
		this.is_valid = data_parts[0];
	}
	
	public Packet001Login_confirm(int is_valid) {
		super(001);
		this.is_valid = new String(Integer.toString(is_valid));
	}
	
	public byte[] getData() {
		return ("001" + this.is_valid).getBytes();
	}
	
	public boolean isValid() {
		if (is_valid.length() != 1)
			return false;
		int is_valid_int = Integer.parseInt(is_valid);
		if (is_valid_int == 1 || is_valid_int == 0)
			return true;
		return false;
	}
	
	public int get_is_valid() {
		return Integer.parseInt(is_valid);
	}

	public void change_validity() {
		if (is_valid.equalsIgnoreCase("1"))
			is_valid = is_valid.replace("1", "0");
		else
			is_valid = is_valid.replace("0", "1");
	}
}