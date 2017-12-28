package application;

/*
 *	Tutorial 	-> 	https://www.javatpoint.com/example-of-sending-attachment-with-email-using-java-mail-api
 *	Download mail.jar from http://www.oracle.com/technetwork/java/javasebusiness/downloads/java-archive-downloads-eeplat-419426.html#javamail-1.4.7-oth-JPR
 *  Place it here - /usr/lib/jvm/java-8-openjdk-amd64/jre/lib/ext
 *
 */

import javax.mail.internet.MimeMultipart;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.mail.Multipart;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.activation.DataSource;
import javax.mail.internet.MimeBodyPart;
import javax.mail.BodyPart;

import java.lang.Exception;
import javax.mail.PasswordAuthentication;
import java.util.Properties;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.InternetAddress;
import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.Authenticator;

public class MyEmail implements EventHandler<ActionEvent>
{
	public static final int OTP = 1;
	public static final int ATTACHMENT = 2;
	
	private TextField textFieldToEmail = null;
	private String otp = null;
	private String verifiedEmailID = null;
	private Integer emailType = null;
	private String fromEmail = null;
	private String password = null;
	private String toEmail = null;
	private String subject = null;
	private String body = null;
	private String host = null;
	private String port = null;
	private String filePath = null;
	private Stage stage = null;

	private Properties properties;
	private Session session;
	private MimeMessage mimeMessage;
	private BodyPart bodyPartMessage;
	private MimeBodyPart bodyPartAttachment;
	private DataSource dataSource;
	private Multipart multiPart;
	
	public MyEmail(TextField textFieldToEmail, String otp, String filePath, int emailType, Stage stage)
	{
		this.textFieldToEmail = textFieldToEmail;
		this.otp = otp;
		this.filePath = filePath;
		this.emailType = emailType;
		this.stage = stage;
	}
	
	public MyEmail(String verifiedEmailID, TextField textFieldToEmail, String filePath, int emailType, Stage stage)
	{
		this.textFieldToEmail =textFieldToEmail;
		this.verifiedEmailID = verifiedEmailID;
		this.filePath = filePath;
		this.emailType = emailType;
		this.stage = stage;
	}

	@Override
	public void handle(ActionEvent event)
	{	
		fromEmail = "saketh9977.test@gmail.com";
		password = "bear_grylls_9977";
		toEmail = textFieldToEmail.getText();
		host = "smtp.gmail.com";
		port = "587";
		
		properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", port);
		session = Session.getInstance(properties, new MyAuthenticator(fromEmail, password));
		
		if( emailType == ATTACHMENT )
		{
			sendAttachment();
		}
		
		else if( emailType == OTP )
		{
			sendOTP();
		}
	}
	
	private void sendOTP()
	{
		StringBuilder stringBuilder = null;
		Alert alert = null;
		OTPVerifierGUI otpVerifierGUI = null;
		
		subject = "Steganography cum Cryptography : Email Verification";
		stringBuilder = new StringBuilder();
		stringBuilder.append("Hi, \n\n");
		stringBuilder.append("OTP to verify your email is " + otp + ".\n\n");
		body = stringBuilder.toString();
		
        try
        {
        		mimeMessage = new MimeMessage(session);
                mimeMessage.setFrom( new InternetAddress(fromEmail) );
                mimeMessage.setRecipients( Message.RecipientType.TO, InternetAddress.parse(toEmail) );
                mimeMessage.setSubject(subject);
                mimeMessage.setText(body);

                Transport.send(mimeMessage);
        }

        catch(Exception e)
        {
                e.printStackTrace();
        }
        
        alert = new Alert( AlertType.INFORMATION );
        alert.setTitle("Step 2 completed!");
        alert.setHeaderText("Success!");
        alert.setContentText("OTP sent successfully!");
        alert.show();
        
        otpVerifierGUI = new OTPVerifierGUI(otp, toEmail, filePath);
        otpVerifierGUI.start(stage);
	}
	
	private void sendAttachment()
	{
		StringBuilder stringBuilder = null;
		Alert alert = null;
		
		subject = "Steganography cum Cryptography : public key";
		stringBuilder = new StringBuilder();
		stringBuilder.append("Hi, \n\n");
		stringBuilder.append("If you want to send data to " + verifiedEmailID + " then please find the attached public key\n\n");
		stringBuilder.append("Else kindly ignore this email.\n\n");
		body = stringBuilder.toString();
		
		try
		{
			mimeMessage = new MimeMessage(session);
			mimeMessage.setFrom( new InternetAddress(fromEmail) );
			mimeMessage.setRecipients( Message.RecipientType.TO, InternetAddress.parse(toEmail) );
			mimeMessage.setSubject(subject);
			
			bodyPartMessage = new MimeBodyPart();
			bodyPartMessage.setText(body);

			bodyPartAttachment = new MimeBodyPart();
			dataSource = new FileDataSource(filePath);
			bodyPartAttachment.setDataHandler( new DataHandler(dataSource) );
			bodyPartAttachment.setFileName("destination_public_key.txt");

			multiPart = new MimeMultipart();
			multiPart.addBodyPart(bodyPartMessage);
			multiPart.addBodyPart(bodyPartAttachment);

			mimeMessage.setContent(multiPart);
			Transport.send(mimeMessage);
		}

		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Step 4 Done!");
		alert.setHeaderText("Success!");
		alert.setContentText("Public key sent successfully!");
		alert.show();
	}

	public void setPassword(String password)
	{
		this.password = password;
	}
}


class MyAuthenticator extends Authenticator
{
	private String username;
	private String password;

	public MyAuthenticator(String username, String password)
	{
		this.username = username;
		this.password = password;
	}

	@Override
	protected PasswordAuthentication getPasswordAuthentication()
	{
		return new PasswordAuthentication(username, password);
	}
}
