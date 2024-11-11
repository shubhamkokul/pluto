package castiel.solutionbyhour.exception;

/**
 * Custom exception for handling errors in fetching stock quotes.
 */
public class FetchException extends RuntimeException {
    public FetchException(String message) {
        super(message);
    }

    public FetchException(String message, Throwable cause) {
        super(message, cause);
    }
}