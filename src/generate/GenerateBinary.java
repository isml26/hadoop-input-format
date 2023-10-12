package generate;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Random;

public class GenerateBinary {

	 public static void main(String[] args) {
	        String filename = "random2.bin";
//	        long fileSize = 2L * 1024 * 1024 * 1024; // 2GB
	        long fileSize = 200L * 1024 * 1024; // 200MB
	        Random random = new Random();

	        try (FileOutputStream fos = new FileOutputStream(filename)) {
	            int headerSize = 24; // Size of a PageHeaderData structure
	            byte[] headerBytes = new byte[headerSize];
	            ByteBuffer buffer = ByteBuffer.wrap(headerBytes);

	            while (fos.getChannel().size() < fileSize) {
	                // Generate random PageHeaderData
	                long pd_lsn = random.nextLong(); // Random long
	                short pd_checksum = (short) random.nextInt(); // Random short
	                short pd_flags = (short) random.nextInt(); // Random short
	                short pd_lower = (short) random.nextInt(); // Random short
	                short pd_upper = (short) random.nextInt(); // Random short
	                short pd_special = (short) random.nextInt(); // Random short
	                short pd_pagesize_version = (short) random.nextInt(); // Random short
	                int pd_prune_xid = random.nextInt(); // Random integer

	                // Write the PageHeaderData to the file
	                buffer.clear();
	                buffer.putLong(pd_lsn);
	                buffer.putShort(pd_checksum);
	                buffer.putShort(pd_flags);
	                buffer.putShort(pd_lower);
	                buffer.putShort(pd_upper);
	                buffer.putShort(pd_special);
	                buffer.putShort(pd_pagesize_version);
	                buffer.putInt(pd_prune_xid);
	                fos.write(headerBytes);
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	        System.out.println("Random page header binary file created: " + filename);
	    }

}
