package ui;

import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.event.DocumentEvent;

public class RecipeDocumentListener implements DocumentListener {
    private TableRowSorter<TableModel> rowSorter;
    private JTextField jtfFilter;

    public RecipeDocumentListener(JTextField jtfFilter, TableRowSorter<TableModel> rowSorter) {
        this.rowSorter = rowSorter;
        this.jtfFilter = jtfFilter;
    }
    
    @Override
    public void insertUpdate(DocumentEvent e) {
        String text = jtfFilter.getText();

        if (text.trim().length() == 0) {
            rowSorter.setRowFilter(null);
        } else {
            rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        String text = jtfFilter.getText();

        if (text.trim().length() == 0) {
            rowSorter.setRowFilter(null);
        } else {
            rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
        }
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods,
                                                                       // choose Tools | Templates.
    }

}
