package ui;

import java.awt.Color;
import java.io.FileNotFoundException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Main extends JFrame {
    public static void main(String[] args) {
        new Graphics();

        try {
            new RecipeApp();
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
    }
}
