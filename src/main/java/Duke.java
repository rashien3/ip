import task.*;

public class Duke {

    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    /**
     * initialises the 3 supporting classes
     * @param filePath
     */
    public Duke(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        tasks = new TaskList();
    }

    /**
     * First loads from file, then
     * runs parser on loop until user inputs 'bye'
     */
    public void run() {
        Storage.load();
        Ui.printGreetingMessage();

        do {
            Task task = Parser.parse(Ui.readInput());
            Ui.printLinebreak();
            if(task != null) {
                Ui.printAddTaskMessage(task);
                Ui.printLinebreak();
            };
        } while (!Ui.getInputCommand().equals("bye"));

        Ui.printByeMessage();
        Ui.printLinebreak();
    }

    public static void main(String[] args) {
        new Duke("ip/data/data.txt").run();
    }
}