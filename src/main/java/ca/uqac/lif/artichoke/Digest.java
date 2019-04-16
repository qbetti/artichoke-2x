package ca.uqac.lif.artichoke;

import java.util.Base64;

public class Digest {

    private byte[] bytes;

    public Digest() {

    }

    public Digest(byte[] bytes) {
        this.bytes = bytes;
    }

    public String encode() {
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static Digest decode(String encodedDigest) {
        Digest digest = new Digest();
        digest.bytes = Base64.getDecoder().decode(encodedDigest);
        return digest;
    }

    @Override
    public String toString() {
        return new String(bytes);
    }

    public byte[] getBytes() {
        return bytes;
    }
}
