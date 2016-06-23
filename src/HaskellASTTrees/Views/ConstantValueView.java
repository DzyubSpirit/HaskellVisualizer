package HaskellASTTrees.Views;

import HaskellASTTrees.DataTypes.HString;
import HaskellASTTrees.Trees.ConstantValueTree;

import javax.swing.*;

/**
 * Created by vlad on 08.06.16.
 */
public class ConstantValueView extends AbstractTreeView {
    private static int DEFAULT_WIDTH = 4;

    private LabelField valueField;
    private LabelField typeField;

    public ConstantValueView(ConstructorSpace constructorSpace, ConstantValueTree<HString> tree) {
        super(constructorSpace, tree);
        valueField = new LabelField(this, "42");
        typeField = new LabelField(this, "Int");
        setLayout(null);
        JLabel typeLabel = new JLabel("Type:");
        JLabel valueLabel = new JLabel("Value:");
        typeLabel.setBounds(2, 2, 96, 20);
        add(typeLabel);
        typeField.setBounds(2, 22, 96, 20);
        add(typeField);
        valueLabel.setBounds(2, 55, 96, 20);
        add(valueLabel);
        valueField.setBounds(2, 78, 96, 20);
        add(valueField);
    }
}
