import org.testng.annotations.Test;

import java.util.Arrays;

import static org.testng.AssertJUnit.assertEquals;

/**
 * Created by John on 09/03/16.
 */
public class RBTreeTest {

    @Test
    public void treeInsert() {
        RBTree<Integer> tree = new RBTree<Integer>();
        tree.insert(5);
        assertEquals(Arrays.asList(5), tree.inOrder());
    }

    @Test
    public void treeInsertMultiple() {
        RBTree<Integer> tree = new RBTree<Integer>();
        tree.insert(5);
        tree.insert(6);
        tree.insert(4);
        tree.insert(7);
        tree.insert(3);
        tree.insert(2);
        tree.print();
        assertEquals((Arrays.asList(2, 3, 4, 5, 6, 7)), tree.inOrder());
    }

    @Test
    public void blackHeightOneElement() {
        RBTree<Integer> tree = new RBTree<Integer>();
        tree.insert(5);
        assertEquals(2, tree.getBlackHeight());
    }

    @Test
    public void oneTwoThreeFour() {
        RBTree<Integer> tree = new RBTree<Integer>();
        tree.insert(1);
        tree.insert(2);
        tree.insert(3);
        tree.insert(4);
        tree.print();
        assertEquals((Arrays.asList(1, 2, 3, 4)), tree.inOrder());
    }

    /*
    @Test
    public void oneTwoThreeFourStructure() {
        RBTree<Integer> tree = new RBTree<Integer>();
        tree.insert(1);
        tree.insert(2);
        tree.insert(3);
        tree.insert(4);
        assertEquals(
                "+-1\n" +
                "  R-2\n" +
                "    R-3\n" +
                "      R-4",
                tree.print());
    }
    */

    @Test
    public void uncleCase() {
        RBTree<Integer> tree = new RBTree<Integer>();
        tree.insert(5);
        tree.insert(6);
        tree.insert(4);
        tree.insert(3);
        tree.print();
        assertEquals((Arrays.asList(3, 4, 5, 6)), tree.inOrder());
    }





}