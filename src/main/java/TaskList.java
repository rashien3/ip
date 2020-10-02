import task.Deadline;
import task.Event;
import task.Task;
import task.ToDo;

import java.util.ArrayList;

/**
 * Contains the list of tasks
 * Deals with everything related to adding and deleting, or changing Tasks
 * These methods are called by Parser when intepreting a user's command,
 * or by Storage when loading from file.
 */
public class TaskList {
    private ArrayList<Task> tasks = new ArrayList<Task>();
    private int numberOfTasks = 0;

    public TaskList() {
    }
    public TaskList(ArrayList<Task> taskList) {
        this.tasks = taskList;
    }

    /**
     * Mark the numbered task as done
     *
     * @param taskNumber Number of task to be marked done - 1
     */
    public void markDone(int taskNumber) {
        Task selectedTask;

        try {
            selectedTask = tasks.get(taskNumber);
        } catch (IndexOutOfBoundsException e) {
            Ui.printTaskDoesNotExistError(taskNumber + 1);
            return;
        }

        selectedTask.setDone(true);
        Ui.printMarkDoneMessage(taskNumber + 1, selectedTask.toString());
    }


    /**
     * Adds a Todo with the input description
     *
     * @param description Description of the task
     * @return Todo task added by this method
     * @throws DukeException Empty description
     */
    public Task addTodo(String description) throws DukeException {

        if (description.equals("")) {
            throw new DukeException();
        }

        Task t = new ToDo(description);
        addTask(t);
        return t;
    }

    /**
     * Adds a Deadline with the input description and 'by'
     *
     * @param deadlineCommand Description of the task + /by tag + 'by'
     * @return Deadline task added through this method
     * @throws DukeException Either description or /by is empty
     */
    public Task addDeadline(String deadlineCommand) throws DukeException {
        String description = "", by = "";
        int byIndex;

        String TAG_BY = "/by ";
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

    /**
     * Adds an Event with the input description and 'at'
     *
     * @param eventCommand Description of the task + /at tag + 'at'
     * @return Event task added through this method
     * @throws DukeException Either description or /at is empty
     */
    public Task addEvent(String eventCommand) throws DukeException {
        String description = "", at = "";
        int atIndex;

        eventCommand = eventCommand.trim();
        String TAG_AT = "/at ";
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

    /**
     * Adds task to this instance of taskList
     *
     * @param t Task to be added to taskList
     */
    public void addTask(Task t) {
        tasks.add(t);
        numberOfTasks++;
    }

    /**
     * Delete task from taskList
     * @param taskNumber Index number of task to be deleted - 1
     */
    public void delete(int taskNumber) {
        Task selectedTask;

        try {
            selectedTask = tasks.get(taskNumber);
        } catch (IndexOutOfBoundsException e) {
            Ui.printTaskDoesNotExistError(taskNumber + 1);
            return;
        }

        Ui.printTaskRemovedMessage(selectedTask.toString(), numberOfTasks--);
        tasks.remove(taskNumber);
    }

    public Task getTask (int i) {
        return tasks.get(i);
    }

    public int getNumberOfTasks() {
        return numberOfTasks;
    }

    /**
     * Searches each task's description for the query,
     * then lists all of the tasks that contains the query
     *
     * @param query Term to search for
     */
    public void find(String query) {
        ArrayList<Task> displayList = new ArrayList<Task>();

        for(Task t : tasks) {
            if(t.getDescription().contains(query)) {
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

    public ArrayList<Task> getTasks() {
        return tasks;
    }
}