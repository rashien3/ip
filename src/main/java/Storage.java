import task.Deadline;
import task.Event;
import task.Task;
import task.ToDo;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Storage {// file path
    static final String home = System.getProperty("user.home");// inserts correct file path separator to data.txt file
    static Path filePath;
    static Path dirPath;
    static boolean directoryExists;

    Storage(String filePath) {
        this.filePath = Paths.get(home, "Documents", filePath);
        this.dirPath = Paths.get(home, "Documents", filePath.substring(0,filePath.lastIndexOf("/")));
        this.directoryExists = Files.exists(dirPath);
    }

    public static void load() {
        String filePathString = filePath.toString();
        File file = new File(filePathString);
        try {
            if (!directoryExists) {
                String dirPathString = dirPath.toString();
                Ui.printCreatingDirectoryMessage(dirPathString);
                File directory = new File(dirPathString);
                directory.mkdir();
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
        try {
            stream = new FileInputStream(filePathString);

            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            try {
                String strLine;
                // format: T/D/E | 0/1 | taskname /by time
                while ((strLine = reader.readLine()) != null) {
                    Task t = null;
                    switch (strLine.charAt(0)) {
                    case 'T':
                        try {
                            t = TaskList.addTodo(strLine);
                        } catch (DukeException e) {
                            Ui.printTodoError();
                        }
                        break;
                    case 'D':
                        try {
                            t = TaskList.addDeadline(strLine);
                        } catch (DukeException e) {
                            Ui.printDeadlineError();
                        }
                        break;
                    case 'E':
                        try {
                            t = TaskList.addEvent(strLine);
                        } catch (DukeException e) {
                            Ui.printEventError();
                        }
                        break;
                    default:
                        return;
                    }
                    if (strLine.charAt(2) == '1') {
                        t.setDone(true);
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
    }

    public static void save() {
        StringBuilder lines = new StringBuilder();

        for (int i = 0; i < TaskList.numberOfTasks; i++) {
            Task task = TaskList.taskList.get(i);
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