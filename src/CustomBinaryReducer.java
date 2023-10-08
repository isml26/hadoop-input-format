import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import java.io.IOException;

public class CustomBinaryReducer extends Reducer<Text, LongWritable, Text, LongWritable> {

    @Override
    protected void reduce(Text key, Iterable<LongWritable> values, Context context)
            throws IOException, InterruptedException {
        // The key is the hexadecimal string of the 24 bytes read in the record reader
        // The values are all 1L

        // Simply count the number of values
        long count = 0L;
        for (LongWritable value : values) {
            count += value.get();
        }

        // Write the key-value pair to the context
        context.write(key, new LongWritable(count));
    }
}