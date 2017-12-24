/*
 *	Tutorial 	-> 	https://www.javatpoint.com/example-of-sending-attachment-with-email-using-java-mail-api
 *	Download mail.jar from http://www.oracle.com/technetwork/java/javasebusiness/downloads/java-archive-downloads-eeplat-419426.html#javamail-1.4.7-oth-JPR
 *  Place it here - /usr/lib/jvm/java-8-openjdk-amd64/jre/lib/ext
 *
 */

import javax.mail.internet.MimeMultipart;
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

public class Email
{
	private static String fromEmail = null;
	private static String password = null;
	private static String toEmail = null;
	private static String subject = null;
	private static String body = null;
	private static String host = null;
	private static String port = null;
	private static String fileName = null;

	private static Properties properties;
	private static Session session;
	private static MimeMessage mimeMessage;
	private static BodyPart bodyPartMessage;
	private static MimeBodyPart bodyPartAttachment;
	private static DataSource dataSource;
	private static Multipart multiPart;

	public static void send(String toEmail, String subject, String body, String fileName)
	{
		Email.toEmail = toEmail;
		Email.subject = subject;
		Email.body = body;
		Email.fromEmail = "saketh9977.test@gmail.com";
		Email.password = "bear_grylls_9977";
		Email.host = "smtp.gmail.com";
		port = "587";

		properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", port);
		session = Session.getInstance(properties, new MyAuthenticator(fromEmail, password));
		
		try
		{
			mimeMessage = new MimeMessage(session);
			mimeMessage.setFrom( new InternetAddress(fromEmail) );
			mimeMessage.setRecipients( Message.RecipientType.TO, InternetAddress.parse(toEmail) );
			mimeMessage.setSubject(subject);
			
			bodyPartMessage = new MimeBodyPart();
			bodyPartMessage.setText("Hi,\n\nPlease find the attached image!\n");

			bodyPartAttachment = new MimeBodyPart();
			dataSource = new FileDataSource(fileName);
			bodyPartAttachment.setDataHandler( new DataHandler(dataSource) );
			bodyPartAttachment.setFileName(fileName);

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
