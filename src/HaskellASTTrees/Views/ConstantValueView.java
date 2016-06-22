package HaskellASTTrees.Views;

import HaskellASTTrees.DataTypes.HString;
import HaskellASTTrees.Trees.ConstantValueTree;

/**
 * Created by vlad on 08.06.16.
 */
public class ConstantValueView extends AbstractTreeView {
    private static int DEFAULT_WIDTH = 4;

    private AbstractTreeView.LabelField valueField;
    private AbstractTreeView.LabelField typeField;

    public ConstantValueView(ConstructorSpace constructorSpace, ConstantValueTree<HString> tree) {
        super(constructorSpace, tree);
        valueField = new LabelField("42");
        typeField = new LabelField("Int");
    }
}
