package project.monsterinc.entities;
// import necessary libraries
import javafx.scene.image.Image;
import project.monsterinc.GameController;
import static project.monsterinc.GameController.gc;
// constructor 
public class Human extends Entity {
    public Human(int x, int y, int size, Image img) {
        super(x, y, size, img);
    }
    // shoot method for the ship
    public Bullet shoot() {
        return new Bullet(x + size / 2 - Bullet.size / 2, y - Bullet.size);
    }
}
