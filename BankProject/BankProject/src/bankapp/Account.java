package bankapp;

import java.io.Serializable;


public abstract class Account  implements Serializable{
    /**
     * Using the account class we implement the details of the accounts,e.g. the balance,the number and the type
     */
    private double balance = 0;
    private int accountNumber;
    private static int numberOfAccounts = 1000000;
    private double transactionFee;
    
    Account(int accountId){
        accountNumber=accountId;
    }
    

    
    public abstract AccType getAccountType();


    public double getBalance() {
        return balance;
    }




    public void setBalance(double balance) {
        this.balance = balance;
    }





    public int getAccountNumber() {
        return accountNumber;
    }
    

    void setAccountNumber(int accountNumber) {

        this.accountNumber = accountNumber;
    }
    @Override
    public String toString(){
        return "Account Type: " + getAccountType() + " Account\n" +
                "Account Number: " + this.getAccountNumber() + "\n" +
                "Balance: " + this.getBalance() + "\n" ;

    }

}
