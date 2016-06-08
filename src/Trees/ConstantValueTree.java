package Trees;

import Trees.AbstractTree;

import java.util.ArrayList;

/**
 * Created by vlad on 24.05.16.
 */
public class ConstantValueTree extends AbstractTree {
    private TreeNodeData data;

    public Tree(String name) {
        data = new TreeNodeData(name);
        children = new ArrayList<>();
    }

    public String getName() {
        return data.getName();
    }
}
