package format5;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;

//Mapper class
public class BinaryMapper extends Mapper<Text, IntWritable, Text, IntWritable> {
	private Map<String, Integer> countMap = new HashMap<>();
	private final IntWritable one = new IntWritable(1);

	@Override
	protected void map(Text key, IntWritable value, Context context) throws IOException, InterruptedException {

		// Perform any value-related operations (e.g., counting) here.
		// In this example, we're just emitting the key with a value of 1.
		context.write(key, one);

//		String hexString = key.toString();
//
//		if (countMap.containsKey(hexString)) {
//			int count = countMap.get(hexString) + 1;
//			countMap.put(hexString, count);
//		} else {
//			countMap.put(hexString, 1);
//		}
	}

	/*
	 * 
	 * The cleanup method is called after all the records have been processed, and
	 * it allows you to emit the final results.
	 */
	protected void cleanup(Context context) throws IOException, InterruptedException {
		for (Map.Entry<String, Integer> entry : countMap.entrySet()) {
			Text outputKey = new Text(entry.getKey());
			IntWritable outputValue = new IntWritable(entry.getValue());
			context.write(outputKey, outputValue);
		}
	}
}
