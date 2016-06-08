package DataTypes;

/**
 * Created by vlad on 08.06.16.
 */
public class HInt implements HType {
    private int data;
    public HInt(int data) {
        this.data = data;
    }

    public int getData() {
        return data;
    }
}
