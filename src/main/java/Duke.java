import java.util.Scanner;
import java.util.ArrayList;

public class Duke {
    public static void main(String[] args) {
        String greeting = "    ____________________________________________________________\n" +
                "    Hello! I'm Duke\n" +
                "    What can I do for you?\n" +
                "    ____________________________________________________________\n";
        System.out.println(greeting);

        //level-1
        String inputCommand = "";
        ArrayList<String> taskList = new ArrayList<String>();
        Scanner in = new Scanner(System.in);
        outerloop:
        while(!inputCommand.toLowerCase().equals("bye")){
            inputCommand = in.nextLine();
            switch(inputCommand){
            case "list":
                int i = 0;
                System.out.println("    ____________________________________________________________");
                for(String task : taskList){
                    System.out.println(++i + ". " + task);
                }
                System.out.println("    ____________________________________________________________");
                break;
            case "bye":
                break outerloop;
            default:
                taskList.add(inputCommand);
                System.out.println("    ____________________________________________________________\n" +
                        "     added: " + inputCommand + "\n" +
                        "    ____________________________________________________________");
                break;
            }
        }
        System.out.println("    ____________________________________________________________\n" +
                "     Bye. Hope to see you again soon!\n" +
                "    ____________________________________________________________");
    }
}
