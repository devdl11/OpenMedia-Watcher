package fr.dl11.openmedia.exceptions;

/**
 * Exception thrown when event handling fails.
 *
 * <p>The {@code FailedEventHandling} class extends the {@link Exception} class
 * and represents an error that occurs during the handling of an event. This
 * exception can be used to indicate that an event could not be processed
 * successfully.</p>
 */
public class FailedEventHandling extends Exception {

    /**
     * Constructs a new {@code FailedEventHandling} exception with the specified detail message.
     *
     * @param message The detail message explaining the reason for the exception. Must not be null.
     */
    public FailedEventHandling(String message) {
        super(message);
    }
}
