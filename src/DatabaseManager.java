import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private Connection connection;

    public DatabaseManager(){
        connect();
         createTaskTable();
    }

    private void connect() {
        try {
            connection = DriverManager.getConnection("", "root", "Gvants@21!");
            System.out.println("Connected to the database");
        } catch (SQLException e){
            System.err.println("Error connecting to the database: " + e.getMessage());
        }
    }

    void createTaskTable(){
        String sql = """
                CREATE TABLE tasks (
                    id INTEGER PRIMARY KEY,
                    task TEXT NOT NULL,
                    date TEXT NOT NULL
                );""";
        try (Statement stmt =   connection.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table created successfully");
        } catch (SQLException e) {
            System.err.println("Error creating table: " + e.getMessage());
        }
    }

    public void insertTask(String task, LocalDate date) {
        String sql = "INSERT INTO tasks(date, task) VALUES(?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, date.toString());
            pstmt.setString(2, task);
            pstmt.executeUpdate();
            System.out.println("Task inserted successfully");
        } catch (SQLException e) {
            System.err.println("Error inserting task: " + e.getMessage());
        }
    }

    public List<String> getTasks(){
        List<String> tasks = new ArrayList<>();
        String sql = "SELECT task FROM tasks WHERE date IS NULL OR date = ''";

        try(Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                tasks.add(rs.getString("task"));
            }
        } catch (SQLException e){
            System.err.println("Error retrieving tasks: " + e.getMessage());
        }

        return tasks;
    }

    public boolean IsTableEmpty(){
        String sql = "SELECT COUNT(*) FROM tasks";
        try (Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql)) {
            if(rs.next()) {
                int count = rs.getInt(1);
                return count == 0;
            }
        } catch (SQLException e) {
            System.err.println("Error checking if table is empty: " + e.getMessage());
        }
        return false;
    }
}
