package ca.uqac.lif.artichoke;

import ca.uqac.lif.artichoke.encoding.Base64Encoder;
import ca.uqac.lif.artichoke.encoding.StringEncoder;

public class PeerAction {

    private static final StringEncoder encoder = Base64Encoder.getInstance();
    private static final String SEP = "-";

    private EncryptedAction encryptedAction;
    private Peer peer;
    private Group group;
    private Digest digest;

    public PeerAction() {

    }

    public PeerAction(EncryptedAction encryptedAction, Peer peer, Group group, Digest digest) {
        this.encryptedAction = encryptedAction;
        this.peer = peer;
        this.group = group;
        this.digest = digest;
    }


    public String encode() {
        String toEncode = encryptedAction.encode() + SEP + peer.encode() + SEP + group.encode() + SEP + digest.encode();
        return encoder.encodeToString(toEncode);
    }


    public static PeerAction decode(String encodedPeerAction) {
        String decodedPeerAction = encoder.decodeToString(encodedPeerAction);

        String[] items = decodedPeerAction.split(SEP);
        if(items.length != 4) {
            return null;
        }

        PeerAction peerAction = new PeerAction();
        peerAction.encryptedAction = EncryptedAction.decode(items[0]);
        peerAction.peer = Peer.decode(items[1]);
        peerAction.group = Group.decode(items[2]);
        peerAction.digest = Digest.decode(items[3]);

        return peerAction;
    }


    @Override
    public String toString() {
        return encryptedAction.toString() + "," + peer.toString() + "," + group.toString() + "," + digest.encode();
    }

    public Digest getDigest() {
        return digest;
    }

    public EncryptedAction getEncryptedAction() {
        return encryptedAction;
    }

    public Group getGroup() {
        return group;
    }

    public Peer getPeer() {
        return peer;
    }
}
