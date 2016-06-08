package Trees;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vlad on 08.06.16.
 */
public abstract class AbstractTree {
    protected List<AbstractTree> children = new ArrayList<>();

    public void addChild(AbstractTree tree) {
        children.add(tree);
    }

    public void removeChild(AbstractTree tree) {
        children.remove(tree);
    }
}
