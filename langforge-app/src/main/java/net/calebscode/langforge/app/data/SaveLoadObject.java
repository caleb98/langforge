package net.calebscode.langforge.app.data;

public record SaveLoadObject<T>(T context, SaveLoadSchema<T> schema) {
	
}
