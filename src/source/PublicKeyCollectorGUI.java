import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import java.security.PublicKey;

class PublicKeyCollectorGUI
{
	private static Stage stage = null;
	private static Label labelHeading = null;
	private static Label labelContent = null;
	private static Label labelProspectus = null;
	private static Button buttonUpload = null;
	private static GridPane gridPane = null;
	private static Scene scene = null;

	private static MyFileChooser keyChooser;
	static PublicKey dstPubKey = null;	

	static void start(Stage stage)
	{
		PublicKeyCollectorGUI.stage = stage;
		keyChooser = new MyFileChooser(stage, "Choose destination's public key");

		labelHeading = new Label();
		labelHeading.setText("Step 3");
		labelHeading.setFont( new Font(Constants.HEAD_SIZE) );
		labelHeading.setTextFill( Color.web(Constants.HEAD_COLOR) );
		labelContent = new Label();
		labelContent.setText("Destination's public key");
		labelContent.setTextFill( Color.web(Constants.HEAD_COLOR) );
		labelProspectus = new Label();
		labelProspectus.setText("You must have received a public key from " + EmailCumIPCollectorGUI.receiverEmailID);
		buttonUpload = new Button();
		buttonUpload.setText("Choose public key");
		buttonUpload.setOnAction(keyChooser);

		gridPane = new GridPane();
		gridPane.add(labelHeading, 0, 0);
		gridPane.add(labelContent, 0, 1);
		gridPane.add(labelProspectus, 0, 2);
		gridPane.add(buttonUpload, 0, 3);
		gridPane.setAlignment(Pos.CENTER);
		GridPane.setMargin(labelContent, new Insets(20, 0, 0, 0));
		GridPane.setMargin(labelProspectus, new Insets(60, 0, 0, 0));
		GridPane.setMargin(buttonUpload, new Insets(20, 0, 0, 0));
		
		scene = new Scene(gridPane, Constants.WIND_COLS, Constants.WIND_ROWS);
        stage.setScene(scene);
        stage.show();
	}
}











