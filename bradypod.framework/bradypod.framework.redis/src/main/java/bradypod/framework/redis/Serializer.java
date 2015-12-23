package bradypod.framework.redis;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;

class Serializer {

	Object marshal(Object object) {
		if (object == null)
			return null;
		return new BytesWrapper((Serializable) object);
	}

	Object unmarshal(Object object) {
		if (object == null)
			return null;
		return ((BytesWrapper) object).readObject();
	}

}

class BytesWrapper {

	private byte[] bytes;

	<T extends Serializable> BytesWrapper(T object) {
		writeBytes(object);
	}

	<T extends Serializable> void writeBytes(T object) {
		try {
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			ObjectOutputStream output = new ObjectOutputStream(buffer);
			output.writeObject(object);
			output.flush();
			bytes = buffer.toByteArray();
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new IllegalStateException(e);
		}
	}

	Object readObject() {
		try {
			ObjectInputStream input = new ObjectInputStream(new ByteArrayInputStream(bytes));
			Object object = input.readObject();
			input.close();
			return object;
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalStateException(e);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(bytes);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BytesWrapper other = (BytesWrapper) obj;
		if (!Arrays.equals(bytes, other.bytes))
			return false;
		return true;
	}

}
