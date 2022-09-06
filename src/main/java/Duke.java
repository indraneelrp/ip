import java.util.Scanner;
import java.util.ArrayList;

public class Duke {

    // HELPER FUNCTIONS
    public static void greetUser() {
        String line = "------------------------\n";
        System.out.println(line + "Hello! I'm Juke!\nWhat can I do for you?\n" + line);
    }

    public static void listTasks(ArrayList<Task> tasks) {
        String line = "\t------------------------\n";
        System.out.println(line);
        for (int i = 0; i < tasks.size(); i++) {
            int j = i + 1;
            System.out.println("\t"+ j + ". " + tasks.get(i) + "\n");
        }
        System.out.println(line);
    }

    public static void printStatus(Task latest, int total){
        String line = "\t------------------------\n";
        System.out.println(line + "\tGot it. I've added this task:\n\t" + latest);
        System.out.println("\n\tNow you have " + total + " task(s) in the list.\n" + line);
    }

    public static void processUserInput() {
        // CONSTANTS
        final String LIST_TRIGGER = "list";
        final String TODO_TRIGGER = "todo";
        final String EVENT_TRIGGER = "event";
        final String DEADLINE_TRIGGER = "deadline";
        final String EXIT_TRIGGER = "bye";
        final String MARK_TRIGGER = "mark";
        final String UNMARK_TRIGGER = "unmark";

        // VARS
        String userInput = "";
        Scanner scanObj = new Scanner(System.in);
        ArrayList<Task> tasks = new ArrayList<Task>();

        // JUKE LOGIC
        while (!userInput.equals("bye")) {
            // Read user input, splits it into 2 strings- the command and the remaining (if exists)
            userInput = scanObj.nextLine();
            String[] words = userInput.split(" ", 2);
            
            // variables for Tasks with timestamps
            int dividerIndex = 0;
            String timestamp = "";
            String taskDescription="";

            switch (words[0]) {
            case LIST_TRIGGER:
                listTasks(tasks);
                break;

            case EXIT_TRIGGER:
                break;

            case TODO_TRIGGER:
                Todo latestTodo = new Todo(words[1]);
                tasks.add(latestTodo);
                printStatus(latestTodo, tasks.size());
                break;

            case EVENT_TRIGGER:
                dividerIndex = words[1].indexOf("/");
                timestamp = words[1].substring(dividerIndex+3);
                taskDescription = words[1].substring(0, dividerIndex-1);
                
                Event latestEvent = new Event(taskDescription, timestamp);
                tasks.add(latestEvent);
                printStatus(latestEvent, tasks.size());
                break;

            case DEADLINE_TRIGGER:
                dividerIndex = words[1].indexOf("/");
                timestamp = words[1].substring(dividerIndex+3);
                taskDescription = words[1].substring(0, dividerIndex-1);
                
                Deadline latestDeadline = new Deadline(taskDescription, timestamp);
                tasks.add(latestDeadline);
                printStatus(latestDeadline, tasks.size());
                break;

            case MARK_TRIGGER:
                // making sure there is valid numerical input after mark/unmark
                if (userInput.matches("mark [0-9]+")) {
                    int val = Integer.parseInt(words[1]);

                    if (val < 0 || val > tasks.size()) {
                        System.out.println("\tOut-of-bounds value entered, try again.\n");
                        break;
                    } else {
                        tasks.get(val - 1).markDone();
                        listTasks(tasks);
                    }
                } 

            case UNMARK_TRIGGER:
                // making sure there is valid numerical input after mark/unmark
                if (userInput.matches("unmark [0-9]+")) {    
                    int val = Integer.parseInt(words[1]);

                    if (val < 0 || val > tasks.size()) {
                        System.out.println("\tOut-of-bounds value entered, try again.\n");
                        break;
                    } else {
                        tasks.get(val - 1).unmark();
                        listTasks(tasks);
                    }
                } 

            default:
                // do nothing
                break;
            }
        }

        scanObj.close();
    }

    public static void printExitGreeting() {
        System.out.println("\nSee you again!");
    }

    // MAIN FUNCTION
    public static void main(String[] args) {
        greetUser();
        processUserInput();
        printExitGreeting();
    }
}