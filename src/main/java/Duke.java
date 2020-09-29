import task.*;

public class Duke {

    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    public Duke(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        tasks = new TaskList();
    }

    public void run() {
        Storage.load();
        Ui.printGreetingMessage();

        do {
            Task task = Parser.parseCommand(Ui.readInput());
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