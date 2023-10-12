package format5;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import java.io.IOException;

//Reducer class
public class BinaryReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
	private final IntWritable result = new IntWritable();

	protected void reduce(Text key, Iterable<IntWritable> values, Context context)
			throws IOException, InterruptedException {
		int sum = 0;
		for (IntWritable value : values) {
			sum += value.get();
		}
		result.set(sum);
		context.write(key, result);
	}
}