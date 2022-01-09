package bankapp;


class InsufficientFundsException extends Exception {
    /**
     * created in case the balance is too low for withdrawal
     *
     */

    public InsufficientFundsException() {
        super("You have insufficient funds to complete the transaction.");
    }
    
}
