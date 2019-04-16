package ca.uqac.lif.artichoke;

import java.util.Base64;

public class Group {

    private String id;

    public Group() {

    }

    public Group(String id) {
        this.id = id;
    }

    public String encode() {
        return Base64.getEncoder().encodeToString(id.getBytes());
    }

    public static Group decode(String encodedGroup) {
        Group group = new Group();
        group.id = new String(Base64.getDecoder().decode(encodedGroup));
        return group;
    }

    @Override
    public String toString() {
        return id;
    }
}
