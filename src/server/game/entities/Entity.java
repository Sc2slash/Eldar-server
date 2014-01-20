package server.game.entities;

import server.game.levels.Level;
import server.utilities.Rectangle.Rect2f;

public abstract class Entity {
	Level level;
	Rect2f location;
	
	float speed;
	byte moving_direction;
	
	String resources_id;
	//entity code is a code for a specific entity. eg. a torch and a rock will have
	//different codes, but there can be multiple torches with the same code.
	//This is pre-assigned.
	int entity_code;
	String name;
	int entity_type;
	
	//entity id identifies only one entity. two torches will have two separate
	//entity ids. This is assigned whenever the entity is created.
	int entity_id;
	
	public static enum EntityType{
        INVALID(-1), MOB(1), PLAYER(2), OBJECT(3), EVENT(4);
        int typeID;
        private EntityType(int type) {
            this.typeID = type;
        }
        
        public int getTypeID() {
        	return typeID;
        }
	}
	
	public Entity(int entity_type, String resources_id, int entity_code, Rect2f location, float speed) {
		this.entity_type = entity_type;
		this.resources_id = resources_id;
		this.entity_code = entity_code;
		this.location = location;
		this.speed = speed;
	}
	
	public Entity(Level level, Rect2f location, int type) {
		this.level = level;
	}
	
	public Rect2f getLocation() {
		return this.location;
	}
	
	public float getSpeed() {
		return this.speed;
	}

	public byte getMovingDirection() {
		return this.moving_direction;
	}
	
	public int getID() {
		return this.entity_id;
	}

	
	
	public void changeID(int id) {
		this.entity_id = id;
	}
	
	public void changeLevel(Level level) {
		this.level = level;
	}
	
	public void changeLocation(float x, float y) {
		this.location.x = x;
		this.location.y = y;
	}
	
	public void changeMovingDirection(byte direction) {
		this.moving_direction = direction;
	}
	
	public void changeSize(float width, float height) {
		this.location.w = width;
		this.location.h = height;
	}
	
	public void changeName(String name) {
		this.name = name;
	}
	
	public static int normEntityType(String type) {
		for (EntityType e : EntityType.values()) {
			if (type.equalsIgnoreCase(e.name()))
				return e.getTypeID();
		}
		return -1;
	}
	
}
