package maheshz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BankService {

    // 1. Initialize the Logger for this specific class
    private static final Logger logger = LoggerFactory.getLogger(BankService.class);

    public void processTransfer(String user, double amount) {

        logger.info("Initializing transfer of ${} for user: {}", amount, user);

        try {
            if (amount > 10000) {
                logger.warn("Security Alert: Unusually large transaction for user {}", user);
            }
            if (amount < 0) {
                throw new IllegalArgumentException("Transfer amount cannot be negative!");
            }

            // Simulating database work...
            Thread.sleep(500);
            logger.info("Transfer completed successfully for user {}.", user);

        } catch (Exception e) {
            // 2. We do NOT use {} for the exception. We pass it as the final argument.
            logger.error("Transaction failed for user {}!", user, e);
        }
    }
}
