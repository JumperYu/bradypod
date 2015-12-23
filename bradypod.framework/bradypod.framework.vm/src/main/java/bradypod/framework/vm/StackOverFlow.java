package bradypod.framework.vm;

/**
 * 栈溢出
 * 
 * @VM -Xss128k
 *
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2015年12月23日 上午9:27:03
 */
public class StackOverFlow {

	private int i; // 栈的值

	public void plus() {

		i++;

		plus();

	}

	public static void main(String[] args) {

		StackOverFlow stackOverFlow = new StackOverFlow();

		try {

			stackOverFlow.plus();

		} catch (Exception e) {

			System.out.println("Exception:stack length:" + stackOverFlow.i);

			e.printStackTrace();

		} catch (Error e) {

			System.out.println("Error:stack length:" + stackOverFlow.i);

			e.printStackTrace();

		}

	}

}
