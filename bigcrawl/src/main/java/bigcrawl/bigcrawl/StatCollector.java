package bigcrawl.bigcrawl;

import java.util.ArrayList;

import org.iq80.leveldb.*;
import static org.fusesource.leveldbjni.JniDBFactory.*;
import java.io.*;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

public class StatCollector {
	
	String longestPage=null;
	ArrayList<String> brickleURLs = new ArrayList<String>();
	Long uniquePages=0L;
	DB tupleDB;
	DB wordDB;
	Long uniqueWords=0L;
	Long foolishness=0L;
	Long deity=0L;
	Long assassination=0L;
	
	public StatCollector(DB db1, DB db2) {
		System.out.println("StatCollector created!");
		this.tupleDB=db1;
		this.wordDB=db2;
	}
	public void incFoolishness() {
		this.foolishness+=1L;
	}
	public void incDeity() {
		this.deity+=1L;
	}
	public void incAssassination() {
		this.assassination+=1L;
	}
	public ArrayList<Long> getWordCounts(){
		ArrayList<Long> words=new ArrayList<Long>();
		words.add(this.foolishness);
		words.add(this.deity);
		words.add(this.assassination);
		return words;
	}
	
	public void addTuple(String word, String url, Integer count) {
		this.tupleDB.put(bytes(word+" "+url), bytes(Integer.toString(count)));
		if (this.wordDB.get(bytes(word))==null) {
			uniqueWords++;
		}	
		this.wordDB.put(bytes(word), bytes(Integer.toString(1)));
	}
	public Long getUniqueWords() {
		return this.uniqueWords;
	}
	public void clearDB(DB db) {
		//goes through the given levelDB and removes all keys and values
		DBIterator iterator = db.iterator();
        try {
          for(iterator.seekToFirst(); iterator.hasNext(); iterator.next()) {
            String key = asString(iterator.peekNext().getKey());
        	  db.delete(bytes(key));
          }
        } finally {
	        try {
	        	iterator.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
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
	public void addUniqueLinks(String url) {
		this.uniquePages+=1L;
	}
	public Long getUniqueLinks() {
		return this.uniquePages;
	}
	
}
