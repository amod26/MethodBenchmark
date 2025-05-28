import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;

public class ExecutionTimer {
    private static class Timing {
        long startTime;
        long endTime;
        long duration;
        String methodName;
    }
    

	private static final String UNDERSCORE = "_";
	private static final String SPACE = " ";
	private static final String CSVEXTENSION = ".csv";
	private static final String CSV_SEPARATOR = ",";
	private static FileOutputStream fileOutputStream = null;
	private static BufferedWriter bw = null;
    private final List<Timing> timings = new ArrayList<>();
    private static final Map<String, Long> startTimes = new HashMap<>();
	private static String[] headers = { "Method/DAO","Start Time (mins)","End Time (mins)","Total Time (ms)","Total Time (mins)"};
	private static String fileOutputPathPrefix = "C:\\DEV\\";
    private static final String LOGICAL_FILENAME = "ExecTiming";
    private static final SimpleDateFormat sdfyyyyMMdd_hhmmss = new SimpleDateFormat(
			"yyyyMMdd hhmmss");
	
    public static void start(String methodName) {
        startTimes.put(methodName, System.currentTimeMillis());
    }
    
    public void end(String methodName) {
        Long start = startTimes.remove(methodName);
        if (start != null) {
            long end = System.currentTimeMillis();
            Timing t = new Timing();
            t.methodName = methodName;
            t.startTime = start;
            t.endTime = end;
            t.duration = end - start;
            timings.add(t);
        }
    }

    public void writeToExcel() throws Exception {

    	try {
    		getCsvFileHandles();
        	if(timings != null) {
            	bw = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
        		createCsvHeader(headers);
        		
        		for (Timing t : timings) {
        			StringBuffer strCsvData = new StringBuffer();
                    // Method/DAO Name
        			strCsvData.append(t.methodName);
    				strCsvData.append(CSV_SEPARATOR);
        			
        			// START TIME(mins)
    				strCsvData.append(new java.sql.Timestamp(t.startTime));
    				strCsvData.append(CSV_SEPARATOR);
    				
        			// END TIME(mins)
    				strCsvData.append(new java.sql.Timestamp(t.endTime));
    				strCsvData.append(CSV_SEPARATOR);
    				
        			// TOTAL DURATION(ms)
    				strCsvData.append(t.duration);
    				strCsvData.append(CSV_SEPARATOR);
    				
    				// TOTAL DURATION(mins)
    				strCsvData.append(msToMinutesTwoDecimals(t.duration));
    				strCsvData.append(CSV_SEPARATOR);
    				bw.write(strCsvData.toString());
                    bw.newLine();	
                }
        		bw.flush();
    	        bw.close();
        	}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e){
	    	 e.printStackTrace();
	    } catch (IOException e){
	    	 e.printStackTrace();
	    }
    }
    
    public static void createCsvHeader(String[] data) throws Exception {
		StringBuffer strCsvHeader = new StringBuffer();
		for (int i = 0; i < data.length; i++) {
			strCsvHeader.append(data[i]);
			if(i != data.length -1){
			 strCsvHeader.append(CSV_SEPARATOR);
			}
		}
		bw.write(strCsvHeader.toString());
        bw.newLine();
	}
    
    
    private static void getCsvFileHandles() throws Exception {
	    	GregorianCalender calendar = new GregorianCalender();
	    	Timestamp currTime = new Timestamp(calendar.getTime().getTime());
		String dateStr = getDate(currTime, "yyyyMMdd hhmmss");
		String fileName = "";
		fileName = fileOutputPathPrefix + LOGICAL_FILENAME + UNDERSCORE + dateStr + CSVEXTENSION;
				
		fileOutputStream = new FileOutputStream(fileName);
	}
    
    public static double msToMinutesTwoDecimals(long ms) {
        double minutes = ms / 60000.0;
        return Math.round(minutes * 100.0) / 100.0;
    }
    
    public static String getDate(Timestamp date, String format) {
		String strDate = null;
		if (format.equalsIgnoreCase("yyyyMMdd hhmmss")) {
			strDate = sdfyyyyMMdd_hhmmss.format(date);
		}
		return strDate;
	}
}
