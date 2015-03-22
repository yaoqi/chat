package com.mercury.chat.common.codec.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.mercury.chat.common.struct.protocol.Header;
import com.mercury.chat.common.struct.protocol.Message;

public class MessageDecoder extends LengthFieldBasedFrameDecoder {

    MarshallingDecoder marshallingDecoder;

    public MessageDecoder(int maxFrameLength, int lengthFieldOffset,int lengthFieldLength) throws IOException {
		super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
		marshallingDecoder = new MarshallingDecoder();
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
		ByteBuf frame = (ByteBuf) super.decode(ctx, in);
		if (frame == null) {
		    return null;
		}
	
		Message message = new Message();
		Header header = new Header();
		header.version(frame.readInt());
		header.length(frame.readInt());
		header.sessionID(frame.readLong());
		header.statusCode(frame.readInt());
		header.type(frame.readByte());
		header.priority(frame.readByte());
		header.requestId(frame.readLong());
		int size = frame.readInt();
		if (size > 0) {
		    Map<String, Object> attch = new HashMap<String, Object>(size);
		    int keySize = 0;
		    byte[] keyArray = null;
		    String key = null;
		    for (int i = 0; i < size; i++) {
			keySize = frame.readInt();
			keyArray = new byte[keySize];
			frame.readBytes(keyArray);
			key = new String(keyArray, "UTF-8");
			attch.put(key, marshallingDecoder.decode(frame));
		    }
		    keyArray = null;
		    key = null;
		    header.attachment(attch);
		}
		if (frame.readableBytes() > 4) {
		    message.setBody(marshallingDecoder.decode(frame));
		}
		message.setHeader(header);
		return message;
    }
}
