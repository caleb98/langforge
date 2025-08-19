package net.calebscode.langforge.app.plugin;

import java.util.HashMap;
import java.util.Optional;

public class LangforgePluginApiProvider {

	private HashMap<Class<?>, Object> apis = new HashMap<>();
	private boolean isInitialized = false;

	void setInitialized() {
		isInitialized = true;
	}

	public <T> boolean registerApi(T api) {
		var apiClass = api.getClass();

		if (apis.containsKey(apiClass)) {
			System.err.println(String.format(
				"Unable to register plugin API of type %s. An API of this type has already been registered.",
				apiClass.getName()));
			return false;
		}

		apis.put(apiClass, api);
		return true;
	}

	public <T> Optional<T> getApi(Class<T> apiClass) {
		if (!isInitialized) {
			return Optional.empty();
		}

		var api = apis.get(apiClass);
		if (api == null) {
			return Optional.empty();
		}

		@SuppressWarnings("unchecked")
		var typedApi = (T) api;
		return Optional.of(typedApi);
	}

}
