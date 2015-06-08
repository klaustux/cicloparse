package com.eimis;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.xml.parsers.ParserConfigurationException;

import net.divbyzero.gpx.GPX;
import net.divbyzero.gpx.Track;
import net.divbyzero.gpx.TrackSegment;
import net.divbyzero.gpx.Waypoint;
import net.divbyzero.gpx.parser.JDOM;
import net.divbyzero.gpx.parser.Parser;
import net.divbyzero.gpx.parser.ParsingException;

import org.joda.time.DateTime;
import org.joda.time.Seconds;
import org.junit.Ignore;
import org.junit.Test;
import org.xml.sax.SAXException;

public class GpxReaderTest {
	public final static String gpxFileName = "src/test/resources/20150308_085155.gpx";
	public final static String csvFileName = "src/test/resources/2015-03-08-10-53-29.csv";
	public final static String combinedFileName = "src/test/resources/combined38.gpx";
	private static final String SEGMENT_START = "<trkseg>\n";
	private static final String SEGMENT_END = "</trkseg>\n";
	private static final String TRACK_START = "<trk>\n";
	private static final String TRACK_END = "</trk>\n";
	private static final String WAYPOINT_START = "<trkpt ";
	private static final String WAYPOINT_END = "</trkpt>\n";
	
	private static final String GPX_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> "
			+ "<gpx creator=\"StravaGPX\" version=\"1.1\" xmlns=\"http://www.topografix.com/GPX/1/1\" "
			+ "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.topografix.com/GPX/1/1 "
			+ " http://www.topografix.com/GPX/1/1/gpx.xsd http://www.garmin.com/xmlschemas/GpxExtensions/v3 "
			+ "http://www.garmin.com/xmlschemas/GpxExtensionsv3.xsd http://www.garmin.com/xmlschemas/TrackPointExtension/v1 "
			+ "http://www.garmin.com/xmlschemas/TrackPointExtensionv1.xsd http://www.garmin.com/xmlschemas/GpxExtensions/v3 "
			+ "http://www.garmin.com/xmlschemas/GpxExtensionsv3.xsd http://www.garmin.com/xmlschemas/TrackPointExtension/v1 "
			+ "http://www.garmin.com/xmlschemas/TrackPointExtensionv1.xsd http://www.garmin.com/xmlschemas/GpxExtensions/v3 "
			+ "http://www.garmin.com/xmlschemas/GpxExtensionsv3.xsd http://www.garmin.com/xmlschemas/TrackPointExtension/v1 "
			+ "http://www.garmin.com/xmlschemas/TrackPointExtensionv1.xsd http://www.garmin.com/xmlschemas/GpxExtensions/v3 "
			+ "http://www.garmin.com/xmlschemas/GpxExtensionsv3.xsd http://www.garmin.com/xmlschemas/TrackPointExtension/v1 http://www.garmin.com/xmlschemas/TrackPointExtensionv1.xsd http://www.garmin.com/xmlschemas/GpxExtensions/v3 http://www.garmin.com/xmlschemas/GpxExtensionsv3.xsd http://www.garmin.com/xmlschemas/TrackPointExtension/v1 http://www.garmin.com/xmlschemas/TrackPointExtensionv1.xsd\" xmlns:gpxtpx=\"http://www.garmin.com/xmlschemas/TrackPointExtension/v1\" xmlns:gpxx=\"http://www.garmin.com/xmlschemas/GpxExtensions/v3\">";
	private static final String GPX_FOOTER = "</gpx>";
	public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
	public static SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	
	@Ignore
	@Test
	public void dateTest() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss");
		Date date = Calendar.getInstance().getTime();
		System.out.println(sdf.format(date));
		String dayTime = "2014.08.31 08:08:43";
		sdf.parse(dayTime);
	}
	
	@Ignore
	@Test
	public void readGpxTest() {
		List<CsvEntry> csvEntries = new CsvReader().readCsv(csvFileName);
		System.err.println(csvEntries.get(0));
		System.err.println(csvEntries.get(1));
		System.err.println(csvEntries.get(2));
		System.err.println(csvEntries.get(3));
		System.err.println(csvEntries.get(4));
		System.out.println("Done");
	}
	
	@Ignore
	@Test
	public void dateTimeTest() {
		DateTime d859 = new DateTime("2014-09-21T08:59:59.000+03:00");
		DateTime d900 = new DateTime("2014-09-21T09:00:05.000+03:00");
		DateTime d901 = new DateTime("2014-09-21T09:01:20.000+03:00");
		DateTime d902 = new DateTime("2014-09-21T09:02:20.000+03:00");
		
		List<CsvEntry> csvEntries = new CsvReader().readCsv(csvFileName);
		CsvEntry entry = findNearestCsvEntry(d859, csvEntries, 20);
		System.err.println(entry);
		entry = findNearestCsvEntry(d900, csvEntries, 20);
		System.err.println(entry);
		entry = findNearestCsvEntry(d901, csvEntries, 20);
		System.err.println(entry);
		entry = findNearestCsvEntry(d902, csvEntries, 20);
		System.err.println(entry);
	}
	
	@Test
	public void readGpx() throws ParserConfigurationException, SAXException, IOException, ParsingException {
		Parser p = new JDOM();
		dateFormat2.setTimeZone(TimeZone.getTimeZone("GMT+2"));
		List<CsvEntry> csvEntries = new CsvReader().readCsv(csvFileName);
		GPX gpx = p.parse(new File(gpxFileName));
		FileWriter fw = new FileWriter(combinedFileName);
		fw.write(GPX_HEADER);
		for (Track track : gpx.getTracks()) {
			fw.write(TRACK_START);
			for (TrackSegment segment : track.getSegments()) {
				fw.write(SEGMENT_START);
				for (Waypoint waypoint : segment.getWaypoints()) {
					fw.write(WAYPOINT_START);
					String coord = "lat=\"" + waypoint.getCoordinate().getLatitude() + "\" lon=\""
							+ waypoint.getCoordinate().getLongitude() + "\">\n";
					fw.write(coord);
					String time = "<time>" + dateFormat.format(waypoint.getTime()) + "</time>\n";
					fw.write(time);
					String elevation = "<ele>" + waypoint.getElevation() + "</ele>\n";
					fw.write(elevation);
					DateTime gpxDate = new DateTime(waypoint.getTime());
					CsvEntry entry = findNearestCsvEntry(gpxDate, csvEntries, 20);
					if (entry == null)
						entry = findNearestCsvEntry(gpxDate, csvEntries, 40);
					if (entry == null)
						entry = findNearestCsvEntry(gpxDate, csvEntries, 60);
					if (entry != null) {
						fw.write("<extensions> <gpxtpx:TrackPointExtension>\n");
						fw.write("<gpxtpx:atemp>" + entry.getTemperature() + "</gpxtpx:atemp>\n");
						if (entry.getCadence() > 0)
							fw.write("<gpxtpx:cad>" + entry.getCadence() + "</gpxtpx:cad>\n");
						if (entry.getHeartRate() > 148)
							System.err.println(dateFormat.format(waypoint.getTime()));
						fw.write("<gpxtpx:hr>" + entry.getHeartRate() + "</gpxtpx:hr>\n");
						fw.write("</gpxtpx:TrackPointExtension></extensions>\n");
					}
					fw.write(WAYPOINT_END);
				}
				fw.write(SEGMENT_END);
			}
			fw.write(TRACK_END);
		}
		
		fw.write(GPX_FOOTER);
		fw.close();
	}
	
	private CsvEntry findNearestCsvEntry(DateTime gpxDate, List<CsvEntry> csvEntries, int delta) {
		for (CsvEntry entry : csvEntries) {
			DateTime entryDate = new DateTime(entry.getDay()).minusHours(2);
			int localDelta = Math.abs(Seconds.secondsBetween(gpxDate, entryDate).getSeconds());
			if (localDelta < delta) {
				return entry;
			}
		}
		return null;
	}
}
