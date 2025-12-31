package net.calebscode.langforge.app.data;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

public interface DataStore {

	public void save(OutputStream output, Map<String, SaveLoadObject<?>> objects) throws IOException;
	public void load(InputStream input, Map<String, SaveLoadObject<?>> objects) throws IOException;
	
}
