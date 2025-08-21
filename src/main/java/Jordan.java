import java.util.ArrayList;
import java.util.Scanner;

public class Jordan {
    public static void main(String[] args) {
        ArrayList<String> tasks = new ArrayList<>();
        String introText = "Hello! I'm Jordan!\n"
                + "How can I help you?";
        System.out.println(introText);
        Scanner scanner = new Scanner(System.in);
        boolean isEcho = true;
        while (isEcho){
            System.out.println("Add task: ");
            String phrase = scanner.nextLine();
            if (phrase.equals("list")) {
                for (int i = 0; i < tasks.size(); i++) {
                    System.out.println(i + 1 + "." + tasks.get(i));
                }
            }
            else {
                tasks.add(phrase);
                System.out.println("added: " + phrase + "\n");
            }
            if (phrase.equals("bye")){
                isEcho = false;
            }
        }
        System.out.println("Bye! See you again!\n");
    }
}
