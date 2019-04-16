package ca.uqac.lif.artichoke;

public class Utils {

    public static byte[] concatenateBytes(byte[] ... byteArrays) {
        int totalLength = 0;
        for(byte[] byteArray : byteArrays) {
            if(byteArray != null) {
                totalLength += byteArray.length;
            }
        }

        byte[] result = new byte[totalLength];
        int offset = 0;

        for(byte[] byteArray : byteArrays) {
            if(byteArray != null) {
                for(int i = 0; i < byteArray.length; i++) {
                    result[offset + i] = byteArray[i];
                }
                offset += byteArray.length;
            }
        }
        return result;
    }
}
