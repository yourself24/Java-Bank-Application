package bankapp;
public class Savings extends Account{

    Savings(int accountId,double initialDeposit){
        /**
         * sets the account type as Savings
         */


        super(accountId);
        this.setBalance(initialDeposit);

    }

    @Override
    public AccType getAccountType() {
        return AccType.Saving;
    }
}
