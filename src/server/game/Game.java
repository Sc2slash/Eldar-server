package server.game;

import java.util.HashMap;
import java.util.TreeSet;

import server.game.entities.Entity;
import server.game.entities.Mob;
import server.utilities.CSVRead;
import server.utilities.Rectangle.Rect2f;

public class Game {
	
	public String mobsFile = "saved_data/mobs.csv";
	
	public static HashMap<String, Entity> entities = new HashMap<String, Entity>();
	public static TreeSet<Integer> used_entity_ids = new TreeSet<Integer>();
	
	public Game() {
		//loadGame();
	}
	
	private void loadGame() {
		CSVRead csv = new CSVRead(mobsFile);
		while (!csv.finishedReading()) {
			String[] entity_data = csv.getNextLine();
			String resources_id = entity_data[0];
			int mob_id = Integer.parseInt(entity_data[1]);
			int x_loc = Integer.parseInt(entity_data[2]);
			int y_loc = Integer.parseInt(entity_data[3]);
			Mob mob = new Mob(resources_id, mob_id, x_loc, y_loc);
			//Entity e = new Entity(resources_id, mob_id, new Rect2f(x_loc, y_loc))
		}
	}
	
	public void addEntity(Entity e) {
		int id = getNextEntityID();
		e.changeID(id);
		entities.put(Integer.toString(id), e);
		used_entity_ids.add(id);
	}
	
	private int getNextEntityID() {
		int prev_id = -1;
		for (int i : used_entity_ids) {
			if (i != prev_id + 1)
				return prev_id+1;
			prev_id++;
 		}
		return prev_id+1;
	}
	
	public void deleteEntity(Entity e) {
		if (!entities.containsValue(e)) {
			System.out.println("Cannot remove entity");
			return;
		}
		entities.remove(e.getID());
		used_entity_ids.remove(e.getID());
		//TODO clear memory associated with the entity
		
		//
	}
}
