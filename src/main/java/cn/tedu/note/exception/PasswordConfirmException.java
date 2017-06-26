package cn.tedu.note.exception;

public class PasswordConfirmException extends RuntimeException {
	private static final long serialVersionUID = -8810604422159652383L;

	public PasswordConfirmException() {
		super();
	}

	public PasswordConfirmException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public PasswordConfirmException(String message, Throwable cause) {
		super(message, cause);
	}

	public PasswordConfirmException(String message) {
		super(message);
	}

	public PasswordConfirmException(Throwable cause) {
		super(cause);
	}
}
