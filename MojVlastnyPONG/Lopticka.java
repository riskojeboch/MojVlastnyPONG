import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Lopticka extends Rectangle{
    Random random;
    int xVelocity;
    int yVelocity;
    int pociatocnaSpeed = 3;
    
    Lopticka(int x,int y, int width, int height) {
        super(x, y, width, height); // super vysvetlene v Paddle
        random = new Random(); // random 
        int randomXDir = random.nextInt(2); // nech sa hybe do X nahodne https://www.geeksforgeeks.org/java-util-random-nextint-java/
        if(randomXDir == 0) {
            randomXDir--;
        }
        setXDir(randomXDir*pociatocnaSpeed); // nech sa hybe pociatocnov rychlostou
        
        int randomYDir = random.nextInt(2); // nech sa hybe do Y nahodne
        if(randomYDir == 0) {
            randomYDir--;
        }
        setYDir(randomYDir*pociatocnaSpeed);
    }
    
    public void setXDir(int randomXDir) { // random strana na osi X do ktorej sa bude lopticka hybat
        xVelocity = randomXDir;
    }
    
    public void setYDir(int randomYDir) { // random strana na osi Y do ktorej sa bude lopticka hybat
        yVelocity = randomYDir;
    }
    
    public void move() {
        x += xVelocity;
        y += yVelocity;
    }
    
    public void draw(Graphics g) {
        g.setColor(Color.white);
        g.fillOval(x, y, width, height); // vyska a sirka su rovnake obe su vlastne BALL_DIAMETER
    }
}
