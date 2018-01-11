import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;

public class MessageCumImageCollectorGUI
{
	private static Stage stage = null;
	private static Label labelHeading = null;
	private static Label labelContent = null;
	private static Label labelMessage = null;
	private static TextField textFieldMessage = null;
	private static Button buttonChooseImage = null;
	private static Button buttonEmbed = null;
	private static GridPane gridPane = null;
	private static Scene scene = null;

	static void start(Stage stage)
	{
		MessageCumImageCollectorGUI.stage = stage;
	
		labelHeading = new Label();
		labelHeading.setText("Step 4");
		labelHeading.setFont( new Font(Constants.HEAD_SIZE) );
        labelHeading.setTextFill( Color.web(Constants.HEAD_COLOR) );
		labelContent = new Label();
		labelContent.setText("Embed a message in an image");
		labelContent.setTextFill( Color.web(Constants.HEAD_COLOR) );
		labelMessage = new Label();
		labelMessage.setText("Message : ");
		textFieldMessage = new TextField();
		buttonChooseImage = new Button();
		buttonChooseImage.setText("Choose an image");
		buttonChooseImage.setOnAction( new MyFileChooser(stage, "Choose an image") );
		MyFileChooser.isImage = true;
		buttonEmbed = new Button();
		buttonEmbed.setText("Embed");
		buttonEmbed.setOnAction( new MessageEmbedder(stage, textFieldMessage) );

		gridPane = new GridPane();
		gridPane.add(labelHeading, 0, 0);
		gridPane.add(labelContent, 0, 1);
		gridPane.add(labelMessage, 0, 2);
		gridPane.add(textFieldMessage, 1, 2);
		gridPane.add(buttonChooseImage, 0, 3);
		gridPane.add(buttonEmbed, 0, 4);
		gridPane.setAlignment(Pos.CENTER);
		GridPane.setMargin(labelContent, new Insets(20, 0, 0, 0));
		GridPane.setMargin(labelMessage, new Insets(60, 0, 0, 0));
		GridPane.setMargin(textFieldMessage, new Insets(60, 0, 0, 0));
		GridPane.setMargin(buttonChooseImage, new Insets(20, 0, 0, 0));
		GridPane.setMargin(buttonEmbed, new Insets(40, 0, 0, 0));
		
		scene = new Scene(gridPane, Constants.WIND_COLS, Constants.WIND_ROWS);
        stage.setScene(scene);
        stage.show();
	}

	static void setMessage(String inpMessage)
	{
		textFieldMessage.setText(inpMessage);
	}

	static String getMessage()
	{
		return textFieldMessage.getText();
	}
}











