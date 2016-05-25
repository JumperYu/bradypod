package bradypod.framework.vm.anq;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * �﷨��
 * 
 * @author xiangmin.zxm
 *
 */
public class AnQ_21 {

	/**
	 * 1, ����List���麬��С��,С��,С��,С��, ����С��,С��,С����ͬ��˳��ȥ�ص�List
	 */
	public static <E> List<E> withoutDuplicates(List<E> origanal) {
		return new ArrayList<>(new LinkedHashSet<E>(origanal));
	}

	/**
	 * 2, ����"С��, С��, С��" ���� ["С��","С��","С��"] ���Һ���ǰ��ո�
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

		// ����1
		System.out.println(withoutDuplicates(Arrays.asList("С��", "С��", "С��", "С��", "Сͩ")));

		// ����2
		System.out.println(Arrays.toString(parse("С��, С��, С��")));

		// ����3
		deepToString(new String[] { "С��", "С��", "С��", "С��", "Сͩ" });
		
		// ����4
		System.out.println(Integer.bitCount(8));
	}

}
