package cn.tedu.note.exception;

public class PasswordNewException extends RuntimeException {
	private static final long serialVersionUID = -8810604422159652383L;

	public PasswordNewException() {
		super();
	}

	public PasswordNewException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public PasswordNewException(String message, Throwable cause) {
		super(message, cause);
	}

	public PasswordNewException(String message) {
		super(message);
	}

	public PasswordNewException(Throwable cause) {
		super(cause);
	}
}
