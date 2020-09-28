import task.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Duke {
    // file path
    private static final String home = System.getProperty("user.home");

    // inserts correct file path separator to data.txt file
    private static final Path filePath = Paths.get(home, "Documents","ip","src", "main", "resources", "data.txt");
    private static final Path dirPath = Paths.get(home, "Documents","ip","src", "main", "resources");
    private static final boolean directoryExists = Files.exists(dirPath);

    private static final String MESSAGE_LINEBREAK = "____________________________________________________________";
    private static final String MESSAGE_GREETING = MESSAGE_LINEBREAK + "\n" +
            "Hello! I'm Duke\n" +
            "What can I do for you?\n" +
            MESSAGE_LINEBREAK;
    private static final String MESSAGE_BYE = "Bye. Hope to see you again soon!";
    private static final String MESSAGE_CREATING_DIRECTORY = " not found. Creating directory...";
    private static final String MESSAGE_CREATING_FILE = " not found. Creating file...";
    private static final String MESSAGE_ERROR_DONE = "ERROR: 'done' must be followed by an integer";
    private static final String MESSAGE_ERROR_TODO = "ERROR: 'todo' must be followed by a desciption";
    private static final String MESSAGE_ERROR_DELETE = "ERROR: 'delete' must be followed by a desciption";
    private static final String MESSAGE_ERROR_DEADLINE = "ERROR: 'deadline' must be followed by a desciption";
    private static final String MESSAGE_ERROR_DEADLINE_MISSING_BY = "ERROR: 'deadline' must have a /by tag followed by a time";
    private static final String MESSAGE_ERROR_EVENT = "ERROR: 'event' must be followed by a desciption";
    private static final String MESSAGE_ERROR_EVENT_MISSING_AT = "ERROR: 'event' must have a /at tag followed by a time";
    private static final String MESSAGE_ERROR_EMPTY_LIST = "ERROR: You have no tasks!";
    private static final String MESSAGE_ERROR_FILE = "ERROR: data.txt not found";


    private static String inputCommand = "";

    public static void printMissingByError() {
        System.out.println(Duke.MESSAGE_ERROR_DEADLINE_MISSING_BY);
    }

    public static void printDoneError() {
        System.out.println(Duke.MESSAGE_ERROR_DONE);
    }

    public static void printMissingAtError() {
        System.out.println(Duke.MESSAGE_ERROR_EVENT_MISSING_AT);
    }

    public static void printDeleteError() {
        System.out.println(Duke.MESSAGE_ERROR_DELETE);
    }

    public static String readInput() {
        Scanner in = new Scanner(System.in);
        return in.nextLine();
    }
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
            return input.substring(input.indexOf(' ') + 1);
        }
        else{
            return "";
        }
    }

    public static void printList() throws DukeException {
        if(TaskList.taskList.size() == 0){
            throw new DukeException();
        }

        for(int i = 0; i < TaskList.taskList.size(); i++) {
            Task t = TaskList.taskList.get(i);
            System.out.println( (i+1) + ". " + t.toString());
        }
    }

    public static void printAddTaskMessage(Task task) {
        System.out.println("Got it. I've added this task:\n\t" + task.toString());
        System.out.println("Now you have " + TaskList.numberOfTasks + " tasks in the list.");
    }

    public static void load() {
        String filePathString = filePath.toString();
        File file = new File(filePathString);
        try {
            if(!directoryExists) {
                printCreatingDirectoryMessage();
                File directory = new File(dirPath.toString());
                directory.mkdir();
            }
            if (file.createNewFile()) {
                printCreatingFileMessage(filePathString);
                printFileCreatedMessage(file);
            } else {
                printFileLoadedMessage(file);
            }
        } catch (IOException e) {
            printFileError(filePathString);
        }

        FileInputStream stream;
        try{
            stream = new FileInputStream(filePathString);

            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            try {
                String strLine;
                // format: T/D/E | 0/1 | taskname /by time
                while ((strLine = reader.readLine()) != null) {
                    Task t = null;
                    switch (strLine.charAt(0)) {
                    case 'T':
                        try{
                            t = TaskList.addTodo(strLine);
                        } catch(DukeException e) {
                            printTodoError();
                        }
                        break;
                    case 'D':
                        try{
                            t = TaskList.addDeadline(strLine);
                        } catch(DukeException e) {
                            printDeadlineError();
                        }
                        break;
                    case 'E':
                        try{
                            t = TaskList.addEvent(strLine);
                        } catch(DukeException e) {
                            printEventError();
                        }
                        break;
                    default:
                        return;
                    }
                    if (strLine.charAt(2) == '1') {
                        t.setDone(true);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw e;
            }
        } catch(IOException e) {
            printFileError();
        }
    }

    public static void printCreatingDirectoryMessage() {
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

    public static void save(){
        StringBuilder lines = new StringBuilder();

        for (int i = 0; i < TaskList.numberOfTasks; i++) {
            Task task = TaskList.taskList.get(i);
            if (task instanceof ToDo) {
                lines.append("T|" + (task.getDone()?"1" : "0") + "| " + task.getDescription());
            } else if (task instanceof Deadline) {
                lines.append("D|" + (task.getDone()?"1" : "0") + "| " + task.getDescription()
                        + " /by " + ((Deadline) task).getBy());
            } else if (task instanceof Event) {
                lines.append("E|" + (task.getDone()?"1" : "0") + "| " + task.getDescription()
                        + " /at " + ((Event) task).getEventTime());
            } else {
                return;
            }
            lines.append('\n');
        }

        // Write to file
        File file = new File(filePath.toString());
        FileWriter fr = null;
        try {
            fr = new FileWriter(file);
            fr.write(lines.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // close resources
            try {
                if (fr != null) {
                    fr.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                printFileError();
            }
        }
    }

    public static void printFileError() {
        System.out.println(MESSAGE_ERROR_FILE);
    }

    public static Task parseCommand(String inputCommand) {
        Task task = null;
        String firstWord = getFirstWordOf(inputCommand);

        try{
            switch(firstWord){
            case "list":
                try{
                    printList();
                } catch(DukeException e){
                    printEmptyListError();
                }
                break;
            case "done":
                TaskList.markDone(inputCommand);
                break;
            case "todo":
                try{
                    task = TaskList.addTodo(inputCommand);
                } catch(DukeException e) {
                    printTodoError();
                }
                break;
            case "deadline":
                try{
                    task = TaskList.addDeadline(inputCommand);
                } catch(DukeException e) {
                    printDeadlineError();
                }
                break;
            case "event":
                try{
                    task = TaskList.addEvent(inputCommand);
                } catch(DukeException e) {
                    printEventError();
                }
                break;
            case "delete":
                TaskList.delete(inputCommand);
                break;
            case "bye":
                return null;
            default:
                throw new DukeException();
            }
            save();
        } catch (DukeException e){
            printInvalidCommandError(firstWord);
        }
        return task;
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

    public static void main(String[] args) {
        load();
        System.out.println(MESSAGE_GREETING);

        do {
            inputCommand = readInput();
            Task task = parseCommand(inputCommand);
            printLinebreak();
            if(task != null) {
                printAddTaskMessage(task);
                printLinebreak();
            };
        } while (!inputCommand.equals("bye"));

        printByeMessage();
        printLinebreak();
    }

    public static void printByeMessage() {
        System.out.println(MESSAGE_BYE);
    }

    public static void printLinebreak() {
        System.out.println(MESSAGE_LINEBREAK);
    }

}