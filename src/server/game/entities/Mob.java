package server.game.entities;

import server.game.entities.mobs.Mob0001Test_mob;
import server.packets.Packet.PacketTypes;
import server.utilities.Rectangle.Rect2f;

public class Mob extends Entity {
	
	boolean is_passive;
	float damage, health;
	
	public static enum MobType{
        INVALID(-1), TEST_MOB(1);
        int typeID;
        private MobType(int type) {
                this.typeID = type;
        }
        
        public int getTypeID() {
                return typeID;
        }
	}
	
	public Mob(String resources_id, int entity_code, int x_loc, int y_loc, float speed) {
		super(normEntityType("MOB"), resources_id, entity_code, new Rect2f(x_loc, y_loc, 0, 0), speed);
		MobType mob_type = lookupMob(entity_code);
		switch(mob_type) {
		case TEST_MOB:
			super.changeSize(Mob0001Test_mob.getWidth(), Mob0001Test_mob.getHeight());
			this.damage = Mob0001Test_mob.getDamage();
			this.health = Mob0001Test_mob.getHealth();
			break;
		default:
			
		}
	}
	
	public MobType lookupMob(int id) {
		for (MobType p : MobType.values()) {
			if (p.getTypeID() == id) {
				return p;
			}
		}
		return MobType.INVALID;
	}
	
	public float getDamage() {
		return this.damage;
	}
	
	public float getHealth() {
		return this.health;
	}
	
	public boolean isPassive() {
		return this.isPassive();
	}
}
