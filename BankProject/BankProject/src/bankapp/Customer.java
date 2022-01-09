package bankapp;

import java.io.Serializable;


public class Customer  implements Serializable{
    /**
     * class for returning an object of customer type which represents the user whose account is present(or will be added
     * or deleted) in the table/database.
     */
    private final String firstName;
    private final String lastName;
    private final String cnp;
    private final Account account;

    Customer(String firstName, String lastName, String cnp, Account account) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.cnp = cnp;
        this.account = account;
    }

    @Override
    public String toString(){
        return "\nCustomer Information\n" +
                "First Name: " + getFirstName() + "\n" +
                "Last Name: " + getLastName() +  "\n" +
                "CNP: " +getCNP() +  "\n" +
                account;
    }

    public String basicInfo(){
        return " Account Number: " + account.getAccountNumber() + " - Name: " + getFirstName() + " " + getLastName();
    }

    Account getAccount(){
        return account;
    }


    public String getFirstName() {
        return firstName;
    }


    public String getLastName() {
        return lastName;
    }


    public String getCNP() {
        return cnp;
    }
    
}
