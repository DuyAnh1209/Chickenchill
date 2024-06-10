package project.monsterinc;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import project.monsterinc.entities.Monster;
import project.monsterinc.entities.Human;
import project.monsterinc.entities.Bullet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class GameController extends SceneController{
    Stage stage = MStage.getInstance().loadStage();
    private final Random RAND = new Random();
    public static int score, playerScore, monsterScore;
    public static GraphicsContext gc;
    int liveTicks;
    int maxMonsters = 10, maxShots = maxMonsters * 2;
    int playerSize = 60;
    boolean gamePause;
    double mouseX;
    Human player;
    List<Bullet> bulletContainer;
    List<Monster> MonsterContainer;
    Image playerImg = new Image(GameController.class.getResource("img/other/Human.png").toString());
    Image[] MonsterImg = {
            new Image(GameController.class.getResource("img/Monster/purple.png").toString()),
            new Image(GameController.class.getResource("img/Monster/green.png").toString()),
            new Image(GameController.class.getResource("img/Monster/red.png").toString()),
            new Image(GameController.class.getResource("img/Monster/blue.png").toString()),
    };
    Image backgroundImg = new Image(GameController.class.getResource("img/other/background1.png").toString());

    // Waiting to add SFX

    //--Game Start--
    public void play() {
        Canvas canvas = new Canvas(MScene.width, MScene.height);
        gc = canvas.getGraphicsContext2D();
        Timeline timeline = new Timeline();
        KeyFrame frame = new KeyFrame(Duration.millis(50), e -> {
            try {
                if (run(gc)) {
                    timeline.stop();
                    OverController oc = new OverController();
                    oc.showScore();
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        timeline.getKeyFrames().add(frame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        Scene ingame = new Scene(new StackPane(canvas));

        //--Ship shoot via key pressed--
        ingame.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()) {
                    case A, S:
                        if (bulletContainer.size() < maxShots)
                            //add bullet if current shots array size does not exceed maxShots
                            bulletContainer.add(player.shoot());
                        break;
                    case ESCAPE:
                        if (!gamePause) {
                            gamePause = true;
                            timeline.pause();
                            gc.setFont(Font.loadFont(getClass().getResource("font/upheavtt.ttf").toExternalForm(), 50));
                            gc.setTextAlign(TextAlignment.CENTER);
                            gc.setFill(Color.WHITE);
                            gc.fillText("PAUSE GAME", MScene.width / 2, MScene.height / 2);
                        } else {
                            gamePause = false;
                            gc.setFill(Color.TRANSPARENT);
                            timeline.play();
                        }
                }
            }
        });

        //--Check if stage is focus or not--
        stage.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (isNowFocused) {
                gc.setFill(Color.TRANSPARENT);
                timeline.play();
            } else {
                timeline.pause();
                gc.setFont(Font.loadFont(getClass().getResource("font/upheavtt.ttf").toExternalForm(), 50));
                gc.setTextAlign(TextAlignment.CENTER);
                gc.setFill(Color.WHITE);
                gc.fillText("PAUSE GAME", MScene.width / 2, MScene.height / 2);
            }
        });

        //--Ship movement via mouse--
        ingame.setCursor(Cursor.MOVE);
        ingame.setOnMouseMoved(e -> mouseX = e.getX());
        ingame.setOnMouseClicked(e -> {
            if (bulletContainer.size() < maxShots)
                bulletContainer.add(player.shoot());
        });

        setup();
        stage.setScene(ingame);
        stage.show();
    }

    //--Game setup--
    private Monster newMonster() { //function to create a new Monster object
        return new Monster(50 + RAND.nextInt(MScene.width - 100), 0, playerSize,
                MonsterImg[RAND.nextInt(MonsterImg.length)]);
    }

    public void setup() {
        bulletContainer = new ArrayList<>();
        MonsterContainer = new ArrayList<>();
        player = new Human(MScene.width / 2, MScene.height - playerSize - 10, playerSize, playerImg);
        liveTicks = 6;
        playerScore = 0;
        monsterScore = 0;
        IntStream.range(0, maxMonsters).mapToObj(i -> this.newMonster()).forEach(MonsterContainer::add);
        //The IntStream.range() method is used to generate a sequence of integers from 0 to maxChickens - 1.
        //For each integer in the sequence, a new chicken object is created using the newChicken() method.
        //Then each get added to the chickens ArrayList using the forEach() method.
    }

    //--Run Graphics
    public boolean run(GraphicsContext gc) throws IOException {
        // setup background
        gc.drawImage(backgroundImg, 0, 0, MScene.width, MScene.height);
        gc.setTextAlign(TextAlignment.LEFT);
        gc.setFont(Font.loadFont(getClass().getResource("font/upheavtt.ttf").toExternalForm(), 20));
        gc.setFill(Color.WHITE);
        gc.fillText("Score: " + score, 30, 20);
        gc.fillText("Lives: " + liveTicks / 2, 30, 40);

        // draw player
        player.update();
        player.draw();
        player.setX((int) mouseX);

        MonsterContainer.stream().peek(Monster::update).peek(Monster::draw).forEach(e -> {
            if (player.collide(e) && !player.exploding) {
                e.explode();
                liveTicks--;
            }
            if (liveTicks == 1) {
                //sfx play here
                player.explode();
            }
        });

        for (int i = bulletContainer.size() - 1; i >= 0; i--) {
            Bullet shot = bulletContainer.get(i);
            if (shot.getY() < 0 || shot.getStatus()) {
                bulletContainer.remove(i);
                continue;
            }
            shot.update();
            shot.draw();
            for (Monster monster : MonsterContainer) {
                if (shot.collide(monster) && !monster.exploding) {
                    playerScore += 2;
                    monster.explode();
                    shot.setStatus(true);
                }
            }
        }

        for (Monster monster : MonsterContainer) {
            if (monster.getY() == MScene.height) {
                monsterScore += 4;
            }
        }

        for (int i = MonsterContainer.size() - 1; i >= 0; i--) {
            if (MonsterContainer.get(i).destroyed) {
                MonsterContainer.set(i, newMonster());
            }
        }

        // assign total score
        score = playerScore - monsterScore;

        return player.destroyed || score < 0;
    }
}
