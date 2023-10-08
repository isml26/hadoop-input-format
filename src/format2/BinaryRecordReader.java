package format2;

import java.io.DataInputStream;
import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

public class BinaryRecordReader extends RecordReader<LongWritable, BytesWritable> {

    private long start;
    private long pos;
    private long end;
    private DataInputStream dis;
    private LongWritable key;
    private BytesWritable value;

    @Override
    public void initialize(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
        FileSplit fileSplit = (FileSplit) split;
        Configuration conf = context.getConfiguration();
        Path path = fileSplit.getPath();
        start = fileSplit.getStart();
        end = start + fileSplit.getLength();
        pos = start;

        dis = new DataInputStream(path.getFileSystem(conf).open(path));
        dis.skip(start);

        key = new LongWritable();
        value = new BytesWritable();
    }

    @Override
    public boolean nextKeyValue() throws IOException, InterruptedException {
        if (pos >= end) {
            return false;
        }

        key.set(pos);

        byte[] buffer = new byte[16];
        int bytesRead = dis.read(buffer);
        if (bytesRead == -1) {
            return false;
        }

        value.set(buffer, 0, bytesRead);
        pos += bytesRead;

        return true;
    }

    @Override
    public LongWritable getCurrentKey() throws IOException, InterruptedException {
        return key;
    }

    @Override
    public BytesWritable getCurrentValue() throws IOException, InterruptedException {
        return value;
    }

    @Override
    public float getProgress() throws IOException, InterruptedException {
        if (start == end) {
            return 0.0f;
        } else {
            return Math.min(1.0f, (pos - start) / (float) (end - start));
        }
    }

    @Override
    public void close() throws IOException {
        dis.close();
    }
}
