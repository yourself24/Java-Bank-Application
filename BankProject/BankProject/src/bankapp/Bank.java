package bankapp;


import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;


public class Bank implements Serializable {
    /**
     * Bank basically takes care of the oeprations: adding or deleting accounts , and deposit and withdrawal of money
     * it's also the one that calls for the database for the different updates made.
     */
    private BankDb database= new BankDb();
    int openAccount(String firstName,String lastName,String CNP,AccType accountType,double balance){
        return database.AddAccount(firstName,lastName,CNP,accountType,balance);
    }
    

    Customer getCustomer(int accountId){
        return database.GetAccount(accountId);
    }
    
    ArrayList<Customer> getCustomers(){
        return database.GetAllAccounts();
    }


    boolean closingAccount(int accountId) {
        return database.DeleteAccount(accountId);
    }
    
    public static double round(double value, int places) {
        /**
         * function is used for rounding a value
         */
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    public void withdraw(int accountId,double amount) throws InsufficientFundsException{
        /**
         * when we withdraw,it gets the current balance and checks is there are enought funds to withdraw
         * (i.e. the amount introduced+the transaction fee). If there are not enough money,throws an insufficent funds
         * exception.
         */
        Customer customer=getCustomer(accountId);
        double transactionFee=getTransactionFee(customer.getAccount().getAccountType());
        if(amount + transactionFee > customer.getAccount().getBalance()){
            throw new InsufficientFundsException();
        }
       double newbalance = customer.getAccount().getBalance()-(amount+transactionFee);
        database.UpdateAccount(accountId,newbalance);

    }

    public void deposit(int accountId,double amount) throws InvalidAmountException{
        /**
         * The deposit function. whenever we deposit money,it adds the interest and the updates
         * the balance in the database for the specific account
         */
        Customer customer=getCustomer(accountId);
        if(amount <= 0){
            throw new InvalidAmountException();
        }
        double interest=checkInterest(customer.getAccount().getBalance(),amount);
       double amountDeposited = amount + (amount * interest);
        database.UpdateAccount(accountId,customer.getAccount().getBalance()+amountDeposited);
    }

    public double checkInterest(double balance,double amount){
        /**
         * calculates the interest rates. for any amount larger than 1000,the interest is 0.05 of deposit and for values
         * lower,it is 0.02.
         */
        double interest;
        if(balance + amount > 1000){
            interest = 0.05;
        } else {
            interest = 0.02;
        }
        return interest;
    }
    public double getTransactionFee(AccType accountType){
        /**
         * returns transaction fee,which is 8 for Check accounts and 10 for Savings. and of course 0 if the account is Undef
         */
        double transactionFee=0;
        switch(accountType){
            case Check:
                transactionFee=8;
            case Saving:
                transactionFee=10;
                break;
            case Undef:
            default:
                transactionFee=0;
                break;
        }
        return transactionFee;
    }
    
}
