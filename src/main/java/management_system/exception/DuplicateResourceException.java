package management_system.exception;

public class DuplicateResourceException extends RuntimeException{

	public DuplicateResourceException(String msg) {
		super(msg);
	}
	
	public DuplicateResourceException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
