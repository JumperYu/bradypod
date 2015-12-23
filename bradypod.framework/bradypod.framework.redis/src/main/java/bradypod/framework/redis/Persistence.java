package bradypod.framework.redis;

import java.util.HashMap;

class Persistence {

	private HashMap<String, Object> storage = new HashMap<String, Object>();

	void put(String key, Object value) {
		storage.put(key, value);
	}

	Object get(String key) {
		return storage.get(key);
	}

}