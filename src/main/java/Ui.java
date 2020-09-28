import task.Task;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Ui {
    static final String MESSAGE_LINEBREAK = "____________________________________________________________";
    static final String MESSAGE_GREETING = MESSAGE_LINEBREAK + "\n" +
            "Hello! I'm Duke\n" +
            "What can I do for you?\n" +
            MESSAGE_LINEBREAK;
    static final String MESSAGE_BYE = "Bye. Hope to see you again soon!";
    static final String MESSAGE_CREATING_DIRECTORY = " not found. Creating directory...";
    static final String MESSAGE_CREATING_FILE = " not found. Creating file...";
    static final String MESSAGE_ERROR_DONE = "ERROR: 'done' must be followed by an integer";
    static final String MESSAGE_ERROR_TODO = "ERROR: 'todo' must be followed by a desciption";
    static final String MESSAGE_ERROR_DELETE = "ERROR: 'delete' must be followed by a desciption";
    static final String MESSAGE_ERROR_DEADLINE = "ERROR: 'deadline' must be followed by a desciption";
    static final String MESSAGE_ERROR_DEADLINE_MISSING_BY = "ERROR: 'deadline' must have a /by tag followed by a time";
    static final String MESSAGE_ERROR_EVENT = "ERROR: 'event' must be followed by a desciption";
    static final String MESSAGE_ERROR_EVENT_MISSING_AT = "ERROR: 'event' must have a /at tag followed by a time";
    static final String MESSAGE_ERROR_EMPTY_LIST = "ERROR: You have no tasks!";
    static final String MESSAGE_ERROR_FILE = "ERROR: data.txt not found";
    static String inputCommand = "";

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

    public static String readInput() {
        Scanner in = new Scanner(System.in);
        return in.nextLine();
    }

    public static String getFirstWordOf(String input) {
        if (input.contains(" ")) {
            return inputCommand.substring(0, inputCommand.indexOf(' '));
        } else {
            return input;
        }
    }

    public static String removeFirstWordOf(String input) {
        if (input.contains(" ")) {
            return input.substring(input.indexOf(' ') + 1);
        } else {
            return "";
        }
    }

    public static void printList() throws DukeException {
        if (TaskList.taskList.size() == 0) {
            throw new DukeException();
        }

        for (int i = 0; i < TaskList.taskList.size(); i++) {
            Task t = TaskList.taskList.get(i);
            System.out.println((i + 1) + ". " + t.toString());
        }
    }

    public static void printAddTaskMessage(Task task) {
        System.out.println("Got it. I've added this task:\n\t" + task.toString());
        System.out.println("Now you have " + TaskList.numberOfTasks + " tasks in the list.");
    }

    public static void printCreatingDirectoryMessage(String dirPath) {
        System.out.println(dirPath + MESSAGE_CREATING_DIRECTORY);
    }

    public static void printCreatingFileMessage(String filePathString) {
        System.out.println(filePathString + MESSAGE_CREATING_FILE);
    }

    public static void printDeadlineError() {
        System.out.println(MESSAGE_ERROR_DEADLINE);
    }

    public static void printFileLoadedMessage(File file) throws IOException {
        System.out.println("File loaded from: " + file.getCanonicalPath());
    }

    public static void printFileCreatedMessage(File file) throws IOException {
        System.out.println("File created at: " + file.getCanonicalPath());
    }

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

    public static void printInvalidCommandError(String firstWord) {
        System.out.println("ERROR: '" + firstWord + "' is not a valid command");
    }

    public static void printByeMessage() {
        System.out.println(MESSAGE_BYE);
    }

    public static void printLinebreak() {
        System.out.println(MESSAGE_LINEBREAK);
    }
}