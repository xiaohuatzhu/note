package cn.tedu.note.exception;

public class NotebookNotEmptyException extends RuntimeException {
	private static final long serialVersionUID = -8810633222159652383L;

	public NotebookNotEmptyException() {
		super();
	}

	public NotebookNotEmptyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NotebookNotEmptyException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotebookNotEmptyException(String message) {
		super(message);
	}

	public NotebookNotEmptyException(Throwable cause) {
		super(cause);
	}
}
