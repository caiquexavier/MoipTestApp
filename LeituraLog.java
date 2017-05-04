import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.Set;
import java.lang.*;

public class LeituraLog
{
  private static final String filePath = "./log.txt";

  public static void main(String args[]) {
    System.out.println("Starting Moip Dev Test Application...\n");
    try {
      // Init Lists
      List<String> urlList = new ArrayList<String>();
      List<String> responseStatusList = new ArrayList<String>();

      // Reading log file
      FileReader file = new FileReader(filePath);
      BufferedReader buffer = new BufferedReader(file);

      // Parses the log String
      String logEntry;
      while ((logEntry = buffer.readLine()) != null) {

        StringTokenizer st = new StringTokenizer(logEntry, " ");
        while (st.hasMoreElements()) {

          String property = st.nextElement().toString();
          //Add urls from request_to property in list
          if (property.contains("request_to")){
            String[] requestUrl = property.split("=");
            urlList.add(requestUrl[1]);
          }
          //Add urls from request_to property in list
          if (property.contains("response_status")){
            String[] responseStatus = property.split("=");
            responseStatusList.add(responseStatus[1]);
          }
        }
      }
      System.out.println("\nThe most 3 requested Urls:");
      bestRequests(urlList);
      System.out.println("\nWebhooks by response status:");
      webhooksByStatus(responseStatusList);

    } catch(Exception e){
      e.printStackTrace();
    } finally {
      System.out.println("\nEnd Application");
    }
  }
  // Count Frequency, Order Urls and show the best 3 results
  public static void bestRequests(List<String> urlList){

    try{
      //Counting Frequency
      Set<String> uniqueSet = new HashSet<String>(urlList);
      Map<Integer, String> unsortedRequests = new HashMap<Integer, String>();
      for (String url : uniqueSet) {
        unsortedRequests.put(Collections.frequency(urlList, url), url);
      }
      // Sorting
      TreeMap<Integer, String> sortedRequests = new TreeMap<Integer, String>(unsortedRequests);
      Map<Integer, String> descendingRequests = sortedRequests.descendingMap();
      // Show Results
      Integer interaction = 0;
      for (Map.Entry<Integer, String> entry : descendingRequests.entrySet())
      {
        if (interaction <= 2){
          System.out.println(entry.getValue() + " - " +  entry.getKey());
        }
        interaction++;
      }
    } catch (Exception e){
      throw e;
    }
  }

  // Count Frequency, Order and show the response status results
  public static void webhooksByStatus(List<String> responseStatusList){

    try{
      //Counting Frequency
      Set<String> uniqueSet = new HashSet<String>(responseStatusList);
      Map<Integer, String> unsortedResponseStatus = new HashMap<Integer, String>();
      for (String responseStatus : uniqueSet) {
        unsortedResponseStatus.put(Collections.frequency(responseStatusList, responseStatus), responseStatus);
      }
      // Sorting
      TreeMap<Integer, String> sortedResponseStatus = new TreeMap<Integer, String>(unsortedResponseStatus);
      Map<Integer, String> descendingResponseStatus = sortedResponseStatus.descendingMap();

      for (Map.Entry<Integer, String> entry : descendingResponseStatus.entrySet())
      {
          System.out.println(entry.getValue() + " - " +  entry.getKey());
      }

    } catch (Exception e){
      throw e;
    }
  }

}
