package com.mercury.chat.common.codec.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.mercury.chat.common.struct.protocol.Header;
import com.mercury.chat.common.struct.protocol.Message;
import com.mercury.chat.user.entity.User;
import static com.mercury.chat.common.constant.Constant.FROM;
import static com.mercury.chat.common.constant.Constant.TO;

public class TestCodeC {

    MarshallingEncoder marshallingEncoder;
    MarshallingDecoder marshallingDecoder;

    public TestCodeC() throws IOException {
    	marshallingDecoder = new MarshallingDecoder();
    	marshallingEncoder = new MarshallingEncoder();
    }

    public Message getMessage() {
		Message message = new Message();
		Header header = new Header();
		header.length(123);
		header.sessionID(99999);
		header.statusCode(121);
		header.type((byte) 1);
		header.priority((byte) 7);
		Map<String, Object> attachment = new HashMap<String, Object>();
		for (int i = 0; i < 10; i++) {
		    attachment.put("ciyt --> " + i, "123456 " + i);
		}
		header.attachment(attachment);
		message.setHeader(header);
		message.setBody("abcdefg-----------------------AAAAAA");
		return message;
    }
    
    public Message getUserMessage() {
		Message message = new Message();
		Header header = new Header();
		header.length(123);
		header.sessionID(99999);
		header.statusCode(121);
		header.type((byte) 1);
		header.priority((byte) 7);
		Map<String, Object> attachment = new HashMap<String, Object>();
		attachment.put(FROM, "god");
		attachment.put(TO, "bigboy");
		header.attachment(attachment);
		message.setHeader(header);
		message.setBody(new User("bigboy","pwd"));
		return message;
    }

    public ByteBuf encode(Message msg) throws Exception {
		ByteBuf sendBuf = Unpooled.buffer();
		sendBuf.writeInt((msg.getHeader().version()));
		sendBuf.writeInt((msg.getHeader().length()));
		sendBuf.writeLong((msg.getHeader().sessionID()));
		sendBuf.writeInt((msg.getHeader().statusCode()));
		sendBuf.writeByte((msg.getHeader().type()));
		sendBuf.writeByte((msg.getHeader().priority()));
		sendBuf.writeInt((msg.getHeader().attachment().size()));
		String key = null;
		byte[] keyArray = null;
		Object value = null;
	
		for (Map.Entry<String, Object> param : msg.getHeader().attachment().entrySet()) {
		    key = param.getKey();
		    keyArray = key.getBytes("UTF-8");
		    sendBuf.writeInt(keyArray.length);
		    sendBuf.writeBytes(keyArray);
		    value = param.getValue();
		    marshallingEncoder.encode(value, sendBuf);
		}
		key = null;
		keyArray = null;
		value = null;
		if (msg.getBody() != null) {
		    marshallingEncoder.encode(msg.getBody(), sendBuf);
		} else
		    sendBuf.writeInt(0);
		sendBuf.setInt(4, sendBuf.readableBytes());
		return sendBuf;
    }

    public Message decode(ByteBuf in) throws Exception {
		Message message = new Message();
		Header header = new Header();
		header.version(in.readInt());
		header.length(in.readInt());
		header.sessionID(in.readLong());
		header.statusCode(in.readInt());
		header.type(in.readByte());
		header.priority(in.readByte());
	
		int size = in.readInt();
		if (size > 0) {
		    Map<String, Object> attch = new HashMap<String, Object>(size);
		    int keySize = 0;
		    byte[] keyArray = null;
		    String key = null;
		    for (int i = 0; i < size; i++) {
			keySize = in.readInt();
			keyArray = new byte[keySize];
			in.readBytes(keyArray);
			key = new String(keyArray, "UTF-8");
			attch.put(key, marshallingDecoder.decode(in));
		    }
		    keyArray = null;
		    key = null;
		    header.attachment(attch);
		}
		if (in.readableBytes() > 4) {
		    message.setBody(marshallingDecoder.decode(in));
		}
		message.setHeader(header);
		return message;
    }

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
		TestCodeC testC = new TestCodeC();
		Message message = testC.getMessage();
		System.out.println(message + "[body ] " + message.getBody());
	
		for (int i = 0; i < 5; i++) {
		    ByteBuf buf = testC.encode(message);
		    Message decodeMsg = testC.decode(buf);
		    System.out.println(decodeMsg + "[body ] " + decodeMsg.getBody());
		    System.out.println("-------------------------------------------------");
		}
		
		 message = testC.getUserMessage();
		 System.out.println(message + "[body ] " + message.getBody());
		
		for (int i = 0; i < 5; i++) {
		    ByteBuf buf = testC.encode(message);
		    Message decodeMsg = testC.decode(buf);
		    System.out.println(decodeMsg + "[body ] " + decodeMsg.getBody());
		    System.out.println("-------------------------------------------------");
		}
		
    }

}
