package bradypod.framework.server;

import java.util.List;

import com.alibaba.fastjson.JSON;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class NioDecoder extends ByteToMessageDecoder {

	private Class<?> genericClass;

	public NioDecoder(Class<?> genericClass) {
		this.genericClass = genericClass;
	}

	@Override
	public final void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out)
			throws Exception {
		if (in.readableBytes() < 4) {
			return;
		}
		in.markReaderIndex();
		int dataLength = in.readInt();
		if (dataLength < 0) {
			ctx.close();
		}
		if (in.readableBytes() < dataLength) {
			in.resetReaderIndex();
		}
		byte[] data = new byte[dataLength];
		in.readBytes(data);

		Object obj = JSON.parse(data);
		out.add(obj);
	}

	public Class<?> getGenericClass() {
		return genericClass;
	}

	public void setGenericClass(Class<?> genericClass) {
		this.genericClass = genericClass;
	}

}
