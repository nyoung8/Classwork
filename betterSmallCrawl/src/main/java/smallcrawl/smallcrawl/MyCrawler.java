package smallcrawl.smallcrawl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import smallcrawl.smallcrawl.StatCollector;

/**
 * Hello world!
 *
 */
public class MyCrawler extends WebCrawler {

    //private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg|png|mp3|mp4|zip|gz))$");
    
	private final static Pattern FILTERS = Pattern.compile("");
	StatCollector stats;
	List<String> stopwords;
	
	public MyCrawler(StatCollector stats) {
		this.stats=stats;
		
		//File file = new File("stopwords.txt");
	    try {
			this.stopwords = Files.readAllLines(Paths.get("stopwords.txt"));	
			for (String s : this.stopwords) {
		    	//System.out.println(s);
		    }
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
                && href.startsWith("http://djp3.westmont.edu/");
     }
     

     /**
      * This function is called when a page is fetched and ready
      * to be processed by your program.
      */
     @Override
     public void visit(Page page) {
         String url = page.getWebURL().getURL();
         //System.out.println("URL: " + url);

         if (page.getParseData() instanceof HtmlParseData) {
             HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
             String text = htmlParseData.getText();
             //String html = htmlParseData.getHtml();
             Set<WebURL> links = htmlParseData.getOutgoingUrls();
             //System.out.println("Text length: " + text.length());
             //System.out.println("Html length: " + html.length());
             //System.out.println("Number of outgoing links: " + links.size());
             this.stats.addOutLinks(links.size());
             this.stats.addUniqueLinks(url);
             
             if (text.contains("banksoopy brickle")) {
            	 this.stats.setBricklePage(url);
             }
             //text=text.replaceAll("[^a-zA-Z ]", "").toLowerCase();
             //ArrayList<String> allWords = Stream.of(text.toLowerCase().split(" ")).collect(Collectors.toCollection(ArrayList<String>::new));
             //*
             Pattern pattern = Pattern.compile("(\\p{IsAlphabetic}|['])+");
 		     Matcher matcher = pattern.matcher(text.toString().toLowerCase(Locale.US));
 		     ArrayList<String> allWords = new ArrayList<String>();
 		     String word;
 		     while (matcher.find()) {
 		    	 word = matcher.group();
 		    	 allWords.add(word);
 		     }
 		     //*/
             allWords.removeAll(this.stopwords);
             //allWords.removeAll(Collections.singleton(""));
             /*
             Integer del=0;
             
             for (String s : allWords) {
            	 
            	 if (!s.trim().contentEquals("")) {
            		 stats.addGram(s, 1);
            		 System.out.println("a word is being added: "+s);
            	 }
            	 else {
            		 allWords.remove(del);
            		 System.out.println("a word is being deleted: "+del);
            	 }
            	 del+=1;
             }
             */
             for (Integer i=0;i<allWords.size();i++) {
            	 String s=allWords.get(i);
            	 //if (!s.trim().contentEquals("")) {
            	 if (!s.isBlank() && !s.equals("'") && !s.contentEquals("'s")) {
            		 stats.addGram(s+"&&"+url, 1);
            		 //System.out.println("a word is being added: "+s);
            	 }
            	 else {
            		 allWords.remove(i);
            		 //System.out.println("a word is being deleted: "+i);
            	 }
             }
             //System.out.println(allWords);
             this.stats.checkLongestPage(allWords.size(), url);
             for (Integer i=0;i<allWords.size()-1;i++) {
            	 //System.out.println("words: "+allWords.get(i));
            	 if (!allWords.get(i).isBlank() && !allWords.get(i+1).isBlank()) {
            		 String twoGram = allWords.get(i)+" "+allWords.get(i+1);
                	 
                	 //System.out.println("two gram added: "+twoGram);
                	 stats.addGram(twoGram, 2);
            	 }
            	 
             }
             
             
             
             
             
             
            
             //System.out.println(twoGrams);
             //System.out.println("TWO GRAM SIZE "+twoGrams.size());
             
             
             
            /*
            Iterator iterator = oneGrams.entrySet().iterator();
 			int ii  = 0;
 			while (iterator.hasNext()) {
 					Map.Entry mapEntry = (Map.Entry) iterator.next();
 					if(ii < 12){
 						System.out.println("The word  " + mapEntry.getKey()
 						+ " : occurs " + mapEntry.getValue() + " times");
 						ii++;
 					}
 			}
 			*/
             //System.out.println(allWords);
             
             
         }
    }
}