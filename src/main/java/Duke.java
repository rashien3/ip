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
    private static int numberOfTasks = 0;

    public static String firstWordOf(String input){
        if(input.contains(" "))
            return inputCommand.substring(0,inputCommand.indexOf(' '));
        else
            return input;
    }

    public static String removeFirstWordOf(String input){
        if(input.contains(" "))
            return inputCommand.substring(inputCommand.indexOf(' ') + 1);
        else
            return "";
    }

    public static void printList(){
        for(int i = 0; i < taskList.size(); i++){
            Task t = taskList.get(i);
            System.out.println( (i+1) + ". " + t.toString());
        }
    }

    public static void markDone(String inputCommand){
        int taskNumber = Integer.parseInt(removeFirstWordOf(inputCommand)) - 1;
        Task inputTask = taskList.get(taskNumber);
        inputTask.setDone(true);
        System.out.println("Nice! I've marked this task as done:\n\t" + inputTask.toString());
    }

    public static void addTodo(String inputCommand){
        Task t = new ToDo(removeFirstWordOf(inputCommand));
        addTask(t);
    }

    public static void addDeadline(String inputCommand){
        int byIndex = inputCommand.indexOf("/by");

        String description = inputCommand.substring(9, byIndex - 1);
        String by = inputCommand.substring(byIndex + 4);

        Task t = new Deadline(description, by);
        addTask(t);
    }

    public static void addEvent(String inputCommand){
        int atIndex = inputCommand.indexOf("/at");

        String description = inputCommand.substring(6, atIndex - 1);
        String at = inputCommand.substring(atIndex + 4);

        Task t = new Event(description, at);
        addTask(t);
    }

    public static void addTask(Task t){
        taskList.add(t);
        System.out.println("Got it. I've added this task:\n\t" + t.toString());
        System.out.println("Now you have " + (++numberOfTasks) + " tasks in the list.");
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
                markDone(inputCommand);
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
