package com.springboot.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import org.springframework.stereotype.Component;

/**
 * @Author:wjy
 * @Date: 2020/7/1.
 * @description:
 */
@Component
public class ChannelInitializerNetty extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel socketChannel) throws Exception {
		ChannelPipeline pipeline = socketChannel.pipeline();
//		pipeline.addLast("decoder",new FotaTcpDecoder());
//		pipeline.addLast("encoder",new FotaTcpEncoder());
		pipeline.addLast("handler",new InHandler());
	}
}
