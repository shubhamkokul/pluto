package castiel.solutionbyhour.exception;

/**
 * Custom exception for handling errors in fetching stock quotes.
 */
public class QuoteFetchException extends RuntimeException {
    public QuoteFetchException(String message) {
        super(message);
    }

    public QuoteFetchException(String message, Throwable cause) {
        super(message, cause);
    }
}