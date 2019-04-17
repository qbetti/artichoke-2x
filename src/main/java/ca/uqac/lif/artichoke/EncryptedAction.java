package ca.uqac.lif.artichoke;


import ca.uqac.lif.artichoke.encoding.Base64Encoder;
import ca.uqac.lif.artichoke.encoding.StringEncoder;

public class EncryptedAction {

    private static final StringEncoder encoder = Base64Encoder.getInstance();

    private byte[] bytes;

    public EncryptedAction() {

    }

    public EncryptedAction(byte[] bytes) {
        this.bytes = bytes;
    }

    public EncryptedAction(Action action) {
        this.bytes = action.toString().getBytes();
    }

    public String encode() {
        return encoder.encodeToString(bytes);
    }

    public static EncryptedAction decode(String encodedEncryptedAction) {
        return new EncryptedAction(encoder.decode(encodedEncryptedAction));
    }

    @Override
    public String toString() {
        return new String(this.bytes);
    }

    public byte[] getBytes() {
        return bytes;
    }
}
