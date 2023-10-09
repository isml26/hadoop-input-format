package format3;

import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.*;
import org.apache.hadoop.mapreduce.lib.output.*;

import format2.CustomBinaryJob;

import java.io.*;

import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.*;
import org.apache.hadoop.mapreduce.lib.output.*;

import java.io.*;
import java.util.Arrays;

public class CustomMapReduceJob {

    public static class CustomInputFormat extends FileInputFormat<LongWritable, Text> {
        @Override
        public RecordReader<LongWritable, Text> createRecordReader(InputSplit split, TaskAttemptContext context) {
            return new CustomRecordReader();
        }
    }

    public static class CustomRecordReader extends RecordReader<LongWritable, Text> {
        private LongWritable key = new LongWritable();
        private Text value = new Text();
        private boolean reachedEnd = false;
        private FSDataInputStream fsin;
        private long bytesRead = 0;

        @Override
        public void initialize(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
            FileSplit fileSplit = (FileSplit) split;
            key.set(fileSplit.getStart()); // Set key to the start position of the split
            Configuration conf = context.getConfiguration();
            Path file = fileSplit.getPath();
            FileSystem fs = file.getFileSystem(conf);
            fsin = fs.open(file);
            fsin.seek(fileSplit.getStart());
        }

        @Override
        public boolean nextKeyValue() throws IOException, InterruptedException {
            if (!reachedEnd) {
                byte[] buffer = new byte[10];
                int bytesRead = fsin.read(buffer, 0, 10);
                if (bytesRead > 0) {
                    String hexString = bytesToHexString(buffer);
                    value.set(hexString);
                    key.set(key.get() + bytesRead); // Update the key to the new position
                    return true;
                } else {
                    reachedEnd = true;
                    fsin.close();
                }
            }
            return !reachedEnd;
        }

        @Override
        public LongWritable getCurrentKey() throws IOException, InterruptedException {
            return key;
        }

        @Override
        public Text getCurrentValue() throws IOException, InterruptedException {
            return value;
        }

        @Override
        public float getProgress() throws IOException, InterruptedException {
            return reachedEnd ? 1.0f : 0.0f;
        }

        @Override
        public void close() throws IOException {
            if (fsin != null) {
                fsin.close();
            }
        }

        private String bytesToHexString(byte[] bytes) {
            StringBuilder hexString = new StringBuilder();
            for (byte b : bytes) {
                hexString.append(String.format("%02X", b));
            }
            return hexString.toString();
        }
    }

    public static class MyMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
        private final static IntWritable one = new IntWritable(1);

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            // Process the 10-byte chunk in 'value' (hexadecimal string) and emit results
            // Your processing logic here
            context.write(value, one);
        }
    }

    public static class MyReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
        @Override
        protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            int sum = 0;
            for (IntWritable value : values) {
                sum += value.get();
            }
            context.write(key, new IntWritable(sum));
        }
    }


    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "Custom Input Format Example");

        job.setInputFormatClass(CustomInputFormat.class);
        job.setJarByClass(CustomMapReduceJob.class);
        job.setMapperClass(MyMapper.class);
        job.setReducerClass(MyReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        FileInputFormat.addInputPath(job, new Path(args[0])); // Input path from command line argument
        FileOutputFormat.setOutputPath(job, new Path(args[1])); // Output path from command line argument

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
