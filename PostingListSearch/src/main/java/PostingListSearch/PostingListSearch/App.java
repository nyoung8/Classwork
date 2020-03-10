package PostingListSearch.PostingListSearch;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.MatchResult;
import java.util.stream.Stream;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import org.iq80.leveldb.*;
import static org.fusesource.leveldbjni.JniDBFactory.*;
import java.io.*;

/**
 * For the calculation of tf-idf scores after a query has been submitted, a corpus of 624 is used with a df +1
 * For the calculation of a document's vector length, a corpus size of 623 is used with df size
 * TreeMap ordering?>
 */
class ValueComparator implements Comparator<String>{
	 
	HashMap<String, Double> map = new HashMap<String, Double>();
 
	public ValueComparator(HashMap<String, Double> map){
		this.map.putAll(map);
	}
 
	@Override
	public int compare(String s1, String s2) {
		if(map.get(s1) >= map.get(s2)){
			return -1;
		}else{
			return 1;
		}	
	}
}
public class App {
	
	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue( Map<K, V> map ) {

	    List<Map.Entry<K, V>> list =
	        new LinkedList<Map.Entry<K, V>>( map.entrySet() );
	    Collections.sort( list, new Comparator<Map.Entry<K, V>>()
	    {
	        public int compare( Map.Entry<K, V> o1, Map.Entry<K, V> o2 )
	        {
	            return (o2.getValue()).compareTo( o1.getValue() );
	        }
	    } );

	    Map<K, V> result = new LinkedHashMap<K, V>();
	    for (Map.Entry<K, V> entry : list)
	    {
	        result.put( entry.getKey(), entry.getValue() );
	    }
	    return result;
	}
	
	public static void clearDB(DB db) {
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
        System.out.println("databased cleared");
	}
	
	
    public static void main( String[] args ) throws IOException, ParseException {    	
    	boolean reset = true;
    	JSONParser jsonParser = new JSONParser();
    	//TreeMap<String, JSONArray> map = new TreeMap<String, JSONArray>();
	    HashSet<String> documents = new HashSet<String>();
	    
	    Options options = new Options();
    	options.createIfMissing(true);
    	File accumFile = new File("accum");
    	
    	DB accumulatorDB = factory.open(accumFile, options);
    	DB postingListDB = factory.open(new File("pList"), options);
    	DB vectorLengths = factory.open(new File("vectorLengths"), options);
    	if (reset) {
    		clearDB(vectorLengths);
        	clearDB(accumulatorDB);
        	clearDB(postingListDB);
    	}
    	if (reset) {
	    	try {
		    	System.out.println("loading in data...");
		    	
		    	File inputFile = new File("small_sample.txt");
		    	//File inputFile = new File("posting_list.txt");
	
		    	BufferedReader reader = new BufferedReader(new FileReader(inputFile));
		    	String currentLine;
		    	Long progress = 0L;
		    	while((currentLine = reader.readLine()) != null) {
		    		progress+=1L;
		    		if (progress%60000==0) {
		    			System.out.println(progress+" terms read");
		    		}
		    		try {
			            //Read JSON file
						String[] entries = currentLine.split("\t");
			            Object obj = jsonParser.parse(entries[1]);
			            JSONArray termArray = (JSONArray) obj;
			            
			            String url;
			            //Long df = (Long) termArray.get(0);
			            //Long tf = (Long) termArray.get(1);
			            for (int i=2;i<termArray.size();i++) {
			    			JSONArray urlArray = (JSONArray) termArray.get(i);
			    			url = (String) urlArray.get(0);
			    			documents.add(url);
			    		}
			            //System.out.println();
			            //map.put(entries[0], termArray);
			    		//accumulatorDB.put(bytes(entries[0]), bytes(termArray.toString()));
			            postingListDB.put(bytes(entries[0]), bytes(termArray.toString()));
			        } catch (ParseException e) {
			            e.printStackTrace();
			        }	    	    
		    	}
		    	System.out.println("corpus size " +(documents.size()));
		    	//reader.reset();
		    	reader.close(); 
		    	//
		    	//DBIterator iterator = accumulatorDB.iterator();
		    	DBIterator iterator = postingListDB.iterator();
		    	Long count2=-1L;
		        try {
		        	iterator.seekToFirst();
		          while( iterator.hasNext()) {
		        	count2++;
		            String key = asString(iterator.peekNext().getKey());
		            if (count2%60000==0) {
		            	System.out.println(count2+" iterated over.");
		            }
		            byte[] recieved = iterator.peekNext().getValue();
		            
		            
		            String value = asString(recieved);
		            
		            Object obj = jsonParser.parse(value);
		            JSONArray info = (JSONArray) obj;
		            Long df = (Long) info.get(0);
		    		Long tf;
		            String url;
		            for (int i=2;i<info.size();i++) {
		    			JSONArray urlArray = (JSONArray) info.get(i);
		    			
		    			url = (String) urlArray.get(0);
		    			/*
		    			if (url.equals("http://djp3.westmont.edu/gutenberg/stacks/gutenberg/2/0/0/1/20011/20011-h/20011-h.htm")) {
		    			//if (key.equals("horchata")) {	
		    				System.out.println(urlArray);
		    				System.out.println(url);
		    				tf = (Long) urlArray.get(1);
		    				System.out.println("tf: "+tf);
		    				System.out.println("df: "+df);
		    				System.out.println("doc size "+documents.size());
		    				System.out.println("tf thingy: "+(1+Math.log10(tf.doubleValue())));
		    				System.out.println("idf: "+(Math.log10((documents.size())/(Double.valueOf(df)))));
		    				
			    			Double tfidf = (1+Math.log10(tf.doubleValue()))*(Math.log10((documents.size())/(Double.valueOf(df))));
		    				System.out.println("tf idf: "+tfidf);
		    			}
		    			//*/
		    			tf = (Long) urlArray.get(1);
		    			//Double tfidf = (1+Math.log10(tf))*(Math.log10((documents.size()+1)/(df+1)));
		    			Double tfidf = (1+Math.log10(tf))*(Math.log10(documents.size()/Double.valueOf(df)));
		    			if (vectorLengths.get(bytes(url))==null) {
		    				/*
		    				if (url.equals("http://djp3.westmont.edu/gutenberg/stacks/gutenberg/2/0/0/1/20011/20011-h/20011-h.htm")) {
			    				System.out.println("first time: "+Math.pow(tfidf, 2));
		    				}
		    				//*/
		    				vectorLengths.put(bytes(url), bytes(Double.toString(Math.pow(tfidf, 2))));
		    			}
		    			else {
		    				Double previous = Double.parseDouble(asString(vectorLengths.get(bytes(url))));
		    				String newTfidf = Double.toString(Math.pow(tfidf, 2)+previous);
		    				/*
		    				if (url.equals("http://djp3.westmont.edu/gutenberg/stacks/gutenberg/2/0/0/1/20011/20011-h/20011-h.htm")) {
			    				System.out.println("previous: "+previous);
		    					System.out.println("total: "+newTfidf);
		    				}
		    				//*/
		    				vectorLengths.put(bytes(url), bytes(newTfidf));
		    			}
		    		}
		        	iterator.next();
		          }
		        } finally {
			        try {
			        	iterator.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
		        }
		    	
		    	
		    	
		    	/*
		    	System.out.println("maybe:");
		    	Stream<MatchResult> found = input.findAll("built");
		    	
		    	//System.out.println(found.count());
		    	found.forEach(s -> System.out.println(s.group()));
		    	//found.forEach(System.out::println);
		    	System.out.println();
		    	*/
				
			} catch (FileNotFoundException e) {
				System.out.println("File not found, make sure the file is in the correct location");
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} 
    	}
    	
	    try(Scanner in = new Scanner(System.in)){
	    	Boolean done = false;
	    	while(!done) {
	    		//System.out.println("size: "+documents.size());
	    		System.out.println("Enter search query or 'quit' to exit");
	    		//String searchTerm = in.next();
		    	String line = in.nextLine();
		    	
		    	if (line.equals("quit")) {
		    		done=true;
		    		System.out.println("program quiting...");
		    	}
		    	else {
		    		HashMap<String, Integer> query = new HashMap<String, Integer>();
		    		String[] words = line.split(" ");
		    		for (String s : words) {
		    			if (query.containsKey(s)) {
		    				query.put(s, query.get(s)+1);
		    			}
		    			else {
		    				query.put(s, 1);
		    			}
		    		}
		    		HashMap<String, Double> results = new HashMap<String, Double>();
		    		
		    		for (String s : query.keySet()) {
		    			String value = asString(postingListDB.get(bytes(s)));
		    			if (value!=null) {
		    				JSONArray facts = (JSONArray) jsonParser.parse(value);
			    			
			    			//System.out.println(s);
			    			
			    			//System.out.print("JSON Array: ");
			    			//System.out.println(facts);
			    			//System.out.print("Parsed Info: ");
				    		Long df = (Long) facts.get(0);
				    		Double qtfidf = (1+Math.log10(query.get(s)))*(Math.log10((documents.size()+1)/(Double.valueOf(df)+1)));
				    		//System.out.println("querty tf idf: "+qtfidf);
				    		
				    		//System.out.print("\tDocument Frequency: "+df);
				    		Long tf = (Long) facts.get(1);
				    		//System.out.print("\tTerm Frequency: "+tf);
				    		//System.out.println("\tURL's and counts:");
				    		String url;
				    		Long count;
				    		for (int i=2;i<facts.size();i++) {
				    			JSONArray urlArray = (JSONArray) facts.get(i);
				    			url = (String) urlArray.get(0);
				    			//System.out.print("\t\t"+url);
				    			count = (Long) urlArray.get(1);
				    			//System.out.println("\twith count: "+count);
				    			Double vLength = Math.sqrt(Double.valueOf(asString(vectorLengths.get(bytes(url)))));
				    			//System.out.println("\tvector length: "+vLength);
				    			Double dVtfidf = (1+Math.log10(count))*(Math.log10((documents.size()+1)/(Double.valueOf(df)+1)));
				    			//System.out.println("docu tfidf "+dVtfidf);
				    			Double cosSim = (dVtfidf*qtfidf/vLength);
				    			//System.out.println("final cosine similarity: "+cosSim);
				    			//results.put(url, cosSim);
				    			
				    			if (accumulatorDB.get(bytes(url))==null) {
				    				/*
				    				if (url.equals("http://djp3.westmont.edu/gutenberg/stacks/gutenberg/2/0/0/1/20011/20011-h/20011-h.htm")) {
					    				System.out.println("first time "+cosSim);
					    			}
					    			*/
				    				accumulatorDB.put(bytes(url), bytes(Double.toString(cosSim)));
				    			}
				    			else {
				    				/*
				    				if (url.equals("http://djp3.westmont.edu/gutenberg/stacks/gutenberg/2/0/0/1/20011/20011-h/20011-h.htm")) {
					    				System.out.println("second time "+cosSim);
					    			}
					    			*/
				    				accumulatorDB.put(bytes(url), bytes(Double.toString(cosSim+Double.valueOf(asString(accumulatorDB.get(bytes(url)))))));
				    			}
				    			
				    		}
				    		DBIterator iterator = accumulatorDB.iterator();
				            try {
				              for(iterator.seekToFirst(); iterator.hasNext(); iterator.next()) {
				                String key = asString(iterator.peekNext().getKey());
				    			/*
				                if (key.equals("http://djp3.westmont.edu/gutenberg/stacks/gutenberg/2/0/0/1/20011/20011-h/20011-h.htm")) {
				    				System.out.println("accum value "+Double.valueOf(asString(accumulatorDB.get(bytes(key)))));
				    			}
				    			*/

				            	  results.put(key, Double.valueOf(asString(accumulatorDB.get(bytes(key)))));
				              }
				            } finally {
				    	        try {
				    	        	iterator.close();
				    			} catch (IOException e) {
				    				e.printStackTrace();
				    			}
				            }
				            
				    		//System.out.println();
				    		TreeMap<String, Double> ordered = new TreeMap<String, Double>(new ValueComparator(results));
				    		ordered.putAll(results);
				    		//System.out.println(ordered);
				    		System.out.println("Top Ten Results: ");
				    		/*
				    		TreeMap<String, Double> myNewMap = ordered.entrySet().stream()
				        		    .limit(10)
				        		    .collect(TreeMap::new, (m, e) -> m.put(e.getKey(), e.getValue()), Map::putAll);
				        	
				    		for (String out : myNewMap.keySet()) {
				    			System.out.println(out+": "+myNewMap.get(out));
				    		}
				    		
				    		//*/
				    		/*
				    		Integer i = 0;
				    		for (String out : ordered.keySet()) {
				    			System.out.println(out);
				    			System.out.println(ordered.get(out));
				    			if (i>=10) {
				    				break;
				    			}
				    			else {
				    				System.out.println(out+": "+ordered.get(out));
					    			i++;
				    			}
				    		}//*/
				    		Map<String, Double> treeMap = ordered;
			    			Map<String, Double> sortedByValue = sortByValue(treeMap);

			    			int i = 0;
			    			for (Map.Entry<String, Double> entry : sortedByValue.entrySet()) {
			    			    if (i < 10) {
			    			        System.out.println(entry.getKey() + " \tScore: " + entry.getValue());
			    			        i++;
			    			    }
			    			    else {
			    			        // no need to keep looping once after we have printed the top 5 entries
			    			        break;
			    			    }
			    			}
		    			}
		    			else {
		    				System.out.println("Word "+s+" does not exist in the posting list");
		    			}
		    			
			    		
		    		}
		    		clearDB(accumulatorDB);
		    	}
	    	}
	    } finally {
	    	System.out.println("Running closing procedures...");
	    	/*
	    	if (reset) {
	    		clearDB(vectorLengths);
	        	clearDB(accumulatorDB);
	        	clearDB(postingListDB);
	    	}
	    	*/
			accumulatorDB.close();
			postingListDB.close();
			vectorLengths.close();
			
		}
    }
}


/*
 * //LOOK here pls
	        for (String term : map.keySet()) {
	        	
	    		JSONArray info = map.get(term);
	    		Long df = (Long) info.get(0);
	    		Long tf;
	            String url;
	            for (int i=2;i<info.size();i++) {
	    			JSONArray urlArray = (JSONArray) info.get(i);
	    			url = (String) urlArray.get(0);
	    			
	    			if (url.equals("http://djp3.westmont.edu/gutenberg/stacks/gutenberg/2/0/0/1/20011/20011-h/20011-h.htm")) {
	    				System.out.println(url);
	    				tf = (Long) urlArray.get(1);
	    				System.out.println("tf: "+tf);
	    				System.out.println("idf: "+(Math.log10((documents.size()+1)/(df+1))));
		    			Double tfidf = (1+Math.log10(tf))*(Math.log10((documents.size()+1)/(df+1)));
	    				System.out.println("tf idf: "+tfidf);
	    			}
	    			
	    			tf = (Long) urlArray.get(1);
	    			Double tfidf = (1+Math.log10(tf))*(Math.log10((documents.size()+1)/(df+1)));
	    			if (vectorLengths.get(bytes(url))==null) {
	    				vectorLengths.put(bytes(url), bytes(Double.toString(Math.pow(tfidf, 2))));
	    			}
	    			else {
	    				Double previous = Double.parseDouble(asString(vectorLengths.get(bytes(url))));
	    				String newTfidf = Double.toString(Math.pow(tfidf, 2)+previous);
	    				
	    				if (url.equals("http://djp3.westmont.edu/gutenberg/stacks/gutenberg/2/0/0/1/20011/20011-h/20011-h.htm")) {
	    					System.out.println("previous: "+previous);
		    				System.out.println(newTfidf);
	    				}
	    				
	    				vectorLengths.put(bytes(url), bytes(newTfidf));
	    			}
	    		}
	            
	    	}
	    	*/
