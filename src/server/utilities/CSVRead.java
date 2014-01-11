package server.utilities;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import au.com.bytecode.opencsv.CSVReader;

public class CSVRead {

	private String file_path;
	private String[] headers;
	private int curRowRead = 0;
	private ArrayList<String[]> rows = new ArrayList<String[]>();
	
	public CSVRead(String file_path) {	
		this.file_path = file_path;
		try {
			read();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}
	
	private void read() throws IOException {
		CSVReader reader = null;
		reader = new CSVReader(new FileReader(file_path), ',');
		headers = reader.readNext();
		String[] line;
		while ((line = reader.readNext()) != null) {
			rows.add(line);
		}
		reader.close();
	}
	
	public String[] getHeaders() {
		return headers;
	}
	
	public ArrayList<String[]> getAllData() {
		return rows;
	}
	
	public String[] getRow(int row) {
		return rows.get(row);
	}
	
	public void startRead() {
		this.curRowRead = 0;
	}
	
	public String[] getNextLine() {
		String[] ret = rows.get(curRowRead);
		curRowRead++;
		return ret;
	}
	
	public boolean finishedReading() {
		return (curRowRead >= rows.size());
	}
	
	public int getNumRows() {
		return rows.size();
	}
}

