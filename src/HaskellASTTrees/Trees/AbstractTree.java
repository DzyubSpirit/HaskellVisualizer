package HaskellASTTrees.Trees;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vlad on 08.06.16.
 */
public abstract class AbstractTree extends AbstractObservable<AbstractTreeObserver> {
    protected AbstractTree parent = null;
    protected List<AbstractTree> children = new ArrayList<>();

    public boolean addChild(AbstractTree tree) {
        if (!tree.isGoodParent(this)) {
            return false;
        }
        for (AbstractTreeObserver observer : observers) {
            observer.childAdded();
        }
        children.add(tree);
        setParent(this);
        return true;
    }

    public void removeChild(AbstractTree tree) {
        for (AbstractTreeObserver observer : observers) {
            observer.childRemoved();
        }
        children.remove(tree);
        setParent(null);
    }

    private void setParent(AbstractTree parent) {
        for (AbstractTreeObserver observer : observers) {
            observer.parentChanged();
        }
        this.parent = parent;
    }
    /**
     *
     * @return parents from highest parent to lowest
     */
    private List<AbstractTree> getParents() {
        List<AbstractTree> res;
        if (parent != null) {
            res = parent.getParents();
            res.add(parent);
        } else {
            res = new ArrayList<>();
        }
        return res;
    }

    public boolean isGoodParent(AbstractTree tree) {
        return tree.getParents().contains(this);
    }
}
