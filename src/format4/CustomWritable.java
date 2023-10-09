package format4;

import org.apache.hadoop.io.*;

import java.io.*;

public class CustomWritable implements Writable {
    private int count;
    private byte[] data;

    public CustomWritable() {
        this.count = 0;
        this.data = new byte[0];
    }

    public CustomWritable(int count, byte[] data) {
        this.count = count;
        this.data = data;
    }

    public int getCount() {
        return count;
    }

    public byte[] getData() {
        return data;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeInt(count);
        out.writeInt(data.length);
        out.write(data);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        count = in.readInt();
        int dataLength = in.readInt();
        data = new byte[dataLength];
        in.readFully(data);
    }
}
