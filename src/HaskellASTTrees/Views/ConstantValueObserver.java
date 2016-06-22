package HaskellASTTrees.Views;

import HaskellASTTrees.Trees.AbstractTreeObserver;

/**
 * Created by vlad on 22.06.16.
 */
public interface ConstantValueObserver extends AbstractTreeObserver {
    void valueChanged();
    void typeChanged();
}
