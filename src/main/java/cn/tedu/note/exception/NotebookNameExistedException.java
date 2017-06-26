package cn.tedu.note.exception;

public class NotebookNameExistedException extends RuntimeException {
	private static final long serialVersionUID = -8810633222159652383L;

	public NotebookNameExistedException() {
		super();
	}

	public NotebookNameExistedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NotebookNameExistedException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotebookNameExistedException(String message) {
		super(message);
	}

	public NotebookNameExistedException(Throwable cause) {
		super(cause);
	}
}
