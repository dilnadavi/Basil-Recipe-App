package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import model.Recipe;

import javax.swing.JFrame;
import javax.swing.JList;

public class SearchActionListener extends JFrame implements ActionListener {

    private JList<Recipe> jList;

    public SearchActionListener(JList<Recipe> jList) {
        this.jList = jList;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

}
