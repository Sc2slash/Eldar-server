package server.game.entities;

import server.game.Game;
import server.game.levels.Level;
import server.utilities.Rectangle.Rect2f;

public abstract class Entity {
	Level level;
	Rect2f location;
	String resources_id;
	int entity_code;
	String name;
	
	int entity_id;
	
	public static enum EntityType{
        MOB(1), PLAYER(2), OBJECT(3), EVENT(4);
        int typeID;
        private EntityType(int type) {
            this.typeID = type;
        }
        
        public int getTypeID() {
        	return typeID;
        }
	}
	
	public Entity(String resources_id, int entity_code, Rect2f location) {
		this.resources_id = resources_id;
		this.entity_code = entity_code;
		
	}
	
	public Entity(Level level, Rect2f location, int type) {
		this.level = level;
	}
	
	public void interact() {
		
	}
	
	public void changeID(int id) {
		this.entity_id = id;
	}
	
	public void changeLevel(Level level) {
		this.level = level;
	}
	
	public void changeLocation(int x, int y) {
		this.location.x = x;
		this.location.y = y;
	}
	
	public void changeSize(float width, float height) {
		this.location.w = width;
		this.location.h = height;
	}
	
	public void changeName(String name) {
		this.name = name;
	}
	
	public int getID() {
		return this.entity_id;
	}
	
}
