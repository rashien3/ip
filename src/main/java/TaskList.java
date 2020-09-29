import task.Deadline;
import task.Event;
import task.Task;
import task.ToDo;

import java.util.ArrayList;

public class TaskList {
    private static final String TAG_BY = "/by ";
    private static final String TAG_AT = "/at ";
    private static ArrayList<Task> taskList = new ArrayList<Task>();
    private static int numberOfTasks = 0;

    public static void markDone(String inputCommand) {
        int taskNumber;
        Task selectedTask;

        try {
            taskNumber = Integer.parseInt(Ui.removeFirstWordOf(inputCommand)) - 1;
        } catch (NumberFormatException e) {
            Ui.printDoneError();
            return;
        }

        try {
            selectedTask = taskList.get(taskNumber);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("ERROR: task '" + (taskNumber + 1) + "' does not exist");
            return;
        }

        selectedTask.setDone(true);
        System.out.println("Nice! I've marked this task as done:\n\t" + selectedTask.toString());
    }



    public static Task addTodo(String inputCommand) throws DukeException {
        String todoName = Ui.removeFirstWordOf(inputCommand);

        if (todoName.equals("")) {
            throw new DukeException();
        }

        Task t = new ToDo(todoName);
        addTask(t);
        return t;
    }

    public static Task addDeadline(String inputCommand) throws DukeException {
        String deadlineCommand, description = "", by = "";
        int byIndex;

        deadlineCommand = Ui.removeFirstWordOf(inputCommand);
        if (deadlineCommand.equals("") || deadlineCommand.equals(TAG_BY.trim())) {
            throw new DukeException();
        }

        try {
            byIndex = inputCommand.indexOf(TAG_BY);
            description = Ui.removeFirstWordOf(inputCommand.substring(0, byIndex - 1));
            by = inputCommand.substring(byIndex + 4);
            if (by.equals("")) {
                throw new DukeException();
            }
        } catch (StringIndexOutOfBoundsException | DukeException e) {
            Ui.printMissingByError();
            return null;
        }

        Task t = new Deadline(description, by);
        addTask(t);
        return t;
    }



    public static Task addEvent(String inputCommand) throws DukeException {
        String description = "", at = "", eventCommand;
        int atIndex;

        eventCommand = Ui.removeFirstWordOf(inputCommand).trim();
        if (eventCommand.equals("") || eventCommand.equals(TAG_AT.trim())) {
            throw new DukeException();
        }

        try {
            atIndex = inputCommand.indexOf(TAG_AT);
            description = Ui.removeFirstWordOf(inputCommand.substring(0, atIndex - 1));
            at = inputCommand.substring(atIndex + 4);
            if (at.equals("")) {
                throw new DukeException();
            }
        } catch (StringIndexOutOfBoundsException | DukeException e) {
            Ui.printMissingAtError();
            return null;
        }

        Task t = new Event(description, at);
        addTask(t);
        return t;
    }
    
    public static void addTask(Task t) {
        taskList.add(t);
        numberOfTasks++;
    }

    public static void delete(String inputCommand) {
        int taskNumber;
        Task selectedTask;

        try {
            taskNumber = Integer.parseInt(Ui.removeFirstWordOf(inputCommand)) - 1;
        } catch (NumberFormatException e) {
            Ui.printDeleteError();
            return;
        }

        try {
            selectedTask = taskList.get(taskNumber);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("ERROR: task '" + (taskNumber + 1) + "' does not exist");
            return;
        }

        System.out.println("Noted. I've removed this task:\n\t" + selectedTask.toString());
        System.out.println("Now you have " + (--numberOfTasks) + " tasks in the list.");
        taskList.remove(taskNumber);
    }

    public static Task getTask (int i) {
        return taskList.get(i);
    }

    public static int getNumberOfTasks() {
        return numberOfTasks;
    }

    public static void find(String inputCommand) {
        String toFind = Ui.removeFirstWordOf(inputCommand);
        ArrayList<Task> displayList = new ArrayList<Task>();

        for(Task t : taskList) {
            if(t.getDescription().contains(toFind)) {
                displayList.add(t);
            }
        }
        try {
            Ui.printFindMessage();
            Ui.printList(displayList);
        } catch (DukeException ex) {
            Ui.printFindError();
        }
    }

    public static ArrayList<Task> getTaskList() {
        return taskList;
    }
}