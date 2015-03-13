package com.mercury.chat.common.codec.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.io.IOException;
import java.util.Map;

import com.mercury.chat.common.struct.protocol.Message;

public final class MessageEncoder extends MessageToByteEncoder<Message> {

    MarshallingEncoder marshallingEncoder;

    public MessageEncoder() throws IOException {
    	this.marshallingEncoder = new MarshallingEncoder();
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf sendBuf) throws Exception {
	if (msg == null || msg.getHeader() == null)
	    throw new Exception("The encode message is null");
		sendBuf.writeInt((msg.getHeader().getVersion()));
		sendBuf.writeInt((msg.getHeader().getLength()));
		sendBuf.writeLong((msg.getHeader().getSessionID()));
		sendBuf.writeByte((msg.getHeader().getType()));
		sendBuf.writeByte((msg.getHeader().getPriority()));
		sendBuf.writeInt((msg.getHeader().getAttachment().size()));
		String key = null;
		byte[] keyArray = null;
		Object value = null;
		for (Map.Entry<String, Object> param : msg.getHeader().getAttachment().entrySet()) {
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
			sendBuf.setInt(4, sendBuf.readableBytes() - 8);
	    }
}
