package bankapp;


public class Checking extends Account{
    private static String accountType = "Checking";

    Checking(int accountId,double initialDeposit){
        /**
         *used for setting the account type as Checking
         */
        super(accountId);
        this.setBalance(initialDeposit);

    }

    @Override
    public AccType getAccountType() {
        return AccType.Check;
    }
}
