import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class EmailCumIPCollectorGUI 
{
	private Label labelHeading = null;
	private Label labelContent = null;
	private Label labelSenderEmailID = null;
	private Label labelReceiverEmailID = null;
	private Label labelReceiverIP = null;
	private TextField textFieldReceiverEmailID = null;
	private TextField textFieldSenderEmailID = null;
	private TextField textFieldReceiverIP = null;
	private Button button = null;
	
	public static String senderEmailID = null;
	public static String receiverEmailID = null;
	public static String receiverIP = null;	

	private GridPane gridPane = null;
	private Scene scene = null;
	
	public void start(Stage stage)
	{
		labelHeading = new Label();
		labelHeading.setText("Step 1");
		labelHeading.setTextFill(Color.web(Constants.HEAD_COLOR));
		labelHeading.setFont( new Font(Constants.HEAD_SIZE) );
		labelContent = new Label();
		labelContent.setText("Provide Email IDs");
		labelContent.setTextFill(Color.web(Constants.HEAD_COLOR));
		labelSenderEmailID = new Label();
		labelSenderEmailID.setText("Your Email ID : ");
		textFieldSenderEmailID = new TextField();
		labelReceiverEmailID = new Label();
		labelReceiverEmailID.setText("Receiver's Email ID : ");
		textFieldReceiverEmailID = new TextField();
		labelReceiverIP = new Label();
		labelReceiverIP.setText("Receiver's IP address (IPv4) : ");
		textFieldReceiverIP = new TextField();
		button = new Button();
		button.setText("Submit");
		button.setOnAction(new PostEmailInputHandler(textFieldReceiverEmailID, textFieldSenderEmailID, textFieldReceiverIP, stage) );
		
		gridPane = new GridPane();
		gridPane.add(labelHeading, 0, 0);
		gridPane.add(labelContent, 0, 1);
		gridPane.add(labelSenderEmailID, 0, 2);
		gridPane.add(textFieldSenderEmailID, 1, 2);
		gridPane.add(labelReceiverEmailID, 0, 3);
		gridPane.add(textFieldReceiverEmailID, 1, 3);
		gridPane.add(labelReceiverIP, 0, 4);
		gridPane.add(textFieldReceiverIP, 1, 4);
		gridPane.add(button, 0, 5);
		gridPane.setAlignment(Pos.CENTER);
		GridPane.setMargin(labelContent, new Insets(20, 0, 0, 0));
		GridPane.setMargin(labelSenderEmailID, new Insets(60, 0, 0, 0));
		GridPane.setMargin(textFieldSenderEmailID, new Insets(60, 0, 0, 0));
		GridPane.setMargin(labelReceiverEmailID, new Insets(20, 0, 0, 0));
		GridPane.setMargin(textFieldReceiverEmailID, new Insets(20, 0, 0, 0));
		GridPane.setMargin(labelReceiverIP, new Insets(20, 0, 0, 0));
		GridPane.setMargin(textFieldReceiverIP, new Insets(20, 0, 0, 0));
		GridPane.setMargin(button, new Insets(60, 0, 0, 0));
		
		scene = new Scene(gridPane, Constants.WIND_COLS, Constants.WIND_ROWS);
		stage.setScene(scene);
		stage.show();
	}
}

class PostEmailInputHandler implements EventHandler<ActionEvent>
{
	private TextField textFieldSenderEmailID = null;
	private TextField textFieldReceiverEmailID = null;
	private TextField textFieldReceiverIP = null;
	private Stage stage = null;
	private MyEmail myEmail = null;
	
	public PostEmailInputHandler(TextField textFieldReceiverEmailID, TextField textFieldSenderEmailID, TextField textFieldReceiverIP, Stage stage) 
	{
		this.textFieldSenderEmailID = textFieldSenderEmailID;
		this.textFieldReceiverEmailID = textFieldReceiverEmailID;
		this.textFieldReceiverIP = textFieldReceiverIP;
		this.stage = stage;
	}
	
	@Override
	public void handle(ActionEvent event) 
	{
		EmailCumIPCollectorGUI.senderEmailID = textFieldSenderEmailID.getText();
		EmailCumIPCollectorGUI.receiverEmailID = textFieldReceiverEmailID.getText();
		EmailCumIPCollectorGUI.receiverIP = textFieldReceiverIP.getText();
	
		System.out.println("Sender : " + EmailCumIPCollectorGUI.senderEmailID);
		System.out.println("Receiver : " + EmailCumIPCollectorGUI.receiverEmailID);
		System.out.println("Receiver's IP address : " + EmailCumIPCollectorGUI.receiverIP + "\n");
		
		myEmail = new MyEmail();
		System.out.println("Sending OTP to " + EmailCumIPCollectorGUI.senderEmailID + "...");
		myEmail.sendOTP(EmailCumIPCollectorGUI.senderEmailID, stage);
	}
}












