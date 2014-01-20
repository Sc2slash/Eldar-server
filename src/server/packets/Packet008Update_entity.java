package server.packets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;

//Each entity feature being updated is preceded by a 1 byte code, from 1-125 (max)
//1: x_loc
//2: y_loc
//3: animation 

//A byte with a value of 0 precedes an entity id. Anything following the entity id,
//until the next 0 or end of packet, are updated features of that specific entity. 

public class Packet008Update_entity extends Packet {
	
	public static class UpdateData {
		public int x_loc, y_loc, animation;
		public boolean feature_is_updated[] = new boolean[3];
	}
	
	HashMap<Integer, UpdateData> updated_entities = new HashMap<Integer, UpdateData>(); 
 	
	public Packet008Update_entity(byte[] data) {
		super(8);
		String data_parts[] = readData(data);
		for (int i = 0; i < data_parts.length;) {
			if (data_parts[i].charAt(0) == 0) {
				i += process_entity(data_parts, i) + 1;
			}
		}
	}
	
	public Packet008Update_entity() {
		super(8);
	}
	
	public byte[] getData() {
		StringBuilder data = new StringBuilder();
		data.append(intToString(8) + sep);
		for (int entity_id : updated_entities.keySet()) {
			UpdateData entity_update = updated_entities.get(entity_id);
			data.append(intToString(0) + intToString(entity_id) + sep);
			if (entity_update.feature_is_updated[0])
				data.append(intToString(1) + intToString(entity_update.x_loc) + sep);
			if (entity_update.feature_is_updated[1])
				data.append(intToString(2) + intToString(entity_update.y_loc) + sep);
			if (entity_update.feature_is_updated[2])
				data.append(intToString(3) + intToString(entity_update.animation) + sep);
		}
		data.deleteCharAt(data.length() - 1);
		return data.toString().getBytes();
	}
	
	public void update_new_entity(int entity_id, UpdateData update) {
		updated_entities.put(entity_id, update);
	}
	
	public int[] entity_ids() {
		return ArrayUtils.toPrimitive(updated_entities.keySet().toArray(new Integer[updated_entities.keySet().size()]));
	}
	
	public UpdateData getEntityUpdate(int entity_id) {
		return updated_entities.get(entity_id);
	}

	public boolean isValid() {
		//TODO
		return true;
	}
	
	//returns the number of updated features for the specific entity
	private int process_entity(String data_parts[], int pos) {
		if (data_parts[pos].length() <= 1)
			return 0;
		
		int num_features = 0;
		UpdateData update = new UpdateData();
		int entity_id = parseToInt(data_parts[pos].substring(1));
		
		for (int i = pos+1; i < data_parts.length && data_parts[i].charAt(0) != 0; i++) {
			int feature_id = data_parts[i].charAt(0);
			String feature_update = data_parts[i].substring(1);
			switch(feature_id) {
			case 1:
				update.x_loc = parseToInt(feature_update);
				update.feature_is_updated[0] = true;
				break;
			case 2:
				update.y_loc = parseToInt(feature_update);
				update.feature_is_updated[1] = true;
				break;
			case 3:
				update.animation = parseToInt(feature_update);
				update.feature_is_updated[2] = true;
				break;
			default:
				break;
			}
			num_features++;
		}
		updated_entities.put(entity_id, update);
		return num_features;
	}
	
}
