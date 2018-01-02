import javafx.stage.Stage;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.GridPane;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.Scene;

public class PostAttachmentHandler implements EventHandler<ActionEvent>
{
	private Stage stage = null;
	private ProgressIndicator progressIndicator = null;
	private Label labelProgress = null;
	private Button buttonNext = null;
	private GridPane gridPane = null;
	private Scene scene = null;

	private DiffieHellman dh = null;
	private String dhPubKeySrc = null;

	public PostAttachmentHandler(Stage stage)
	{
		this.stage = stage;
	}
	
	@Override
	public void handle(ActionEvent event)
	{
		initialize();

		dh = new DiffieHellman();
		
	}

	private void initialize()
	{
		progressIndicator = new ProgressIndicator();
		progressIndicator.setProgress(-1.0);
		labelProgress = new Label();
		buttonNext = new Button();
		buttonNext.setText("Next");
		buttonNext.setDisable(true);
		buttonNext.setOnAction( new PostDHHandler(stage) );

		gridPane = new GridPane();
		gridPane.add(progressIndicator, 0, 0);
		gridPane.add(labelProgress, 0, 1);
		gridPane.add(buttonNext, 0, 2);
		gridPane.setAlignment(Pos.CENTER);
		GridPane.setMargin(progressIndicator, new Insets(30, 0, 0, 0));
		GridPane.setMargin(labelProgress, new Insets(20, 0, 0, 0));
		GridPane.setMargin(buttonNext, new Insets(60, 0, 0, 0));

		scene = new Scene(gridPane, Constants.WIND_COLS, Constants.WIND_ROWS);
		stage.setScene(scene);
		stage.show();
	}
}

class PostDHHandler implements EventHandler<ActionEvent>
{
	private Stage stage = null;

	public PostDHHandler(Stage stage)	
	{
		this.stage = stage;
	}

	@Override
	public void handle(ActionEvent event)
	{
		System.out.println("Yet to implement it!");
	}
}



















