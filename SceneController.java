package project.monsterinc;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
public class SceneController {
	@FXML
	private ImageView playImgView;
	@FXML
	private ImageView tutorImgView;
	@FXML
	private ImageView creditImgView;
	@FXML
	private ImageView exitImgView;
	@FXML
	private ImageView backImgView;
	@FXML
	private ImageView yesImgView;
	@FXML
	private ImageView noImgView;
	@FXML
	private ImageView tutorContent;
	Stage stage = MStage.getInstance().loadStage();

	//--Button Animation--
	public void changePlayImg(MouseEvent event) {
		if (Objects.equals(getClass().getResource("img/button/start1.png").toString(), playImgView.getImage().getUrl().toString())) {
			Image img = new Image(getClass().getResource("img/button/start2.png").toString());
			playImgView.setImage(img);
		}
		else {
			Image img = new Image(getClass().getResource("img/button/start1.png").toString());
			playImgView.setImage(img);
		}
	}
	public void changeTutorImg(MouseEvent event) {
		if (Objects.equals(getClass().getResource("img/button/tutor1.png").toString(), tutorImgView.getImage().getUrl().toString())) {
			Image img = new Image(getClass().getResource("img/button/tutor2.png").toString());
			tutorImgView.setImage(img);
		}
		else{
			Image img = new Image(getClass().getResource("img/button/tutor1.png").toString());
			tutorImgView.setImage(img);
		}
	}
	public void changeCreditImg(MouseEvent event) {
		if (Objects.equals(getClass().getResource("img/button/credit1.png").toString(), creditImgView.getImage().getUrl().toString())) {
			Image img = new Image(getClass().getResource("img/button/credit2.png").toString());
			creditImgView.setImage(img);
		}
		else{
			Image img = new Image(getClass().getResource("img/button/credit1.png").toString());
			creditImgView.setImage(img);
		}
	}
	public void changeExitImg(MouseEvent event) {
		if (Objects.equals(getClass().getResource("img/button/exit1.png").toString(), exitImgView.getImage().getUrl().toString())) {
			Image img = new Image(getClass().getResource("img/button/exit2.png").toString());
			exitImgView.setImage(img);
		}
		else{
			Image img = new Image(getClass().getResource("img/button/exit1.png").toString());
			exitImgView.setImage(img);
		}
	}
	public void changeBackImg(MouseEvent event) {
		if (Objects.equals(getClass().getResource("img/content/back1.png").toString(), backImgView.getImage().getUrl().toString())) {
			Image img = new Image(getClass().getResource("img/content/back2.png").toString());
			backImgView.setImage(img);
		}
		else{
			Image img = new Image(getClass().getResource("img/content/back1.png").toString());
			backImgView.setImage(img);
		}
	}
	public void changeYesImg(MouseEvent event) {
		if (Objects.equals(getClass().getResource("img/content/yes1.png").toString(), yesImgView.getImage().getUrl().toString())) {
			Image img = new Image(getClass().getResource("img/content/yes2.png").toString());
			yesImgView.setImage(img);
		}
		else {
			Image img = new Image(getClass().getResource("img/content/yes1.png").toString());
			yesImgView.setImage(img);
		}
	}
	public void changeNoImg(MouseEvent event) {
		if (Objects.equals(getClass().getResource("img/content/no1.png").toString(), noImgView.getImage().getUrl().toString())) {
			Image img = new Image(getClass().getResource("img/content/no2.png").toString());
			noImgView.setImage(img);
		}
		else{
			Image img = new Image(getClass().getResource("img/content/no1.png").toString());
			noImgView.setImage(img);
		}
	}

	// go to next page on tutorial content
	public void nextPage() {
		String[] imgPath = {
				getClass().getResource("img/content/tutorcontent1.png").toString(),
				getClass().getResource("img/content/tutorcontent2.png").toString(),
				getClass().getResource("img/content/tutorcontent3.png").toString()
		};
		String currentPath = tutorContent.getImage().getUrl().toString();
		try{
			for (int i = 0; i < imgPath.length; i++) {
				if (Objects.equals(imgPath[i], currentPath)) {
					tutorContent.setImage(new Image(imgPath[i + 1]));
				}
			}
		} catch (ArrayIndexOutOfBoundsException error) {
			tutorContent.setImage(new Image(imgPath[0]));
		}
	}

	//--Switch Scene--
	public void switchtoMenu(MouseEvent event) throws IOException {
		MScene menu = new MScene("fxml/menu.fxml");
		stage.setScene(menu.loadScene());
		stage.show();
	}

	public void switchtoTutor(MouseEvent event) throws IOException {
		MScene tutor = new MScene("fxml/tutorial.fxml");
		stage.setScene(tutor.loadScene());
		stage.show();
	}
	public void switchtoCredit(MouseEvent event) throws IOException {
		MScene tutor = new MScene("fxml/credit.fxml");
		stage.setScene(tutor.loadScene());
		stage.show();
	}
	public void exit() {
		stage.close();
	}
}
