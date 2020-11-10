import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
public class Paddle extends Rectangle {
    // player id pre prveho aj druheho hraca
    int id;
    int yVelocity; // rychlost pohybu hore dole
    int speed = 10;
    
    
    Paddle(int x, int y, int PADDLE_WIDTH, int PADDLE_HEIGHT, int id) {
        super(x,y,PADDLE_WIDTH,PADDLE_HEIGHT); // https://www.w3schools.com/java/ref_keyword_super.asp
        this.id = id;
    }
    
    public void keyPressed(KeyEvent e) {
        switch(id){
            case 1:
                if(e.getKeyCode()==KeyEvent.VK_W) { // W 
                    setYDir(-speed);
                    move();
                }
                if(e.getKeyCode()==KeyEvent.VK_S) { // S
                    setYDir(speed);
                    move();
                }
                break;
            case 2:
                if(e.getKeyCode()==KeyEvent.VK_UP) { // sipka hore
                    setYDir(-speed);
                    move();
                }
                if(e.getKeyCode()==KeyEvent.VK_DOWN) { // sipka dole
                    setYDir(speed);
                    move();
                }
                break;
        }
    }
    
    public void keyReleased(KeyEvent e) {
        switch(id){
            case 1:
                if(e.getKeyCode()==KeyEvent.VK_W) {
                    setYDir(0); // 0 nech sa uz nehybe ked pustime tlacitko same s kazdym dole pod tymto
                    move();
                }
                if(e.getKeyCode()==KeyEvent.VK_S) {
                    setYDir(0);
                    move();
                }
                break;
            case 2:
                if(e.getKeyCode()==KeyEvent.VK_UP) {
                    setYDir(0);
                    move();
                }
                if(e.getKeyCode()==KeyEvent.VK_DOWN) {
                    setYDir(0);
                    move();
                }
                break;
        }
    }
    // Tuto v Pade staci len y os pretoze sa hybu len hore a dole
    public void setYDir(int yDir) {
        yVelocity = yDir;
    }
    
    public void move() {
        y = y + yVelocity;
    }
    
    public void draw(Graphics g) {
        if(id ==1)
            g.setColor(Color.blue);
        else
            g.setColor(Color.red);
        g.fillRect(x, y, width, height);
    }
}
