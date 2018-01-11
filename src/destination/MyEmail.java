/*
 *	Tutorial 	-> 	https://www.javatpoint.com/example-of-sending-attachment-with-email-using-java-mail-api
 *	Download mail.jar from http://www.oracle.com/technetwork/java/javasebusiness/downloads/java-archive-downloads-eeplat-419426.html#javamail-1.4.7-oth-JPR
 *  Place it here - /usr/lib/jvm/java-8-openjdk-amd64/jre/lib/ext
 *
 */

import java.util.Random;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.concurrent.Task;
import java.lang.Thread;
import java.lang.Exception;

public class MyEmail
{
	private static Thread thread = null;
	private static ProgressIndicator progressIndicator = null;
	private static Label labelProgress = null;
	private static Button buttonNext = null;
	private static Button buttonModifyEmail = null;
	private static GridPane gridPane = null;
	private static Scene scene = null;
	static Stage stage = null;
	
	private static BackGroundEmail backGroundEmail = null;
	private static Thread backGroundThread = null;
	static String otp = null;
	static OTPSender otpSender = null;
	static AttachmentSender attachmentSender = null;

	static void sendOTP(String toEmailID, Stage stage)
	{
		MyEmail.stage = stage;
		initialize();
		backGroundEmail = new BackGroundEmail(toEmailID, stage, BackGroundEmail.OTP, buttonNext, buttonModifyEmail);
		otpSender = new OTPSender(toEmailID);
		buttonNext.textProperty().bind( otpSender.messageProperty() );
		otpSender.updateMessage("Next");
		startBackGroundThread();
	}

	static void sendAttachment(String toEmailID, Stage stage)
	{
		MyEmail.stage = stage;
		initialize();
		backGroundEmail = new BackGroundEmail(toEmailID, stage, BackGroundEmail.ATTACHMENT, buttonNext, buttonModifyEmail);
		attachmentSender = new AttachmentSender(toEmailID, MyKeyGenerator.getPublicKeyPath());
		buttonNext.textProperty().bind( attachmentSender.messageProperty() );
		attachmentSender.updateMessage("Next");
		startBackGroundThread();
	}

	private static void startBackGroundThread()
	{
		backGroundThread = new Thread(backGroundEmail);
		progressIndicator.progressProperty().bind( backGroundEmail.progressProperty() );
		labelProgress.textProperty().bind( backGroundEmail.messageProperty() );
		backGroundThread.start();
	}

	private static void initialize()
	{
		progressIndicator = new ProgressIndicator();
		progressIndicator.setProgress(-1.0);
		labelProgress = new Label();
		buttonNext = new Button();
		buttonNext.setDisable(true);
		buttonNext.setOnAction( new PostEmailHandler(stage) );
		buttonModifyEmail = new Button();
		buttonModifyEmail.setText("Change Email");
		buttonModifyEmail.setOnAction( new EmailModifier(stage) );
		buttonModifyEmail.setDisable(true);

		gridPane = new GridPane();
		gridPane.add(progressIndicator, 0,  0);
		gridPane.add(labelProgress, 0, 1);
		gridPane.add(buttonNext, 0, 2);
		gridPane.add(buttonModifyEmail, 0, 3);
		gridPane.setAlignment(Pos.CENTER);
		GridPane.setMargin(labelProgress, new Insets(20, 0, 0, 0));
		GridPane.setMargin(buttonNext, new Insets(60, 0, 0, 0));
		GridPane.setMargin(buttonModifyEmail, new Insets(20, 0, 0, 0));
		
		scene = new Scene(gridPane, Constants.WIND_COLS, Constants.WIND_ROWS);
		stage.setScene(scene);
		stage.show();
	}
}

class BackGroundEmail extends Task<Void>
{
	private Stage stage = null;
	private String toEmailID = null;
	static int mode = -1;
	private Thread thread = null;
	private Button buttonNext = null;
	private Button buttonModifyEmail = null;

	static int OTP = 0;
	static int ATTACHMENT = 1;

	public BackGroundEmail(String toEmailID, Stage stage, int mode, Button buttonNext, Button buttonModifyEmail)
	{
		this.toEmailID = toEmailID;
		this.stage = stage;
		BackGroundEmail.mode = mode;
		this.buttonNext = buttonNext;
		this.buttonModifyEmail = buttonModifyEmail;
	}

	@Override
	public Void call()
	{
		if( BackGroundEmail.mode == OTP )
		{
			sendOTP();
		}

		else if( BackGroundEmail.mode == ATTACHMENT )
		{
			sendAttachment();
		}

		return null;
	}	
	
	public void sendOTP()
	{		
		try
		{
			updateMessage("Sending OTP to " + toEmailID + "...");	
			this.stage = stage;
			MyEmail.otp = getOTP();
		
			thread = new Thread(MyEmail.otpSender);
			thread.start();
			thread.join();

			if( !OTPSender.status )
			{
				updateMessage("Error in sending OTP; may be due to -\n\n1. No Internet (or)\n2. Invalid Email ID");
				System.out.println("Error in sending OTP; may be due to -\n\n1. No Internet (or)\n2. Invalid Email ID");
				updateProgress(0.0, 1.0);
				buttonModifyEmail.setDisable(false);
			}

			else
			{
				updateMessage("OTP sent!");
				System.out.println("OTP Sent!");
				updateProgress(1.0, 1.0);
			}

			buttonNext.setDisable(false);		
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public void sendAttachment()
	{	
		try
		{	
			updateMessage("Sending public key to " + toEmailID + "...");	
			System.out.println("Sending public key to " + toEmailID + "...");
			thread = new Thread(MyEmail.attachmentSender);
			thread.start();
			thread.join();

			if( !AttachmentSender.status )
			{
				updateMessage("Error in sending the public key; may be due to -\n\n1. No Internet (or)\n2. Invalid Email ID");
				System.out.println("Error in sending the public key; may be due to -\n\n1. No Internet (or)\n2. Invalid Email ID");
				updateProgress(0.0, 1.0);
				buttonModifyEmail.setDisable(false);
			}

			else
			{
				updateMessage("Public Key sent!");
				System.out.println("Public Key sent!\n");
				updateProgress(1.0, 1.0);
			}

			buttonNext.setDisable(false);		
		}

		catch(Exception e)
		{
			e.printStackTrace();
		}
	}	
	
	private String getOTP()
	{
		Random random = null;
		Integer min = 9999;
		Integer max = 99999999;
		
		random = new Random();
		return String.valueOf( random.nextInt( (max - min)+1 ) + min );
		
	}

	@Override
        protected void updateProgress(double workDone, double max)
        {
                super.updateProgress(workDone, max);
        }

	@Override
        protected void updateMessage(String message)
        {
                super.updateMessage(message);
        }
	
}

class PostEmailHandler implements EventHandler<ActionEvent>
{
	private Stage stage = null;
	private String otp = null;

	public PostEmailHandler(Stage stage, String otp)
	{
		this.stage = stage;
		this.otp = otp;
	}

	public PostEmailHandler(Stage stage)
	{
		this.stage = stage;
	}

	@Override
	public void handle(ActionEvent event)
	{
		OTPVerifierGUI otpVerifierGUI = null;
		PublicKeyCollectorGUI publicKeyCollectorGUI = null;

		if(BackGroundEmail.mode == BackGroundEmail.OTP)
		{
			if( !OTPSender.status )
			{
				MyEmail.sendOTP(EmailCumIPCollectorGUI.receiverEmailID, MyEmail.stage);
				return;
			}

			otpVerifierGUI = new OTPVerifierGUI();
	        otpVerifierGUI.start(MyEmail.otp, stage);
		}

		else if(BackGroundEmail.mode == BackGroundEmail.ATTACHMENT)
		{
			if( !AttachmentSender.status )
			{
				MyEmail.sendAttachment( EmailCumIPCollectorGUI.senderEmailID, MyEmail.stage );
				return;
			}

			publicKeyCollectorGUI = new PublicKeyCollectorGUI();
			publicKeyCollectorGUI.start(stage);	
		}
	}
}

class EmailModifier implements EventHandler<ActionEvent>
{
	Stage stage = null;
	EmailCumIPCollectorGUI emailCumIPCollectorGUI = null;

	public EmailModifier(Stage stage)
	{
		this.stage = stage;
	}

	@Override
	public void handle(ActionEvent event)
	{
		emailCumIPCollectorGUI = new EmailCumIPCollectorGUI();
		emailCumIPCollectorGUI.start(stage);
	}
}

























