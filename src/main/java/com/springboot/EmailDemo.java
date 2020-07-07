package com.springboot;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;
import java.io.StringWriter;
import java.util.Properties;

/**
 * @Author:wjy
 * @Date: 2020/7/7.
 * @description:
 */
public class EmailDemo {
	public static void main(String[] args) {
		System.out.println("https://blog.csdn.net/qq_37745470/article/details/89094227");
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

		javaMailSender.setHost("smtp.qq.com");
		javaMailSender.setUsername("1415085202@qq.com");
		javaMailSender.setPassword("cqzhezsjkddojfjh");  //授权码
		javaMailSender.setDefaultEncoding("UTF-8");

		Properties properties = new Properties();
		//设置通过ssl协议使用465端口发送、使用默认端口（25）时下面三行不需要
		properties.setProperty("mail.smtp.auth", "true");//开启认证
		properties.setProperty("mail.smtp.socketFactory.port", "465");//设置ssl端口
		properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		javaMailSender.setJavaMailProperties(properties);

		try {

			//
			Configuration cfg = new Configuration(Configuration.VERSION_2_3_27);
			cfg.setClassLoaderForTemplateLoading(ClassLoader.getSystemClassLoader(),"ftl");
			Template emailTemplate = cfg.getTemplate("ce.ftl");
			StringWriter out = new StringWriter();
			Object data = new Object();

			emailTemplate.process(data,out);


			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message,true);
			helper.setFrom("1415085202@qq.com","1415085202");
			helper.setTo("w_j_y3512@163.com");
			helper.setSubject("测试邮件");
			helper.setText("测试邮件内容",true);
			javaMailSender.send(message);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
