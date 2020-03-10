package smallMapReduce.smallMapReduce;

/*
 * Nathan Young
 * Small Map / Reduce problem
 * A word counter that counts the occurrences of all the words in Metamorphosis by Franz Kafka
 */

import java.io.IOException;
import java.util.Iterator;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;



public class WordCounter {
	
	//mapper class for hadoop to use
	public static class TokenizeMapper extends Mapper<Object, Text, Text, IntWritable>{
		public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
			System.out.println("mapper");
			System.out.println("value: "+value.toString());
			//uses pattern recognition to divide Metamorphosis into individual words including apostrophes
			Pattern pattern = Pattern.compile("(\\p{IsAlphabetic}|['])+");
		    Matcher matcher = pattern.matcher(value.toString().toLowerCase(Locale.US));
		    
		    //writes each word to a map
		    Text wordOut = new Text();
		    IntWritable one = new IntWritable(1);
		    while (matcher.find()) {
		    	String nextWord = matcher.group();
		    	wordOut.set(nextWord);
		    	context.write(wordOut, one);
		    }
		}
	}
	
	//reducer class to find the total count of each word in the text
	public static class SumReducer extends Reducer<Text, IntWritable, Text, IntWritable>{
		public void reduce(Text term, Iterable<IntWritable> ones, Context context) throws IOException, InterruptedException {
			int count = 0;
			Iterator<IntWritable> iterator = ones.iterator();
			while (iterator.hasNext()) {
				count++;
				iterator.next();
			}
			IntWritable output = new IntWritable(count);
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
    	job.setJarByClass(WordCounter.class);
    	job.setMapperClass(TokenizeMapper.class);
    	job.setReducerClass(SumReducer.class);
    	job.setNumReduceTasks(1);
    	job.setOutputKeyClass(Text.class);
    	job.setOutputValueClass(IntWritable.class);
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
