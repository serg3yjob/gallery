package ru.service.gallery.service;

import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import ru.service.gallery.entity.UserInfo;
import ru.service.gallery.model.UserRegistration;


@Service("mailSendService")
public class MailSendServiceImpl implements MailSendService{

	
    private JavaMailSender mailSender;
    private SimpleMailMessage templateMessage;
    private VelocityEngine velocityEngine;
    
    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void setTemplateMessage(SimpleMailMessage templateMessage) {
        this.templateMessage = templateMessage;
    }
    public void setVelocityEngine(VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
     }

	@Override
	public boolean sendResorePswEmail(final UserInfo userInfo, final String restoreLink) {
		
		MimeMessagePreparator preparator = null;
		try{
				preparator = new MimeMessagePreparator() {
		   
				public void prepare(MimeMessage mimeMessage) throws Exception {
					
					String pathToTemplate = "emailTemplates/templateRestorePsw.vm";
					MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
					message.setTo(userInfo.getEmail());
					message.setSubject("Восстановление пароля для ru.service.gallery");
					message.setFrom("a.sh.s_reg@mail.ru");
					Map<String, Object> model = new HashMap<String, Object>();
					model.put("user", userInfo);
					model.put("restoreLink", restoreLink);
					String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, pathToTemplate, "UTF-8", model);
					message.setText(text, true);
				}
			};
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		try{
			this.mailSender.send(preparator);
		}catch(MailException e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	@Override
	public boolean sendRegistrationEmail(final UserRegistration userRegistration, final String registrationLink) {
		MimeMessagePreparator preparator = null;
		try{
				preparator = new MimeMessagePreparator() {
		   
				public void prepare(MimeMessage mimeMessage) throws Exception {
					
					String pathToTemplate = "emailTemplates/templateRegistration.vm";
					MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
					message.setTo(userRegistration.getEmail());
					message.setSubject("Регистрация на сервисе ru.service.gallery");
					message.setFrom("a.sh.s_reg@mail.ru");
					Map<String, Object> model = new HashMap<String, Object>();
					model.put("user", userRegistration);
					model.put("registrationLink", registrationLink);
					String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, pathToTemplate, "UTF-8", model);
					message.setText(text, true);
				}
			};
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		try{
			this.mailSender.send(preparator);
		}catch(MailException e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean sendChangeEmail(final String userName, final String changeEmailLink, final String email) {
		MimeMessagePreparator preparator = null;
		try{
				preparator = new MimeMessagePreparator() {
		   
				public void prepare(MimeMessage mimeMessage) throws Exception {
					
					String pathToTemplate = "emailTemplates/templateChangeEmail.vm";
					MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
					message.setTo(email);
					message.setSubject("Смена вашего e-mail на сервисе ru.service.gallery");
					message.setFrom("a.sh.s_reg@mail.ru");
					Map<String, Object> model = new HashMap<String, Object>();
					model.put("user", userName);
					model.put("email", email);
					model.put("changeEmailLink",changeEmailLink);
					String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, pathToTemplate, "UTF-8", model);
					message.setText(text, true);
				}
			};
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		try{
			this.mailSender.send(preparator);
		}catch(MailException e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
