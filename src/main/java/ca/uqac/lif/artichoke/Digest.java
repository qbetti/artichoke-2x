package ca.uqac.lif.artichoke;

import ca.uqac.lif.artichoke.encoding.Base64Encoder;
import ca.uqac.lif.artichoke.encoding.StringEncoder;

public class Digest {

    private final static StringEncoder encoder = Base64Encoder.getInstance();

    private byte[] bytes;

    public Digest() {

    }

    public Digest(byte[] bytes) {
        this.bytes = bytes;
    }

    public String encode() {
        return encoder.encodeToString(bytes);
    }

    public static Digest decode(String encodedDigest) {
        return new Digest(encoder.decode(encodedDigest));
    }

    @Override
    public String toString() {
        return new String(bytes);
    }

    public byte[] getBytes() {
        return bytes;
    }
}
