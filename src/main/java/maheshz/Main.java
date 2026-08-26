package maheshz;

import org.slf4j.MDC;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        BankService bank = new BankService();

        // Create a Thread Pool with 3 workers
        ExecutorService factory = Executors.newFixedThreadPool(3);

        // Submit 3 parallel transactions
        factory.submit(() -> runTransaction(bank, "TXN-991", "Alice", 500.00));
        factory.submit(() -> runTransaction(bank, "TXN-992", "Bob", 15000.00));
        factory.submit(() -> runTransaction(bank, "TXN-993", "Charlie", 100.00));

        factory.shutdown();
    }

    // A helper method for our worker threads
    private static void runTransaction(BankService bank, String txId, String user, double amount) {
        // 1. Put the Transaction ID into the MDC backpack!
        MDC.put("transactionId", txId);

        try {
            // We do NOT need to pass the txId into the bank method.
            // The logger pulls it directly out of the thread's backpack!
            bank.processTransfer(user, amount);
        } finally {
            // 2. ALWAYS clear the backpack when the thread is done,
            // because this worker thread will be reused for a different customer later!
            MDC.clear();
        }
    }
}
