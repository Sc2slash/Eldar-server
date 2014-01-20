package server.game.entities;

import server.utilities.Rectangle.Rect2f;

public class Player extends Entity {
	
	public String username;
	//float damage, health;

	public Player(String username, String resources_id, Rect2f location, float speed) {
		super(normEntityType("Player"), resources_id, 1, location, speed);
		this.username = username;
	}
	
}
