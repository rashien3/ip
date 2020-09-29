import task.Task;

public class Parser {
    public static Task parseCommand(String input) {
        Task task = null;
        String command = getFirstWordOf(input);

        try {
            switch (command) {
            case "list":
                try {
                    Ui.printList(TaskList.getTaskList());
                } catch (DukeException e) {
                    Ui.printEmptyListError();
                }
                break;
            case "done":
                try {
                    int taskNumber = Integer.parseInt(removeFirstWordOf(input)) - 1;
                    TaskList.markDone(taskNumber);
                } catch (NumberFormatException e) {
                    Ui.printDoneError();
                }

                break;
            case "todo":
                try {
                    task = TaskList.addTodo(removeFirstWordOf(input));
                } catch (DukeException e) {
                    Ui.printTodoError();
                }
                break;
            case "deadline":
                try {
                    task = TaskList.addDeadline(removeFirstWordOf(input));
                } catch (DukeException e) {
                    Ui.printDeadlineError();
                }
                break;
            case "event":
                try {
                    task = TaskList.addEvent(removeFirstWordOf(input));
                } catch (DukeException e) {
                    Ui.printEventError();
                }
                break;
            case "delete":
                try {
                    int taskNumber = Integer.parseInt(removeFirstWordOf(input)) - 1;
                    TaskList.delete(taskNumber);
                } catch (NumberFormatException e) {
                    Ui.printDeleteError();
                }
                break;
            case "find":
                TaskList.find(removeFirstWordOf(input));
            case "bye":
                return null;
            default:
                throw new DukeException();
            }
            Storage.save();
        } catch (DukeException e) {
            Ui.printInvalidCommandError(command);
        }
        return task;
    }

    public static String getFirstWordOf(String input) {
        if (input.contains(" ")) {
            return input.substring(0, input.indexOf(' '));
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
}