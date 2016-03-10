import java.util.ArrayList;

/**
 * Created by John on 09/03/16.
 */
public class RBTree<T extends Comparable> {

    private Node<T> root;
    private Node<T> nil;

    public RBTree() {
        this.nil = new Node<T>(null, null, null,null);
        this.nil.parent = this.nil;
        this.nil.left = this.nil;
        this.nil.right = this.nil;
        this.nil.setRed(false);

        this.root = this.nil;
    }

    private class Node<T> {
        private T element;
        private Node<T> parent;
        private Node<T> left;
        private Node<T> right;
        private Boolean red;

        public Node(T element, Node<T> parent, Node<T> left, Node<T> right) {
            this.element = element;
            this.parent = parent;
            this.left = left;
            this.right = right;
            this.red = true;
        }

        public void setRed(Boolean red) {
            this.red = red;
        }
    }

    public void insert(T element) {
        Node<T> parent = this.nil;
        Node<T> a = this.root;

        // walk down tree
        while (a != this.nil) {
            parent = a;
            if (element.compareTo(a.element) < 0) {
                a = a.left;
            }
            else {
                a = a.right;
            }
        }

        Node<T> newNode;

        // empty tree -> new root
        if (parent == nil) {
            newNode = new Node<T>(element, this.nil, this.nil, this.nil);
            this.root = newNode;
        }
        else if (element.compareTo(parent.element) < 0) {
            newNode = new Node<T>(element, parent, this.nil, this.nil);
            parent.left = new Node<T>(element, parent, this.nil, this.nil);
        }
        else {
            newNode = new Node<T>(element, parent, this.nil, this.nil);
            parent.right = new Node<T>(element, parent, this.nil, this.nil);
        }

        // fix
        fix(newNode);
    }

    private void fix(Node <T> node) {
        while (node.parent.red) {
            if (node.parent.equals(node.parent.parent.left)) {
                Node<T> uncle = node.parent.parent.right;
                if (uncle.red) {
                    // case 1
                    node.parent.setRed(false);
                    uncle.setRed(false);
                    node.parent.parent.setRed(true);
                    node = node.parent.parent;
                }
                else if (node.equals(node.parent.right)) {
                    // case 2
                    node = node.parent;
                    leftRotate(node);
                }
                else {
                    // case 3
                    node.parent.setRed(false);
                    node.parent.parent.setRed(true);
                    rightRotate(node.parent.parent);
                }
            }
            else {
                // @TODO left right exchanged
                System.out.println("WARNING, NOT IMPLEMENTED!");
                // same wirh right and left exchanged
                break;
            }
        }

        // set root black
        this.root.setRed(false);
    }

    public void leftRotate(Node<T> node) {
        Node<T> right = node.right;
        node.right = right.left;
        if (!right.left.equals(this.nil)) {
            right.left.parent = node;
        }
        right.parent = node.parent;
        if (node.parent.equals(this.nil)) {
            this.root = right;
        }
        else if (node.equals(node.parent.left)) {
            node.parent.left = right;
        }
        else {
            node.parent.right = right;
        }
        right.left = node;
        node.parent = right;
    }

    public void rightRotate(Node<T> node) {
        Node<T> left = node.left;
        node.left = left.right;
        if (!left.right.equals(this.nil)) {
            left.right.parent = node;
        }
        left.parent = node.parent;
        if (left.parent.equals(this.nil)) {
            this.root = left;
        }
        else if (node.equals(node.parent.left)) {
            node.parent.left = left;
        }
        else {
            node.parent.right = left;
        }
        left.right = node;
        node.parent = left;
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
        String print = "";
        print = this.printRec(this.root, "", "+");
        System.out.print(print);
        return print;
    }

    public String printRec(Node<T> node, String padding, String nodeId) {
        if (node == this.nil) {
            return "";
        }
        String print = padding + nodeId + "-" + (node.red ? "\u001B[31m" : "") + node.element.toString() + "\u001B[0m" + "\n";

        print += this.printRec(node.left, padding + (node.parent.right != this.nil ? "| " : "  "), "L");
        print += this.printRec(node.right, padding + "  ", "R");
        return print;
    }

    public int getBlackHeight() {
        int bh = 1;
        Node<T> a = this.root;
        while (a != this.nil) {
            bh ++;
            a = a.left;
        }
        return bh;
    }
}
