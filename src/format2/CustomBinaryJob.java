package format2;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class CustomBinaryJob {

    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            System.err.println("Usage: CustomBinaryJob <inputPath> <outputPath>");
            System.exit(1);
        }

        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "CustomBinaryProcessing");

        job.setInputFormatClass(CustomBinaryInputFormat.class);
        job.setJarByClass(CustomBinaryJob.class);
        job.setMapperClass(CustomBinaryMapper.class);
        job.setReducerClass(CustomBinaryReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        FileInputFormat.addInputPath(job, new Path(args[0])); // Input path from command line argument
        FileOutputFormat.setOutputPath(job, new Path(args[1])); // Output path from command line argument

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}