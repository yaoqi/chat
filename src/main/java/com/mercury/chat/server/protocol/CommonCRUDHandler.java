package com.mercury.chat.server.protocol;

import static com.mercury.chat.common.MessageType.CRUD;
import static com.mercury.chat.common.util.Messages.buildMessage;
import io.netty.channel.ChannelHandlerContext;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mercury.chat.common.MessageType;
import com.mercury.chat.common.OrderRequest;
import com.mercury.chat.common.ProductRequest;
import com.mercury.chat.common.QuickReplyRequest;
import com.mercury.chat.common.constant.Operation;
import com.mercury.chat.common.struct.protocol.Message;
import com.mercury.chat.user.repository.UserRepository;

public class CommonCRUDHandler extends ValidatableMessageHandler {

	static final Logger logger = LogManager.getLogger(CommonCRUDHandler.class);
	
	private final UserRepository userService;
	
	public CommonCRUDHandler(UserRepository userService){
		this.userService = userService;
	}
	
	@Override
	protected MessageType _() {
		return CRUD;
	}
	
	@Override
	 public void onMessage(ChannelHandlerContext ctx, Message msg) throws Exception {
		
		logger.log(Level.INFO, "Receive client CRUD request : ---> "+ msg);

		Object response = null;
		
		Object request = msg.getBody();
		if(request instanceof QuickReplyRequest){
			QuickReplyRequest qrreq = (QuickReplyRequest) request;
			Operation operation = Operation.valOf(qrreq.getOperation());
			switch(operation){
				case LOAD:
					response = userService.loadQuickReply(qrreq.getSaleId());
					break;
				case UPDATE:
					userService.updateQuickReply(qrreq.getSaleId(), qrreq.getQuickReply());
					break;
				case DELETE:
					userService.deleteReply(qrreq.getSaleId(), qrreq.getQuickReply());
					break;
				default:
					break;
			}
		}else{
			//add other handler logic.
			if(request instanceof ProductRequest){
				ProductRequest productRequest = (ProductRequest) request;
				response = userService.loadProductSummary(productRequest.getProductId());
			}else if(request instanceof OrderRequest){
				OrderRequest orderRequest = (OrderRequest) request;
				response = userService.loadOrderSummary(orderRequest.getOrderId());
			}
		}
		
		ctx.writeAndFlush(buildMessage(CRUD, msg.getRequestId(), response));
    }
	
}
