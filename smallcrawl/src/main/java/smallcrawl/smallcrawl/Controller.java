package smallcrawl.smallcrawl;

import java.util.TreeMap;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import smallcrawl.smallcrawl.StatCollector;

//https://www.baeldung.com/java-string-remove-stopwords
//https://stackoverflow.com/questions/18830813/how-can-i-remove-punctuation-from-input-text-in-java
//https://github.com/yasserg/crawler4j/blob/master/crawler4j-examples/crawler4j-examples-base/src/test/java/edu/uci/ics/crawler4j/examples/basic/BasicCrawler.java
public class Controller {
    public static void main(String[] args) throws Exception {
        String crawlStorageFolder = "/data/crawl/root";
        int numberOfCrawlers = 14;

        CrawlConfig config = new CrawlConfig();
        //config.setMaxDepthOfCrawling(0);
        config.setCrawlStorageFolder(crawlStorageFolder);
        config.setPolitenessDelay(100);
        config.setUserAgentString("Westmont IR Nathan Young: Team Extra Credit?");
        // Instantiate the controller for this crawl.
        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

        // For each crawl, you need to add some seed urls. These are the first
        // URLs that are fetched and then the crawler starts following links
        // which are found in these pages
        controller.addSeed("http://djp3.westmont.edu/classes/2019_08_CS128/Bible/bible.html");
    	StatCollector stats = new StatCollector();
    	// The factory which creates instances of crawlers.
        CrawlController.WebCrawlerFactory<MyCrawler> factory = () -> new MyCrawler(stats);
        
        // Start the crawl. This is a blocking operation, meaning that your code
        // will reach the line after this only when crawling is finished.
        long startTime=System.nanoTime();
        controller.start(factory, numberOfCrawlers);
        long endTime=System.nanoTime();
        
        
        System.out.println("crawling finished");
        
        TreeMap<String, Integer> oneGrams = stats.getOneGrams();
        //System.out.println("ONEGRAM SIZE: "+oneGrams.size());
        
        TreeMap<String, Integer> popular = (TreeMap<String, Integer>) oneGrams.clone();
        for (Integer i=0;i<25;i++) {
       	 Integer frequency=0;
       	 String word="";
       	 for (String s : popular.keySet()) {
           	 if (popular.get(s)>frequency){
           		 frequency=popular.get(s);
           		 word=s;
           	 }
            }
       	 System.out.println("the "+i+"th most common word is: "+word);
            System.out.println("it shows up "+oneGrams.get(word)+" times");
            popular.remove(word);
        }
        
        TreeMap<String, Integer> twoGrams = stats.getTwoGrams();
        TreeMap<String, Integer> popular2 = (TreeMap<String, Integer>) twoGrams.clone();
   	 	for (Integer i=0;i<25;i++) {
       	 Integer frequency2=0;
       	 String word2="";
       	 for (String s : popular2.keySet()) {
           	 if (popular2.get(s)>frequency2){
           		 frequency2=popular2.get(s);
           		 word2=s;
           	 }
            }
       	 System.out.println("the "+i+"th most common word is: "+word2);
            System.out.println("it shows up "+twoGrams.get(word2)+" times");
            popular2.remove(word2);
   	 }
    
        
        System.out.println("Crawling took "+(endTime-startTime)/1_000_000_000.0+" seconds");        
        System.out.println("Banksoopy Brickle is on "+stats.getBricklePage());
        System.out.println("Number of outlinks: "+stats.getOutLinks());
        System.out.println("Longest page: "+stats.getLongestPage()+" with length "+stats.getLongPageLength());
        System.out.println("Unique Links: "+stats.getUniqueLinks());
        
        
        
        
    }
}