package costumer.management.system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class CustomerManager {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/customerDB_java";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    private Connection connection;
    private Scanner scanner;

    public CustomerManager() {
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        scanner = new Scanner(System.in);
    }

    public void showMenu() {
        while (true) {
            System.out.println("=====================");
            System.out.println("1. Add Customer");
            System.out.println("2. Display Customer");
            System.out.println("3. Search Customer by name");
            System.out.println("4. Update Customer");
            System.out.println("5. Delete Customer");
            System.out.println("6. Exit");
            System.out.print("Choose an Option: ");
            int choise = scanner.nextInt();
            scanner.nextLine();

            switch (choise) {
                case 1:
                    addCustomer();
                    break;
                case 2:
                    displayCustoomer();
                    break;
                case 3:
                    searchCustomer();
                    break;
                case 4:
                    updateCustomer();
                    break;
                case 5:
                    deleteCustomer();
                    break;
                case 6:
                    System.out.println("Exiting...");
                    closeConnection();
                    return;
                default:
                    System.out.println("Invalid  choise. Please try again!");
            }
        }
    }

    private void addCustomer() {
        System.out.println("*****************");
        System.out.print("Enter Customer Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Customer Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Customer Phone: ");
        String phone = scanner.nextLine();

        String query = "INSERT INTO customers(name,email,phone) VALUES(?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, phone);
            preparedStatement.executeUpdate();
            System.out.println("Customer added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void displayCustoomer() {
        String query = "SELECT *FROM customers";
        try (Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone");
                System.out.println(new Customer(id, name, email, phone));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void searchCustomer() {
        System.out.println("********************");
        System.out.print("Enter Customer Name to search: ");
        String name = scanner.nextLine();

        String query = "SELECT *FROM customers WHERE name LIKE ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, "%" + name + "%");
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String customerName = resultSet.getString("name");
                    String email = resultSet.getString("email");
                    String phone = resultSet.getString("phone");
                    System.out.println(new Customer(id, customerName, email, phone));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateCustomer() {
        System.out.println("********************");
        System.out.print("Enter Customer ID to Update: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter new Customer Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter new Customer Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter new Customer Phone: ");
        String phone = scanner.nextLine();

        String query = "UPDATE customers SET name = ?, email = ?, phone = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, phone);
            preparedStatement.setInt(4, id);
            int rowUpdated = preparedStatement.executeUpdate();
            if (rowUpdated > 0) {
                System.out.println("Customer updated successfully!");
            } else {
                System.out.println("Customer not found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteCustomer() {
        System.out.println("***************");
        System.out.print("Enter Customer id to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        String query = "DELETE FROM customers WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Customer deleted successfully");
            } else {
                System.out.println("Customer not found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
