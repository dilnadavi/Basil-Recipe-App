package ui;

import java.awt.Color;
import java.io.FileNotFoundException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Main extends JFrame {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setTitle("Basil: The Recipe App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setResizable(false);
        frame.setSize(1000,500);
        frame.setVisible(true);

        frame.getContentPane().setBackground(new Color(255,255,255));
        ImageIcon image = new ImageIcon("icon.png");
        frame.setIconImage(image.getImage());

        try {
            new RecipeApp();
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
    }
}
