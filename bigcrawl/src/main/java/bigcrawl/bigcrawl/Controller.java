package bigcrawl.bigcrawl;

import java.util.ArrayList;
import java.util.TreeMap;

import org.iq80.leveldb.*;
import static org.fusesource.leveldbjni.JniDBFactory.*;
import java.io.*;

import bigcrawl.bigcrawl.StatCollector;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;


//https://www.baeldung.com/java-string-remove-stopwords
//https://stackoverflow.com/questions/18830813/how-can-i-remove-punctuation-from-input-text-in-java
//https://github.com/yasserg/crawler4j/blob/master/crawler4j-examples/crawler4j-examples-base/src/test/java/edu/uci/ics/crawler4j/examples/basic/BasicCrawler.java
public class Controller {
    public static void main(String[] args) throws Exception {    	                                                              
        String crawlStorageFolder = "data/crawl/root";        
        
        int numberOfCrawlers = 69;
        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder(crawlStorageFolder);
        config.setPolitenessDelay(100);
        //config.setMaxPagesToFetch(10);
        config.setUserAgentString("Westmont IR Maya Rouillard & Nathan Young: Team Attempt 4");
        config.setMaxDownloadSize(Integer.MAX_VALUE);
        // Instantiate the controller for this crawl.
        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
        

        // For each crawl, you need to add some seed urls. These are the first
        // URLs that are fetched and then the crawler starts following links
        // which are found in these pages
        controller.addSeed("http://djp3.westmont.edu/gutenberg/index.php");
        Options options = new Options();
    	options.createIfMissing(true);
    	
    	//open up some levelDB entries to store tuples and unique words
    	File tupleFile = new File("tupleDB");
    	DB db1 = factory.open(tupleFile, options);
    	DB db2 = factory.open(new File("wordsDB"), options);
    	
    	StatCollector stats = new StatCollector(db1, db2);
    	
    	try {
	        CrawlController.WebCrawlerFactory<MyCrawler> factory = () -> new MyCrawler(stats);
	        //starts a timer to be recorded
	        long startTime=System.nanoTime();
	        controller.start(factory, numberOfCrawlers);
	        long endTime=System.nanoTime();	 
	        System.out.println("crawling finished");
	        
	        System.out.println("Crawling took "+(endTime-startTime)/1_000_000_000.0+" seconds");        
	        System.out.println("Banksoopy Brickle is on \n"+stats.getBricklePage());
	        System.out.println("Number of pages with the following words:");
	        ArrayList<Long> words=stats.getWordCounts();
	        System.out.println("\tfoolishness: "+words.get(0));
	        System.out.println("\tdeity: "+words.get(1));
	        System.out.println("\tassassination: "+words.get(2));
	        System.out.println("Unique Links: "+stats.getUniqueLinks());
	        System.out.println("Number of unique words found: "+stats.getUniqueWords());
	        System.out.println("Space taken up by tuples: "+tupleFile.length()+" bytes");
	        PrintWriter writer = new PrintWriter("CrawlStatsOutput.txt", "UTF-8");
	        writer.println("Crawling took "+(endTime-startTime)/1_000_000_000.0+" seconds");
	        writer.println("Banksoopy Brickle is on"+stats.getBricklePage());
	        writer.println("Number of pages with the following words:");
	        writer.println("\tfoolishness: "+words.get(0));
	        writer.println("\tdeity: "+words.get(1));
	        writer.println("\tassassination: "+words.get(2));
	        writer.println("Unique Links: "+stats.getUniqueLinks());
	        writer.println("Number of unique words found: "+stats.getUniqueWords());
	        writer.println("Space taken up by tuples: "+tupleFile.length()+" bytes");
	        writer.close();
	        stats.clearDB(db1);
	        stats.clearDB(db2);
	        
	        
    	} finally {
      	  db1.close();
      	  db2.close();
      	}
        
        
        
        
    }
}