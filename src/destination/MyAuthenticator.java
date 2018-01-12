import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

class MyAuthenticator extends Authenticator
{
	private String username;
	private String password;

	MyAuthenticator(String username, String password)
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
