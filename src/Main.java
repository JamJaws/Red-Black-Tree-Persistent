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
        while (!input.equals("exit")) {
            switch (input) {
                case ("1"):
                    while (input.toLowerCase() != "exit") {
                        System.out.print("Insert: ");
                        input = reader.nextLine();

                        if (input.equals("d")) {
                            rbTree.printAll();
                        }
                        rbTree.insert(Integer.parseInt(input));

                        rbTree.print();
                        // rbTree.printAll();
                    }
                    break;
                case ("2"):
                    Random rand = new Random();
                    Integer inserts = 0;

                    for (int i = 0; i < 1; i++) {

                        rbTree = new RBTree<>();
                        while (inserts < 20) {
                            Integer nr = rand.nextInt(50) + 1;
                            inserts++;
                            // System.out.println(inserts + ": inserting \"" + nr + "\"");
                            rbTree.insert(nr);
                            // rbTree.print();
                        }
                        rbTree.print();
                        inserts = 0;
                        System.out.println(i+1);

                    }

                    // rbTree.printAll();

                    break;
                case ("3"):
                    rbTree.printAll();
                default:

                    break;
            }
            input = reader.nextLine();
        }
    }
}
