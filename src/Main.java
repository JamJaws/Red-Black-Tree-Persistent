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

                        if (input.equals("a")) {
                            rbTree.printAll();
                        }
                        else if (input.equals("r")) {
                            System.out.print("Tree #: ");
                            input = reader.nextLine();
                            rbTree.restoreTree(Integer.parseInt(input)-1);
                        }
                        else {
                            rbTree.insert(Integer.parseInt(input));
                            rbTree.print();
                        }
                    }
                    break;
                case ("2"):
                    Random rand = new Random();
                    Integer inserts = 0;

                    System.out.print("#: ");
                    input = reader.nextLine();
                    Integer nr = Integer.parseInt(input);

                    System.out.print("From: ");
                    input = reader.nextLine();
                    Integer from = Integer.parseInt(input);

                    System.out.print("To: ");
                    input = reader.nextLine();
                    Integer to = Integer.parseInt(input);



                    for (int i = 0; i < 1; i++) {

                        rbTree = new RBTree<>();
                        while (inserts < nr) {
                            Integer random = rand.nextInt(to - from + 1) + from;
                            inserts++;
                            // System.out.println(inserts + ": inserting \"" + nr + "\"");
                            rbTree.insert(random);
                            // rbTree.print();
                        }
                        rbTree.print();
                        // rbTree.testBlackHeight();
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
