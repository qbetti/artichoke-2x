package ca.uqac.lif.artichoke;

import ca.uqac.lif.artichoke.encoding.Base64Encoder;
import ca.uqac.lif.artichoke.encoding.StringEncoder;

public class Peer {

    private static final StringEncoder encoder = Base64Encoder.getInstance();
    private static final String SEP = ",";

    private String id;
    private String hexPublicKey;

    public Peer() {

    }

    public Peer(String id, String hexPublicKey) {
        this.id = id;
        this.hexPublicKey = hexPublicKey;
    }

    public String encode() {
        String toEncode = id + SEP + hexPublicKey;
        return encoder.encodeToString(toEncode);
    }

    public static Peer decode(String encodedPeer) {
        String decodedPeer = encoder.decodeToString(encodedPeer);
        String[] items = decodedPeer.split(SEP);

        if(items.length != 2)
            return null;

        Peer peer = new Peer();
        peer.id = items[0];
        peer.hexPublicKey = items[1];
        return peer;
    }

    @Override
    public String toString() {
        return "{" + id + "," + hexPublicKey + "}";
    }

    public String getHexPublicKey() {
        return hexPublicKey;
    }
}
