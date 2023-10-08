package info;

public class Header {
    private byte[] pd_lsn;
    private byte[] pd_checksum;
    private byte[] pd_flags;
    private byte[] pd_lower;
    private byte[] pd_upper;
    private byte[] pd_special;
    private byte[] pd_pagesize_version;
    private byte[] pd_prune_xid;

    public Header(byte[] byteArray) {
        if (byteArray.length != 24) {
            throw new IllegalArgumentException("Byte array must be 24 bytes long.");
        }

        // Divide the 24-byte array into individual fields
        int offset = 0;
        pd_lsn = new byte[8];
        System.arraycopy(byteArray, offset, pd_lsn, 0, 8);
        offset += 8;

        pd_checksum = new byte[2];
        System.arraycopy(byteArray, offset, pd_checksum, 0, 2);
        offset += 2;

        pd_flags = new byte[2];
        System.arraycopy(byteArray, offset, pd_flags, 0, 2);
        offset += 2;

        pd_lower = new byte[2];
        System.arraycopy(byteArray, offset, pd_lower, 0, 2);
        offset += 2;

        pd_upper = new byte[2];
        System.arraycopy(byteArray, offset, pd_upper, 0, 2);
        offset += 2;

        pd_special = new byte[2];
        System.arraycopy(byteArray, offset, pd_special, 0, 2);
        offset += 2;

        pd_pagesize_version = new byte[2];
        System.arraycopy(byteArray, offset, pd_pagesize_version, 0, 2);
        offset += 2;

        pd_prune_xid = new byte[4];
        System.arraycopy(byteArray, offset, pd_prune_xid, 0, 4);
    }


    // Helper method to convert a byte array to a hexadecimal string
    private String byteArrayToString(byte[] byteArray) {
        StringBuilder sb = new StringBuilder();
        for (byte b : byteArray) {
            sb.append(String.format("%02X ", b));
        }
        return sb.toString().trim();
    }

    @Override
    public String toString() {
        return "pd_lsn: " + byteArrayToString(pd_lsn) +
               "\npd_checksum: " + byteArrayToString(pd_checksum) +
               "\npd_flags: " + byteArrayToString(pd_flags) +
               "\npd_lower: " + byteArrayToString(pd_lower) +
               "\npd_upper: " + byteArrayToString(pd_upper) +
               "\npd_special: " + byteArrayToString(pd_special) +
               "\npd_pagesize_version: " + byteArrayToString(pd_pagesize_version) +
               "\npd_prune_xid: " + byteArrayToString(pd_prune_xid);
    }
}
