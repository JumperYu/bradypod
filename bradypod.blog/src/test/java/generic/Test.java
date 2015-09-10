package generic;

import java.util.List;


public interface Test {
	
	public <T> Object get(Class<T> clasz);
	
	<E> List<E> selectList(String statement, Object parameter);
}
