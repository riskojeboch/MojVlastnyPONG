import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class GameFrame extends JFrame{ // https://javatutorial.net/swing-jframe-basics-create-jframe
    //okraje okna kvazi
    GamePanel panel;
    
    GameFrame() {
        panel = new GamePanel();
        this.add(panel);
        this.setTitle("Pong Pong Madafakaa");
        this.setResizable(false); //nech sa to neda zmensit ani zvacsit
        this.setBackground(Color.black); //farba pozadia
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Hore X v okne ked zmacknes nech sa zatvori okno
        /**
         * https://stackoverflow.com/questions/22982295/what-does-pack-do <---- tu je len hruba poucka
         * bassicaly ak sa ti to nechce citat (TL;DR): Metoda pack dimenzuje frame tak, aby vsetok jeho obsah bol NA alebo NAD ich preferovanymi velkostami.
         * tuto je to dost dobre vysvetlene https://www.roseindia.net/java/example/java/swing/packvssetsize.shtml
        */
        this.pack();
        this.setVisible(true); //nech sa to zobrazi 
        this.setLocationRelativeTo(null); // tento prikaz je tu na to aby sa mi okno otvorilo v strede obrazovky
    }
}
