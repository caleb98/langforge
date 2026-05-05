package net.calebscode.langforge.app.data;

import net.calebscode.langforge.app.util.VersionNumber;

public interface Migration {

	VersionNumber targetVersion();
	SaveLoadObject migrate(SaveLoadObject from);

}
