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

    private static KeyRing keyRing;


    @BeforeClass
    public static void init() {
        Security.addProvider(new BouncyCastleProvider());
        keyRing = KeyRing.generateNew("root", true);
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
        History test = History.decode(encodedHistory);
        assertEquals(h.toString(), test.toString());
    }

    @Test
    public void testComputeUnsignedDigest() throws PrivateKeyDecryptionException, BadPassphraseException {
        History h = new History();
        EncryptedAction encAction = new EncryptedAction(new Action("field0", "ah", "data"));
        Group group0 = new Group("group0");
        Peer peer = new Peer("moi", keyRing.getHexPublicKey());
        Digest digest = h.computeDigest(encAction, group0, keyRing);

        PeerAction pa = new PeerAction(encAction, peer, group0, digest);
        h.addPeerAction(pa);

        System.out.println(h.toString());

        assertTrue(h.validatePeerAction(pa, null));
    }
}