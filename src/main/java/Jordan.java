import java.util.ArrayList;
import java.util.Scanner;

public class Jordan {
    public static void main(String[] args) {
        ArrayList<Task> tasks = new ArrayList<>();
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
                    String msg = "%d.[%s] %s";
                    Task currTask = tasks.get(i);
                    System.out.printf((msg) + "%n",i+1,currTask.getStatusIcon(),currTask.description);
                }
            }
            else if (phrase.equals("bye")){
                isEcho = false;
            }
            else if (phrase.startsWith("mark ")){
                Scanner markScanner = new Scanner(phrase);
                markScanner.next();
                int taskNumber = markScanner.nextInt();
                System.out.println("Nice! I've marked this task as complete!");
                Task markedTask = tasks.get(taskNumber-1);
                markedTask.markAsDone();
                System.out.printf("[%s] %s \n", markedTask.getStatusIcon(),markedTask.description);
            }
            else {
                tasks.add(new Task(phrase));
                System.out.println("added: " + phrase + "\n");
            }
        }
        System.out.println("Bye! See you again!\n");
    }
}
