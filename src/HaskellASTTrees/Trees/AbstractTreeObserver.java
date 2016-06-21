package HaskellASTTrees.Trees;

/**
 * Created by vlad on 22.06.16.
 */
public interface AbstractTreeObserver {
    void parentChanged();
    void childAdded();
    void childRemoved();
}
