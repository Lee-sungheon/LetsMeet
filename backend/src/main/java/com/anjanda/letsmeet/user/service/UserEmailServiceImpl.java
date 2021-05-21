package com.anjanda.letsmeet.user.service;

import java.util.Random;

import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.anjanda.letsmeet.repository.dto.Emailconfirm;
import com.anjanda.letsmeet.repository.mapper.EmailconfirmMapper;

@Service
public class UserEmailServiceImpl implements UserEmailService {
	
	@Autowired
	JavaMailSender emailSender;
	
	@Autowired
	EmailconfirmMapper mapper;
	
	public static final String ePw = createKey();
	
	private MimeMessage createMessage(String to) throws Exception {
		System.out.println("보내는 대상 : " + to);
		System.out.println("인증 번호 : " + ePw);
		MimeMessage message = emailSender.createMimeMessage();
		message.addRecipients(RecipientType.TO, to); // 보내는 대상
		message.setSubject("레쓰밋(Let's Meet) 인증번호가 도착했습니다."); // 제목
		
        String msgg="";
        msgg+= "<div style='margin:100px;'>";
       	msgg+= "<h1> 안녕하세요 레쓰밋(Let's Meet) 입니다 ༼ つ ◕_◕ ༽つ </h1>";
        msgg+= "<br>";
        msgg+= "<p>아래 코드를 회원가입 창으로 돌아가 입력해주세요<p>";
        msgg+= "<br>";
        msgg+= "<p>(☞ﾟヮﾟ)☞ 감사합니다 ☜(ﾟヮﾟ☜)<p>";
        msgg+= "<br>";
		msgg+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
		msgg+= "<h3 style='color:blue;'>회원가입 코드입니다.</h3>";
		msgg+= "<div style='font-size:130%'>";
		msgg+= "CODE : <strong>";
		msgg+= ePw+"</strong><div><br/> ";
		msgg+= "</div>";
        message.setText(msgg, "utf-8", "html");//내용
        message.setFrom(new InternetAddress("khyun7621@naver.com","Let's Meet"));//보내는 사람
        
        Emailconfirm emailconfirm=new Emailconfirm();
        emailconfirm.seteEmail(to);
        emailconfirm.seteConfirm(ePw);
		mapper.insertEmailconfirm(emailconfirm);
        return message;
	}
	
	/* 인증코드 제작 메소드 */
	public static String createKey() {
		StringBuffer key = new StringBuffer();
		Random rnd = new Random();
		
		for(int i = 0; i < 8; i++) {
			int index = rnd.nextInt(3); // 0~2까지 랜덤
			
			switch(index) {
			case 0:
				key.append((char) ((int)(rnd.nextInt(26))+97)); // a~z
				break;
			case 1:
				key.append((char) ((int)(rnd.nextInt(26))+65)); // A~Z
				break;
			case 2:
				key.append((rnd.nextInt(10))); // 0~9
				break;
			}
		}
		return key.toString();
	}
	
	@Override
	public void sendSimpleMessage(String to) throws Exception {
		MimeMessage message = createMessage(to);
		try {
			emailSender.send(message);
		} catch(MailException e) {
			e.printStackTrace();
			throw new IllegalArgumentException();
		}
	}

	@Override
	public Emailconfirm selectEmailconfirm(Emailconfirm emailconfirm) {

		return mapper.selectEmailconfirm(emailconfirm);
	}



		

}
