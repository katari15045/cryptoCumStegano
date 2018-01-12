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

class EmailCumIPCollectorGUI 
{
	private Label labelHeading = null;
	private Label labelContent = null;
	private Label labelSenderEmailID = null;
	private Label labelReceiverEmailID = null;
	private Label labelSenderIP = null;
	private TextField textFieldReceiverEmailID = null;
	private TextField textFieldSenderEmailID = null;
	private TextField textFieldSenderIP = null;
	private Button button = null;
	
	static String senderEmailID = null;
	static String receiverEmailID = null;
	static String senderIP = null;	

	private GridPane gridPane = null;
	private Scene scene = null;
	
	void start(Stage stage)
	{
		labelHeading = new Label();
		labelHeading.setText("Step 1");
		labelHeading.setTextFill(Color.web(Constants.HEAD_COLOR));
		labelHeading.setFont( new Font(Constants.HEAD_SIZE) );
		labelContent = new Label();
		labelContent.setText("Provide Email IDs");
		labelContent.setTextFill(Color.web(Constants.HEAD_COLOR));
		labelReceiverEmailID = new Label();
		labelReceiverEmailID.setText("Your Email ID : ");
		textFieldReceiverEmailID = new TextField();
		labelSenderEmailID = new Label();
		labelSenderEmailID.setText("Sender's Email ID : ");
		textFieldSenderEmailID = new TextField();
		labelSenderIP = new Label();
		labelSenderIP.setText("Sender's IP address (IPv4) : ");
		textFieldSenderIP = new TextField();
		button = new Button();
		button.setText("Submit");
		button.setOnAction(new PostEmailInputHandler(textFieldReceiverEmailID, textFieldSenderEmailID, textFieldSenderIP, stage) );
		
		gridPane = new GridPane();
		gridPane.add(labelHeading, 0, 0);
		gridPane.add(labelContent, 0, 1);
		gridPane.add(labelReceiverEmailID, 0, 2);
		gridPane.add(textFieldReceiverEmailID, 1, 2);
		gridPane.add(labelSenderEmailID, 0, 3);
		gridPane.add(textFieldSenderEmailID, 1, 3);
		gridPane.add(labelSenderIP, 0, 4);
		gridPane.add(textFieldSenderIP, 1, 4);
		gridPane.add(button, 0, 5);
		gridPane.setAlignment(Pos.CENTER);
		GridPane.setMargin(labelContent, new Insets(20, 0, 0, 0));
		GridPane.setMargin(labelReceiverEmailID, new Insets(60, 0, 0, 0));
		GridPane.setMargin(textFieldReceiverEmailID, new Insets(60, 0, 0, 0));
		GridPane.setMargin(labelSenderEmailID, new Insets(20, 0, 0, 0));
		GridPane.setMargin(textFieldSenderEmailID, new Insets(20, 0, 0, 0));
		GridPane.setMargin(labelSenderIP, new Insets(20, 0, 0, 0));
		GridPane.setMargin(textFieldSenderIP, new Insets(20, 0, 0, 0));
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
	private TextField textFieldSenderIP = null;
	private Stage stage = null;
	
	PostEmailInputHandler(TextField textFieldReceiverEmailID, TextField textFieldSenderEmailID, TextField textFieldSenderIP, Stage stage) 
	{
		this.textFieldSenderEmailID = textFieldSenderEmailID;
		this.textFieldReceiverEmailID = textFieldReceiverEmailID;
		this.textFieldSenderIP = textFieldSenderIP;
		this.stage = stage;
	}
	
	@Override
	public void handle(ActionEvent event) 
	{
		EmailCumIPCollectorGUI.senderEmailID = textFieldSenderEmailID.getText();
		EmailCumIPCollectorGUI.receiverEmailID = textFieldReceiverEmailID.getText();
		EmailCumIPCollectorGUI.senderIP = textFieldSenderIP.getText();
		
		System.out.println("Sender : " + EmailCumIPCollectorGUI.senderEmailID);
		System.out.println("Receiver : " + EmailCumIPCollectorGUI.receiverEmailID);
		System.out.println("Sender's IP address : " + EmailCumIPCollectorGUI.senderIP + "\n");
		
		System.out.println("Sending OTP to " + EmailCumIPCollectorGUI.receiverEmailID + "...");
		MyEmail.sendOTP(EmailCumIPCollectorGUI.receiverEmailID, stage);
	}
}












