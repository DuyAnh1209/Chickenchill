package project.monsterinc.entities;
// import necessary libraries
import javafx.scene.image.Image;
import project.monsterinc.GameController;
import project.monsterinc.MScene;
// declare variables and constructor 
public class Monster extends Entity{
    private final int speed = (GameController.playerScore / 10) + 4;
    public Monster(int x, int y, int size, Image img) {
        super(x, y, size, img);
    }
    // update method for the chicken
    @Override
    public void update() {
        super.update();
        if (!exploding && !destroyed) y += speed;
        if (y > MScene.height) destroyed = true;
    }
}