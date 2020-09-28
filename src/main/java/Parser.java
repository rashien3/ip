import task.Task;

public class Parser {
    public static Task parseCommand(String inputCommand) {
        Task task = null;
        String firstWord = Ui.getFirstWordOf(inputCommand);

        try {
            switch (firstWord) {
            case "list":
                try {
                    Ui.printList();
                } catch (DukeException e) {
                    Ui.printEmptyListError();
                }
                break;
            case "done":
                TaskList.markDone(inputCommand);
                break;
            case "todo":
                try {
                    task = TaskList.addTodo(inputCommand);
                } catch (DukeException e) {
                    Ui.printTodoError();
                }
                break;
            case "deadline":
                try {
                    task = TaskList.addDeadline(inputCommand);
                } catch (DukeException e) {
                    Ui.printDeadlineError();
                }
                break;
            case "event":
                try {
                    task = TaskList.addEvent(inputCommand);
                } catch (DukeException e) {
                    Ui.printEventError();
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
            Storage.save();
        } catch (DukeException e) {
            Ui.printInvalidCommandError(firstWord);
        }
        return task;
    }
}