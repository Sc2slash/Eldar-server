package server;

import server.game.entities.Entity;
import server.utilities.Timer;

//direction contains a value representative of East, North-east, North, etc...
//0: E
//1: NE
//2: N
//3: NW
//4: W
//5: SW
//6: S
//7: SE

public class MoveEntities extends Thread {
	
	private static final double root_two = 1.41421356237;
	private static final double reciprocal_root_two = 1/root_two;

	Server server;
	
	boolean is_running;
	
	public MoveEntities(Server server) {
		this.server = server;
	}
	
	public void run() {
		is_running = true;
		Timer t = new Timer();
		t.start();
		while (is_running) {
			for (Entity e : server.moving_entities.values()) {
				byte direction = e.getMovingDirection();
				float speed = e.getSpeed();
				float x_speed = 0, y_speed = 0;
				switch(direction) {
				case 0:
					x_speed = speed;
					y_speed = 0;
					break;
				case 1:
					x_speed = (float) (speed*reciprocal_root_two);
					y_speed = (float) (speed*reciprocal_root_two);
					break;
				case 2:
					x_speed = 0;
					y_speed = speed;
					break;
				case 3:
					x_speed = (float) (-1*speed*reciprocal_root_two);
					y_speed = (float) (speed*reciprocal_root_two);
					break;
				case 4:
					x_speed = -1*speed;
					y_speed = 0;
					break;
				case 5:
					x_speed = (float) (-1*speed*reciprocal_root_two);
					y_speed = (float) (-1*speed*reciprocal_root_two);
					break;
				case 6:
					x_speed = 0;
					y_speed = -1*speed;
					break;
				case 7:
					x_speed = (float) (1*speed*reciprocal_root_two);
					y_speed = (float) (-1*speed*reciprocal_root_two);
					break;
				}
				e.changeLocation(e.getLocation().x + x_speed*server.SERVER_FRAME_TIME, e.getLocation().y + y_speed*server.SERVER_FRAME_TIME);
				server.update_clients.addChange(e.getID(), server.update_clients.feature_str_to_int("x_loc"));
				server.update_clients.addChange(e.getID(), server.update_clients.feature_str_to_int("y_loc"));
			}
			t.update();
			while (t.getTimeS() < server.SERVER_FRAME_TIME);
		}
	}
}
