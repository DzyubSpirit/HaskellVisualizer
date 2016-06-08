package Trees;

import DataTypes.HType;
import Trees.AbstractTree;

import java.util.ArrayList;

/**
 * Created by vlad on 24.05.16.
 */
public class ConstantValueTree<T extends HType> extends AbstractTree {
    private T data;

    public ConstantValueTree(T data) {
        this.data = data;
    }
}
