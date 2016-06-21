package HaskellASTTrees.Views;

/**
 * Created by vlad on 08.06.16.
 */
public class ConstantValueView extends AbstractTreeView {
    private static int DEFAULT_WIDTH = 4;

    private LabelField valueField;
    private LabelField typeField;

    ConstantValueView() {
        valueField = new LabelField("42");
        typeField = new LabelField("Int");
    }
}
