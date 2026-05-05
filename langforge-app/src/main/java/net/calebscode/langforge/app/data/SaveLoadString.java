package net.calebscode.langforge.app.data;

public final record SaveLoadString(String value) implements SaveLoadValue {

	@Override
	public boolean isString() {
		return true;
	}

	@Override
	public SaveLoadString asString() {
		return this;
	}

}