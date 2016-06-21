package HaskellASTTrees.Trees;

import HaskellASTTrees.DataTypes.HType;

/**
 * Created by vlad on 08.06.16.
 */
public class Synonym extends AbstractTree {
    private HType target;
    private String name;

    public Synonym(String name, HType target) {
        this.name = name;
        this.target = target;
    }

    @Override
    public boolean addChild(AbstractTree tree) {
        return true;
    }

    @Override
    public void removeChild(AbstractTree tree) {}
}
