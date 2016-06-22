package HaskellASTTrees.Trees;

import HaskellASTTrees.Views.AbstractTreeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vlad on 08.06.16.
 */
public abstract class AbstractTree<T extends AbstractTreeObserver> extends AbstractObservable<T> {
    protected AbstractTree parent = null;
    protected List<AbstractTree> children = new ArrayList<>();
    protected List<AbstractTreeView> treeViews = new ArrayList<>();

    public boolean addChild(AbstractTree tree) {
        if (!isGoodParentFor(tree)) {
            return false;
        }
        children.add(tree);
        tree.setParent(this);
        for (AbstractTreeObserver observer : observers) {
            observer.childAdded(this, tree);
        }
       return true;
    }

    public void removeChild(AbstractTree tree) {
        children.remove(tree);
        tree.setParent(null);
        for (AbstractTreeObserver observer : observers) {
            observer.childRemoved(this, tree);
        }
    }

    private void setParent(AbstractTree parent) {
        AbstractTree oldParent = this.parent;
        this.parent = parent;
        for (AbstractTreeObserver observer : observers) {
            observer.parentChanged(this, oldParent);
        }
    }

    public AbstractTree getParent() {
       return parent;
    }

    public void addTreeView(AbstractTreeView treeView) {
        treeViews.add(treeView);
    }

    public List<AbstractTreeView> getTreeViews() {
       return treeViews;
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

    public boolean isGoodParentFor(AbstractTree<T> tree) {
       AbstractTree cur = this;
       while (cur != null && cur != tree) {
          cur = cur.parent;
       }
       return cur == null;
    }
}
