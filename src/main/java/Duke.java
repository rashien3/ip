import java.util.Scanner;
import java.util.ArrayList;

public class Duke {
    private static final String MESSAGE_LINEBREAK = "____________________________________________________________";
    private static final String MESSAGE_GREETING = MESSAGE_LINEBREAK + "\n" +
            "Hello! I'm Duke\n" +
            "What can I do for you?\n" +
            MESSAGE_LINEBREAK;
    private static final String MESSAGE_BYE = "Bye. Hope to see you again soon!";
    private static final String MESSAGE_ERROR = "Please enter a valid command";
    private static ArrayList<Task> taskList = new ArrayList<Task>();
    private static Scanner in = new Scanner(System.in);
    private static String inputCommand = "";

    public static String firstWordOf(String input){
        if(input.contains(" "))
            return inputCommand.substring(0,inputCommand.indexOf(' '));
        else
            return input;
    }

    public static String removeFirstWord(String input){
        if(input.contains(" "))
            return inputCommand.substring(inputCommand.indexOf(' '));
        else
            return "";
    }

    public static void printList(){
        for(int i = 0; i < taskList.size(); i++){
            Task t = taskList.get(i);
            System.out.println( (i+1) + ". " + t.toString());
        }
    }

    public static void markDone(int taskNumber){
        Task inputTask = taskList.get(taskNumber);
        inputTask.setDone(true);
        System.out.println("Nice! I've marked this task as done:\n" +
                "[" + inputTask.getStatusIcon() + "] " + inputTask.getDescription());
    }

    public static void addTodo(String inputCommand){

    }

    public static void addDeadline(String inputCommand){

    }

    public static void addEvent(String inputCommand){

    }

    public static void main(String[] args) {
        System.out.println(MESSAGE_GREETING);
        while(!inputCommand.toLowerCase().equals("bye")){
            inputCommand = in.nextLine();
            System.out.println(MESSAGE_LINEBREAK);
            switch(firstWordOf(inputCommand)){
            case "list":
                printList();
                break;
            case "bye":
                System.out.println(MESSAGE_BYE);
                break;
            case "done":
                int taskNumber = Integer.parseInt(inputCommand.substring(5)) - 1;
                markDone(taskNumber);
                break;
            case "add":
                taskList.add(new Task(inputCommand));
                System.out.println("added: " + inputCommand);
                break;
            case "todo":
                addTodo(inputCommand);
                break;
            case "deadline":
                addDeadline(inputCommand);
                break;
            case "event":
                addEvent(inputCommand);
                break;
            default:
                System.out.println(MESSAGE_ERROR);
                break;
            }
            System.out.println(MESSAGE_LINEBREAK);
        }

    }
}
