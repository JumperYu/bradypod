package bradypod.framework.vm.anq;

import java.util.IdentityHashMap;
import java.util.Map;

/**
 * HashMapµÄÎÊÌâ
 * 
 * @author xiangmin.zxm
 *
 */
public class AnQ_22 {

	public static void main(String[] args) {

		Map<String, String> m = new IdentityHashMap<String, String>();
		m.put("Mickey", "Mouse");
		m.put("Mickey", "Mantle");
		System.out.println(m.size());

	}

}
