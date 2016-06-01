import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vlad on 25.05.16.
 */
public class ToolChangeObserver {
    private static ToolChangeObserver instance;

    private List<ToolChangeObservable> observables = new ArrayList<>();
    private Tool curTool = Tool.HAND;

    public static ToolChangeObserver getInstance() {
        if (instance == null) {
            instance = new ToolChangeObserver();
        }
        return instance;
    }

    private ToolChangeObserver() {}

    public void add(ToolChangeObservable observable) {
        observables.add(observable);
        observable.toolChanged(curTool);
    }

    public void remove(ToolChangeObservable observable) {
        observables.remove(observable);
    }

    public void setTool(Tool tool) {
        curTool = tool;
        observables.forEach((ToolChangeObservable observable) -> {
            observable.toolChanged(tool);
        });
    }

    public static void setNewMouseAdapters(Container container,
                                            List<MouseAdapter> newMouseAdapters) {
        MouseListener[] mouseListeners = container.getMouseListeners();
        for (MouseListener mouseListener : mouseListeners) {
            container.removeMouseListener(mouseListener);
        }
        MouseMotionListener[] mouseMotionListeners = container.getMouseMotionListeners();
        for (MouseMotionListener mouseMotionListener : mouseMotionListeners) {
            container.removeMouseMotionListener(mouseMotionListener);
        }
        newMouseAdapters.forEach((MouseAdapter mouseAdapter) -> {
            container.addMouseListener(mouseAdapter);
            container.addMouseMotionListener(mouseAdapter);
        });
    }
}

