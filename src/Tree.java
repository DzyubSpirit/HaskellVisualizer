import java.util.List;

/**
 * Created by vlad on 24.05.16.
 */
public class Tree {
    private TreeNodeData data;
    private List<Tree> children;

    public Tree(String name) {
        data = new TreeNodeData(name);
    }

    public String getName() {
        return data.getName();
    }
}
