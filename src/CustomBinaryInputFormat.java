import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;

import java.io.IOException;

public class CustomBinaryInputFormat extends FileInputFormat<Text, LongWritable> {

    @Override
    protected boolean isSplitable(JobContext context, Path file) {
        // Ensure that binary files are not split into multiple parts
        return false;
    }

    @Override
    public RecordReader<Text, LongWritable> createRecordReader(
            InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
        return new CustomBinaryRecordReader();
    }
}
