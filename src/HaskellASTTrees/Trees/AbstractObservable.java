package HaskellASTTrees.Trees;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vlad on 22.06.16.
 */
public abstract class AbstractObservable<T> {
    protected List<T> observers = new ArrayList<>();

    public void addObserver(T observer) {
        observers.add(observer);
    }

    public List<T> getObservers() { return observers; }

    public void removeObserver(T observer) {
        observers.remove(observer);
    }
}
