package Trees;

/**
 * Created by vlad on 25.05.16.
 */
public  class TreeNodeData {
    public static final String defaultName = "Unnamed";
    private String name;

    public TreeNodeData(String name) {
        this.name = name;
    }

    public TreeNodeData() {
        this(defaultName);
    }

    public String getName() {
        return name;
    }

}
