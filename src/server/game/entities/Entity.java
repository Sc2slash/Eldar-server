package server.game.entities;

import server.game.levels.Level;
import server.utilities.Rectangle.Rect2f;

public abstract class Entity {
	Level level;
	Rect2f location;
	
	public Entity(Level level){
		this.level = level;
	}
	public void update(){
		
	}
	public void interact(){
		
	}
	
}
