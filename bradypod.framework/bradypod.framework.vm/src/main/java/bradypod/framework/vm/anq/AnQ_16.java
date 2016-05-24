package bradypod.framework.vm.anq;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String.relaceAll
 * 
 * @author xiangmin.zxm
 *
 */
public class AnQ_16 {

	public static void main(String[] args) {
		
		// �ڴ��Ǵ�ӡ bradypod/framework/vm/anq/AnQ_16.class, ʵ����.�����������ʽ
		System.out.println(AnQ_16.class.getName().replaceAll(".", "/") + ".class");
		
		// ��ȷ�ķ�ʽ
		System.out.println(AnQ_16.class.getName().replaceAll("\\.", "/") + ".class");
		
		// regex���
		System.out.println(AnQ_16.class.getName().replaceAll(Pattern.quote("."), "/") + ".class");
		
		// ����ķ�ʽ
		//System.out.println(AnQ_16.class.getName().replaceAll(Pattern.quote("."), File.separator) + ".class");
		// ��ȷ�ķ�ʽ
		System.out.println(AnQ_16.class.getName().replaceAll(Pattern.quote("."), Matcher.quoteReplacement(File.separator)) + ".class");
	}

}
