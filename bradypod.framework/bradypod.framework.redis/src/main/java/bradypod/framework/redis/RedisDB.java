package bradypod.framework.redis;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.TreeSet;

@SuppressWarnings("unchecked")
public class RedisDB {

	private Persistence persistence = new Persistence();

	private Serializer serializer = new Serializer();

	private static final Object[] NULL = new Object[0];

	// =================================================
	// String value
	// =================================================

	public void Set(String key, Object value) {
		persistence.put(key, serializer.marshal(value));
	}

	public Object Get(String key) {
		return serializer.unmarshal(persistence.get(key));
	}

	public Object[] MGet(String... keys) {
		Object[] values = new Object[keys.length];
		for (int i = 0; i < keys.length; i++)
			values[i] = Get(keys[i]);
		return values;
	}

	public int Incr(String key) {
		Object value = Get(key);
		Integer valueRef = (value == null) ? 1 : (Integer) value;
		Set(key, valueRef + 1);
		return valueRef;
	}

	// =================================================
	// List value
	// =================================================

	public void LPush(String key, Object... values) {
		Object list = persistence.get(key);
		if (list == null)
			list = new LinkedList<Object>();
		else
			list = serializer.unmarshal(list);

		LinkedList<Object> listRef = (LinkedList<Object>) list;
		for (Object value : values)
			listRef.addFirst(value);
		persistence.put(key, serializer.marshal(list));
	}

	public void RPush(String key, Object... values) {
		Object list = persistence.get(key);
		if (list == null)
			list = new LinkedList<Object>();
		else
			list = serializer.unmarshal(list);

		LinkedList<Object> listRef = (LinkedList<Object>) list;
		for (Object value : values)
			listRef.addLast(value);
		persistence.put(key, serializer.marshal(list));
	}

	public Object[] LRange(String key, int start, int end) {
		Object list = persistence.get(key);
		if (list == null)
			return NULL;

		LinkedList<Object> listRef = (LinkedList<Object>) serializer.unmarshal(list);
		if (end > listRef.size())
			end = listRef.size();
		return listRef.subList(start, end).toArray();
	}

	// =================================================
	// Unsorted Set value
	// =================================================

	public void SAdd(String key, Object... values) {
		Object set = persistence.get(key);
		if (set == null)
			set = new HashSet<Object>();
		else
			set = serializer.unmarshal(set);

		HashSet<Object> setRef = (HashSet<Object>) set;
		for (Object value : values)
			setRef.add(value);
		persistence.put(key, serializer.marshal(set));
	}

	public Object[] SMembers(String key) {
		Object set = persistence.get(key);
		if (set == null)
			return NULL;

		set = serializer.unmarshal(set);
		return ((HashSet<Object>) set).toArray();
	}

	public Object[] SInter(String key1, String key2) {
		Object set1 = persistence.get(key1);
		Object set2 = persistence.get(key2);
		if (set1 == null || set2 == null)
			return NULL;

		HashSet<Object> set1Ref = (HashSet<Object>) serializer.unmarshal(set1);
		HashSet<Object> set2Ref = (HashSet<Object>) serializer.unmarshal(set2);
		set1Ref.retainAll(set2Ref);
		return set1Ref.toArray();
	}

	public Object[] SDiff(String key1, String key2) {
		Object set1 = persistence.get(key1);
		Object set2 = persistence.get(key2);
		if (set1 == null || set2 == null)
			return NULL;

		HashSet<Object> set1Ref = (HashSet<Object>) serializer.unmarshal(set1);
		HashSet<Object> set2Ref = (HashSet<Object>) serializer.unmarshal(set2);
		set1Ref.removeAll(set2Ref);
		return set1Ref.toArray();
	}

	// =================================================
	// Sorted Set value
	// =================================================

	public void ZAdd(String key, Object... values) {
		Object set = persistence.get(key);
		if (set == null)
			set = new TreeSet<Object>();
		else
			set = serializer.unmarshal(set);

		TreeSet<Object> setRef = (TreeSet<Object>) set;
		for (Object value : values)
			setRef.add(value);
		persistence.put(key, serializer.marshal(set));
	}

	public Object[] SRange(String key, Object from) {
		Object set = persistence.get(key);
		if (set == null)
			return NULL;

		set = serializer.unmarshal(set);
		return ((TreeSet<Object>) set).tailSet(from).toArray();
	}

}
