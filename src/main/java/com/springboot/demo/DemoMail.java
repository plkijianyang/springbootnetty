package com.springboot.demo;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import java.io.StringWriter;

/**
 * @Author:wjy
 * @Date: 2020/7/7.
 * @description:
 */
@Service
public class DemoMail {

	@Resource
	private JavaMailSender javaMailSender;

	public void tets(){

		try {
//			Configuration cfg = new Configuration(Configuration.VERSION_2_3_27);
//			cfg.setClassLoaderForTemplateLoading(ClassLoader.getSystemClassLoader(),"ftl");
//			Template emailTemplate = cfg.getTemplate("ce.ftl");
//			StringWriter out = new StringWriter();
//			Object data = new Object();
//
//			emailTemplate.process(data,out);


			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message,true);
			helper.setFrom("1415085202@qq.com","1415085202");
			helper.setTo("w_j_y3512@163.com");
			helper.setSubject("测试邮件");
			helper.setText("测试邮件内容",true);
			javaMailSender.send(message);
		}catch (Exception e){

		}
	}
}
