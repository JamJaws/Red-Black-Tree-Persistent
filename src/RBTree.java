import java.util.ArrayList;

/**
 * Created by John on 09/03/16.
 */
public class RBTree<T extends Comparable> {

    private Node<T> root;
    private Node<T> nil;
    private ArrayList<Node<T>> roots;

    public RBTree() {
        this.nil = new Node<T>(null, null, null,null);
//        this.nil.parent = this.nil;
        this.nil.left = this.nil;
        this.nil.right = this.nil;
        this.nil.setRed(false);

        this.root = this.nil;
        this.roots = new ArrayList<>();
    }

    private class Node<T> {
        private T element;
        // private Node<T> parent;
        private Node<T> left;
        private Node<T> right;
        private Boolean red;

        public Node(T element, Node<T> left, Node<T> right) {
            this.element = element;
//            this.parent = parent;
            this.left = left;
            this.right = right;
            this.red = true;
        }

        public Node(T element, Node<T> left, Node<T> right, Boolean red) {
            this.element = element;
//            this.parent = parent;
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

        ArrayList<Node<T>> parents = new ArrayList<>();

//        this.root = (!this.root.equals(this.nil) ? this.root.clone() : this.nil);

        // walk down tree
//        Node<T> parent = this.nil;
        Node<T> a = this.root;
        while (!a.equals(this.nil)) {
//            parent = a;

            if (element.compareTo(a.element) < 0) {
                parents.add(a.clone());
//                if (!a.left.equals(this.nil)) {
//
////                    a.left = a.left.clone();
////                    a.left.parent = a;
//                }
                //clone.parent = parentClone;
                //parentClone.left = clone;
                //parentClone = clone;
                a = a.left;
            }
            else {
                parents.add(a.clone());
//                if (!a.right.equals(this.nil)) {
////                    a.right = a.right.clone();
////                    a.right.parent = a;
//                }
                //Node<T> clone = a.left.clone();
                //clone.parent = parentClone;
                //parentClone.right = clone;
                //parentClone = clone;
                a = a.right;
            }
        }

        Node<T> newNode = new Node<T>(element, this.nil, this.nil);

        // empty tree -> new root
        if (parents.size() == 0) {
            this.root = newNode;
        }
        else if (element.compareTo(parents.get(parents.size()-1).element) < 0) {
            parents.get(parents.size()-1).left = newNode;
            this.root = parents.get(0); // @TODO fix bug
        }
        else {
            parents.get(parents.size()-1).right = newNode;
            this.root = parents.get(0); // @TODO fix bug
        }

        // fix
        fix(newNode, parents);
        this.roots.add(this.root);
    }

    private void fix(Node<T> node, ArrayList<Node<T>> parents) {
        // last parent
        int parentOffset = 0;
        Node<T> parent = parents.size() > 1 ? parents.get(parents.size()-1) : this.nil;
        Node<T> grandParent = parents.size() > 2 ? parents.get(parents.size()-2) : this.nil;


        while (!parent.equals(this.nil) && parent.red) {
            if (parent.equals(grandParent.left)) {

                if (grandParent.right.red) {
                    // case 1
                    parent.setRed(false);
                    Node<T> uncle = grandParent.right.clone();
                    grandParent.right = uncle;
                    uncle.setRed(false);
                    grandParent.setRed(true);
                    node = grandParent;

                    parentOffset += 2;
                    parent = parents.size() > parentOffset + 1 ? parents.get(parents.size() - parentOffset - 1) : this.nil;
                    grandParent = parents.size() > parentOffset + 2 ? parents.get(parents.size() - parentOffset - 2) : this.nil;
                }
                else if (node.equals(parent.right)) {
                    // case 2
                    node = parent;
                    leftRotate(node, grandParent);
                }
                else {
                    // case 3
                    parent.setRed(false);
                    grandParent.setRed(true);
                    rightRotate(grandParent, parents.get(parents.size()-3));
                }
            }
            /*
            else {
                // same wirh right and left exchanged
                if (node.parent.parent.left.red) {
                    // case 1
                    node.parent.setRed(false);
                    Node<T> uncle = node.parent.parent.left.clone();
                    node.parent.parent.left = uncle;
                    uncle.setRed(false);
                    node.parent.parent.setRed(true);
                    node = node.parent.parent;
                }
                else if (node.equals(node.parent.left)) {
                    // case 2
                    node = node.parent;
                    rightRotate(node);
                }
                else {
                    // case 3
                    node.parent.setRed(false);
                    node.parent.parent.setRed(true);
                    leftRotate(node.parent.parent);
                }
            }
            */
        }

        // set root black
        this.root.setRed(false);
    }

    public void leftRotate(Node<T> node, Node<T> parent) {
        Node<T> right = node.right;
        // node.right = right.left;
        if (!right.left.equals(this.nil)) {
            node.right = right.left; // clone???
//            node.right.parent = node;
        }
        else {
            node.right = this.nil;
        }
//        right.parent = node.parent;
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
//        node.parent = right;
    }

    public void rightRotate(Node<T> node, Node<T> parent) {
        Node<T> left = node.left;
        // node.left = left.right;
        if (!left.right.equals(this.nil)) {
            node.left = left.right; // clone??
//            node.left.parent = node;
        }
        else {
            node.left = this.nil;
        }
//        left.parent = node.parent;
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
//        node.parent = left;
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
            print += "\n\n";
        }
        System.out.print(print);
        return print;

    }
    private String printRec(Node<T> node, String padding, String nodeId, Node<T> root) {
        if (node == this.nil) {
            return "";
        }
        if (!node.equals(root)) {
//            padding += (node.parent.equals(node.parent.parent.left) && !node.parent.parent.right.equals(this.nil) ? "| " : "  ");
            padding += "  ";
        }
        String print = padding + nodeId + "-" + (node.red ? "\u001B[31m" : "") + node.element.toString() + "\u001B[0m" + "\n";

        print += this.printRec(node.left, padding, "L", root);
        print += this.printRec(node.right, padding, "R", root);
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
