package net.calebscode.langforge.app.data;

import java.util.function.Function;

import net.calebscode.langforge.app.util.VersionNumber;

public interface Migration {

	public VersionNumber targetVersion();
	public SaveLoadValue migrate(SaveLoadValue from);

	public static Migration forVersion(
		VersionNumber targetVersion,
		Function<SaveLoadValue, SaveLoadValue> migration
	) {
		return new FunctionMigration(targetVersion, migration);
	}

}
