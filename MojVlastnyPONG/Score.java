import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Score extends Rectangle{
      /**
       * Vyska, sirka pochopitelne
       * int player1 drzi skore hraca 1
       * int player2 drzi skore hraca 2
       */
    static int GAME_WIDTH;
    static int GAME_HEIGHT;
    int player1;
    int player2;
    
    Score(int GAME_WIDTH, int GAME_HEIGHT) {
        Score.GAME_WIDTH = GAME_WIDTH; // konstanta pre GAME_WIDTH
        Score.GAME_HEIGHT = GAME_HEIGHT; //konstanta pre GAME_HEIGHT
    }
    
    public void draw(Graphics g) {
        g.setColor(Color.white); // farba pisma score
        g.setFont(new Font("Consolas", Font.PLAIN, 60)); // font pre score
        
        g.drawLine(GAME_WIDTH/2 , 0, GAME_WIDTH/2, GAME_HEIGHT); // stredova rozdelovacia ciara
        g.drawString(String.valueOf(player1/10)+String.valueOf(player1%10), (GAME_WIDTH/2)-85, 50); //nech je score dvojmiestne a nech sa pre player1 zobrazuje na lavej strane
        g.drawString(String.valueOf(player2/10)+String.valueOf(player2%10), (GAME_WIDTH/2)+20, 50); //nech je score dvojmiestne a nech sa zobrazzuje napravo obrazovky
    }
}
