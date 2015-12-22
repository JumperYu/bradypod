package bradypod.framework.server;

import com.alibaba.fastjson.JSON;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class NioEncoder extends MessageToByteEncoder<Object> {

	private Class<?> genericClass;

	public NioEncoder(Class<?> genericClass) {
		this.genericClass = genericClass;
	}

	@Override
	public void encode(ChannelHandlerContext ctx, Object in, ByteBuf out) throws Exception {
		if (genericClass.isInstance(in)) {
			// 使用FastJSON
			byte[] data = JSON.toJSONBytes(in);
			out.writeInt(data.length);
			out.writeBytes(data);
		}
	}

	public Class<?> getGenericClass() {
		return genericClass;
	}

	public void setGenericClass(Class<?> genericClass) {
		this.genericClass = genericClass;
	}

}
