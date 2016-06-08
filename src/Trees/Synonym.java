package Trees;

import DataTypes.HType;

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
    public void addChild(AbstractTree tree) {}

    @Override
    public void removeChild(AbstractTree tree) {}
}
