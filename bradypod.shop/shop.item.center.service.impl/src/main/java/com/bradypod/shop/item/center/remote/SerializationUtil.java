package com.bradypod.shop.item.center.remote;


/**
 * 序列化扩展
 * 
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2016年2月14日
 */
public class SerializationUtil {

	//private static Objenesis objenesis = new ObjenesisStd(true);

	private SerializationUtil() {
	}

	public static <T> byte[] serialize(T obj) {
		return null;
	}

	public static <T> T deserialize(byte[] data, Class<T> cls) {
		return null;
	}

}
