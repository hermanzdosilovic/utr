/**
 * Exception thrown when <i>DFA</i> definition error occurs.
 * 
 * @author Herman Zvonimir Dosilovic
 */
public final class DFAException extends Exception {

  private static final long serialVersionUID = 1L;

  /**
   * Constructs an {@code DFAException} with the specified detail message.
   *
   * @param message The detail message (which is saved for later retrieval by the
   *        {@link #getMessage()} method)
   */
  public DFAException(String message) {
    super(message);
  }

  /**
   * Constructs an {@code DFAException} with the specified detail message and cause.
   * 
   * @param message The detail message (which is saved for later retrieval by the
   *        {@link #getMessage()} method)
   *
   * @param cause The cause (which is saved for later retrieval by the {@link #getCause()} method).
   *        (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
   *
   */
  public DFAException(String message, Throwable throwable) {
    super(message, throwable);
  }

}
