package format4;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import java.io.IOException;

public class MyMapper extends Mapper<LongWritable, CustomWritable, Text, CustomWritable> {
    @Override
    protected void map(LongWritable key, CustomWritable value, Context context) throws IOException, InterruptedException {
        // Your processing logic here
        // In this example, you can count occurrences of the pattern and emit the data
        byte[] data = value.getData();

        // Check for the pattern (0x59, 0xA8)
        int counter = value.getCount();
        for (int i = 0; i < data.length - 1; i++) {
            if (data[i] == 0x59 && data[i + 1] == 0xA8) {
                counter++;
            }
        }

        context.write(new Text("Pattern Count"), new CustomWritable(counter, data));
    }
}
