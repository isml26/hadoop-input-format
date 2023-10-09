package format4;

import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.*;
import org.apache.hadoop.mapreduce.lib.output.*;

public class CustomMapReduceJob {
    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.println("Usage: CustomMapReduceJob <inputPath> <outputPath>");
            System.exit(1);
        }

        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "Custom Input Format Example");

        // Set the custom input format class
        job.setInputFormatClass(CustomInputFormat.class);
        // Set the main class
        job.setJarByClass(CustomMapReduceJob.class);
        // Set the Mapper class
        job.setMapperClass(MyMapper.class);

        // Set the output key and value classes
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(CustomWritable.class);

        // Set the input and output paths
        FileInputFormat.addInputPath(job, new Path(args[0])); // Input path from command-line argument
        FileOutputFormat.setOutputPath(job, new Path(args[1])); // Output path from command-line argument

        // Run the job and exit with a success code (0) or error code (1)
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
