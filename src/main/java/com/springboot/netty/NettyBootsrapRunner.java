package com.springboot.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.AttributeKey;
import org.springframework.beans.BeansException;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author:wjy
 * @Date: 2020/7/1.
 * @description:
 */
@Component
public class NettyBootsrapRunner implements ApplicationRunner, ApplicationListener<ContextClosedEvent> {

	private EventLoopGroup bossGroup;
	private EventLoopGroup workerGroup;


	@Resource
	private ChannelInitializerNetty channelInitializerNetty;


	@Override
	public void run(ApplicationArguments args) throws Exception {

		// 1. 配置 bossGroup 和 workerGroup
		bossGroup = new NioEventLoopGroup(1);
		workerGroup = new NioEventLoopGroup();

		// 3. 创建并配置服务端启动辅助类 ServerBootstrap
		ServerBootstrap b = new ServerBootstrap();
		b.group(bossGroup, workerGroup)
				.channel(NioServerSocketChannel.class)
				.option(ChannelOption.SO_BACKLOG, 100)
				.handler(new LoggingHandler(LogLevel.INFO))
				.attr(AttributeKey.newInstance("name"), "godme")
				.childHandler(channelInitializerNetty);

//		(ChannelHandler) applicationContext.getBean("ChannelInitializerNetty")
		// 4. 阻塞绑定端口
		ChannelFuture f = b.bind(8081).sync();

		// 5. 为服务端关闭的 ChannelFuture 添加监听器，用于实现优雅关闭
		f.channel().closeFuture().addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
				bossGroup.shutdownGracefully();
				workerGroup.shutdownGracefully();
			}
		});

	}


	/**
	 * springboot关闭事件
	 * @param contextClosedEvent
	 */
	@Override
	public void onApplicationEvent(ContextClosedEvent contextClosedEvent) {
		bossGroup.shutdownGracefully();
		workerGroup.shutdownGracefully();
	}
}
