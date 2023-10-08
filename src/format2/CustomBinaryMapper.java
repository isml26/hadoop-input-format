package format2;


import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.*;
import java.io.IOException;


public class CustomBinaryMapper extends Mapper<LongWritable, BytesWritable, Text, Text> {
    @Override
    protected void map(LongWritable key, BytesWritable value, Context context) throws IOException, InterruptedException {
        byte[] data = value.getBytes();

        // Extract the first 4 bytes and convert them to hex
        int hexValue = ((data[0] & 0xFF) << 24) |
                ((data[1] & 0xFF) << 16) |
                ((data[2] & 0xFF) << 8) |
                (data[3] & 0xFF);

        // Map the message based on the hex value
        String message;
        switch (hexValue) {
            case 0x12345678:
                message = "Message type A";
                break;
            case 0xabcdef01:
                message = "Message type B";
                break;
            default:
                message = "Unknown message type";
                break;
        }

        // Emit the message as the key and the data as the value
        context.write(new Text(message), new Text(value.toString()));
    }
}