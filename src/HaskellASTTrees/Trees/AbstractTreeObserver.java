package HaskellASTTrees.Trees;

/**
 * Created by vlad on 22.06.16.
 */
public interface AbstractTreeObserver {
    void parentChanged(AbstractTree child, AbstractTree prevParent);
    void childAdded(AbstractTree parent, AbstractTree newChild);
    void childRemoved(AbstractTree parent, AbstractTree oldChild);
}
