import java.util.ArrayList;

/**
 * Created by John on 09/03/16.
 */
public class RBTree<T extends Comparable> {

    Boolean debug = false;
    Boolean visualization = false;


    private Node<T> root;
    private Node<T> nil;
    private ArrayList<Node<T>> roots;

    public RBTree() {
        this.nil = new Node<T>(null, null, null,null);
        this.nil.left = this.nil;
        this.nil.right = this.nil;
        this.nil.setRed(false);

        this.root = this.nil;
        this.roots = new ArrayList<>();
    }

    private class Node<T> {
        private T element;
        private Node<T> left;
        private Node<T> right;
        private Boolean red;

        public Node(T element, Node<T> left, Node<T> right) {
            this.element = element;
            this.left = left;
            this.right = right;
            this.red = true;
        }

        public Node(T element, Node<T> left, Node<T> right, Boolean red) {
            this.element = element;
            this.left = left;
            this.right = right;
            this.red = red;
        }

        public void setRed(Boolean red) {
            this.red = red;
        }

        public Node clone() {
            return new Node<T>(this.element, this.left, this.right, this.red);
        }
    }

    public void insert(T element) {

        ArrayList<Node<T>> path = new ArrayList<>();

        // walk down tree
        Node<T> a = !this.root.equals(this.nil) ? this.root.clone() : this.nil;
        while (!a.equals(this.nil)) {
            path.add(a);

            if (element.compareTo(a.element) < 0) {
                if (!a.left.equals(this.nil)) {
                    a.left = a.left.clone();
                }
                a = a.left;
            }
            else {
                if (!a.right.equals(this.nil)) {
                    a.right = a.right.clone();
                }
                a = a.right;
            }
        }

        Node<T> newNode = new Node<T>(element, this.nil, this.nil);

        // empty tree -> new root
        if (path.size() == 0) {
            this.root = newNode;
        }
        else if (element.compareTo(path.get(path.size()-1).element) < 0) {
            path.get(path.size()-1).left = newNode;
            this.root = path.get(0); // @TODO fix bug
        }
        else {
            path.get(path.size()-1).right = newNode;
            this.root = path.get(0); // @TODO fix bug
        }

        path.add(newNode);

        // fix
        fix(path);
        this.roots.add(this.root);
        if (visualization) System.out.println("\u001B[32m" + "Red-Black-Tree stable" + "\u001B[0m");

    }

    private void fix(ArrayList<Node<T>> path) {

        Node<T> node = path.get(path.size()-1);
        Node<T> parent = getParent(node, path);
        Node<T> grandParent = getParent(parent, path);

        while (parent.red) {

            if (debug) {
                System.out.println("Node: " + node.element);
                System.out.println("Parent: " + parent.element);
                System.out.println("Grandpa: " + grandParent.element);
                this.printPath(path);
            }

            if (parent.equals(grandParent.left)) {
                if (debug) System.out.println("Left");

                if (grandParent.right.red) {
                    if (debug) System.out.println("Case 1");
                    if (visualization) printViz("Case 1: uncle is red -> flip color");

                    // case 1
                    parent.setRed(false);
                    Node<T> uncle = grandParent.right.clone();
                    grandParent.right = uncle;
                    uncle.setRed(false);
                    grandParent.setRed(true);


                    node = grandParent;
                    parent = getParent(node, path);
                    grandParent = getParent(parent, path);
                }
                else if (node.equals(parent.right)) {
                    if (debug) System.out.println("Case 2");
                    if (visualization) printViz("Case 2 -> left rotate \"" + parent.element + "\"");
                    // case 2
                    leftRotate(parent, grandParent);

                    // swap in path
                    path.add(path.indexOf(parent), path.remove(path.indexOf(node)));

                    node = parent;
                    parent = getParent(node, path);

                }
                else {
                    if (debug) System.out.println("Case 3");
                    if (visualization) printViz("Case 3 -> right rotate \"" + grandParent.element + "\"");
                    // case 3
                    parent.setRed(false);
                    grandParent.setRed(true);
                    rightRotate(grandParent, getParent(grandParent, path));
                }

            }

            else {
                if (debug) System.out.println("Right");

                if (grandParent.left.red) {
                    if (debug) System.out.println("Case 1");
                    if (visualization) printViz("Case 1 -> uncle is red -> flip color");
                    // case 1
                    parent.setRed(false);
                    Node<T> uncle = grandParent.left.clone();
                    grandParent.left = uncle;
                    uncle.setRed(false);
                    grandParent.setRed(true);


                    node = grandParent;
                    parent = getParent(node, path);
                    grandParent = getParent(parent, path);
                }
                else if (node.equals(parent.left)) {
                    if (debug) System.out.println("Case 2");
                    if (visualization) printViz("Case 2 -> right rotate \"" + parent.element + "\"");
                    // case 2
                    rightRotate(parent, grandParent);

                    // swap in path
                    path.add(path.indexOf(parent), path.remove(path.indexOf(node)));

                    node = parent;
                    parent = getParent(node, path);
                }
                else {
                    if (debug) System.out.println("Case 3");
                    if (visualization) printViz("Case 3 -> left rotate \"" + grandParent.element + "\"");
                    // case 3
                    parent.setRed(false);
                    grandParent.setRed(true);
                    leftRotate(grandParent, getParent(grandParent, path));
                }

                /*
                // same wirh right and left exchanged
                if (node.parent.parent.left.red) {
                    // case 1
                    node.parent.setRed(false);
                    Node<T> uncle = node.parent.parent.left.clone();
                    node.parent.parent.left = uncle;
                    uncle.setRed(false);
                    node.parent.parent.setRed(true);
                    node = node.parent.parent;
                } else if (node.equals(node.parent.left)) {
                    // case 2
                    node = node.parent;
                    rightRotate(node);
                } else {
                    // case 3
                    node.parent.setRed(false);
                    node.parent.parent.setRed(true);
                    leftRotate(node.parent.parent);
                }
                */
            }
        }

        // set root black
        this.root.setRed(false);
    }

    private Node<T> getParent(Node<T> node, ArrayList<Node<T>> path) {
        return path.indexOf(node)-1 > -1 ? path.get(path.indexOf(node)-1) : this.nil;
    }

    public void leftRotate(Node<T> node, Node<T> parent) {
        if (debug) System.out.println("Left rotate node: " + node.element + ", parent:" + parent.element);

        Node<T> right = node.right;
        if (!right.left.equals(this.nil)) {
            node.right = right.left; // clone???
        }
        else {
            node.right = this.nil;
        }
        if (node.equals(this.root)) {
            this.root = right;
        }
        else if (node.equals(parent.left)) {
            parent.left = right;
        }
        else {
            parent.right = right;
        }
        right.left = node;
    }

    public void rightRotate(Node<T> node, Node<T> parent) {
        if (debug) System.out.println("Right rotate node: " + node.element + ", parent:" + parent.element);

        Node<T> left = node.left;
        if (!left.right.equals(this.nil)) {
            node.left = left.right; // clone??
        }
        else {
            node.left = this.nil;
        }
        if (node.equals(this.root)) {
            this.root = left;
        }
        else if (node.equals(parent.left)) {
            parent.left = left;
        }
        else {
            parent.right = left;
        }
        left.right = node;
    }

    // misc

    public ArrayList<T> inOrder() {
        ArrayList<T> arrayList = new ArrayList<T>();
        inOrderRec(this.root, arrayList);
        return arrayList;
    }

    private void inOrderRec(Node<T> node, ArrayList<T> arrayList) {
        if (node == this.nil) {
            return;
        }
        inOrderRec(node.left, arrayList);
        arrayList.add(node.element);
        inOrderRec(node.right, arrayList);
    }

    public String print() {
        String print = this.printRec(this.root, "", "+", this.root);
        System.out.print(print);
        return print;
    }

    public String printAll() {
        String print = "";
        for (int i = 0; i < this.roots.size(); i++) {
            print += "Tree " + (i + 1) + ":\n";
            print += this.printRec(this.roots.get(i), "", "+", this.roots.get(i));
            print += "\n";
        }
        System.out.print(print);
        return print;

    }
    private String printRec(Node<T> node, String padding, String nodeId, Node<T> root) {
        if (node == this.nil) {
            return "";
        }

        String print = "";

        print += this.printRec(node.right, padding + (nodeId.equals("L") ? "| " : "  "), "R", root);

        print += padding + nodeId + "-" + (node.red ? "\u001B[31m" : "") + node.element.toString() + "\u001B[0m" + "\n";

        print += this.printRec(node.left, padding + (nodeId.equals("R") ? "| " : "  "), "L", root);
        return print;
    }

    public int getBlackHeight() {
        int bh = 1;
        Node<T> a = this.root;
        while (a != this.nil) {
            if (!a.red) bh ++;
            a = a.left;
        }
        return bh;
    }

    public void testBlackHeight() {
        int bhLeft = 0;
        Node<T> a = this.root;
        while (a != this.nil) {
            if (!a.red) bhLeft ++;
            a = a.left;
        }
        int bhRight = 0;
        a = this.root;
        while (a != this.nil) {
            if (!a.red) bhRight ++;
            a = a.right;
        }

        int bhCenter = 0;
        a = this.root;
        boolean left = true;
        while (a != this.nil) {
            if (!a.red) bhCenter ++;
            if (left) {
                a = a.left;
                left = false;
            }
            else {
                a = a.right;
                left = true;
            }
        }
        if (bhLeft == bhCenter && bhCenter == bhRight) {
            System.out.println("\u001B[32m" + "Black height \"" + bhLeft + "\" valid" + "\u001B[0m");
        }
        else {
            System.out.println("\u001B[31m" + "BLACK HEIGHT ERROR" + "\u001B[0m");
        }
    }

    // @TODO remove
    public void printRoots() {
        System.out.print("Roots: ");
        for (Node<T> node : roots) {
            System.out.print(node.element + " ");
        }
        System.out.println();
    }

    // @TODO remove
    private void printPath(ArrayList<Node<T>> path) {
        System.out.print("Path: ");
        for (Node<T> node : path) {
            System.out.print(node.element + " ");
        }
        System.out.println();
    }

    private void printViz(String str) {
        System.out.println("\u001B[32m" + "Visualization" + "\u001B[0m");
        print();
        System.out.println("\u001B[33m" + str + "\u001B[0m");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void restoreTree(Integer root) {
        this.root = this.roots.get(root);
        System.out.println("Restored");
    }

    public Boolean getVisualization() {
        return visualization;
    }

    public void setVisualization(Boolean visualization) {
        this.visualization = visualization;
    }
}
