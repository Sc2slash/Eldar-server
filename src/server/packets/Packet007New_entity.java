package server.packets;

import server.entities.player.PlayerMP;
import server.game.entities.Entity;
import server.game.entities.Player;

public class Packet007New_entity extends Packet {
	
	Entity entity;
	
	public Packet007New_entity(Entity entity) {
		super(7);
		this.entity = entity;
	}
	
	public byte[] getData() {
//		if(entity instanceof NPC){
//			return (intToString(7) + sep + intToString(0)+ sep + intToString(Integer.parseInt(entity.getServerID()))  + sep + intToString(entity.getBox().x) + sep +
//					intToString(entity.getBox().y) + sep + intToString(entity.getBox().w) + sep + intToString(entity.getBox().h) + sep + end).getBytes();
//		}
		if(entity instanceof Player || entity instanceof PlayerMP){
			return (intToString(7) + sep + intToString(1) + sep + intToString(entity.getID())  + sep + intToString((int)entity.getLocation().x) + sep +
					intToString((int)entity.getLocation().y) + sep + intToString((int)entity.getLocation().w) + sep + intToString((int)entity.getLocation().h) + sep + end).getBytes();
		}
		return null;
	}
	
	public boolean isValid() {
		//TODO
		return true;
	}
	
}
