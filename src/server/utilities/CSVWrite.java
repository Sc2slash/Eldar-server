package server.utilities;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import au.com.bytecode.opencsv.CSVReader;

public class CSVWrite {

	private String file_path;
	String[] headers;
	
	public CSVWrite(String file_path) {
		this.file_path = file_path;
		try {
			read();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void read() throws IOException {
		CSVReader reader = new CSVReader(new FileReader(file_path), ',');
		headers = reader.readNext();
		reader.close();
	}
	
	private String joinRow(String[] row) {
		return "\""+StringUtils.join(row, "\",\"")+"\"";
	}
	
	public void addRow(String[] row) throws IOException {
		if (row.length != headers.length)
			return;
		PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file_path, true)));
		writer.print("\n"+joinRow(row));
	    writer.close();
	}
	
	public void addMultipleRows(ArrayList<String[]> rows) throws IOException {
		PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file_path, true)));
		for (String[] row : rows) {
			if (row.length != headers.length)
				continue;
			writer.print("\n"+joinRow(row));
		}
		writer.close();
	}
	
	public void addColumn(String column_header, String[] column) throws IOException {
		CSVRead read = new CSVRead(file_path);
		BufferedWriter writer = new BufferedWriter(new FileWriter(file_path, false));
		if (column.length != read.getNumRows()) {
			writer.close();
			return;
		}
		String[] headers = read.getHeaders();
		writer.write(joinRow(headers) + ",\"" + column_header + "\"");
		for (String ele : column) {
			writer.write("\n"+joinRow(read.getNextLine()) + ",\"" + ele + "\"");
		}
		writer.close();
		headers = ArrayUtils.add(headers, column_header);
	}
}
