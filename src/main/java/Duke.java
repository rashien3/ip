import task.Deadline;
import task.Event;
import task.Task;
import task.ToDo;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.ArrayList;

public class Duke {
    // file path
    private static final String root = System.getProperty("user.dir");

    // inserts correct file path separator to data.txt file
    private static final Path filePath = Paths.get(root, "src", "main", "resources", "data.txt");
    private static final Path dirPath = Paths.get(root, "src", "main", "resources");
    private static final boolean directoryExists = Files.exists(dirPath);

    private static final String MESSAGE_LINEBREAK = "____________________________________________________________";
    private static final String MESSAGE_GREETING = MESSAGE_LINEBREAK + "\n" +
            "Hello! I'm Duke\n" +
            "What can I do for you?\n" +
            MESSAGE_LINEBREAK;
    private static final String MESSAGE_BYE = "Bye. Hope to see you again soon!";
    private static final String MESSAGE_CREATING_FILE = "data.txt not found. Creating file...";
    private static final String MESSAGE_ERROR_INVALID_COMMAND = " is not a valid command";
    private static final String MESSAGE_ERROR_DONE = "ERROR: 'done' must be followed by an integer";
    private static final String MESSAGE_ERROR_TODO = "ERROR: 'todo' must be followed by a desciption";
    private static final String MESSAGE_ERROR_DEADLINE = "ERROR: 'deadline' must be followed by a desciption";
    private static final String MESSAGE_ERROR_DEADLINE_MISSING_BY = "ERROR: 'deadline' must have a /by tag followed by a time";
    private static final String MESSAGE_ERROR_EVENT = "ERROR: 'event' must be followed by a desciption";
    private static final String MESSAGE_ERROR_EVENT_MISSING_AT = "ERROR: 'event' must have a /at tag followed by a time";
    private static final String MESSAGE_ERROR_EMPTY_LIST = "ERROR: You have no tasks!";
    private static final String MESSAGE_ERROR_FILE = "ERROR: data.txt not found";
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
            return input.substring(input.indexOf(' ') + 1);
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

    public static Task addTodo(String inputCommand) throws DukeException {
        String todoName = removeFirstWordOf(inputCommand);

        if(todoName.equals("")){
            throw new DukeException();
        }

        Task t = new ToDo(todoName);
        addTask(t);
        return t;
    }

    public static Task addDeadline(String inputCommand) throws DukeException{
        String deadlineCommand, description = "", by = "";
        int byIndex;

        deadlineCommand = removeFirstWordOf(inputCommand);
        if(deadlineCommand.equals("") || deadlineCommand.equals(TAG_BY.trim())) {
            throw new DukeException();
        }

        try {
            byIndex = inputCommand.indexOf(TAG_BY);
            description = removeFirstWordOf(inputCommand.substring(0, byIndex - 1));
            by = inputCommand.substring(byIndex + 4);
            if(by.equals("")) {
                throw new DukeException();
            }
        } catch (StringIndexOutOfBoundsException | DukeException e){
            System.out.println(MESSAGE_ERROR_DEADLINE_MISSING_BY);
            return null;
        }

        Task t = new Deadline(description, by);
        addTask(t);
        return t;
    }

    public static Task addEvent(String inputCommand) throws DukeException {
        String description = "", at = "", eventCommand;
        int atIndex;

        eventCommand = removeFirstWordOf(inputCommand).trim();
        if(eventCommand.equals("") || eventCommand.equals(TAG_AT.trim())){
            throw new DukeException();
        }

        try{
            atIndex = inputCommand.indexOf(TAG_AT);
            description = removeFirstWordOf(inputCommand.substring(0, atIndex - 1));
            at = inputCommand.substring(atIndex + 4);
            if(at.equals("")) {
                throw new DukeException();
            }
        } catch (StringIndexOutOfBoundsException | DukeException e){
            System.out.println(MESSAGE_ERROR_EVENT_MISSING_AT);
            return null;
        }

        Task t = new Event(description, at);
        addTask(t);
        return t;
    }

    public static void addTask(Task t){
        taskList.add(t);
        System.out.println("Got it. I've added this task:\n\t" + t.toString());
        System.out.println("Now you have " + (++numberOfTasks) + " tasks in the list.");
    }

    public static void load() {
        String filePathString = filePath.toString();
        File file = new File(filePathString);
        try {
            if (file.createNewFile()) {
                System.out.println(MESSAGE_CREATING_FILE);
                System.out.println("File created at: " + file.getCanonicalPath());
            } else {
                System.out.println("File loaded from: " + file.getCanonicalPath());
            }
        } catch (IOException e) {
            e.printStackTrace();
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
                            t = addTodo(strLine);
                        } catch(DukeException e) {
                            System.out.println(MESSAGE_ERROR_TODO);
                        }
                        break;
                    case 'D':
                        try{
                            t = addDeadline(strLine);
                        } catch(DukeException e) {
                            System.out.println(MESSAGE_ERROR_DEADLINE);
                        }
                        break;
                    case 'E':
                        try{
                            t = addEvent(strLine);
                        } catch(DukeException e) {
                            System.out.println(MESSAGE_ERROR_EVENT);
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
            System.out.println(MESSAGE_ERROR_FILE);
        }
    }

    public static void save(){
        StringBuilder lines = new StringBuilder();

        for (int i = 0; i < numberOfTasks; i++) {
            Task task = taskList.get(i);
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
                System.out.println(MESSAGE_ERROR_FILE);
            }
        }
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


            save();
            } catch (DukeException e){
            System.out.println("'" + firstWord + "'" + MESSAGE_ERROR_INVALID_COMMAND);
        }
        System.out.println(MESSAGE_LINEBREAK);
    }

    public static void main(String[] args) {
        load();
        System.out.println(MESSAGE_GREETING);

        do {
            inputCommand = in.nextLine();
            parseCommand(inputCommand);
        } while (!inputCommand.equals("bye"));

        System.out.println(MESSAGE_BYE);
        System.out.println(MESSAGE_LINEBREAK);
    }
}
