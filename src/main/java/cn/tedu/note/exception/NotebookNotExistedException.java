package cn.tedu.note.exception;

public class NotebookNotExistedException extends RuntimeException {
	private static final long serialVersionUID = -8810633222159652383L;

	public NotebookNotExistedException() {
		super();
	}

	public NotebookNotExistedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NotebookNotExistedException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotebookNotExistedException(String message) {
		super(message);
	}

	public NotebookNotExistedException(Throwable cause) {
		super(cause);
	}
}
