import java.util.Random;
import java.util.Scanner;

/**
 * Created by John on 10/03/16.
 */
public class Main {


    public static void main(String[] args) {
        RBTree<Integer> rbTree = new RBTree<Integer>();
        Scanner reader = new Scanner(System.in);

        System.out.println("1. Manual, 2. Random");
        System.out.print("Input: ");
        String input = "";
            input = reader.nextLine();
        switch (input) {
            case ("1"):
                while (input.toLowerCase() != "exit") {
                    System.out.print("Insert: ");
                    input = reader.next();
                    rbTree.insert(Integer.parseInt(input));
                    // rbTree.print();
                    rbTree.printAll();
                }
                break;
            case ("2"):
                Random rand = new Random();
                Integer inserts = 0;

                while (inserts < 10) {
                    Integer nr = rand.nextInt(50) + 1;
                    rbTree.insert(nr);
                    inserts++;
                }
                rbTree.print();
                rbTree.printAll();

                break;
            default:

                break;
        }
    }
}
