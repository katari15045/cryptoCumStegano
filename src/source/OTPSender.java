import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import javafx.concurrent.Task;
import javafx.scene.control.Button;

public class OTPSender extends Task<Boolean> 
{
	private String fromEmail = null;
	private String password = null;
	private String toEmailID = null;
	private String subject = null;
	private String body = null;
	private String host = null;
	private String port = null;
	
	private Properties properties = null;
	private MimeMessage mimeMessage = null;
	private Session session = null;
	
	public OTPSender(String toEmailID)
	{
		this.toEmailID = toEmailID;
	}
	
	@Override
	public Boolean call()
	{
		StringBuilder stringBuilder = null;
		
		updateMessage("Sending OTP...");
		fromEmail = "saketh9977.test@gmail.com";
		password = "bear_grylls_9977";	
		host = "smtp.gmail.com";
		port = "587";
		
		properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", port);
		session = Session.getInstance(properties, new MyAuthenticator(fromEmail, password));
		
		subject = "Steganography cum Cryptography : Email Verification";
		stringBuilder = new StringBuilder();
		stringBuilder.append("Hi, \n\n");
		stringBuilder.append("OTP to verify your email is " + MyEmail.otp + ".\n\n");
		body = stringBuilder.toString();
		
        	try
        	{
        		mimeMessage = new MimeMessage(session);
        	        mimeMessage.setFrom( new InternetAddress(fromEmail) );
	                mimeMessage.setRecipients( Message.RecipientType.TO, InternetAddress.parse(toEmailID) );
                	mimeMessage.setSubject(subject);
	                mimeMessage.setText(body);

        	        Transport.send(mimeMessage);
                	System.out.println("OTP Sent!");
        	}

        	catch(Exception e)
        	{
                	e.printStackTrace();
	        }
	
		return true;
	}
	
}
