package org.kuali.student.commons.ui.widgets.trees;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.kuali.student.commons.ui.mvc.client.model.ModelObject;
import org.kuali.student.commons.ui.mvc.client.widgets.ModelWidget;
import org.kuali.student.commons.ui.widgets.tables.ModelTableSelectionListener;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.TreeListener;

public abstract class SimpleTree<T extends ModelObject> extends Composite implements ModelWidget<T>, TreeListener {

    final Tree tree = new Tree();
    final Map<String, ItemMap<T>> index = new HashMap<String, ItemMap<T>>();
    final List<T> items = new ArrayList<T>();
    private final Set<ModelTableSelectionListener<T>> listeners = new HashSet<ModelTableSelectionListener<T>>();
    private final Set<TreeMouseOverListener<T>>mouseOverlisteners = new HashSet<TreeMouseOverListener<T>>();
    boolean loaded = false;

    public SimpleTree() {
        super.initWidget(tree);
    }

    /**
     * Called by the container to initialize the table. Do not call directly.
     */
    @Override
    public void onLoad() {
        if (!loaded) {
            loaded = true;
            tree.addTreeListener(this);
        }
    }

    public abstract List<String> getPath(T modelObject);
    
    public void add(T modelObject) {
        List<String> path = getPath(modelObject);
        Iterator<String> itr = path.iterator();

        // does the root branch exist?
        String root = itr.next();
        ItemMap<T> im = index.get(root);
        if (im == null) {
            TreeItem item = tree.addItem(root);
            im = new ItemMap<T>();
            im.key = root;
            im.item = item;
            index.put(root, im);
            sortRootChildren();
        }
        // recursively add branches
        if (itr.hasNext()) {
            add(itr, im, modelObject);
        } else {
            im.modelObject = modelObject;
        }
        items.add(modelObject);
    }

    private void add(Iterator<String> itr, ItemMap<T> parentMap, T modelObject) {
        if (itr.hasNext()) {
            String key = itr.next();
            ItemMap<T> im = parentMap.index.get(key);
            if (im == null) {
                TreeItem item = parentMap.item.addItem(key);
                im = new ItemMap<T>();
                im.key = key;
                im.item = item;
                parentMap.index.put(key, im);
                sortChildren(parentMap.item);
            }
            if (itr.hasNext()) {
                add(itr, im, modelObject);
            } else {
                im.modelObject = modelObject;
            }
        }
    }

    // uber nasty hack to "reorder" items if one was added
    private void sortChildren(TreeItem item) {
        if (item.getChildCount() > 1) {
            List<TreeItem> children = new ArrayList<TreeItem>();
            // hack to work around indentation bug in tree
            TreeItem blank = new TreeItem("blank");
            item.addItem(blank);
            while (item.getChildCount() > 1) {
                TreeItem t = item.getChild(0);
                children.add(t);
                item.removeItem(t);
            }
            Collections.sort(children, TreeItemComparator.INSTANCE);
            for (TreeItem t : children) {
                item.addItem(t);
            }
            item.removeItem(blank);
        }
    }

    // another uber nasty reorder hack
    private void sortRootChildren() {
        if (tree.getItemCount() > 1) {
            List<TreeItem> children = new ArrayList<TreeItem>();
            while (tree.getItemCount() > 0) {
                TreeItem t = tree.getItem(0);
                children.add(t);
                tree.removeItem(t);
            }
            Collections.sort(children, TreeItemComparator.INSTANCE);
            for (TreeItem t : children) {
                tree.addItem(t);
            }
        }
    }

    public void addBulk(Collection<T> collection) {
        for (T t : collection) {
            add(t);
        }
    }

    public void clear() {
        tree.clear();
        index.clear();
    }

    public List<T> getItems() {
        return items;
    }

    public T getSelection() {
        return getModelObject(tree.getSelectedItem());
    }

    public void remove(T modelObject) {
        List<String> path = getPath(modelObject);
        Iterator<String> itr = path.iterator();
        String rootKey = itr.next();
        ItemMap<T> map = index.get(rootKey);

        remove(itr, map);
        if (map.index.size() == 0) {
            index.remove(rootKey);
            tree.removeItem(map.item);
        }
    }

    private void remove(Iterator<String> itr, ItemMap<T> parentMap) {
        String key = itr.next();
        ItemMap<T> map = parentMap.index.get(key);

        if (itr.hasNext()) {
            remove(itr, map);
        }
        if (map.index.size() == 0) {
            parentMap.index.remove(key);
            parentMap.item.removeItem(map.item);
        }
    }

    public void select(T modelObject) {
        TreeItem item = getTreeItem(modelObject);
        if (item != null) {
            tree.setSelectedItem(item, false);
            item.setSelected(true);
        }
        tree.ensureSelectedItemVisible();
    }

    public void unSelect() {
    	tree.setSelectedItem(null);
    }
    
    public void update(T modelObject) {
        remove(modelObject);
        add(modelObject);
    }
    
    private T getModelObject(TreeItem item) {
        List<String> path = getItemPath(item);
        ItemMap<T> map = null;
        for (String s : path) {
            if (map == null) {
                map = index.get(s);
            } else {
                map = map.index.get(s);
            }
        }

        return map.modelObject;
    }

    private TreeItem getTreeItem(T modelObject) {
        List<String> path = getPath(modelObject);
        Iterator<String> itr = path.iterator();
        String rootKey = itr.next();
        ItemMap<T> map = index.get(rootKey);

        while (itr.hasNext()) {
            map = map.index.get(itr.next());
        }

        return map.item;
    }

    private List<String> getItemPath(TreeItem item) {
        List<String> result = new ArrayList<String>();
        while (item != null) {
            result.add(0, item.getText());
            item = item.getParentItem();
        }
        return result;
    }

    static class ItemMap<T extends ModelObject> {
        public String key;
        public TreeItem item;
        public T modelObject;
        public Map<String, ItemMap<T>> index = new HashMap<String, ItemMap<T>>();
    }

    static class TreeItemComparator implements Comparator<TreeItem> {
        public static final TreeItemComparator INSTANCE = new TreeItemComparator();

        private TreeItemComparator() {}

        public int compare(TreeItem o1, TreeItem o2) {
            return String.CASE_INSENSITIVE_ORDER.compare(o1.getText(), o2.getText());
        }
    }

    /**
     * Adds a ModelTableSelectionListener to be fired when the selected item changes
     * 
     * @param listener
     *            the listener to add
     */
    public void addSelectionListener(ModelTableSelectionListener<T> listener) {
        listeners.add(listener);
    }

    /**
     * Adds a ModelTableSelectionListener to be fired when the selected item changes
     * 
     * @param listener
     *            the listener to add
     */
    public void addMouseOverListener(TreeMouseOverListener<T> mouseOverListener) {
        mouseOverlisteners.add(mouseOverListener);
    }

    /**
     * Removes a selection change listener
     * 
     * @param listener
     *            the listener to remove
     */
    public void removeSelectionListener(ModelTableSelectionListener<T> listener) {
        listeners.remove(listener);
    }

    public void onTreeItemSelected(TreeItem item) {
        fireSelection(getSelection());
    }

    public void onTreeItemStateChanged(TreeItem item) {}

    /**
     * Fires the selection change event for the specified ModelObject
     * 
     * @param modelObject
     *            the ModelObject that was selected
     */
    protected void fireSelection(T modelObject) {
        for (ModelTableSelectionListener<T> listener : listeners) {
            listener.onSelect(modelObject);
        }
    }
    
    protected void fireMouseOver(T modelObject,
            int x, int y) {
        for (TreeMouseOverListener<T> mouseOverlistener : mouseOverlisteners) {
            mouseOverlistener.onTreeItemMouseOver(modelObject, x, y);
        }
    }
    
    protected void fireMouseOut() {
        for (TreeMouseOverListener<T> mouseOverlistener : mouseOverlisteners) {
            mouseOverlistener.onMouseOut();
        }
    }
        
    
    @Override
    public void onBrowserEvent(Event event) {
        super.onBrowserEvent(event);
        int eventType = DOM.eventGetType(event);
        switch(eventType) {
            case Event.ONMOUSEOVER: {
                com.google.gwt.user.client.Element element = 
                    DOM.eventGetTarget(event);
                List<T> items = getItems();
                T mouseOverredItem = null;
                int xPosition = 0;
                int yPosition = 0;
                for (T item : items) {
                    TreeItem treeItem = getTreeItem(item);
                    if (treeItem.getElement().isOrHasChild(element)) {
                        mouseOverredItem = item;
                        xPosition = element.getAbsoluteLeft();
                        yPosition = element.getAbsoluteTop();
                    }
                }
                if (mouseOverredItem != null) {
                    fireMouseOver(mouseOverredItem, 
                            xPosition, yPosition);
                }
                break;
            }
            case Event.ONMOUSEOUT: {
                fireMouseOut();
            }
        }

    }

}
