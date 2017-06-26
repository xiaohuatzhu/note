package cn.tedu.note.exception;

public class NoteTitleExistedException extends RuntimeException {
	private static final long serialVersionUID = -8810622222159652383L;

	public NoteTitleExistedException() {
		super();
	}

	public NoteTitleExistedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NoteTitleExistedException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoteTitleExistedException(String message) {
		super(message);
	}

	public NoteTitleExistedException(Throwable cause) {
		super(cause);
	}
}
