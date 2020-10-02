import task.*;

import java.io.IOException;

public class Duke {

    private final TaskList taskList;
    private final Parser parser;
    private final Storage storage;

    /**
     * initialises the taskList, parser and storage classes
     *
     * @param filePath Path of the file to load from under Documents file
     */
    public Duke(String filePath) {
        TaskList taskList_temp;
        storage = new Storage(filePath);
        try {
            taskList_temp = storage.load();
        } catch (IOException e) {
            Ui.printFileError();
            taskList_temp = new TaskList();
        }
        taskList = taskList_temp;
        parser = new Parser(storage, taskList);
    }

    /**
     * Prints greeting message,
     * runs parser on loop until user inputs 'bye',
     * then prints bye message
     */
    public void run() {
        Ui.printGreetingMessage();
        do {
            Task task = parser.parse(Ui.readInput());
            if (task != null) {
                Ui.printAddTaskMessage(task, taskList.getNumberOfTasks());
            }
            ;
        } while (!Ui.getInputCommand().equals("bye"));

        Ui.printByeMessage();
    }

    public static void main(String[] args) {
        new Duke("ip/data/data.txt").run();
    }
}