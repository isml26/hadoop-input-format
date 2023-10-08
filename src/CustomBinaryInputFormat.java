import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomBinaryInputFormat extends FileInputFormat<Text, LongWritable> {

    @Override
    protected boolean isSplitable(JobContext context, Path file) {
        // Ensure that binary files are not split into multiple parts
        return false;
    }

    @Override
    public List<InputSplit> getSplits(JobContext context) throws IOException {
        List<InputSplit> splits = new ArrayList<>();
        for (FileStatus status : listStatus(context)) {
            splits.add(new FileSplit(status.getPath(), 0, status.getLen(), null));
        }
        return splits;
    }

    @Override
    public RecordReader<Text, LongWritable> createRecordReader(
            InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
        return new CustomBinaryRecordReader();
    }
}
