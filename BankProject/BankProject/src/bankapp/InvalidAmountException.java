package bankapp;

class InvalidAmountException extends Exception {
    /**
     *created for an invalid amount(not a number)
     */


    public InvalidAmountException() {
        super("Invalid amount for transaction.");
    }
    
}
