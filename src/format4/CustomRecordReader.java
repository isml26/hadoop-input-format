package format4;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;

public class CustomRecordReader extends RecordReader<LongWritable, CustomWritable> {
    private LongWritable key = new LongWritable();
    private CustomWritable value = new CustomWritable();
    private FSDataInputStream fsin;
    private long bytesRead = 0;
    private long fileLength = 0;

    @Override
    public void initialize(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
        FileSplit fileSplit = (FileSplit) split;
        key.set(fileSplit.getStart());
        fileLength = fileSplit.getLength();
        Configuration conf = context.getConfiguration();
        Path file = fileSplit.getPath();
        FileSystem fs = file.getFileSystem(conf);
        fsin = fs.open(file);
        fsin.seek(fileSplit.getStart());
    }

    @Override
    public boolean nextKeyValue() throws IOException, InterruptedException {
        if (bytesRead < fileLength) {
            int byte1 = fsin.read();
            int byte2 = fsin.read();
            if (byte1 != -1 && byte2 != -1) {
                if (byte1 == 0x59 && byte2 == 0xA8) {
                    // If the pattern is found, read the next 14 bytes
                    byte[] buffer_data = new byte[14];
                    fsin.read(buffer_data);
                    value = new CustomWritable(1, buffer_data);
                } else {
                    bytesRead++;
                }
            }
        }
        return bytesRead < fileLength;
    }

    @Override
    public LongWritable getCurrentKey() throws IOException, InterruptedException {
        return key;
    }

    @Override
    public CustomWritable getCurrentValue() throws IOException, InterruptedException {
        return value;
    }

    @Override
    public float getProgress() throws IOException, InterruptedException {
        return bytesRead / (float) fileLength;
    }

    @Override
    public void close() throws IOException {
        if (fsin != null) {
            fsin.close();
        }
    }
}
