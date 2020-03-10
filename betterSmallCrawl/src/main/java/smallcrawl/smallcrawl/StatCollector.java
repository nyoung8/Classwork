package smallcrawl.smallcrawl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

import org.json.simple.parser.JSONParser;

public class StatCollector {
	
	Integer outLinks=0;
	Integer visitedLinks=0;
	Integer longestPageLength=0;
	String longestPage=null;
	ArrayList<String> brickleURLs = new ArrayList<String>();
	TreeMap<String, Integer> oneGrams = new TreeMap<String, Integer>();
	TreeMap<String, Integer> twoGrams = new TreeMap<String, Integer>();
	Integer uniquePages=0;
	//JSONParser jsonParser = new JSONParser();
	
	
	public StatCollector() {
		System.out.println("StatCollector created!");
		
	}
	
	public void addOutLinks(Integer number) {
		this.outLinks+=number;
	}
	public Integer getOutLinks() {
		return this.outLinks;
	}
	public void checkLongestPage(Integer length, String url) {
		if (length>this.longestPageLength) {
			this.longestPage=url;
			this.longestPageLength=length;
		}
	}
	public Integer getLongPageLength() {
		return this.longestPageLength;
	}
	public String getLongestPage() {
		return this.longestPage;
	}
	public void setBricklePage(String url) {
		this.brickleURLs.add(url);
	}
	public String getBricklePage() {
		String output="";
		for (String s : this.brickleURLs) {
			output+=s+"\n";
		}
		return output;
	}
	public void addGram(String word, Integer gram) {
		TreeMap<String, Integer> map;
		if (gram==2) {
			map = this.twoGrams;
		}
		else {
			map = this.oneGrams;
		}
		
		
		Integer count = map.get(word);
		if (count==null) {
			count=0;
		}
		map.put(word, count+1);
		//System.out.println("word put into map with count "+(count+1)+" "+word);
		//System.out.println("whats actually in the map? "+map.get(word));
	
	}
	public TreeMap<String, Integer> getOneGrams(){
		//System.out.println("size of 1grams: "+this.oneGrams.size());
		//TreeMap<String, Integer> sorted = new TreeMap<String, Integer>(new ValueComparator(this.oneGrams));
		//sorted.putAll(this.oneGrams);
		//System.out.println("size of sorted: "+sorted.size());
		return this.oneGrams;
	}
	public TreeMap<String, Integer> getTwoGrams(){
		return this.twoGrams;
	}
	public TreeMap<String, Integer> sortMap(TreeMap<String, Integer> unsorted) {
		TreeMap<String,Integer> sorted = new TreeMap<String, Integer>(new ValueComparator(unsorted));
		return sorted;
	}
	public void addUniqueLinks(String url) {
		this.uniquePages+=1;
	}
	public Integer getUniqueLinks() {
		return this.uniquePages;
	}
	
	
	class ValueComparator implements Comparator {
		 
		Map map;
	 
		public ValueComparator(Map map) {
			this.map = map;
		}
	 
		public int compare(Object keyA, Object keyB) {
			Comparable valueA = (Comparable) map.get(keyA);
			Comparable valueB = (Comparable) map.get(keyB);
			return valueB.compareTo(valueA);
		}
	}
	
}
