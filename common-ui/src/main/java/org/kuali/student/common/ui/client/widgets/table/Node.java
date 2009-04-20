package org.kuali.student.common.ui.client.widgets.table;

import java.util.ArrayList;
import java.util.List;
/**
 * A generic tree node. 
 * */
public class Node<T> {
    /** Children are stored in a list*/
    List<Node> childrenList = new ArrayList<Node>();
    /** Parent Node. null if node has no parent*/
    Node parent;
    /** User Object*/
    T userObject;
    String id;

    public Node() {}

    public Node(T obj) {
        userObject = obj;
    }

    public T getUserObject() {
        return userObject;
    }

    public void setUserObject(T obj) {
        userObject = obj;
    }

    public void setParent(Node p) {
        parent = p;
    }

    public Node getParent() {
        return parent;
    }
    /**
     * Add one child and set child's parent to current node
     * */
    public void addNode(Node node) {
        childrenList.add(node);
        node.setParent(this);
    }
    /** If it has no children, it is a leaf*/
    public boolean isLeaf() {
        return getChildCount() == 0;
    }
    /** Return the child count */
    public int getChildCount() {
        return childrenList.size();
    }
    /** Get the child at index*/
    public Node getChildAt(int index) {
        return childrenList.get(index);
    }
    /** Remove child from parent*/
    public void removeFromParent() {
        Node parent = this.getParent();
        if (parent == null) {
            return;
        } else {
            parent.children().remove(this);
            this.setParent(null);
        }
    }
    /** Remove child at childIndex*/
    public void remove(int childIndex) {
        Node child = getChildAt(childIndex);
        childrenList.remove(childIndex);
        child.setParent(null);
    }
    /** Remove child from children list
     * 
     * @param child 
     * */
    public void remove(Node child) {
        childrenList.remove(child);
        child.setParent(null);
    }
    public void removeAllChildren(){
        childrenList.clear();
    }
    /** 
     * Is parsed in node a child
     * */
    public boolean isNodeChild(Node aNode) {
        boolean retval;
        if (aNode == null) {
            retval = false;
        } else {
            if (getChildCount() == 0) {
                retval = false;
            } else {
                retval = (aNode.getParent() == this);
            }
        }
        return retval;
    }
    
    public int getIndex(Node aChild) {
        if (!isNodeChild(aChild)) {
            return -1;
        }
        return childrenList.indexOf(aChild); // linear search
    }
    /** Is parsed in node a sibling */
    public boolean isNodeSibling(Node anotherNode) {
        boolean retval;

        if (anotherNode == null) {
            retval = false;
        } else if (anotherNode == this) {
            retval = true;
        } else {
            Node myParent = getParent();
            retval = (myParent != null && myParent == anotherNode.getParent());

            if (retval && !((Node) getParent()).isNodeChild(anotherNode)) {
                throw new Error("sibling has different parent");
            }
        }
        return retval;
    }

    /**
     * Returns the total number of leaves that are descendants of this node.
     * 
     * @return the number of leaves beneath this node
     */
    public int getAllLeafCount() {
        int count = 0;
        List<Node<T>> nodeList = getAllChildren();
        for (Node<T> node : nodeList) {
            if (node.isLeaf()) {
                count++;
            }
        }

        return count;
    }
    /** Return all children and grand children*/
    public List<Node<T>> getAllChildren() {
        List<Node<T>> nodeList = new ArrayList<Node<T>>();
        for (Node<T> child : childrenList) {
            nodeList.add(child);
            if (!child.isLeaf()) {
                nodeList.addAll(child.getAllChildren());
            }
        }
        return nodeList;
    }
    /** Get the first leaf among all its children*/
    public Node<T> getFirstLeafDescendant() {
        List<Node<T>> list = getAllChildren();
        for (Node<T> node : list) {
            if (node.isLeaf()) {
                return node;
            }
        }
        return null;

    }
    /** Return all non-leaf children 
     * 
     * @return List<Node> list of non-leaf children
     * */
    public List<Node> getNonLeafChildren() {
        List<Node> list = new ArrayList<Node>();
        for (Node n : children()) {
            if (n.isLeaf() == false) {
                list.add(n);
            }
        }
        return list;
    }
    
    public List<Node> getLeafChildren() {
        List<Node> list = new ArrayList<Node>();
        for (Node n : children()) {
            if (n.isLeaf()) {
                list.add(n);
            }
        }
        return list;
    }

    public List<Node> getSiblings() {
        Node parent = this.getParent();
        List<Node> list = new ArrayList<Node>();
        if (parent != null) {

            for (int i = 0; i < parent.getChildCount(); i++) {
                if (parent.getChildAt(i) != this) {
                    list.add(parent.getChildAt(i));
                }
            }
            return list;
        }
        return list;

    }

    public List<Node> getLeafSiblings() {
        List<Node> list = new ArrayList<Node>();
        List<Node> siblings = getSiblings();
        for (Node n : siblings) {
            if (n.isLeaf()) {
                list.add(n);
            }
        }

        return list;
    }

    public List<Node> children() {
        if (childrenList == null) {
            return new ArrayList<Node>();
        } else {
            return childrenList;
        }
    }

    // root is the level one
    // results contain current node
    public List<List<Node>> toLevel() {
        List<List<Node>> levelList = new ArrayList<List<Node>>();

        List<Node> level = new ArrayList<Node>();
        level.add(this);
        levelList.add(level);

        int maxDistance = getMaxLevelDistance();

        List<Node<T>> nodeList = getAllChildren();
        for (int levelIndex = 1; levelIndex <= maxDistance; levelIndex++) {
            level = new ArrayList<Node>();
            for (Node node : nodeList) {
                int d = getDistance(node);
                if (levelIndex == d) {
                    level.add(node);
                }
            }
            levelList.add(level);
        }

        return levelList;
    }

    public List<Node> deepTrans(Node root) {
        List<Node> list = new ArrayList<Node>();
        for (int i = 0; i < root.getChildCount(); i++) {
            Node n = root.getChildAt(i);
            if (n.isLeaf()) {
                list.add(n);
            } else {
                list.addAll(deepTrans(n));
            }

        }
        list.add(root);
        return list;
    }

    public int getMaxLevelDistance() {
        List<Node<T>> nodeList = getAllChildren();
        int maxDistance = 0;
        for (Node<T> node : nodeList) {
            int d = getDistance(node);
            if (maxDistance < d) {
                maxDistance = d;
            }
        }
        return maxDistance;
    }

    // return the level distance to the current node
    public int getDistance(Node node) {
        Node myParent = node.getParent();
        int level = 1;
        while (myParent != null && myParent != this) {
            level++;
            myParent = myParent.getParent();
        }
        return level;
    }

    public String toString() {
        if (userObject == null) {
            return "no user object";
        }

        StringBuilder sb = new StringBuilder(userObject.toString());
        if (this.getChildCount() == 0) {
            return sb.toString();
        }
        sb.append("{");
        for (int i = 0; i < this.getChildCount(); i++) {
            if (this.getChildAt(i).getParent() == this) {
                sb.append(this.getChildAt(i).toString() + ",");
            }

        }
        sb.append("}");
        return sb.toString();
    }

    public Node<T> clone() {
        Node<T> newNode = new Node<T>();
        newNode.setUserObject(this.getUserObject());
        if (this.isLeaf() == false) {
            for (Node<T> n : this.childrenList) {
                newNode.addNode(n.clone());
            }
        }
        return newNode;
    }

    public static void main(String[] argv) {
/*        Node root = new Node();
        root.setUserObject("root");
        Node r = new Node();
        r.setUserObject("r");
        Node q = new Node();
        q.setUserObject("q");
        root.addNode(q);
        root.addNode(r);

        Node a = new Node();
        a.setUserObject("a");
        q.addNode(a);

//        System.out.println(root.deepTrans(root));
  //      System.out.println(root.deepTrans(root.clone()));
        
        Node x = new Node();
        x.setUserObject("x");

        Node y = new Node();
        y.setUserObject("y");

        Node z = new Node();
        z.setUserObject("z");
        
        x.addNode(y);
        x.addNode(z);
        System.out.println(x.deepTrans(x.clone()));
*/        
        ExpressionParser p = new ExpressionParser();
        Node<Token> n = p.parse("(a or b) and c or d and f");
        System.out.println(n);
        System.out.println(ExpressionParser.getExpressionString(n));
    }
}   