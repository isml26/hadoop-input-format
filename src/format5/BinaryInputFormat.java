package format5;

import org.apache.hadoop.fs.BlockLocation;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BinaryInputFormat extends FileInputFormat<Text, IntWritable> {
	private static final long SPLIT_SIZE = 1*1024 * 512; // 1MB

	@Override
	public RecordReader<Text, IntWritable> createRecordReader(InputSplit split, TaskAttemptContext context) {
		return new BinaryRecordReader();
	}

	@Override
	protected boolean isSplitable(JobContext context, Path file) {
		// Return true to indicate that the input can be split into multiple splits
		return false;
	}

//	@Override
//	public List<InputSplit> getSplits(JobContext context) throws IOException {
//		List<InputSplit> splits = new ArrayList<>();
//		Path inputFile = FileInputFormat.getInputPaths(context)[0];
//		FileSystem fs = inputFile.getFileSystem(context.getConfiguration());
//		long fileSize = fs.getFileLinkStatus(inputFile).getLen();
//
//		long start = 0;
//		while (start < fileSize) {
//			long end = Math.min(start + SPLIT_SIZE, fileSize);
//			InputSplit split = new FileSplit(inputFile, start, end, null);
//			splits.add(split);
//			start = end;
//		}
//		return splits;
//	}
}
