package generic;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class TestImpl implements Test {

	@Override
	public <T> Object get(Class<T> clasz) {
		return 1;
	}
	
	@Override
	public <E> List<E> selectList(String statement, Object parameter) {
		return  null;
	}
	
	public static void main(String[] args) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(null, null);
		Hashtable<String, Object> table = new Hashtable<String, Object>();
		// get的时候会出现nullpointer
//		table.put(null, null);
		System.out.println(map.get(null));
		System.out.println(table.get("a"));
	}

}
