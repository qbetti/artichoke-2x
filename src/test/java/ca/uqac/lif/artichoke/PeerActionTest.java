package ca.uqac.lif.artichoke;

import org.junit.Test;

import static org.junit.Assert.*;

public class PeerActionTest {

    @Test
    public void encode() {
        Action action = new Action("field0", "write", "hello");
        EncryptedAction encryptedAction = new EncryptedAction(action);
        Peer peer = new Peer("moi", "0x00");
        Group group = new Group("my-group");
        Digest digest = new Digest("0x42424242".getBytes());

        PeerAction peerAction = new PeerAction(encryptedAction, peer, group, digest);
        System.out.println(peerAction);

        String encPeerAction = peerAction.encode();
        System.out.println(encPeerAction);

        PeerAction test = PeerAction.decode(encPeerAction);
        System.out.println(test);


    }

    @Test
    public void decode() {
    }
}