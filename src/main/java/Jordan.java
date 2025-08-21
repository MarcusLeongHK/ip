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
                    Task currTask = tasks.get(i);
                    System.out.printf("%d." + currTask.toString() + "\n",i+1);
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
                System.out.print(markedTask);
            }
            else{
                if (phrase.startsWith("todo ")){
                    String desc = phrase.substring(5).trim();
                    tasks.add(new Todo(desc));
                }
                else if (phrase.startsWith("deadline ")){
                    int indexOfBy = phrase.indexOf("/by");
                    String desc = phrase.substring(9, indexOfBy).trim();
                    String by = phrase.substring(indexOfBy+3).trim();
                    tasks.add(new Deadline(desc,by));
                }
                else if (phrase.startsWith("event ")) {
                    int fromIndex = phrase.indexOf("/from");
                    int toIndex = phrase.indexOf("/to");
                    String desc = phrase.substring(6, fromIndex).trim();
                    String from = phrase.substring(fromIndex + 5, toIndex).trim();
                    String to = phrase.substring(toIndex + 3).trim();
                    tasks.add(new Event(desc, from, to));
                }
                System.out.println("I have added task: " + tasks.get(tasks.size()-1));
                System.out.println("Now you have " + tasks.size() + " tasks in the list");
            }
        }
        System.out.println("Bye! See you again!\n");
    }
}
