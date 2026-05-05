package net.calebscode.langforge.app.data;

public class SaveLoadValueException extends RuntimeException {

	private static final long serialVersionUID = 3681484753286047496L;

	public SaveLoadValueException(String msg) {
		super(msg);
	}

	public SaveLoadValueException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
