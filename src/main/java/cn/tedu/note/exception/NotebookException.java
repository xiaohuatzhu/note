package cn.tedu.note.exception;

public class NotebookException extends RuntimeException {
	private static final long serialVersionUID = -8810633222159652383L;

	public NotebookException() {
		super();
	}

	public NotebookException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NotebookException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotebookException(String message) {
		super(message);
	}

	public NotebookException(Throwable cause) {
		super(cause);
	}
}
