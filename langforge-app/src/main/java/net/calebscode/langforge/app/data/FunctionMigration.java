package net.calebscode.langforge.app.data;

import java.util.function.Function;

import net.calebscode.langforge.app.util.VersionNumber;

class FunctionMigration implements Migration {

	VersionNumber targetVersion;
	Function<SaveLoadValue, SaveLoadValue> migration;

	FunctionMigration(
		VersionNumber targetVersion,
		Function<SaveLoadValue, SaveLoadValue> migration
	) {
		this.targetVersion = targetVersion;
		this.migration = migration;
	}

	@Override
	public VersionNumber targetVersion() {
		return targetVersion;
	}

	@Override
	public SaveLoadValue migrate(SaveLoadValue from) {
		return migration.apply(from);
	}

}