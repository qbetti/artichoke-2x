package ca.uqac.lif.artichoke;


import java.util.Base64;

public class EncryptedAction {

    private byte[] bytes;

    public EncryptedAction() {

    }

    public EncryptedAction(Action action) {
        this.bytes = action.toString().getBytes();
    }

    public String encode() {
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static EncryptedAction decode(String encodedEncryptedAction) {
        EncryptedAction encryptedAction = new EncryptedAction();
        encryptedAction.bytes = Base64.getDecoder().decode(encodedEncryptedAction);

        return encryptedAction;
    }

    @Override
    public String toString() {
        return new String(this.bytes);
    }

    public byte[] getBytes() {
        return bytes;
    }

    public byte[] concatenate(byte[] others) {
        if(this.bytes == null)
            return null;

        if(others == null ) {
            others = new byte[0];
        }

        byte[] result = new byte[others.length + this.bytes.length];

        int offset = 0;
        for(int i = 0; i < others.length; i++) {
            result[offset + i] = others[i];
        }
        offset += others.length;
        for(int i = 0; i < this.bytes.length; i++) {
            result[offset + i] = this.bytes[i];
        }

        return result;
    }
}
