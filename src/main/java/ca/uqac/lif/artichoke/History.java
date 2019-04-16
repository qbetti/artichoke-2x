package ca.uqac.lif.artichoke;

import ca.uqac.lif.artichoke.keyring.KeyRing;
import ca.uqac.lif.artichoke.keyring.exceptions.BadPassphraseException;
import ca.uqac.lif.artichoke.keyring.exceptions.PrivateKeyDecryptionException;
import org.bouncycastle.jcajce.provider.digest.SHA3;

import java.util.LinkedList;

public class History {

    private final static String SEP = "%";
    private final static SHA3.DigestSHA3 SHA3 = new SHA3.Digest256();

    private LinkedList<PeerAction> peerActions;

    public History() {
        peerActions = new LinkedList<>();
    }


    public void addPeerAction(PeerAction peerAction) {
        this.peerActions.add(peerAction);
    }

    public static History decode(String encodedHistory) {
        History h = new History();
        String[] encodedPeerActions = encodedHistory.split(SEP);

        for(String encodedPeerAction : encodedPeerActions) {
            if(encodedPeerAction != null && !encodedPeerAction.isEmpty()) {
                PeerAction pa = PeerAction.decode(encodedPeerAction);
                h.addPeerAction(pa);
            }
        }
        return h;
    }

    public String encode() {
        StringBuilder sb = new StringBuilder();
        for(PeerAction pa : peerActions) {
            sb.append(pa.encode()).append(SEP);
        }
        return sb.toString();
    }

    public Digest computeDigest(EncryptedAction nextEncryptedAction, Group group, KeyRing keyRing, String passphrase)
            throws PrivateKeyDecryptionException, BadPassphraseException {

        byte[] unsignedDigest = computeUnsignedDigest(nextEncryptedAction, group);
        byte[] digestBytes = keyRing.sign(unsignedDigest, passphrase);
        return new Digest(digestBytes);
    }

    public Digest computeDigest(EncryptedAction nextEncryptedAction, Group group, KeyRing keyRing)
            throws PrivateKeyDecryptionException, BadPassphraseException {

        return computeDigest(nextEncryptedAction, group, keyRing, null);
    }

    public byte[] computeUnsignedDigest(EncryptedAction nextEncryptedAction, Group group, Digest lastDigest) {
        byte[] lastDigestBytes = null;

//        if(peerActions.size() != 0) {
//            lastDigestBytes = peerActions.getLast().getDigest().getBytes();
//        }

        if(lastDigest != null) {
            lastDigestBytes = lastDigest.getBytes();
        }

        byte[] concatenatedBytes = Utils.concatenateBytes(
                lastDigestBytes,
                group.toString().getBytes(),
                nextEncryptedAction.getBytes());

        return SHA3.digest(concatenatedBytes);
    }

    public byte[] computeUnsignedDigest(EncryptedAction nextEncryptedAction, Group group) {
        Digest lastDigest = null;

        if(peerActions.size() != 0) {
            lastDigest = peerActions.getLast().getDigest();
        }
        return computeUnsignedDigest(nextEncryptedAction, group, lastDigest);
    }

    public boolean validatePeerAction(PeerAction pa, PeerAction previousPa) {
        byte[] unsignedDigest;

        if(previousPa == null)
            unsignedDigest = computeUnsignedDigest(pa.getEncryptedAction(), pa.getGroup(), null);
        else
            unsignedDigest = computeUnsignedDigest(pa.getEncryptedAction(), pa.getGroup(), previousPa.getDigest());

        return KeyRing.verifySignature(pa.getDigest().getBytes(), unsignedDigest, pa.getPeer().getHexPublicKey());
    }

    public boolean validatePeerAction(PeerAction pa) {
        byte[] unsignedDigest = computeUnsignedDigest(pa.getEncryptedAction(), pa.getGroup());
        return KeyRing.verifySignature(pa.getDigest().getBytes(), unsignedDigest, pa.getPeer().getHexPublicKey());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[\n");
        for(PeerAction peerAction : peerActions) {
            sb.append("\t").append(peerAction.toString()).append("\n");
        }
        sb.append("]");
        return sb.toString();
    }
}