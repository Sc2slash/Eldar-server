package server.packets;

import java.util.Arrays;

public abstract class Packet {
	
	protected final char sep = (char) 126;
	protected final char end = (char) 127;
	
	public static enum PacketTypes {
		INVALID(-1), LOGIN(0), LOGIN_CONFIRM(1), DISCONNECT(2), PING(3), CONNECT(4), CONNECTION_SUCCEEDED(5), CHECK_CONNECTION(6);
		
		private int packetID;
		private PacketTypes(int packetID) {
			this.packetID = packetID;
		}
		
		public int getID() {
			return packetID;
		}
	}
	
	public byte packetID;
	
	public Packet(int packetID) {
		this.packetID = (byte) packetID;
	}
	
	public abstract byte[] getData();
	
	//Each packet type has a certain format
	//This method ensures that all parts of the packet
	//correspond to the data required for the specific part,
	//and more importantly, that the packet doesn't create any security issues
	public abstract boolean isValid();
	
	public static String[] readData(byte[] data) {
		String message = new String(data).trim();
		message = message.substring(0, message.length()-1);
		String message_parts[] = message.split((new Character((char)126)).toString());
		if (message_parts.length == 0)
			return Arrays.copyOfRange(message_parts, 0, 0);
		return Arrays.copyOfRange(message_parts, 1, message_parts.length);
	}
	
	public static PacketTypes lookupPacket(String id) {
		try {
			return lookupPacket(parseToInt(id));
		} catch (NumberFormatException e) {
			return PacketTypes.INVALID;
		}
	}
	
	public static PacketTypes lookupPacket(int id) {
		for (PacketTypes p : PacketTypes.values()) {
			if (p.getID() == id) {
				return p;
			}
		}
		return PacketTypes.INVALID;
	}
	
	public static int parseToInt(String s) {
		int ret = 0, counter = 1;
		for (char c : s.toCharArray()) {
			ret += (int)c * Math.pow(125, s.length()-counter);
			counter++;
		}
		return ret;
	}
	
	public static String intToString(int i) {
		if (i == 0) return (new Character((char)0)).toString();
		String s = "";
		while (i > 0) {
			s += (char) (i % 125);
			i /= 125;
		}
		return new StringBuffer(s).reverse().toString();
	}
	
	public static PacketTypes getPrefix(byte[] packet) {
		String prefix = "";
		for (byte b : packet) {
			if ((char)b == (char)126 || (char)b == (char)127) {
				break;
			}
			prefix += (char)b;
		}
		return lookupPacket(parseToInt(prefix));
	}
}
