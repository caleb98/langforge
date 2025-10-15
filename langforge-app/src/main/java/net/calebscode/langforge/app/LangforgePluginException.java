package net.calebscode.langforge.app;

public class LangforgePluginException extends Exception {

	private static final long serialVersionUID = 954615961131247225L;

	public LangforgePluginException() {
		super();
	}
	
	public LangforgePluginException(String message) {
		super(message);
	}
	
	public LangforgePluginException(Throwable cause) {
		super(cause);
	}
	
	public LangforgePluginException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
