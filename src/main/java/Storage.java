import task.Deadline;
import task.Event;
import task.Task;
import task.ToDo;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Deals with saving, loading, and everything to deal with files
 */
public class Storage {
    private static final String home = System.getProperty("user.home");
    private final Path filePath;
    private final Path dirPath;
    private final boolean directoryExists;

    /**
     * initialiser accepts a string and sets filePath, dirPath and
     * direcctoryExists in the Documents folder
     * @param filePath String of paths under Documents/ that data will be saved in
     */
    Storage(String filePath) {
        this.filePath = Paths.get(home, "Documents", filePath);
        this.dirPath = Paths.get(home, "Documents", filePath.substring(0,filePath.lastIndexOf("/")));
        this.directoryExists = Files.exists(dirPath);
    }

    /**
     * loads the information from data.txt into the instance of Storage
     * Creates the directory and file if it is not found
     * If file is found, parses each line and uses Tasklist methods to add the appropriate type
     * format: (T/D/E)|(done)| (taskname) /by (time)
     * eg. T|0| laundry
     * eg. D|1| homework /by tomorrow
     */
    public TaskList load() {
        String filePathString = filePath.toString();
        File file = new File(filePathString);
        try {
            if (!directoryExists) {
                String dirPathString = dirPath.toString();
                File directory = new File(dirPathString);
                if(directory.mkdir()) {
                    Ui.printCreatingDirectoryMessage(dirPathString);
                };
            }
            if (file.createNewFile()) {
                Ui.printCreatingFileMessage(filePathString);
                Ui.printFileCreatedMessage(file);
            } else {
                Ui.printFileLoadedMessage(file);
            }
        } catch (IOException e) {
            Ui.printFileError(filePathString);
        }

        FileInputStream stream;
        TaskList taskList = new TaskList();
        try {
            stream = new FileInputStream(filePathString);

            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            try {
                String strLine;
                // format: (T/D/E)|(done)| taskname /by time
                while ((strLine = reader.readLine()) != null) {
                    Task task = null;
                    switch (strLine.charAt(0)) {
                    case 'T':
                        try {
                            task = taskList.addTodo(strLine.substring(5));
                        } catch (DukeException e) {
                            Ui.printTodoError();
                        }
                        break;
                    case 'D':
                        try {
                            task = taskList.addDeadline(strLine.substring(5));
                        } catch (DukeException e) {
                            Ui.printDeadlineError();
                        }
                        break;
                    case 'E':
                        try {
                            task = taskList.addEvent(strLine.substring(5));
                        } catch (DukeException e) {
                            Ui.printEventError();
                        }
                        break;
                    default:
                        return null;
                    }
                    if (strLine.charAt(2) == '1') {
                        task.setDone(true);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw e;
            }
        } catch (IOException e) {
            Ui.printFileError();
        }
        return taskList;
    }

    /**
     * automatically called after every change in taskList
     * save in the format: (T/D/E)|(done)| taskname /by timeeg.
     * eg. T|0| laundry
     * eg. D|1| homework /by tomorrow
     */
    public void save(TaskList tasks) {
        StringBuilder lines = new StringBuilder();

        for (int i = 0; i < tasks.getNumberOfTasks(); i++) {
            Task task = tasks.getTask(i);
            if (task instanceof ToDo) {
                lines.append("T|" + (task.getDone() ? "1" : "0") + "| " + task.getDescription());
            } else if (task instanceof Deadline) {
                lines.append("D|" + (task.getDone() ? "1" : "0") + "| " + task.getDescription()
                        + " /by " + ((Deadline) task).getBy());
            } else if (task instanceof Event) {
                lines.append("E|" + (task.getDone() ? "1" : "0") + "| " + task.getDescription()
                        + " /at " + ((Event) task).getEventTime());
            } else {
                return;
            }
            lines.append('\n');
        }

        // Write to file
        File file = new File(filePath.toString());
        FileWriter fr = null;
        try {
            fr = new FileWriter(file);
            fr.write(lines.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // close resources
            try {
                if (fr != null) {
                    fr.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                Ui.printFileError();
            }
        }
    }
}