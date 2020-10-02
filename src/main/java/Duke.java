import task.*;

public class Duke {

    private final TaskList taskList;
    private final Parser parser;

    /**
     * initialises the 3 supporting classes
     * @param filePath Path of the file to load from under Documents file
     */
    public Duke(String filePath) {
        Storage storage = new Storage(filePath);
        taskList = storage.load();
        parser = new Parser(storage, taskList);
    }

    /**
     * First loads from file, then
     * runs parser on loop until user inputs 'bye'
     */
    public void run() {
        Ui.printGreetingMessage();

        do {
            Task task = parser.parse(Ui.readInput());
            if(task != null) {
                Ui.printAddTaskMessage(task, taskList.getNumberOfTasks());
            };
        } while (!Ui.getInputCommand().equals("bye"));

        Ui.printByeMessage();
    }

    public static void main(String[] args) {
        new Duke("ip/data/data.txt").run();
    }
}