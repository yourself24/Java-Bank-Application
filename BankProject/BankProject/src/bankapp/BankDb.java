package bankapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class BankDb {

    String url = "jdbc:mysql://localhost:3306/bancat";
    String user = "Luca";
    String password = "tomita24";

    private Connection connect() {
        Connection connection;
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            connection = null;
        }
        return connection;
    }


    int AddAccount(String firstName, String lastName, String cnp, AccType accountType, Double balance) {
        /**
         *  adding an account to the database by giving sql code
         */
        int userId = -1;
        int accountId = -1;
        Connection connection = connect();
        try {
            /**
             *connects to database  and adds the given data(if it exists and is correct)
             */
            connection.setAutoCommit(false);
            //Add User
            try (PreparedStatement addUser = connection.prepareStatement("INSERT INTO Users(FirstName,LastName,CNP) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
                addUser.setString(1, firstName);
                addUser.setString(2, lastName);
                addUser.setString(3, cnp);
                addUser.executeUpdate();
                ResultSet addUserResults = addUser.getGeneratedKeys();
                if (addUserResults.next()) {
                    userId = addUserResults.getInt(1);
                }
            }
            //Add Account
            try (PreparedStatement addAccount = connection.prepareStatement("INSERT INTO Accounts(Type, Balance) VALUES(?,?)", Statement.RETURN_GENERATED_KEYS)) {
                addAccount.setString(1, accountType.name());
                addAccount.setDouble(2, balance);
                addAccount.executeUpdate();
                ResultSet addAccountResults = addAccount.getGeneratedKeys();
                if (addAccountResults.next()) {
                    accountId = addAccountResults.getInt(1);
                }
            }
            //Link User to Account
            if (userId > 0 && accountId > 0) {
                try (PreparedStatement linkAccount = connection.prepareStatement("INSERT INTO Mapping(UserId, AccountId) VALUES(?,?)")) {
                    linkAccount.setInt(1, userId);
                    linkAccount.setInt(2, accountId);
                    linkAccount.executeUpdate();
                }
                connection.commit();
            } else {
                connection.rollback();
            }
            //Disconnect
            connection.close();
        } catch (SQLException ex) {
            /**
             *prints an error if anything fails
             */
            System.err.println("An error has occured." + ex.getMessage());
        }
        return accountId;
    }
    //Read
    Customer GetAccount(int accountId) {
        Customer customer = null;
        Connection connection = connect();
        try {
            /**
             *returns the account details by using the SELECT and JOIN statements
             */
            try (PreparedStatement findUser = connection.prepareStatement(
                    "SELECT FirstName,LastName,CNP,Type,Balance "
                            + "FROM Users a JOIN Mapping b on a.ID = b.UserId "
                            + "JOIN Accounts c on b.AccountId = c.ID "
                            + "WHERE c.ID = ?")) {
                findUser.setInt(1, accountId);
                ResultSet findUserResults = findUser.executeQuery();
                if (findUserResults.next()) {
                    String firstName = findUserResults.getString("FirstName");
                    String lastName = findUserResults.getString("LastName");
                    String cnp = findUserResults.getString("CNP");
                    String accountType = findUserResults.getString("Type");
                    Double balance = findUserResults.getDouble("Balance");
                    Account account;
                    if (accountType.equals(AccType.Check.name())) {
                        account = new Checking(accountId, balance);
                    } else {
                        account = new Savings(accountId, balance);
                    }
                    customer = new Customer(firstName, lastName, cnp, account);
                }
            }
        } catch (SQLException ex) {
            System.err.println("An error has occured." + ex.getMessage());
        }
        return customer;
    }
    //Update
    boolean UpdateAccount(int accountId, Double newBalance){
        boolean success = false;
        Connection connection = connect();
        try {
            /**
             *updates the tables whenever we modify the balance by either depositing or withdrawing
             */
            try (PreparedStatement updateBalance = connection.prepareStatement(
                    "UPDATE Accounts SET Balance = ? WHERE ID = ?")) {
                updateBalance.setDouble(1, newBalance);
                updateBalance.setInt(2, accountId);
                updateBalance.executeUpdate();
            }
            success = true;
        } catch (SQLException ex) {
            System.err.println("An error has occured." + ex.getMessage());
        }
        return success;
    }
    //Delete
    boolean DeleteAccount(int accountId) {
        boolean success = false;
        Connection connection = connect();
        try {
            /**
             *Deletes the account by ID
             */
            try (PreparedStatement deleteRecords = connection.prepareStatement(
                    "DELETE Users,Accounts FROM Users "
                            + "JOIN Mapping on Users.ID = Mapping.UserId "
                            + "JOIN Accounts on Accounts.ID = Mapping.AccountId "
                            + "WHERE Accounts.ID = ?")) {
                deleteRecords.setInt(1, accountId);
                deleteRecords.executeUpdate();
            }
            success = true;
        } catch (SQLException ex) {
            System.err.println("An error has occured." + ex.getMessage());
        }
        return success;
    }

    ArrayList<Customer> GetAllAccounts() {
        ArrayList<Customer> customers = new ArrayList<Customer>();
        Connection connection = connect();
        try {
            /**
             *returs all of the accounts in our table in the main menu
             */
            try (PreparedStatement findUser = connection.prepareStatement(
                    "SELECT AccountId,FirstName,LastName,CNP,Type,Balance "
                            + "FROM Users a JOIN Mapping b on a.ID = b.UserId "
                            + "JOIN Accounts c on b.AccountId = c.ID")) {
                ResultSet findUserResults = findUser.executeQuery();
                while (findUserResults.next()) {
                    String firstName = findUserResults.getString("FirstName");
                    String lastName = findUserResults.getString("LastName");
                    String cnp = findUserResults.getString("CNP");
                    String accountType = findUserResults.getString("Type");
                    Double balance = findUserResults.getDouble("Balance");
                    int accountId = findUserResults.getInt("AccountId");
                    Account account;
                    if (accountType.equals(AccType.Check.name())) {
                        account = new Checking(accountId, balance);
                    } else {
                        account = new Savings(accountId, balance);
                    }
                    Customer customer = new Customer(firstName, lastName, cnp, account);
                    customers.add(customer);
                }
            }
        } catch (SQLException ex) {
            System.err.println("An error has occured." + ex.getMessage());
        }
        return customers;
    }
}