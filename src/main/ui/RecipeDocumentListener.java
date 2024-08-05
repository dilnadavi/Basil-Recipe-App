package ui;

import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.event.DocumentEvent;

public class RecipeDocumentListener implements DocumentListener {
    private TableRowSorter<TableModel> sorter;
    private JTextField filter;

    public RecipeDocumentListener(JTextField filter, TableRowSorter<TableModel> sorter) {
        this.sorter = sorter;
        this.filter = filter;
    }
    
    @Override
    public void insertUpdate(DocumentEvent e) {
        if (filter.getText().trim().length() != 0) {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + filter.getText()));
        } else {
            sorter.setRowFilter(null);
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        if (filter.getText().trim().length() != 0) {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + filter.getText()));
        } else {
            sorter.setRowFilter(null);
        }
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        throw new UnsupportedOperationException("Unimplemented method 'changedUpdate'");
    }

}

