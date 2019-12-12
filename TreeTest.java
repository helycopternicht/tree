import java.util.Comparator;

class TreeTest {

    public static void main(String[] args) {
        printTree();
        find();
    }

    public static void printTree() {
        Tree<Integer> tree = new Tree<>(Comparator.comparingInt(e -> e));
        tree.add(101);
        tree.add(15);
        tree.add(190);
        tree.add(171);
        tree.add(3);
        tree.add(91);
        tree.add(205);
        tree.add(155);
        tree.add(13);
        tree.add(17);
        tree.add(203);

        tree.printTree();
    }

    public static void find() {
        Tree<Integer> tree = new Tree<>(Comparator.comparingInt(e -> e));
        tree.add(10);
        tree.add(15);

        boolean filed = false;
        if (!tree.find(10)) {
            filed = true;
        }

        if (!tree.find(15)) {
            filed = true;
        }

        System.out.println(filed ? "TEST FILED!" : "TEST PASSED");
    }
}
