package HaskellASTTrees.Tools;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by vlad on 21.06.16.
 */
public class ToolChangingManager implements ToolChangeObservable {
    private HashMap<Tool, List<MouseAdapter>> toolMouseAdapters = new HashMap<>();
    private List<MouseAdapter> curList = new ArrayList<>();
    private Container container;

    public ToolChangingManager(Container container) {
       ToolChangeObserver.getInstance().add(this);
       this.container = container;
    }

    public ToolChangingManager addMouseAdapter(MouseAdapter mouseAdapter) {
        curList.add(mouseAdapter);
        return this;
    }

    public ToolChangingManager saveToolAdapters(Tool tool) {
        toolMouseAdapters.put(tool, curList);
        curList = new ArrayList<>();
        return this;
    }

    @Override
    public void toolChanged(Tool tool) {
        List<MouseAdapter> newMouseAdapters = toolMouseAdapters.get(tool);
        if (newMouseAdapters != null) {
            ToolChangeObserver.setNewMouseAdapters(container, newMouseAdapters);
        }
    }
}
