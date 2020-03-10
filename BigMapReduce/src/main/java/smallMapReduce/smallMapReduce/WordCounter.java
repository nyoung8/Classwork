package smallMapReduce.smallMapReduce;

/*
 * Nathan Young
 * Big Map / Reduce problem
 */


import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
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
import org.archive.io.ArchiveReader;
import org.archive.io.ArchiveRecord;
import org.apache.commons.io.IOUtils;


public class WordCounter {
	
	//mapper class for hadoop to use
	//public static class TokenizeMapper extends Mapper<Object, Text, Text, IntWritable>{
	public static class TokenizeMapper extends Mapper<Text, ArchiveReader, Text, IntWritable>{
		public void map(Text key, ArchiveReader value, Context context) throws IOException, InterruptedException {
			for (ArchiveRecord r : value) {
			    try {
			        if (r.getHeader().getMimetype().equals("text/plain")) {
			            // Convenience function that reads the full message into a raw byte array
			            byte[] rawData = IOUtils.toByteArray(r, r.available());
			            String content = new String(rawData);
			            System.out.println(content.length());
			            //Process the content
			            
			            
			            
			            //uses pattern recognition to divide content into individual words including apostrophes
						Pattern pattern = Pattern.compile("\\p{IsAlphabetic}+'?\\p{IsAlphabetic}+|\\p{IsAlphabetic}");
					    Matcher matcher = pattern.matcher(content.toLowerCase(Locale.US));
					    
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
			    catch (Exception ex) {
			    	System.err.println("Exception caught:" + ex.toString());
			    }
			    
			}

		}//*/
	}
	 
	
	//reducer class to find the total count of each word in the text
	public static class SumReducer extends Reducer<Text, IntWritable, Text, IntWritable>{
		public void reduce(Text term, Iterable<IntWritable> ones, Context context) throws IOException, InterruptedException {
			int count = 0;
			Iterator<IntWritable> iterator = ones.iterator();
			Integer value = 0;
			while (iterator.hasNext()) {
				value = iterator.next().get();
				count = count+value;
				
			}
			IntWritable output = new IntWritable(count);
			context.write(term, output);
		}
	}
	
    public static void main( String[] args ) throws IOException, ClassNotFoundException, InterruptedException {
    	//retrieve the arguments and verify both input and output were given
    	Configuration conf = new Configuration();
    	String [] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();

       	//*
       	List<String> paths = S3FileStuff.loadFileFromS3(otherArgs[2], otherArgs[0]);
       	Set<String> fullPaths = S3FileStuff.curateFileNames(paths, "s3://commoncrawl/", 1000);
       	//*/
       	
       	//create a job with the proper configuration
    	Job job = Job.getInstance(conf, "Word Count");
    	job.setJarByClass(WordCounter.class);
    	job.setMapperClass(TokenizeMapper.class);
    	job.setReducerClass(SumReducer.class);
    	job.setCombinerClass(SumReducer.class);
    	job.setNumReduceTasks(1);
    	job.setInputFormatClass(PattersonFileInputFormat.class);
    	job.setOutputKeyClass(Text.class);
    	job.setOutputValueClass(IntWritable.class);
    	
    	//*
    	for (String link : fullPaths) {
       		FileInputFormat.addInputPath(job, new Path(link));
       		System.out.println("link: "+link);
       	}//*/
  
    	
    	
    	//FileInputFormat.addInputPath(job, new Path("CC-MAIN-20190915052433-20190915074433-00000.warc.wet.gz"));
    	
    	
    	FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
    	
    	//FileOutputFormat.setOutputPath(job, new Path("ouput"));
    	//exit with information about job success
    	boolean status = job.waitForCompletion(true);
    	if (status) {
    		System.out.println("done");
			System.exit(0);
    	}
    	else {
    		System.exit(1);
    		System.out.println("error");
    	}    	
    }
}
