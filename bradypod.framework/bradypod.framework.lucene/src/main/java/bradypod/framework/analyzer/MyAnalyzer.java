package bradypod.framework.analyzer;

/**
 * 倒序查找
 *
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2015年12月25日 上午11:03:18
 */
public class MyAnalyzer {

	/**
	 * 过程分析： 第一词循环： 太好了，今天是星期六啊 好了，今天是星期六啊 了，今天是星期六啊 ，今天是星期六啊 今天是星期六啊 天是星期六啊
	 * 是星期六啊 星期六啊 期六啊 六啊 啊 第一次循环没有找到，则从“太好了，今天是星期六啊”中把最后一个截取，开始第二次循环
	 * 
	 * 第二次循环： 太好了，今天是星期六 好了，今天是星期六 了，今天是星期六 ，今天是星期六 今天是星期六 天是星期六 是星期六 星期六
	 * --------->输出：星期六，并在原字符串中截去“星期六”-->变为“太好了，今天是” 太好了，今天是 好了，今天是 了，今天是 ，今天是
	 * 今天是 天是 是 ----------> 输出：是， 并在原字符串中截去“是”-->变为“太好了，今天”
	 * 
	 * . . . 直到原字符串变为空。
	 */

	public static void main(String[] args) {
		String input = "太好了，今天是星期六啊"; // 要匹配的字符串
		new MyAnalyzer(input).start();
	}

	private String[] dictionary = { "今天", "是", "星期", "星期六" }; // 词典
	private String input = null;

	public MyAnalyzer(String input) {
		this.input = input;
	}

	public void start() {
		String temp = null;
		for (int i = 0; i < this.input.length(); i++) {
			temp = this.input.substring(i); // 每次从字符串的首部截取一个字，并存到temp中
			// System.out.println("*****" + temp + "*********" + this.input);
			// 如果该词在字典中， 则删除该词并在原始字符串中截取该词
			if (this.isInDictionary(temp)) {
				System.out.println(temp);
				this.input = this.input.replace(temp, "");
				i = -1; // i=-1是因为要重新查找， 而要先执行循环中的i++
			}
		}

		// 当前循环完毕，词的末尾截去一个字，继续循环， 直到词变为空
		if (null != this.input && !"".equals(this.input)) {
			this.input = this.input.substring(0, this.input.length() - 1);
			this.start();
		}
	}

	// 判断当前词是否在字典中
	public boolean isInDictionary(String temp) {
		for (int i = 0; i < this.dictionary.length; i++) {
			if (temp.equals(this.dictionary[i])) {
				return true;
			}
		}
		return false;
	}
}
