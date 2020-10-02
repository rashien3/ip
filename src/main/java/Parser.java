import task.Task;

/**
 * Deals with intepretation of a user's command.
 * Removes the first word and calls the relevant method in TaskList
 */
public class Parser {
    Storage storage;
    TaskList taskList;

    public Parser(Storage storage, TaskList taskList) {
        this.storage = storage;
        this.taskList = taskList;
    }

    /**
     * Parse the user's input. The first word determines the command,
     * and everything after the first words are parameters for that command
     * Available commands:
     * 'list': prints all the tasks
     * 'done' [taskNumber]:marks the task as done
     * 'todo' [description]: adds a Todo task
     * 'deadline' [description] '/by' [by]: adds a Deadline task
     * 'event' [description] '/at' [at]: adds an Event task
     * 'delete' [taskNumber]: deletes a task
     * 'find' [query]: searches the tasks for the query
     * 'bye': terminate Duke
     *
     * @param input Line of text inputted by the user
     * @return If there was a task added, returns that task. Otherwise, null
     */
    public Task parse(String input) {
        Task task = null;
        String command = getFirstWordOf(input);

        try {
            switch (command) {
            case "list":
                try {
                    Ui.printList(taskList.getTasks());
                } catch (DukeException e) {
                    Ui.printEmptyListError();
                }
                break;
            case "done":
                try {
                    int taskNumber = Integer.parseInt(removeFirstWordOf(input)) - 1;
                    taskList.markDone(taskNumber);
                } catch (NumberFormatException e) {
                    Ui.printDoneError();
                }

                break;
            case "todo":
                try {
                    task = taskList.addTodo(removeFirstWordOf(input));
                } catch (DukeException e) {
                    Ui.printTodoError();
                }
                break;
            case "deadline":
                try {
                    task = taskList.addDeadline(removeFirstWordOf(input));
                } catch (DukeException e) {
                    Ui.printDeadlineError();
                }
                break;
            case "event":
                try {
                    task = taskList.addEvent(removeFirstWordOf(input));
                } catch (DukeException e) {
                    Ui.printEventError();
                }
                break;
            case "delete":
                try {
                    int taskNumber = Integer.parseInt(removeFirstWordOf(input)) - 1;
                    taskList.delete(taskNumber);
                } catch (NumberFormatException e) {
                    Ui.printDeleteError();
                }
                break;
            case "find":
                taskList.find(removeFirstWordOf(input));
            case "bye":
                return null;
            default:
                throw new DukeException();
            }
            storage.save(taskList);
        } catch (DukeException e) {
            Ui.printInvalidCommandError(command);
        }
        return task;
    }

    /**
     * Returns the first word of input String
     *
     * @param input Sentence you want the first word only of
     * @return First word of input String
     */
    public static String getFirstWordOf(String input) {
        if (input.contains(" ")) {
            return input.substring(0, input.indexOf(' '));
        } else {
            return input;
        }
    }

    /**
     * Returns a sentence without the first word
     *
     * @param input Sentence you want the first word removed from
     * @return The rest of the sentence without the first word
     */
    public static String removeFirstWordOf(String input) {
        if (input.contains(" ")) {
            return input.substring(input.indexOf(' ') + 1);
        } else {
            return "";
        }
    }
}