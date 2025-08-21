import java.util.Scanner;

public class Jordan {
    public static void main(String[] args) {
        String introText = "Hello! I'm Jordan!\n"
                + "How can I help you?\n";
        System.out.println(introText);
        Scanner scanner = new Scanner(System.in);
        boolean isEcho = true;
        while (isEcho){
            String phrase = scanner.next();
            if (phrase.equals("bye")){
                isEcho = false;
            }
            System.out.println(phrase);
        }
        System.out.println("Bye! See you again!\n");
    }
}
