package net.calebscode.langforge.app;

import net.calebscode.langforge.app.util.VersionNumber;

public class LangforgeAppInfoApi {

	public VersionNumber getCurrentVersion() {
		return LangforgeApplication.CURRENT_VERSION;
	}

}
