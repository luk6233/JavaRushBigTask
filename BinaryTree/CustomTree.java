package JavaRushBigTasks.BinaryTree;

import java.io.Serializable;
import java.util.*;

/* 
Построй дерево(1)
*/
public class CustomTree extends AbstractList<String> implements Serializable, Cloneable {

    Entry<String> root;
    private String name;
    int count;
    List<Entry<String>> entryList = new ArrayList<>();

    public CustomTree() {
        root = new Entry<String>("0");
        entryList.add(root);
    }

    static class Entry<T> implements Serializable {
        String elementName;
        boolean availableToAddLeftChildren, availableToAddRightChildren;
        Entry<T> parent, leftChild, rightChild;

        public Entry (String elementName) {
            this.elementName = elementName;
            availableToAddLeftChildren = true;
            availableToAddRightChildren = true;
        }

        public boolean isAvailableToAddChildren() {
            return availableToAddLeftChildren || availableToAddRightChildren;
        }

    }

    @Override
    public boolean add(String s) {
        Entry<String> entry = new Entry<>(s);
        count++;

        Queue<Entry<String>> queue = new LinkedList<>();
        queue.add(root);
        Entry<String> top;
        while (!queue.isEmpty()) {
            top = queue.poll();
            if (top == null) continue;

            if (!top.isAvailableToAddChildren() && (top.leftChild == null && top.rightChild == null)) {
                top.availableToAddLeftChildren = true;
                top.availableToAddRightChildren = true;
            }

            if (top.isAvailableToAddChildren()) {
                if (top.availableToAddLeftChildren) {
                    top.leftChild = entry;
                    entry.parent = top;
                    top.availableToAddLeftChildren = false;
                   return true;
                }
                else if (top.availableToAddRightChildren) {
                    top.rightChild = entry;
                    entry.parent = top;
                    top.availableToAddRightChildren = false;
                    return true;
                }
            }
            else {
                queue.add(top.leftChild);
                queue.add(top.rightChild);
            }
        }
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (!(o instanceof String)) throw new UnsupportedOperationException();

        String name = (String) o;

        Queue<Entry<String>> queue = new LinkedList<>();
        queue.add(root);
        Entry<String> top;

        while (!queue.isEmpty()) {
            top = queue.poll();
            if (top.elementName.equals(name)) {
                Queue<Entry<String>> queueRemoveChildren = new LinkedList<>();
                queueRemoveChildren.add(top);

                while (!queueRemoveChildren.isEmpty()) {
                    top = queueRemoveChildren.poll();

                    if (top.parent.leftChild != null && top.parent.leftChild.equals(top)) {
                        top.parent.leftChild = null;
                        count--;
                    }
                    else if (top.parent.rightChild != null && top.parent.rightChild.equals(top)) {
                        top.parent.rightChild = null;
                        count--;
                    }
                    if (top.leftChild != null) {
                        queueRemoveChildren.add(top.leftChild);
                    }
                    if (top.rightChild != null) {
                        queueRemoveChildren.add(top.rightChild);
                    }
                }
                return true;
            }
            if (top.leftChild != null) queue.add(top.leftChild);
            if (top.rightChild != null) queue.add(top.rightChild);

        }

        return true;
    }

    @Override
    public int size() {
        return count;
    }

    public String getParent(String s) {
        Queue<Entry<String>> queue = new LinkedList<>();
        queue.add(root);
        Entry<String> top;
        while (!queue.isEmpty()) {
            top = queue.poll();

            if (top.leftChild != null) {
                if (top.leftChild.elementName.equals(s)) {
                    return top.elementName;
                }
                else queue.add(top.leftChild);
            }
            if (top.rightChild != null){
                if (top.rightChild.elementName.equals(s)) {
                    return top.elementName;
                }
                else queue.add(top.rightChild);
            }
        }
        return null;
    }

    @Override
    public String get(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String set(int index, String element){
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(int index, String element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String remove(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void removeRange(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends String> c) {
        throw new UnsupportedOperationException();
    }
}
