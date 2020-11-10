import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
public class GamePanel extends JPanel implements Runnable{
    /**
     * static pretoze keby sme mali viac instancii GamePanel triedy tak by vsetky zdielali jednu premennu GAME_WIDTH
     * final je tam len pre istotu aby som to nahodou nepokazil ked budem dalej programovat + uz neplanujem menit ten GAME_WIDTH
     * GAME_WIDTH je vsetko velkym pretoze pouzivam final a bude to jednoduchsie identifikovatelne v kode
     * GAME_HEIGHT par slov :
     * 
     * (int)(GAME_WIDTH * (152/274)) <---- tento command pretoze pouzivam realne rozmery pinpongoveho stolu (sirka = 152cm, dlzka = 274cm)
     * tym padom ked zmenim GAME_WIDTH budem mat stale korespondujucu vysku
     * 
     * okay nvm toto pisem trocha neskor... riadok 17 18 necham len pre pochopenie
     * keby som tam nechal tie cisla co tam boli nemohol by som pouzivat int a nechcem to prepisovat lebo mozno by som to pokazil...
     * jednoducho som dal len 152/274 a setol som tam cele cislo (desatine cisla treba pisat s . nie s , lebo by to bralo ako 2 rozne cisla)
     */ 
    static final int GAME_WIDTH = 1000;
    static final int GAME_HEIGHT = (int)(GAME_WIDTH * (0.5555));
    static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH,GAME_HEIGHT);
    static final int BALL_DIAMETER = 20;
    static final int PADDLE_WIDTH = 25;
    static final int PADDLE_HEIGHT = 100;
    // https://www.w3schools.com/java/java_threads.asp
    Thread gameThread;
    Image image;
    Graphics graphics;
    Random random;
    Paddle paddle1;
    Paddle paddle2;
    Lopticka ball;
    Score score;
    
    
    GamePanel() {
        newPaddles(); //30minutova medzera btw medzi new a Paddles
        newBall(); //novy objekt lopticka ze jo
        score = new Score(GAME_WIDTH, GAME_HEIGHT);
        this.setFocusable(true); // https://stackoverflow.com/questions/14095018/setfocusable-method-or-focusing-components-java <-- je tam dobry priklad na to co to robi
        this.addKeyListener(new AL()); //dole mame tu "podtriedu" alebo vnutornu triedu AL
        this.setPreferredSize(SCREEN_SIZE);
        
        gameThread = new Thread(this); //fyi pouzivame Runnable interface
        gameThread.start(); // zapne hru
    }
    
    public void newBall() {
        random = new Random(); // nech sa lopticka spawnuje hoci kde v okne
        ball = new Lopticka((GAME_WIDTH/2)-(BALL_DIAMETER/2), random.nextInt(GAME_HEIGHT-BALL_DIAMETER), BALL_DIAMETER, BALL_DIAMETER);
    }
    
    public void newPaddles() {
        /**
         * paddle1 je hrac 1, paddle2 je hrac 2
         * paddle1: 
         * 0 pretoze chcem aby sa hrac 1 nachadzal uplne nalavo
         * (GAME_HEIGHT/2)-(PADDLE_HEIGHT/2) aby zacinajuca pozicia bola presne v strede
         * PADDLE_WIDTH, PADDLE_HEIGHT rozmery padu
         * 1 id cislo padu
         * 
         * paddle2: GAME_WIDTH-PADDLE_WIDTH  nech sa hrac 2 nachadza napravo
         * (GAME_HEIGHT/2)-(PADDLE_HEIGHT/2), PADDLE_WIDTH, PADDLE_HEIGHT  -||-
         * 2 id cislo padu
         */
        paddle1 = new Paddle(0,(GAME_HEIGHT/2)-(PADDLE_HEIGHT/2),PADDLE_WIDTH,PADDLE_HEIGHT,1);
        paddle2 = new Paddle(GAME_WIDTH-PADDLE_WIDTH,(GAME_HEIGHT/2)-(PADDLE_HEIGHT/2),PADDLE_WIDTH,PADDLE_HEIGHT,2); 
    }
    
    public void paint(Graphics g) {
        image = createImage(getWidth(), getHeight());
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image, 0, 0, this); // takto vyzera nezmeneny prikaz drawImage: g.drawImage(_img_, _x_, _y_, _observer_)
    }
    
    public void draw(Graphics g) { // cele toto je len na ukazanie vsetkych tych objektov v okne
        paddle1.draw(g);
        paddle2.draw(g);
        ball.draw(g);
        score.draw(g); 
    }
    
    public void move() {
        paddle1.move(); // tento prikaz sposobuje to ze tie pady sa hybu omnoho viac smooth
        paddle2.move(); // ak chces mozes skusit zakomentovat prikazy a uvidis ako to laguje
        ball.move(); // taktiez len optimalizacia pre lopticku
    }
    
    public void checkCollision() {
        //zastavi lopticku na kraji okna
        if(ball.y <= 0) {
            ball.setYDir(-ball.yVelocity);
        }
        
        if(ball.y >= GAME_HEIGHT-BALL_DIAMETER) {
            ball.setYDir(-ball.yVelocity);
        }
        
        // odrazi lopticku od paddlov
        if(ball.intersects(paddle1)) { // cely tento command je pre paddle1 ktory je nalavo obrazovky
            ball.xVelocity = Math.abs(ball.xVelocity); //ball.xVel je negativne a co robim pomocou Math.abs je ze menim negativnu hodnotu na pozitivnu
            ball.xVelocity++; //pri dotyku lopticky s paddom sa rychlost lopticky zvysi tento command staci zakomentovat ak to nechces
            if(ball.yVelocity > 0) {
                ball.yVelocity++; // tuto je aby sa ta rychlost zvysila aj pre y os
            } else {
                ball.yVelocity--; // ak je yVel negativna znamena to ze lopticka ide smerom hore tak tu tiez len zvysujeme rychlost nech to je vsetko rovnake
            }
            ball.setXDir(ball.xVelocity);
            ball.setYDir(ball.yVelocity);
        }
            // uplne to iste co plati pre paddle1 prakticky som to len skopiroval a premenil x-ka na y-ka
        if(ball.intersects(paddle2)) {
            ball.xVelocity = Math.abs(ball.xVelocity);
            ball.xVelocity++;
            if(ball.yVelocity > 0) {
                ball.yVelocity++;
            } else {
                ball.yVelocity--;
            }
            ball.setXDir(-ball.xVelocity);
            ball.setYDir(ball.yVelocity);
        }
        
        //zastavi paddle na kraji okna
        if(paddle1.y <= 0) {
            paddle1.y = 0;
        }
        if(paddle1.y >= (GAME_HEIGHT-PADDLE_HEIGHT)) {
            paddle1.y = GAME_HEIGHT-PADDLE_HEIGHT;
        }
        
        if(paddle2.y <= 0) {
            paddle2.y = 0;
        }
        if(paddle2.y >= (GAME_HEIGHT-PADDLE_HEIGHT)) {
            paddle2.y = GAME_HEIGHT-PADDLE_HEIGHT;
        }
        
        // da hracovi bod a resetne celu hru
        if(ball.x <= 0) {
            score.player2++; // ak zascorujes prida ti to bod
            newPaddles(); //resetne paddly nech sa nachadzaju opat v strede
            newBall(); // resetne lopticku nech sa opat spawne na nahodnom bode
            System.out.println("Player 2: " + score.player2); // vypise pripocitane score hracovi 2
        }
            //presne to iste co s hracom 2
        if(ball.x >= GAME_WIDTH-BALL_DIAMETER) {
            score.player1++;
            newPaddles();
            newBall();
            System.out.println("Player 1: " + score.player1);
        }
        
        
    }
    
    public void run() {
        /**
         * game loop
         * bassicaly nech to bezi na 60 FPS
         * fun fact tento game loop pouziva aj minecraft len s inymi hodnotami
         */
        long lastTime = System.nanoTime(); //nanoTime = nanosekundy
        double amountOfTicks = 60.0; //FPS i guess 
        double ns = 1000000000 / amountOfTicks; // ns = nanosekundy  1sec = 1*10^9 sekund
        double delta = 0; // delta - the maximum delta between expected and actual for which both numbers are still considered equal. aspon podla stackoverflow
        while(true) { // tu by mohlo byt running ale nech to je jednoduchsie je tam len true https://www.w3schools.com/java/java_while_loop.asp
            long now = System.nanoTime(); // now je basically casova metoda teda aspon z toho co som pochopil https://www.geeksforgeeks.org/instant-now-method-in-java-with-examples/
            delta += (now -lastTime)/ns;
            lastTime = now;
                if(delta >=1){ // tuto je ukazka delta pomocou >=1
                    move();
                    checkCollision(); // https://stackoverflow.com/questions/335600/collision-detection-between-two-images-in-java https://www.youtube.com/watch?v=JIXhCvXgjsQ
                    repaint(); // ked zavolame update metodu a paint metodu  update vycisti pouzivane okno urobi update a potom zavola paint metodu... cele zhrnute v repaint
                               //https://www.educba.com/repaint-in-java/
                    delta--;
                }
        }
    }
      /** AL = Action Listener <-- to asi chapes
       * metody keyPressed a keyReleased sa nachadzaju v triede Pad dufam ze nemusim pisat co robia
       */
      public class AL extends KeyAdapter{
        public void keyPressed(KeyEvent e) {
            paddle1.keyPressed(e);
            paddle2.keyPressed(e);
        }
        
        public void keyReleased(KeyEvent e) {
            paddle1.keyReleased(e);
            paddle2.keyReleased(e);
        }
    }
}
