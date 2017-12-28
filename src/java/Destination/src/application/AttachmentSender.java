package application;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import javafx.concurrent.Task;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AttachmentSender extends Task<Boolean> 
{
	private String fromEmail = null;
	private String password = null;
	private TextField textFieldToEmail = null;
	private String toEmail = null;
	private String subject = null;
	private String verifiedEmailID = null;
	private String body = null;
	private String filePath = null;
	
	private String host = null;
	private String port = null;
	private Button button = null;
	
	private Properties properties = null;
	private MimeMessage mimeMessage = null;
	private Session session = null;
	private BodyPart bodyPartMessage = null;
	private MimeBodyPart bodyPartAttachment = null;
	private DataSource dataSource = null;
	private Multipart multiPart = null;
	
	public AttachmentSender(String verifiedEmailID, TextField textFieldToEmail, String filePath, Button button) 
	{
		this.verifiedEmailID = verifiedEmailID;
		this.textFieldToEmail = textFieldToEmail;
		this.filePath = filePath;
		this.button = button;
	}
	
	@Override
	public Boolean call()
	{
		StringBuilder stringBuilder = null;
		
		updateMessage("Sending public key...");
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
			
			updateProgress(1.0, 1.0);
			updateMessage("Attachment sent successfully!");
			button.setDisable(false);
		}

		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return true;
	}
	
	@Override
	protected void updateProgress(long workDone, long max) 
	{
		super.updateProgress(workDone, max);
	}
	
	@Override
	protected void updateMessage(String message) 
	{
		super.updateMessage(message);
	}
}
