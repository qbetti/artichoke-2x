package ca.uqac.lif.artichoke;

import ca.uqac.lif.artichoke.encoding.Base64Encoder;
import ca.uqac.lif.artichoke.encoding.StringEncoder;

public class Group {

    private static final StringEncoder encoder = Base64Encoder.getInstance();
    private String id;

    public Group() {

    }

    public Group(String id) {
        this.id = id;
    }

    public String encode() {
        return encoder.encodeToString(id.getBytes());
    }

    public static Group decode(String encodedGroup) {
        Group group = new Group();
        group.id = new String(encoder.decode(encodedGroup));
        return group;
    }

    @Override
    public String toString() {
        return id;
    }
}
