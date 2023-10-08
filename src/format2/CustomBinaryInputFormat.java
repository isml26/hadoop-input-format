package format2;

import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.LongWritable;

public class CustomBinaryInputFormat extends FileInputFormat<LongWritable, BytesWritable> {

    @Override
    public RecordReader<LongWritable, BytesWritable> createRecordReader(InputSplit split, TaskAttemptContext context)
            throws IOException, InterruptedException {
        return new BinaryRecordReader();
    }

    @Override
    protected boolean isSplitable(JobContext context, Path filename) {
        // Set isSplitable to false to prevent Hadoop from splitting the binary file
        return false;
    }
}
