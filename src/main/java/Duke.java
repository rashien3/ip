import task.Deadline;
import task.Event;
import task.Task;
import task.ToDo;

import java.util.Scanner;
import java.util.ArrayList;

public class Duke {
    private static final String MESSAGE_LINEBREAK = "____________________________________________________________";
    private static final String MESSAGE_GREETING = MESSAGE_LINEBREAK + "\n" +
            "Hello! I'm Duke\n" +
            "What can I do for you?\n" +
            MESSAGE_LINEBREAK;
    private static final String MESSAGE_BYE = "Bye. Hope to see you again soon!";
    private static final String MESSAGE_ERROR_INVALID_COMMAND = " is not a valid command";
    private static final String MESSAGE_ERROR_DONE = "ERROR: 'done' must be followed by an integer";
    private static final String MESSAGE_ERROR_TODO = "ERROR: 'todo' must be followed by a desciption";
    private static final String MESSAGE_ERROR_DEADLINE = "ERROR: 'deadline' must be followed by a desciption";
    private static final String MESSAGE_ERROR_DEADLINE_MISSING_BY = "ERROR: 'deadline' must have a /by tag followed by a time";
    private static final String MESSAGE_ERROR_EVENT = "ERROR: 'event' must be followed by a desciption";
    private static final String MESSAGE_ERROR_EVENT_MISSING_AT = "ERROR: 'event' must have a /at tag followed by a time";
    private static final String MESSAGE_ERROR_EMPTY_LIST = "ERROR: You have no tasks!";
    private static final String TAG_BY = "/by ";
    private static final String TAG_AT = "/at ";
    private static ArrayList<Task> taskList = new ArrayList<Task>();
    private static Scanner in = new Scanner(System.in);
    private static String inputCommand = "";
    private static int numberOfTasks = 0;

    public static String getFirstWordOf(String input) {
        if(input.contains(" ")) {
            return inputCommand.substring(0,inputCommand.indexOf(' '));
        }
        else{
            return input;
        }
    }

    public static String removeFirstWordOf(String input) {
        if(input.contains(" ")){
            return inputCommand.substring(inputCommand.indexOf(' ') + 1);
        }
        else{
            return "";
        }
    }

    public static void printList() throws DukeException {
        if(taskList.size() == 0){
            throw new DukeException();
        }

        for(int i = 0; i < taskList.size(); i++) {
            Task t = taskList.get(i);
            System.out.println( (i+1) + ". " + t.toString());
        }
    }

    public static void markDone(String inputCommand) {
        int taskNumber;
        Task selectedTask;

        try{
            taskNumber = Integer.parseInt(removeFirstWordOf(inputCommand)) - 1;
        } catch (NumberFormatException e) {
            System.out.println(MESSAGE_ERROR_DONE);
            return;
        }

        try{
            selectedTask = taskList.get(taskNumber);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("ERROR: task '" + (taskNumber + 1) + "' does not exist");
            return;
        }

        selectedTask.setDone(true);
        System.out.println("Nice! I've marked this task as done:\n\t" + selectedTask.toString());
    }

    public static void addTodo(String inputCommand) throws DukeException {
        String todoName = removeFirstWordOf(inputCommand);

        if(todoName.equals("")){
            throw new DukeException();
        }

        Task t = new ToDo(todoName);
        addTask(t);
    }

    public static void addDeadline(String inputCommand) throws DukeException{
        String deadlineCommand, description = "", by = "";
        int byIndex;

        deadlineCommand = removeFirstWordOf(inputCommand);
        if(deadlineCommand.equals("") || deadlineCommand.equals(TAG_BY.trim())) {
            throw new DukeException();
        }

        try {
            byIndex = inputCommand.indexOf(TAG_BY);
            description = inputCommand.substring(9, byIndex - 1);
            by = inputCommand.substring(byIndex + 4);
            if(by.equals("")) {
                throw new DukeException();
            }
        } catch (StringIndexOutOfBoundsException | DukeException e){
            System.out.println(MESSAGE_ERROR_DEADLINE_MISSING_BY);
            return;
        }

        Task t = new Deadline(description, by);
        addTask(t);
    }

    public static void addEvent(String inputCommand) throws DukeException {
        String description = "", at = "", eventCommand;
        int atIndex;

        eventCommand = removeFirstWordOf(inputCommand).trim();
        if(eventCommand.equals("") || eventCommand.equals(TAG_AT.trim())){
            throw new DukeException();
        }

        try{
            atIndex = inputCommand.indexOf(TAG_AT);
            description = inputCommand.substring(6, atIndex - 1);
            at = inputCommand.substring(atIndex + 4);
            if(at.equals("")) {
                throw new DukeException();
            }
        } catch (StringIndexOutOfBoundsException | DukeException e){
            System.out.println(MESSAGE_ERROR_EVENT_MISSING_AT);
            return;
        }

        Task t = new Event(description, at);
        addTask(t);
    }

    public static void addTask(Task t){
        taskList.add(t);
        System.out.println("Got it. I've added this task:\n\t" + t.toString());
        System.out.println("Now you have " + (++numberOfTasks) + " tasks in the list.");
    }

    public static void parseCommand(String inputCommand) {
        String firstWord = getFirstWordOf(inputCommand);

        System.out.println(MESSAGE_LINEBREAK);
        try{
            switch(firstWord){
            case "list":
                try{
                    printList();
                } catch(DukeException e){
                    System.out.println(MESSAGE_ERROR_EMPTY_LIST);
                }
                break;
            case "done":
                markDone(inputCommand);
                break;
            case "todo":
                try{
                    addTodo(inputCommand);
                } catch(DukeException e) {
                    System.out.println(MESSAGE_ERROR_TODO);
                }
                break;
            case "deadline":
                try{
                    addDeadline(inputCommand);
                } catch(DukeException e) {
                    System.out.println(MESSAGE_ERROR_DEADLINE);
                }
                break;
            case "event":
                try{
                    addEvent(inputCommand);
                } catch(DukeException e) {
                    System.out.println(MESSAGE_ERROR_EVENT);
                }
                break;
            case "bye":
                return;
            default:
                throw new DukeException();
            }
        } catch (DukeException e){
            System.out.println("'" + firstWord + "'" + MESSAGE_ERROR_INVALID_COMMAND);
        }
        System.out.println(MESSAGE_LINEBREAK);
    }

    public static void main(String[] args) {
        System.out.println(MESSAGE_GREETING);

        do {
            inputCommand = in.nextLine();
            parseCommand(inputCommand);
        } while (!inputCommand.equals("bye"));

        System.out.println(MESSAGE_BYE);
        System.out.println(MESSAGE_LINEBREAK);
    }
}
