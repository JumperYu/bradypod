package bradypod.framework.vm;

import java.util.ArrayList;
import java.util.List;

/**
 * 堆溢出
 *
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2015年12月23日 上午8:56:21
 */
public class HeapOutOfMemory {

	public static void main(String[] args) {
		HeapOutOfMemory heap = new HeapOutOfMemory();
		List<TestCase> cases = new ArrayList<TestCase>();
		while (true) {
			cases.add(heap.new TestCase()); // 如果直接这样创建TestCase实例将无法进行自动回收
//			cases.add(heap.new TestCase());
//			TestCase test = new TestCase();
		}
	}

	/**
	 * TestCase testCase = new TestCase(); 1, testCase 在栈上 2, new TestCase() 在堆里
	 * 
	 * @author zengxm<http://github.com/JumperYu>
	 *
	 * @date 2015年12月23日 上午9:21:51
	 */
	class TestCase {
	}

}
