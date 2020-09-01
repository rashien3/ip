import java.util.Scanner;
import java.util.ArrayList;

public class Duke {
    public static void main(String[] args) {
        static final String MESSAGE_GREETING = "\t____________________________________________________________\n" +
                "\tHello! I'm Duke\n" +
                "\tWhat can I do for you?\n" +
                "\t____________________________________________________________\n";

        System.out.println(MESSAGE_GREETING);

        ArrayList<Task> taskList = new ArrayList<Task>();
        Scanner in = new Scanner(System.in);
        String inputCommand = "";
        while(!inputCommand.toLowerCase().equals("bye")){
            inputCommand = in.nextLine();
            System.out.println("\t____________________________________________________________");
            switch(inputCommand){
            // list out tasks
            case "list":
                for(int i = 0; i < taskList.size(); i++){
                    Task t = taskList.get(i);
                    System.out.println( "\t" + (i+1) + ". " + "[" + t.getStatusIcon() + "] " + t.getDescription());
                }
                break;
            //break loop
            case "bye":
                System.out.println("\tBye. Hope to see you again soon!");
                break;
            default:
                // done input
                if(inputCommand.length() > 5 && inputCommand.substring(0,4).toLowerCase().equals("done")){
                    Task inputTask = taskList.get(Integer.parseInt(inputCommand.substring(5)) - 1);
                    inputTask.setDone(true);
                    System.out.println("\tNice! I've marked this task as done:\n" +
                            "\t[" + inputTask.getStatusIcon() + "] " + inputTask.getDescription());
                }else{ // add input
                    taskList.add(new Task(inputCommand));
                    System.out.println("\tadded: " + inputCommand);
                }
                break;
            }
            System.out.println("\t____________________________________________________________");
        }

    }
}
