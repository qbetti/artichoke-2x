package ca.uqac.lif.artichoke;

public class Action {

    private String field;
    private String type;
    private String value;

    public Action(String field, String type, String value) {
        this.field = field;
        this.type = type;
        this.value = value;
    }

    @Override
    public String toString() {
        return "{"+field+","+type+","+value+"}";
    }
}
