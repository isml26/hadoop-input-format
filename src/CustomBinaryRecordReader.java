import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import java.io.*;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

public class CustomBinaryRecordReader extends RecordReader<Text, LongWritable> {

    private FileSplit fileSplit;
    private Configuration conf;
    private boolean processed = false;
    private Text key = new Text();
    private LongWritable value = new LongWritable();

    @Override
    public void initialize(InputSplit split, TaskAttemptContext context)
            throws IOException, InterruptedException {
        this.fileSplit = (FileSplit) split;
        this.conf = context.getConfiguration();
    }

    @Override
    public boolean nextKeyValue() throws IOException, InterruptedException {
        if (!processed) {
            Path file = fileSplit.getPath();
            FileSystem fs = file.getFileSystem(conf);
            try (FSDataInputStream in = fs.open(file)) {

                // Read 24 bytes at a time
                byte[] bytes = new byte[24];
                int bytesRead = in.read(bytes);
                while (bytesRead > 0) {

                    // Create a hexadecimal string from the bytes
                    String hexString = byteArrayToHexString(bytes);

                    // Set the key and value
                    key.set(hexString);
                    value.set(1L);

                    // Read the next 24 bytes
                    bytesRead = in.read(bytes);
                }
            }
            processed = true;
            return true;
        }
        return false;
    }

    // Helper method to convert a byte array to a hexadecimal string
    private String byteArrayToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X ", b));
        }
        return sb.toString().trim();
    }

    @Override
    public Text getCurrentKey() throws IOException, InterruptedException {
        return key;
    }

    @Override
    public LongWritable getCurrentValue() throws IOException, InterruptedException {
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