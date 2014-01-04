package server.packets;

import server.Server;

public abstract class Packet {
	
	public static enum PacketTypes {
		INVALID(-1), LOGIN(000), LOGIN_CONFIRM(001), DISCONNECT(002), PING(003), CONNECT(004), CONNECTION_SUCCEEDED(005);
		
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
	
	public String[] readData(byte[] data) {
		String message = new String(data).trim().substring(3);
		String message_parts[] = message.split("&");
		return message_parts;
	}
	
	public static PacketTypes lookupPacket(String id) {
		try {
			return lookupPacket(Integer.parseInt(id));
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
	
	public int parseToInt(String s) {
		int ret = 0, counter = 1;
		for (char c : s.toCharArray()) {
			ret += (int)c * Math.pow(255, s.length()-counter);
			counter++;
		}
		return ret;
	}
	
	public String intToString(int i) {
		String s = "";
		while (i > 0) {
			s += (char) (i % 255);
			i /= 255;
		}
		return new StringBuffer(s).reverse().toString();
	}
	
}
