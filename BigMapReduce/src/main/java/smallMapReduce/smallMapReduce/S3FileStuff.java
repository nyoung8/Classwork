package smallMapReduce.smallMapReduce;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;

public class S3FileStuff {

	private static final AmazonS3 amazonS3Client = AmazonS3ClientBuilder.standard().build();

	/**
	 * This method will retrieve the lines of text from a file hosted amazon S3 as a list.
	 * @param bucketName, for example: edu.westmont.cs128.fall2019.map
	 * @param fileName, for example: wet.1.paths
	 * @return a list created from the lines in the file.
	 */
	public static List<String> loadFileFromS3(String bucketName, String fileName) {
	    try (final S3Object s3Object = amazonS3Client.getObject(bucketName, fileName)){
	    	final InputStreamReader streamReader = new InputStreamReader(s3Object.getObjectContent(), StandardCharsets.UTF_8);
	    	final BufferedReader reader = new BufferedReader(streamReader);
	    	return reader.lines().collect(Collectors.toList());
	    } catch (final IOException e) {
	        return Collections.emptyList();
	    }
	}
	
	/**
	 * This method takes a list of file names, possibly with duplicates, and returns a set of filenames created from the
	 * list.  It will prepend the prefix to each filename it adds to the set.  The set that is returned is guaranteed
	 * to have no more than max elements in it.  Those elements will be the first unique filenames found in the incoming
	 * list of filenames.
	 * @param _fileNames
	 * @param prefix
	 * @param max
	 * @return set of filenames with prefixes prepended and no more than max in total
	 */
	public static Set<String> curateFileNames(List<String> _fileNames, String prefix, Integer max) {
		//Convert to Set to remove duplicates
		System.out.println("curate files");
		LinkedList<String> fileNames = new LinkedList<String>(_fileNames);
		Set<String> lines = new HashSet<String>();
		while((lines.size() < max) && (fileNames.size() > 0)){
			lines.add(prefix+fileNames.poll());
		}
		return lines;
	}
}
