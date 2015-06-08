package com.eimis;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {
	public List<CsvEntry> readCsv(String fileName) {
		ArrayList<CsvEntry> entries = new ArrayList<CsvEntry>();
		
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ";";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		try {
			br = new BufferedReader(new FileReader(fileName));
			br.readLine(); // skip header
			while ((line = br.readLine()) != null) {
				String[] readLine = line.split(cvsSplitBy);
				CsvEntry entry = new CsvEntry();
				String dayTime = readLine[0] + readLine[1];
				entry.setDay(sdf.parse(dayTime));
				entry.setSpeed(Float.valueOf(readLine[3]));
				entry.setHeartRate(Float.valueOf(readLine[4]));
				String cadenceString = readLine[5].replaceAll("\\s+", "");
				if (cadenceString.length() < 4) // avoid parsing millions
					entry.setCadence(Integer.valueOf(cadenceString));
				entry.setPower(readLine[6]);
				entry.setAltitude(Integer.valueOf(readLine[8].replaceAll("\\s+", "")));
				entry.setTemperature(Integer.valueOf(readLine[9].replaceAll("\\s+", "")));
				entries.add(entry);
			}
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if (br != null) {
				try {
					br.close();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return entries;
		
	}
}
