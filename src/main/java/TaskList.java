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

    /**
     *
     * @param taskNumber
     */
    public static void markDone(int taskNumber) {
        Task selectedTask;

        try {
            selectedTask = taskList.get(taskNumber);
        } catch (IndexOutOfBoundsException e) {
            Ui.printTaskDoesNotExistError(taskNumber + 1);
            return;
        }

        selectedTask.setDone(true);
        Ui.printMarkDoneMessage(taskNumber + 1, selectedTask.toString());
    }



    public static Task addTodo(String description) throws DukeException {

        if (description.equals("")) {
            throw new DukeException();
        }

        Task t = new ToDo(description);
        addTask(t);
        return t;
    }

    public static Task addDeadline(String deadlineCommand) throws DukeException {
        String description = "", by = "";
        int byIndex;

        if (deadlineCommand.equals("") || deadlineCommand.equals(TAG_BY.trim())) {
            throw new DukeException();
        }

        try {
            byIndex = deadlineCommand.indexOf(TAG_BY);
            description = deadlineCommand.substring(0, byIndex - 1);
            by = deadlineCommand.substring(byIndex + 4);
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



    public static Task addEvent(String eventCommand) throws DukeException {
        String description = "", at = "";
        int atIndex;

        eventCommand = eventCommand.trim();
        if (eventCommand.equals("") || eventCommand.equals(TAG_AT.trim())) {
            throw new DukeException();
        }

        try {
            atIndex = eventCommand.indexOf(TAG_AT);
            description = eventCommand.substring(0, atIndex - 1);
            at = eventCommand.substring(atIndex + 4);
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

    public static void delete(int taskNumber) {
        Task selectedTask;

        try {
            selectedTask = taskList.get(taskNumber);
        } catch (IndexOutOfBoundsException e) {
            Ui.printTaskDoesNotExistError(taskNumber);
            return;
        }

        Ui.printTaskRemovedMessage(selectedTask.toString(), numberOfTasks--);
        taskList.remove(taskNumber);
    }

    public static Task getTask (int i) {
        return taskList.get(i);
    }

    public static int getNumberOfTasks() {
        return numberOfTasks;
    }

    public static void find(String inputCommand) {
        String toFind = Parser.removeFirstWordOf(inputCommand);
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