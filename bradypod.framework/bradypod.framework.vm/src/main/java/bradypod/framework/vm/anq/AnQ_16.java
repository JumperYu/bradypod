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
		
		// 期待是打印 bradypod/framework/vm/anq/AnQ_16.class, 实际上.变成了正则表达式
		System.out.println(AnQ_16.class.getName().replaceAll(".", "/") + ".class");
		
		// 正确的方式
		System.out.println(AnQ_16.class.getName().replaceAll("\\.", "/") + ".class");
		
		// regex类库
		System.out.println(AnQ_16.class.getName().replaceAll(Pattern.quote("."), "/") + ".class");
		
		// 错误的方式
		//System.out.println(AnQ_16.class.getName().replaceAll(Pattern.quote("."), File.separator) + ".class");
		// 正确的方式
		System.out.println(AnQ_16.class.getName().replaceAll(Pattern.quote("."), Matcher.quoteReplacement(File.separator)) + ".class");
	}

}
