// import needed packages
package project.monsterinc.entities;

import javafx.scene.image.Image;
import project.monsterinc.GameController;
import static project.monsterinc.GameController.*;
// list all the variables and constants
public abstract class Entity {
    protected Image img;
    protected int x;
    protected int y;
    protected int size;
    public boolean exploding, destroyed;
    protected int explosionStep = 0;
    protected final Image explosionImg = new Image(GameController.class.getResource("img/other/explosion1.png").toString());
// constructor
protected Entity(int x, int y, int size, Image img) {
        this.img = img;
        this.size = size;
        this.x = x;
        this.y = y;
    }
// getters and setters
    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }
    public void update() {
        if (exploding) destroyed=true;
    }

    public void draw() {
        if (exploding) { //checking if exploding is true
            int explosionWidth = 128;
            int explosionHeight = 128;
            int explosionRow = 3;
            int explosionCol = 3;
            gc.drawImage(explosionImg, explosionStep % explosionCol * explosionWidth,
                    ((double) explosionStep / explosionRow) * explosionHeight + 1,
                    explosionWidth, explosionHeight, x, y, size, size);
        } else {
            gc.drawImage(img, x, y, size, size);
        }
    }

    public void explode() {
        exploding = true;
        explosionStep = -1;
    }
    // check collision
    public boolean collide(Monster enemy) {
        int d = distance(x + size / 2, y + size / 2,
                enemy.x + enemy.size / 2, enemy.y + enemy.size / 2);
        return d < enemy.size / 2 + size / 2;
    }
    // check distance
    private int distance(int x1, int y1, int x2, int y2) {
        return (int) Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
    }
}