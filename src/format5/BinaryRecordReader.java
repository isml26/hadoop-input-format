package format5;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import java.io.*;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

public class BinaryRecordReader extends RecordReader<Text, IntWritable> {

	private boolean processed = false;
	private byte[] buffer = new byte[10];
	private Text key;
	private IntWritable value;
	private FSDataInputStream in;
	int byte1 = 0, byte2 = 0;

	@Override
	public void initialize(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
		FileSplit fileSplit = (FileSplit) split;
		Configuration conf = context.getConfiguration();
		Path file = fileSplit.getPath();
		FileSystem fs = file.getFileSystem(conf);
		in = fs.open(file);
		in.seek(fileSplit.getStart());

		// Initialize the key and value variables
		key = new Text();
		value = new IntWritable(0);
	}

	@Override
	public boolean nextKeyValue() throws IOException, InterruptedException {
		// int bytesRead = in.read(buffer, 0, 10);
		// if (bytesRead == -1) {
		// return false; // No more data
		// }
		// key.set(toHexString(buffer));
		// value.set(value.get() + 1);
		// return true;

	    if (byte1 == -1 || byte2 == -1) {
	        // End of input, return false
	        return false;
	    }

	    while (byte1 != -1 && byte2 != -1) {
	        if (byte1 == 0x17 && byte2 == 0x0E) {
	            key.set("170E");
	            value.set(1);
	            byte1 = byte2;
	            byte2 = in.read(); // Read the next two bytes
	            return true;
	        }

	        byte1 = byte2;
	        byte2 = in.read(); // Read the next two bytes
	    }
	    return false; // No more matches found

	}

	private String toHexString(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		for (byte b : bytes) {
			sb.append(String.format("%02x", b));
		}
		return sb.toString();
	}

	@Override
	public Text getCurrentKey() throws IOException, InterruptedException {
		return key;
	}

	@Override
	public IntWritable getCurrentValue() throws IOException, InterruptedException {
		return value;
	}

	@Override
	public float getProgress() throws IOException, InterruptedException {
		return processed ? 1.0f : 0.0f;
	}

	@Override
	public void close() throws IOException {
		// Nothing to do here
	}
}