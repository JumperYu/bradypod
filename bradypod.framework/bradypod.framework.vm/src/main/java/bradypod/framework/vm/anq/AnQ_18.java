package bradypod.framework.vm.anq;

import java.util.Random;

/**
 * switch
 */
public class AnQ_18 {

	private static Random rnd = new Random();
	
	public static void main(String[] args) {

		// ����bug
		// 1: randomֻ�ܵ�0��1��
		// 2: ľ��break��� 
		// 3: new StringBuffer(int capacity)
		
		StringBuffer word = null;
		switch (rnd.nextInt(2)) {
		case 1:
			word = new StringBuffer('P');
		case 2:
			word = new StringBuffer('G');
		default:
			word = new StringBuffer('M');
		}
		word.append('a');
		word.append('i');
		word.append('n');
		System.out.println(word);

	}

}
