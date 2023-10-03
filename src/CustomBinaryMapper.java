
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;

import java.io.IOException;

public class CustomBinaryMapper extends Mapper<Text, LongWritable, Text, LongWritable> {
    @Override
    protected void map(Text key, LongWritable value, Context context)
            throws IOException, InterruptedException {
        // Map function receives file name as key and log entry count as value
        // You can further process the data as needed
        context.write(key, value);
    }
}
