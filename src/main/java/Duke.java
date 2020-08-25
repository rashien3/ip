import java.util.Scanner;

public class Duke {
    public static void main(String[] args) {
        String greeting = "    ____________________________________________________________\n" +
                "    Hello! I'm Duke\n" +
                "    What can I do for you?\n" +
                "    ____________________________________________________________\n";
        System.out.println(greeting);

        //level-1
        String inputCommand = "a";
        Scanner in = new Scanner(System.in);
        while(!inputCommand.toLowerCase().equals("bye")){
            inputCommand = in.nextLine();
            System.out.println("    ____________________________________________________________\n" +
                    "    " + inputCommand + "\n" +
                    "    ____________________________________________________________");
        }
        System.out.println("    ____________________________________________________________\n" +
                "     Bye. Hope to see you again soon!\n" +
                "    ____________________________________________________________");
    }
}
