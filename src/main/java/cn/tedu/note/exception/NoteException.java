package cn.tedu.note.exception;

public class NoteException extends RuntimeException {
	private static final long serialVersionUID = -8810622222159652383L;

	public NoteException() {
		super();
	}

	public NoteException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NoteException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoteException(String message) {
		super(message);
	}

	public NoteException(Throwable cause) {
		super(cause);
	}
}
