package bradypod.framework.vm.anq;

/**
 * ×Ö·û´®µÄequals
 * 
 * @author xiangmin.zxm
 *
 */
public class AnQ_11 {

	public static void main(String[] args) {

		final String pig = "length: 10";
		final String dog = "length: " + pig.length();
		System.out.printf("Animals pig:%s,dog:%s are equal: %b%n", pig, dog, pig == dog);
		System.out.printf("Animals pig:%s,dog:%s are equal: %b%n", pig, dog, pig.equals(dog));
	}

}
