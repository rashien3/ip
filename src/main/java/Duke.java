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
        System.out.println(Ui.MESSAGE_GREETING);

        do {
            Ui.inputCommand = Ui.readInput();
            Task task = Parser.parseCommand(Ui.inputCommand);
            Ui.printLinebreak();
            if(task != null) {
                Ui.printAddTaskMessage(task);
                Ui.printLinebreak();
            };
        } while (!Ui.inputCommand.equals("bye"));

        Ui.printByeMessage();
        Ui.printLinebreak();
    }

    public static void main(String[] args) {
        new Duke("ip/data/data.txt").run();
    }
}

/*
public class Duke {
    public static void main(String[] args) {
        Storage.load();
        System.out.println(Ui.MESSAGE_GREETING);

        do {
            Ui.inputCommand = Ui.readInput();
            Task task = Parser.parseCommand(Ui.inputCommand);
            Ui.printLinebreak();
            if(task != null) {
                Ui.printAddTaskMessage(task);
                Ui.printLinebreak();
            };
        } while (!Ui.inputCommand.equals("bye"));

        Ui.printByeMessage();
        Ui.printLinebreak();
    }
}
*/
