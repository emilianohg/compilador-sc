package Main;

public class Identifier {
    private final String type;
    private final String name;
    private final String value;
    private final int size;
    private final int pos;

    public Identifier(String type, String name, String value, int size, int pos) {
        this.type = type;
        this.name = name;
        this.value = value;
        this.size = size;
        this.pos = pos;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public int getSize() {
        return size;
    }

    public int getPos() {
        return pos;
    }

    @Override
    public String toString() {
        return "Identifier{" + "type=" + type + ", name=" + name + ", value=" + value + ", size=" + size + ", pos=" + pos + '}';
    }
    
}
