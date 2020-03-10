package postingList.postingList;

/*
 * Nathan Young
 * Small Map / Reduce problem
 * A word counter that counts the occurrences of all the words in Metamorphosis by Franz Kafka
 */

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
 
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class PostingList {
	
	//mapper class for hadoop to use
	public static class TokenizeMapper extends Mapper<Object, Text, Text, Text>{
		public HashMap<String, Long> testing = new HashMap<String, Long>();
		public void map(Object key, Text value, Context context) throws IOException, InterruptedException {			
			
			JSONParser jsonParser = new JSONParser();
	         
			String word="";
	        try
	        {
	            //Read JSON file
	            Object obj = jsonParser.parse(value.toString());
	 
	            JSONArray termArray = (JSONArray) obj;
	            word = (String) termArray.get(1);
	            
	          //uses pattern recognition to divide Metamorphosis into individual words including apostrophes
				//Pattern pattern = Pattern.compile("(\\p{IsAlphabetic}|['])+");
			    //Matcher matcher = pattern.matcher(word.toLowerCase(Locale.US));
			    //writes each word to a map
			    Text keyOut = new Text();
			    Text valueOut = new Text();
			    //frequency.
			    String url = (String) termArray.get(0);
			    Long tf = (Long) termArray.get(2);
			    /*
			    if (testing.put(word+url, (Long) termArray.get(2))!=null) {
			    	System.out.println("we have a duplicate! "+word+" in "+ url);
			    }
			    */
			    //while (matcher.find()) {
			    	//String nextWord = matcher.group();
			    keyOut.set(word);
			    valueOut.set(url);
			    for (Long i=0L;i<tf;i+=1L) {
			    	context.write(keyOut, valueOut);
			    }
			    //}
	        } catch (ParseException e) {
	            e.printStackTrace();
	        }
			
		}
	}
	
	//reducer class to find the total count of each word in the text
	public static class SumReducer extends Reducer<Text, Text, Text, Text>{
		public void reduce(Text term, Iterable<Text> urls, Context context) throws IOException, InterruptedException {
			Long tf = 0L;
			
			Iterator<Text> iterator = urls.iterator();
			TreeMap<String, Long> counter = new TreeMap<String,Long>();
			while (iterator.hasNext()) {
				//count+=iterator.next().get();
				String url = iterator.next().toString();
				tf+=1L;
				if (counter.containsKey(url)) {
					counter.put(url, counter.get(url)+1L);
				}
				else {
					counter.put(url, 1L);
				}
			}
			JSONArray joutput = new JSONArray();
			joutput.add(tf);
			joutput.add(counter.size());
			for (String key : counter.keySet()) {
				JSONArray entry = new JSONArray();
				entry.add(key);
				entry.add(counter.get(key));
				joutput.add(entry);
			}
			
			Text output = new Text();
			//output.set(tf.toString()+" "+counter.size()+" "+counter.toString());
			output.set(joutput.toString());
			//LongWritable output = new LongWritable(count);
			context.write(term, output);
		}
	}
	
    public static void main( String[] args ) throws IOException, ClassNotFoundException, InterruptedException {
    	//retrieve the arguments and verify both input and output were given
    	Configuration conf = new Configuration();
    	String [] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
       	if (otherArgs.length != 2) {
    		System.err.println("Not enough arguments. WordCount <input> <output>");
    		System.exit(2);
    	}
       	
       	//create a job with the proper configuration
    	Job job = Job.getInstance(conf, "Word Count");
    	job.setJarByClass(PostingList.class);
    	job.setMapperClass(TokenizeMapper.class);
    	job.setReducerClass(SumReducer.class);
    	job.setNumReduceTasks(1);
    	job.setOutputKeyClass(Text.class);
    	job.setOutputValueClass(Text.class);
    	FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
    	FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));

    	//exit with information about job success
    	boolean status = job.waitForCompletion(true);
    	if (status) {
			System.exit(0);
    	}
    	else {
    		System.exit(1);
    	}    	
    }
}
