package format4;


import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;


public class CustomInputFormat extends FileInputFormat<LongWritable, CustomWritable> {
    @Override
    public RecordReader<LongWritable, CustomWritable> createRecordReader(InputSplit split, TaskAttemptContext context) {
        return new CustomRecordReader();
    }
}
