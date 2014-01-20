package server.entities.player;

import server.game.Game;
import server.utilities.CSVRead;
import server.utilities.Rectangle.Rect2f;

public class LoadPlayer {

	String resources_id;
	Rect2f location;
	float health, damage;
	float speed;
	
	public LoadPlayer(String username) {
		String[] player_data = load(username);
		if (player_data != null && player_data.length == 9) {
			resources_id = player_data[1];
			location = new Rect2f(Float.parseFloat(player_data[2]), 
					Float.parseFloat(player_data[3]), Float.parseFloat(player_data[4]), 
							Float.parseFloat(player_data[5]));
			health = Float.parseFloat(player_data[6]);
			damage = Float.parseFloat(player_data[7]);
			speed = Float.parseFloat(player_data[8]);
		}
	}
	
	private String[] load(String username) {
		CSVRead read = new CSVRead(Game.playersFile);
		while (!read.finishedReading()) {
			String[] line = read.getNextLine();
			if (username.equalsIgnoreCase(line[0])) {
				return line;
			}
		}
		return null;
	}
	
	public String getResourcesID() {
		return this.resources_id;
	}
	
	public Rect2f getLocation() {
		return this.location;
	}
	
	public float getDamage() {
		return this.damage;
	}
	
	public float getHealth() {
		return this.health;
	}
	
	public float getSpeed() {
		return this.speed;
	}
}
