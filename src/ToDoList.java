import java.time.LocalDate;
import java.util.Scanner;
import java.util.List;

public class ToDoList {
    private static final Scanner scanner = new Scanner(System.in);
    private static final DatabaseManager databaseManager = new DatabaseManager();

    public static void main(String[] args) {
        databaseManager.createTaskTable();

        while (true) {
            System.out.println("\n1. Add Task");
            System.out.println("2. View Tasks");
            System.out.println("3. Exit");
            System.out.println("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addTask();
                    break;
                case 2:
                    viewTasks();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addTask(){
        System.out.print("Enter task description: ");
        String task = scanner.nextLine();

        System.out.println("Enter task date: ");
        String stringDate = scanner.nextLine();
        LocalDate date = LocalDate.parse(stringDate);

        databaseManager.insertTask(task, date);
        System.out.println("Task added successfully");
    }

    private static void viewTasks(){
        List<String> tasks = databaseManager.getTasks();
        if(databaseManager.IsTableEmpty()) {
            System.out.println("No tasks found");
        }
        else {
            System.out.println("Tasks: ");
            for(String task : tasks) {
                System.out.println("- " + task);
            }
        }
    }
}
