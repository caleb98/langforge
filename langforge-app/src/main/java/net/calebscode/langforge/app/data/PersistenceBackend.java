package net.calebscode.langforge.app.data;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface PersistenceBackend {

	public void save(OutputStream output, SaveLoadObject object) throws IOException;
	public SaveLoadObject load(InputStream input) throws IOException;

}
