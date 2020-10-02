import task.Task;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Deals with all the printing all messages, and reading the user's commands.
 * These methods are called whenever the program does anything or encounters an error.
 * This class is entirely static because it just prints things
 */
public class Ui {
    private static final String MESSAGE_LINEBREAK = "____________________________________________________________";
    private static final String MESSAGE_GREETING = MESSAGE_LINEBREAK + "\n" +
            "Hello! I'm Duke\n" +
            "What can I do for you?\n" +
            MESSAGE_LINEBREAK;
    private static final String MESSAGE_BYE = "Bye. Hope to see you again soon!";
    private static final String MESSAGE_CREATING_DIRECTORY = " not found. Creating directory...";
    private static final String MESSAGE_CREATING_FILE = " not found. Creating file...";
    private static final String MESSAGE_FIND = "Here are the matching tasks in your list:";
    private static final String MESSAGE_ERROR_DONE = "ERROR: 'done' must be followed by an integer";
    private static final String MESSAGE_ERROR_TODO = "ERROR: 'todo' must be followed by a desciption";
    private static final String MESSAGE_ERROR_DELETE = "ERROR: 'delete' must be followed by an integer";
    private static final String MESSAGE_ERROR_DEADLINE = "ERROR: 'deadline' must be followed by a desciption";
    private static final String MESSAGE_ERROR_DEADLINE_MISSING_BY = "ERROR: 'deadline' must have a /by tag followed by a time";
    private static final String MESSAGE_ERROR_EVENT = "ERROR: 'event' must be followed by a desciption";
    private static final String MESSAGE_ERROR_EVENT_MISSING_AT = "ERROR: 'event' must have a /at tag followed by a time";
    private static final String MESSAGE_ERROR_EMPTY_LIST = "ERROR: You have no tasks!";
    private static final String MESSAGE_ERROR_FILE = "ERROR: data.txt not found";
    private static final String MESSAGE_ERROR_FIND = "ERROR: No results containing your search term was found";
    private static String inputCommand = "";

    public static void printMissingByError() {
        System.out.println(MESSAGE_ERROR_DEADLINE_MISSING_BY);
    }

    public static void printDoneError() {
        System.out.println(MESSAGE_ERROR_DONE);
    }

    public static void printMissingAtError() {
        System.out.println(MESSAGE_ERROR_EVENT_MISSING_AT);
    }

    public static void printDeleteError() {
        System.out.println(MESSAGE_ERROR_DELETE);
    }

    /**
     * Reads in one line from the command terminal
     *
     * @return The line the user inputted
     */
    public static String readInput() {
        Scanner in = new Scanner(System.in);
        inputCommand = in.nextLine();
        return inputCommand;
    }

    /**
     * prints an indexed list of tasks, whether it's done, and its description, and time (if applicable)
     * eg.
     * 1. [T][✘] laundry
     * 2. [E][✘] CS tutorial (at: 2pm)
     *
     * @param tasks ArrayList of tasks to be printed
     * @throws DukeException tasks is empty
     */
    public static void printList(ArrayList<Task> tasks) throws DukeException {
        Ui.printLinebreak();
        if (tasks.size() == 0) {
            throw new DukeException();
        }

        for (int i = 0; i < tasks.size(); i++) {
            Task t = tasks.get(i);
            System.out.println((i + 1) + ". " + t.toString());
        }
        Ui.printLinebreak();
    }

    public static void printGreetingMessage() {
        System.out.println(MESSAGE_GREETING);
    }

    /**
     * Prints the 'task successfully added' message
     *
     * @param task          Task that was added
     * @param numberOfTasks Number of tasks in taskList
     */
    public static void printAddTaskMessage(Task task, int numberOfTasks) {
        Ui.printLinebreak();
        System.out.println("Got it. I've added this task:\n\t" + task.toString());
        System.out.println("Now you have " + numberOfTasks + " tasks in the list.");
        Ui.printLinebreak();
    }

    public static void printCreatingDirectoryMessage(String dirPath) {
        System.out.println(dirPath + MESSAGE_CREATING_DIRECTORY);
    }

    /**
     * Prints the 'creating file' message
     *
     * @param filePathString path of file
     */
    public static void printCreatingFileMessage(String filePathString) {
        System.out.println(filePathString + MESSAGE_CREATING_FILE);
    }

    public static void printDeadlineError() {
        System.out.println(MESSAGE_ERROR_DEADLINE);
    }

    /**
     * Prints the 'file successfully loaded' error message
     *
     * @param file File that was loaded
     * @throws IOException File does not exist
     */
    public static void printFileLoadedMessage(File file) throws IOException {
        System.out.println("File loaded from: " + file.getCanonicalPath());
    }

    /**
     * Prints the 'file created' message
     *
     * @param file File that was created
     * @throws IOException File does not exist
     */
    public static void printFileCreatedMessage(File file) throws IOException {
        System.out.println("File created at: " + file.getCanonicalPath());
    }

    /**
     * prints the 'file not found' error
     *
     * @param filePathString Path that file is supposed to be at
     */
    public static void printFileError(String filePathString) {
        System.out.println("ERROR: Can't find file at: " + filePathString);
    }

    public static void printFileError() {
        System.out.println(MESSAGE_ERROR_FILE);
    }

    public static void printEventError() {
        System.out.println(MESSAGE_ERROR_EVENT);
    }

    public static void printTodoError() {
        System.out.println(MESSAGE_ERROR_TODO);
    }

    public static void printEmptyListError() {
        System.out.println(MESSAGE_ERROR_EMPTY_LIST);
    }

    /**
     * Prints the 'invalid command' error message
     *
     * @param firstWord First word of the line the user inputted
     */
    public static void printInvalidCommandError(String firstWord) {
        System.out.println("ERROR: '" + firstWord + "' is not a valid command");
    }

    public static void printByeMessage() {
        Ui.printLinebreak();
        System.out.println(MESSAGE_BYE);
        Ui.printLinebreak();
    }

    public static void printLinebreak() {
        System.out.println(MESSAGE_LINEBREAK);
    }

    public static String getInputCommand() {
        return inputCommand;
    }

    public static void printFindError() {
        System.out.println(MESSAGE_ERROR_FIND);
    }

    public static void printFindMessage() {
        System.out.println(MESSAGE_FIND);
    }

    /**
     * Prints the 'task does not exist' error
     *
     * @param taskNumber Number of task that the user tried to interact with
     */
    public static void printTaskDoesNotExistError(int taskNumber) {
        System.out.println("ERROR: task '" + taskNumber + "' does not exist");
    }

    /**
     * Prints the 'task removed successfully' message
     *
     * @param description   Description of task that was removed
     * @param numberOfTasks Number of tasks in taskList after removal
     */
    public static void printTaskRemovedMessage(String description, int numberOfTasks) {
        printLinebreak();
        System.out.println("Noted. I've removed this task:\n\t" + description);
        System.out.println("Now you have " + numberOfTasks + " tasks in the list.");
        printLinebreak();
    }

    /**
     * Prints the 'mark done successfully' message, and the task that was marked as done
     *
     * @param taskNumber  Index number of the task that was marked done
     * @param description Description of the task that was marked done
     */
    public static void printMarkDoneMessage(int taskNumber, String description) {
        printLinebreak();
        System.out.println("Nice! I've marked this task as done:\n\t" + taskNumber + ". " + description);
        printLinebreak();
    }
}