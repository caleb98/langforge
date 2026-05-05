package net.calebscode.langforge.app.data;

public final record SaveLoadDouble(double value) implements SaveLoadValue {

	@Override
	public boolean isDouble() {
		return true;
	}

	@Override
	public SaveLoadDouble asDouble() {
		return this;
	}

}