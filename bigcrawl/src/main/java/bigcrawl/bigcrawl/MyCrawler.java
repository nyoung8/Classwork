package bigcrawl.bigcrawl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import bigcrawl.bigcrawl.StatCollector;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

/**
 * Hello world!
 *
 */
public class MyCrawler extends WebCrawler {

    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|bmp|gif|jpg|jpeg|png|tif|tiff|mid|mp2|mp3|mp4|wav|avi|mov|mpeg|ram|m4v|pdf|rm|smil|wmv|swf|wma|zip|rar|gz))$");
    
	StatCollector stats;
	List<String> stopwords;
	
	public MyCrawler(StatCollector stats) {
		this.stats=stats;
		
		//read in all stopwords
			
	    try {
	    	this.stopwords = Files.readAllLines(Paths.get("stopwords.txt"));
			
		} catch (IOException e) {
			System.out.println("File not found, make sure the file is in the correct location");
			e.printStackTrace();
		}
	    
	    
	}

    /**
     * This method receives two parameters. The first parameter is the page
     * in which we have discovered this new url and the second parameter is
     * the new url. You should implement this function to specify whether
     * the given url should be crawled or not (based on your crawling logic).
     * In this example, we are instructing the crawler to ignore urls that
     * have css, js, git, ... extensions and to only accept urls that start
     * with "https://www.ics.uci.edu/". In this case, we didn't need the
     * referringPage parameter to make the decision.
     */
     @Override
     public boolean shouldVisit(Page referringPage, WebURL url) {
         String href = url.getURL().toLowerCase();
         return !FILTERS.matcher(href).matches()
                && href.startsWith("http://djp3.westmont.edu/gutenberg/");
     }
     

     /**
      * This function is called when a page is fetched and ready
      * to be processed by your program.
      */
     @Override
     public void visit(Page page) {
         String url = page.getWebURL().getURL();
         System.out.println("URL: " + url);

         if (page.getParseData() instanceof HtmlParseData) {
             HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
             String text = htmlParseData.getText();
             
             //add current url to lis of all unique links
             this.stats.addUniqueLinks(url);
             //System.out.println("url: "+url);
             
             //if the link has banksoopy brickle, add it to a set
             if (text.toLowerCase().contains("banksoopy brickle")) 
             {
            	 this.stats.setBricklePage(url);
             }
             
             //remove all non english alphabet characters (including punctuation)
             text=text.replaceAll("[^a-zA-Z ]", "").toLowerCase();
             ArrayList<String> allWords = Stream.of(text.toLowerCase().split(" ")).collect(Collectors.toCollection(ArrayList<String>::new));
             
             //remove all the stopwords and empty entries
             allWords.removeAll(this.stopwords);
             allWords.removeAll(Collections.singleton(""));
             
             //collect all 1grams on the page
             TreeMap<String, Integer> oneGrams = new TreeMap<String, Integer>();
             for (Integer i=0;i<allWords.size();i++) {
            	 String word=allWords.get(i);
            	 if (!word.isBlank()) {
            		 Integer count = oneGrams.get(word);
            			if (count==null) {
            				count=0;
            			}
            		oneGrams.put(word, count+1);
            	 }
             }
             
             //add each unique word as a tuple, and check for words that we are searching for
             for (String word : oneGrams.keySet()) {
            	 stats.addTuple(word, url, oneGrams.get(word));
            	 if (word.equals("foolishness")) {
            		 stats.incFoolishness();
            	 }
            	 if (word.equals("deity")) {
            		 stats.incDeity();
            	 }
            	 if (word.equals("assassination")) {
            		 stats.incAssassination();
            	 }
             }             
         }
         /*
         if (this.stats.getUniqueLinks()%10000==0) {
        	File tupleFile = new File("tupleDB");
 	        System.out.println("Banksoopy Brickle is on "+stats.getBricklePage());
 	        System.out.println("Number of pages with the following words:");
 	        ArrayList<Integer> words=stats.getWordCounts();
 	        System.out.println("\tfoolishness: "+words.get(0));
 	        System.out.println("\tdeity: "+words.get(1));
 	        System.out.println("\tassassination: "+words.get(2));
 	        System.out.println("Unique Links: "+stats.getUniqueLinks());
 	        System.out.println("Number of unique words found: "+stats.getUniqueWords());
 	        System.out.println("Space taken up by tuples: "+tupleFile.length()+" bytes");
 	        PrintWriter writer;
 			try {
 				writer = new PrintWriter("CrawlStatsOutput.txt", "UTF-8");
 				writer.println("Banksoopy Brickle is on"+stats.getBricklePage());
 		        writer.println("Number of pages with the following words:");
 		        writer.println("\tfoolishness: "+words.get(0));
 		        writer.println("\tdeity: "+words.get(1));
 		        writer.println("\tassassination: "+words.get(2));
 		        writer.println("Unique Links: "+stats.getUniqueLinks());
 		        writer.println("Number of unique words found: "+stats.getUniqueWords());
 		        writer.println("Space taken up by tuples: "+tupleFile.length()+" bytes");
 		        writer.close();
 			} catch (FileNotFoundException e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			} catch (UnsupportedEncodingException e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			}
         }
         */
         
	        
         
    }
}