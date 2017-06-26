package cn.tedu.note.exception;

public class NoteTitleException extends RuntimeException {
	private static final long serialVersionUID = -8810633222159652383L;

	public NoteTitleException() {
		super();
	}

	public NoteTitleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NoteTitleException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoteTitleException(String message) {
		super(message);
	}

	public NoteTitleException(Throwable cause) {
		super(cause);
	}
}
