package bradypod.framework.agent.core.asm;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

/**
 * 写一些可能有用的工具
 * 
 * @author xiangmin.zxm
 * @date 2017/02/04
 */
public class ASMUtil implements Opcodes {

	/**
	 * get opcode by type
	 * 
	 * <p>
	 * ILOAD is used to load a boolean, byte, char, short, or int local
	 * variable, LLOAD, FLOAD and DLOAD are used to load a long, float, double
	 * value. ALOAD is used to load any non primitive value, i.e. object and
	 * array reference.
	 * 
	 * @param type
	 *            the parameter type
	 * @return {@code opcodes} return
	 * @see org.objectweb.asm.Opcodes
	 */
	public static int getXLOAD(Class<?> type) {
		if (type.isPrimitive()) {
			if (type == int.class || type == short.class || type == byte.class || type == char.class) {
				return ILOAD;
			} else if (type == long.class) {
				return LLOAD;
			} else if (type == float.class) {
				return FLOAD;
			} else if (type == double.class) {
				return DLOAD;
			} else {
				throw new IllegalArgumentException("unknown primitive type");
			}
		} else {
			return ALOAD;
		}
	}

	/**
	 * @see bradypod.framework.agent.core.asm.ASMUtil.getXLOAD(Class<?>)
	 */
	public static int getXSTORE(Class<?> type) {
		if (type.isPrimitive()) {
			if (type == int.class || type == short.class || type == byte.class || type == char.class) {
				return ISTORE;
			} else if (type == long.class) {
				return LSTORE;
			} else if (type == float.class) {
				return FSTORE;
			} else if (type == double.class) {
				return DSTORE;
			} else {
				throw new IllegalArgumentException("unknown primitive type");
			}
		} else {
			return ASTORE;
		}
	}
	
	/**
	 * @see bradypod.framework.agent.core.asm.ASMUtil.getXLOAD(Class<?>)
	 */
	public static int getXRETURN(Class<?> type) {
		if (type.isPrimitive()) {
			if (type == int.class || type == short.class || type == byte.class || type == char.class) {
				return IRETURN;
			} else if (type == long.class) {
				return LRETURN;
			} else if (type == float.class) {
				return FRETURN;
			} else if (type == double.class) {
				return DRETURN;
			} else {
				throw new IllegalArgumentException("unknown primitive type");
			}
		} else {
			return ARETURN;
		}
	}

	/**
	 * 获取字节描述符, 含特殊逻辑
	 * 
	 * @param type
	 * @param returnType
	 * @param isSpecial
	 *            {@code true} is going to hack code, false is default
	 * @return
	 */
	public static String getDescriptor(Class<?> type, boolean isSpecial) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("(");
		if (isSpecial && !type.isPrimitive() && type != String.class) {
			buffer.append("Ljava/lang/Object;");
		} else {
			buffer.append(Type.getDescriptor(type));
		}
		buffer.append(")").append("V");
		return buffer.toString();
	}
}
