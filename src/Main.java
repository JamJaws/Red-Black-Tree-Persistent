import java.util.Random;
import java.util.Scanner;

/**
 * Created by John on 10/03/16.
 */
public class Main {


    public static void main(String[] args) {
        RBTree<Integer> rbTree = new RBTree<Integer>();
        Scanner reader = new Scanner(System.in);

        System.out.println("1. Manual, 2. Random, p. print, all. print all revisions, new. new tree, t. test black height, v. enable visualisation, r. restore tree");
        System.out.print("Input: ");
        String input = "";
        input = reader.nextLine();
        while (!input.equals("exit")) {
            switch (input) {
                case ("1"):
                    while (!input.toLowerCase().equals("exit")) {
                        System.out.print("Type exit to cancel input");
                        System.out.print("Insert: ");
                        input = reader.nextLine();

                        try {
                            rbTree.insert(Integer.parseInt(input));
                            rbTree.print();
                        } catch (NumberFormatException e) {

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



                    while (inserts < nr) {
                        Integer random = rand.nextInt(to - from + 1) + from;
                        inserts++;
                        // System.out.println(inserts + ": inserting \"" + nr + "\"");
                        rbTree.insert(random);
                    }
                    rbTree.print();


                    break;
                case ("all"):
                    rbTree.printAll();

                    break;

                case ("new"):
                    rbTree = new RBTree<Integer>();
                    break;

                case ("r"):

                    System.out.print("Tree #: ");
                    input = reader.nextLine();
                    rbTree.restoreTree(Integer.parseInt(input)-1);

                    break;

                case ("p"):
                    rbTree.print();

                    break;

                case ("v"):
                    rbTree.setVisualization(!rbTree.getVisualization());

                    if (rbTree.getVisualization()) {
                        System.out.println("Visualization enabled");
                    }
                    else {
                        System.out.println("Visualization disabled");
                    }

                    break;

                case ("t"):
                    rbTree.testBlackHeight();
                    break;


                default:

                    break;
            }
            System.out.print("Input: ");
            input = reader.nextLine();
        }
    }
}
