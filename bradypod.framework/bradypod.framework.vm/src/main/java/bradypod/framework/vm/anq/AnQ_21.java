package bradypod.framework.vm.anq;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * 语法糖
 * 
 * @author xiangmin.zxm
 *
 */
public class AnQ_21 {

	/**
	 * 1, 传入List数组含有小红,小花,小红,小明, 返回小红,小花,小明的同样顺序但去重的List
	 */
	public static <E> List<E> withoutDuplicates(List<E> origanal) {
		return new ArrayList<>(new LinkedHashSet<E>(origanal));
	}

	/**
	 * 2, 传入"小红, 小花, 小明" 返回 ["小红","小花","小明"] 并且忽略前后空格
	 */
	public static String[] parse(String input) {
		return input.split(",\\S?");
	}

	public static <E> void deepToString(E[] array) {
		System.out.println(Arrays.deepToString(array));
	}

	public static Boolean hasMoreBitsSet(int i, int j) {
		return (Integer.bitCount(i) > Integer.bitCount(j));
	}

	public static void main(String[] args) {

		// 测试1
		System.out.println(withoutDuplicates(Arrays.asList("小明", "小花", "小明", "小菜", "小桐")));

		// 测试2
		System.out.println(Arrays.toString(parse("小红, 小花, 小明")));

		// 测试3
		deepToString(new String[] { "小明", "小花", "小明", "小菜", "小桐" });
		
		// 测试4
		System.out.println(Integer.bitCount(8));
	}

}
