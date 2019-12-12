import java.util.*;

public class Tree<T> {
    private Node<T> root;
    private Comparator<T> comparator;

    public Tree(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public T add(T e) {
        if (null == e) {
            throw new IllegalArgumentException();
        }

        if (isEmpty()) {
            root = new Node<T>(e);
            return e;
        }

        return addNode(root, e);
    }

    public boolean find(T e) {
        if (isEmpty()) {
            return false;
        }

        Queue<Node<T>> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node<T> node = queue.poll();
            if (node.value.equals(e)) {
                return true;
            }
            if (null != node.left) {
                queue.add(node.left);
            }
            if (null != node.right) {
                queue.add(node.right);
            }
        }
        return false;
    }

    public boolean isEmpty() {
        return null == this.root;
    }

    public void printTree() {
        if (null == root) {
            return;
        }

        // building balanced tree
        List<Node<T>> list = new ArrayList<>();
        list.add(root);

        int index = 0;
        int currentLevel = 2;
        int maxLevel = maxLevel(root);
        while (currentLevel <= maxLevel) {
            int nodesOnCurrentLevel = getElementsCountOnLevel(currentLevel);
            for (int i = 0; i < nodesOnCurrentLevel; i += 2, index++) {
                Node<T> node = list.get(index);
                list.add(null != node ? node.left : null);
                list.add(null != node ? node.right : null);
            }
            currentLevel++;
        }

        // finding width
        int maxWidth = list.stream()
                .filter(Objects::nonNull)
                .map(e -> getElementsWidth(e.value))
                .max(Integer::compareTo).get();

        // building string tree
        StringBuilder treeBuilder = new StringBuilder();
        int used = 0;
        int spaceWidth = 1;
        for (int level = maxLevel; level > 0 ; level--) {
            int elementsCount = getElementsCountOnLevel(level);
            int beginIndex = list.size() - (elementsCount + used);
            int endIndex = list.size() - used;
            used += elementsCount;

            StringBuilder levelString = new StringBuilder();
            List<Node<T>> nodes = list.subList(beginIndex, endIndex);
            for (Node<T> node : nodes) {
                levelString.append(null == node ? " ".repeat(maxWidth + spaceWidth) : getCenteredString(maxWidth + spaceWidth, node.value.toString()));
            }
            maxWidth = (2 * maxWidth) + spaceWidth;
            treeBuilder.insert(0, levelString.toString() + '\n');
        }

        // printing tree
        System.out.println("*".repeat(treeBuilder.length() / maxLevel));
        System.out.println(treeBuilder.toString());
        System.out.println("*".repeat(treeBuilder.length() / maxLevel));
    }

    private String getCenteredString(int width, String s) {
        int padSize = width - s.length();
        int padStart = s.length() + padSize / 2;
        s = String.format("%" + padStart + "s", s);
        return String.format("%-" + width  + "s", s);
    }

    private int getElementsWidth(T e) {
        return e.toString().length();
    }

    private int getElementsCountOnLevel(int level) {
        return (int) Math.pow(2, level - 1);
    }

    private int maxLevel(Node<T> node) {
        if (node == null) {
            return 0;
        }
        return Math.max(maxLevel(node.left), maxLevel(node.right)) + 1;
    }

    private T addNode(Node<T> node, T value) {
        if (node.value.equals(node)) {
            return null;
        }

        Node<T> n;
        if (comparator.compare(value, node.value) < 0) {
            n = node.left;
            if (null == node.left) {
                node.left = new Node<T>(value);
                return value;
            }
        } else {
            if (null == node.right) {
                node.right = new Node<T>(value);
                return value;
            }
            n = node.right;
        }
        return addNode(n, value);
    }

    static final class Node<T> {
        T value;
        Node<T> left, right;

        Node(T value) {
            this.value = value;
        }
    }
}
