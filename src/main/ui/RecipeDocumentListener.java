package ui;

import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

// DocumentListener that filters the JTable based on user input
// insertUpdate() and removeUpdate() ADAPTED FROM: 
// https://stackoverflow.com/questions/22066387/how-to-search-an-element-in-a-jtable-java (Author: Paul Samsotha)
public class RecipeDocumentListener implements DocumentListener {
    private TableRowSorter<TableModel> sorter;
    private JTextField filter;

    // EFFECTS: sets the sorter and filter from the input
    public RecipeDocumentListener(JTextField filter, TableRowSorter<TableModel> sorter) {
        this.sorter = sorter;
        this.filter = filter;
    }
    
    // MODIFIES: this
    // EFFECTS: performs filter action based on updated user input
    @Override
    public void insertUpdate(DocumentEvent e) {
        if (filter.getText().trim().length() != 0) {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + filter.getText()));
        } else {
            sorter.setRowFilter(null);
        }
    }

    // MODIFIES: this
    // EFFECTS: performs filter action based on removed user input
    @Override
    public void removeUpdate(DocumentEvent e) {
        if (filter.getText().trim().length() != 0) {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + filter.getText()));
        } else {
            sorter.setRowFilter(null);
        }
    }

    // EFFECTS: throws UnsupportedOperationException as method is not implemented for this version.
    @Override
    public void changedUpdate(DocumentEvent e) {
        throw new UnsupportedOperationException("Unimplemented method 'changedUpdate'");
    }

}

