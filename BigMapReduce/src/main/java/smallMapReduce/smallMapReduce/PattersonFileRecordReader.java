package smallMapReduce.smallMapReduce;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.archive.io.ArchiveReader;
import org.archive.io.warc.WARCReaderFactory;

/**
 * The Patterson File Record Reader processes a single compressed input.
 * The Record Reader returns a single ArchiveReader that can contain
 * numerous individual documents, each document handled in a single mapper.
 *
 * @auther djp3, based on https://github.com/commoncrawl/cc-warc-examples/blob/master/src/org/commoncrawl/warc/WARCFileRecordReader.java
 * @author Stephen Merity (Smerity)
 */
public class PattersonFileRecordReader extends RecordReader<Text, ArchiveReader> {
	private String archivePath;
	private ArchiveReader archiveReader;
	private FSDataInputStream fsin;
	private boolean hasBeenRead = false;

	@Override
	public void initialize(InputSplit inputSplit, TaskAttemptContext context) throws IOException, InterruptedException {
		FileSplit split = (FileSplit) inputSplit;
		Configuration conf = context.getConfiguration();
		Path path = split.getPath();
		FileSystem fs = path.getFileSystem(conf);
		fsin = fs.open(path);
		archivePath = path.getName();
		archiveReader = WARCReaderFactory.get(path.getName(), fsin, true);
	}

	@Override
	public void close() throws IOException {
		fsin.close();
		archiveReader.close();
	}

	@Override
	public Text getCurrentKey() throws IOException, InterruptedException {
		// Provide the path used for the compressed file as the key
		return new Text(archivePath);
	}

	@Override
	public ArchiveReader getCurrentValue() throws IOException, InterruptedException {
		// We only ever have one value to give -- the output of the compressed file
		return archiveReader;
	}

	@Override
	public float getProgress() throws IOException, InterruptedException {
		// Progress of reader through the data as a float
		// As each file only produces one ArchiveReader, this will be one immediately
		return hasBeenRead ? 1 : 0;
	}

	@Override
	public boolean nextKeyValue() throws IOException, InterruptedException {
		// As each file only produces one ArchiveReader, if it has been read, there are no more
		if (hasBeenRead) {
			return false;
		}
		hasBeenRead = true;
		return true;
	}
}
