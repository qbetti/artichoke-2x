package ca.uqac.lif.artichoke;

import ca.uqac.lif.artichoke.keyring.KeyRing;
import ca.uqac.lif.artichoke.keyring.exceptions.BadPassphraseException;
import ca.uqac.lif.artichoke.keyring.exceptions.PrivateKeyDecryptionException;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.BeforeClass;
import org.junit.Test;

import java.security.Security;

import static org.junit.Assert.*;

public class HistoryTest {

    @BeforeClass
    public static void init() {
        Security.addProvider(new BouncyCastleProvider());
    }

    @Test
    public void encode() {
        History h = new History();
        PeerAction pa0 = new PeerAction(
                new EncryptedAction(new Action("field0", "ah", "data")),
                new Peer("Moi", "0x000000"),
                new Group("group0"),
                new Digest("allo".getBytes()));

        PeerAction pa1 = new PeerAction(
                new EncryptedAction(new Action("heinfield", "bon", "otherdata")),
                new Peer("You", "0x0000150"),
                new Group("group2"),
                new Digest("hey".getBytes()));

        h.addPeerAction(pa0);
        h.addPeerAction(pa1);

        String encodedHistory = h.encode();
        System.out.println(encodedHistory);
        History test = History.decode(encodedHistory);

        System.out.println(h);
        System.out.println(test);
        assertEquals(h.toString(), test.toString());
    }

    @Test
    public void testComputeUnsignedDigest() throws PrivateKeyDecryptionException, BadPassphraseException {
        History h = new History();
        KeyRing keyRing = KeyRing.generateNew("root", true);

        EncryptedAction encAction = new EncryptedAction(new Action("field0", "ah", "data"));
        Peer peer = new Peer("moi", keyRing.getHexPublicKey());
        Group group0 = new Group("group0");
        Digest digest = h.computeDigest(encAction, group0, keyRing);

        PeerAction pa = new PeerAction(encAction, peer, group0, digest);
        h.addPeerAction(pa);

        System.out.println(h.validatePeerAction(pa, null));


        System.out.println(h.toString());
    }
}