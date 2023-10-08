import java.io.IOException;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;

public class CustomBinaryMapper extends Mapper<Text, LongWritable, Text, LongWritable> {

    @Override
    protected void map(Text key, LongWritable value, Context context)
            throws IOException, InterruptedException {
        // The key is the hexadecimal string of the 24 bytes read in the record reader
        // The value is always 1L

        // You can further process the data as needed
        // In this example, we simply pass through the data
        context.write(key, value);
    }
}