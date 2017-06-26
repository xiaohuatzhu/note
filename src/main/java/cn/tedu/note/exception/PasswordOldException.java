package cn.tedu.note.exception;

public class PasswordOldException extends RuntimeException {
	private static final long serialVersionUID = -8810604422159652383L;

	public PasswordOldException() {
		super();
	}

	public PasswordOldException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public PasswordOldException(String message, Throwable cause) {
		super(message, cause);
	}

	public PasswordOldException(String message) {
		super(message);
	}

	public PasswordOldException(Throwable cause) {
		super(cause);
	}
}
