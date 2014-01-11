package server.packets;

public class Packet007New_entity extends Packet{

	String entity_prefix;
	int entity_num, server_id;
	boolean is_hostile;
	int x_loc, y_loc;
	
	
	public Packet007New_entity(){
		super(007);
		
	}
	
	public byte[] getData() {
		//prefix, num, id_server, type (hostile, friendly), x, y, animation, name
		return (intToString(7) + sep + intToString(1) + sep + intToString(0) + sep + intToString(0) + sep + intToString(200) + sep + intToString(300)+ sep + intToString(30) + sep + intToString(30) + sep + intToString(0)+ sep + intToString(2) + sep + intToString(0) + sep + intToString(0) + sep + intToString(200) + sep + intToString(400)+ sep + intToString(30) + sep + intToString(30) + sep + intToString(0)+end).getBytes();  
	}

	public boolean isValid() {
		return true;
	}
}
