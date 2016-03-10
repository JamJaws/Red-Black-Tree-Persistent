import java.util.Scanner;

/**
 * Created by John on 10/03/16.
 */
public class Main {


    public static void main(String[] args) {
        RBTree<Integer> rbTree = new RBTree<Integer>();
        Scanner reader = new Scanner(System.in);

        System.out.print("Insert: ");
        String input = reader.next();
        while (input.toLowerCase() != "exit") {
            System.out.print("Insert: ");
            input = reader.next();
            rbTree.insert(Integer.parseInt(input));
            rbTree.print();
        }


    }
}
