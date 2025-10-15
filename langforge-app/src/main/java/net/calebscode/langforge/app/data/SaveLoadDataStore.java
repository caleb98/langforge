package net.calebscode.langforge.app.data;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

public interface SaveLoadDataStore<TSaveContext, TLoadContext> {

	public void save(Map<String, SaveLoadModel> models, OutputStream output) throws IOException;
	public void load(Map<String, SaveLoadModel> models, InputStream input) throws IOException;

	public void saveBoolean(SaveLoadBoolean saveLoadBoolean, TSaveContext saveContext) throws IOException;
	public void saveByte(SaveLoadByte saveLoadByte, TSaveContext saveContext) throws IOException;
	public void saveCharacter(SaveLoadCharacter saveLoadCharacter, TSaveContext saveContext) throws IOException;
	public void saveDouble(SaveLoadDouble saveLoadDouble, TSaveContext saveContext) throws IOException;
	public void saveFloat(SaveLoadFloat saveLoadFloat, TSaveContext saveContext) throws IOException;
	public void saveInteger(SaveLoadInteger saveLoadInteger, TSaveContext saveContext) throws IOException;
	public void saveList(SaveLoadList<?> saveLoadList, TSaveContext saveContext) throws IOException;
	public void saveLong(SaveLoadLong saveLoadLong, TSaveContext saveContext) throws IOException;
	public void saveShort(SaveLoadShort saveLoadShort, TSaveContext saveContext) throws IOException;
	public void saveString(SaveLoadString saveLoadString, TSaveContext saveContext) throws IOException;

	public void loadBoolean(SaveLoadBoolean saveLoadBoolean, TLoadContext loadContext) throws IOException;
	public void loadByte(SaveLoadByte saveLoadByte, TLoadContext loadContext) throws IOException;
	public void loadCharacter(SaveLoadCharacter saveLoadCharacter, TLoadContext loadContext) throws IOException;
	public void loadDouble(SaveLoadDouble saveLoadDouble, TLoadContext loadContext) throws IOException;
	public void loadFloat(SaveLoadFloat saveLoadFloat, TLoadContext loadContext) throws IOException;
	public void loadInteger(SaveLoadInteger saveLoadInteger, TLoadContext loadContext) throws IOException;
	public void loadList(SaveLoadList<?> saveLoadList, TLoadContext loadContext) throws IOException;
	public void loadLong(SaveLoadLong saveLoadLong, TLoadContext loadContext) throws IOException;
	public void loadShort(SaveLoadShort saveLoadShort, TLoadContext loadContext) throws IOException;
	public void loadString(SaveLoadString saveLoadString, TLoadContext loadContext) throws IOException;

}
