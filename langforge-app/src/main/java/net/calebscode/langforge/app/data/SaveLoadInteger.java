package net.calebscode.langforge.app.data;

public final record SaveLoadInteger(int value) implements SaveLoadValue {

	@Override
	public boolean isInteger() {
		return true;
	}

	@Override
	public SaveLoadInteger asInteger() {
		return this;
	}

}