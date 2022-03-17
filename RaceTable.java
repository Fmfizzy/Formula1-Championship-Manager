import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.AdjustmentListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.awt.BorderLayout;
import java.awt.event.AdjustmentEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import java.util.List;



public class RaceTable extends JFrame {
    ArrayList<Race> raceValues = new ArrayList<Race>();
    private static String[] header = {"Date (yyyy/mm/dd)", "1st Place","2nd Place","3rd Place","4th Place","5th Place","6th Place","7th Place","8th Place","9th Place","10th place"};
    private DefaultTableModel tableModel = new DefaultTableModel(null, header); //https://stackoverflow.com/questions/19471404/create-dynamic-table-to-add-new-entry-with-button
    private JTable table = new JTable(tableModel);
    private JScrollPane scrollPane = new JScrollPane(table);
    private JScrollBar vScroll = scrollPane.getVerticalScrollBar();
    private boolean isAutoScroll;

    public RaceTable() {
        this.setLayout(new BorderLayout());
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        vScroll.addAdjustmentListener(new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) { 
                isAutoScroll = !e.getValueIsAdjusting();
            }
        });//https://stackoverflow.com/questions/19471404/create-dynamic-table-to-add-new-entry-with-button
        this.add(scrollPane, BorderLayout.CENTER);
        JPanel panel = new JPanel();
        this.add(panel, BorderLayout.SOUTH);
    }

    private void addRow(Race raceRow){
        tableModel.addRow(new Object[]{
            String.valueOf(raceRow.getDate()),
            String.valueOf(raceRow.getPosition(1)),
            String.valueOf(raceRow.getPosition(2)),
            String.valueOf(raceRow.getPosition(3)),
            String.valueOf(raceRow.getPosition(4)),
            String.valueOf(raceRow.getPosition(5)),
            String.valueOf(raceRow.getPosition(6)),
            String.valueOf(raceRow.getPosition(7)),
            String.valueOf(raceRow.getPosition(8)),
            String.valueOf(raceRow.getPosition(9)),
            String.valueOf(raceRow.getPosition(10))
        });
    }


    public void setRaceValues(ArrayList<Race> raceVal){
        emptyDriverValues();
        for (int i = 0; i < raceVal.size(); i++) {
            this.raceValues.add(raceVal.get(i));
            addRow(raceVal.get(i));
        }
        sortDate();
    }

    public void emptyDriverValues(){
        tableModel.setRowCount(0);
        raceValues.clear();  
    }

    public void sortDate(){
        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(table.getModel());
        sorter.setComparator(0,new Comparator<String>() { //overrides current comparison which only compares string values
            @Override
            public int compare(String val1, String val2) { 
                try{
                    int num1 = Integer.parseInt(val1);
                    int num2 = Integer.parseInt(val2);  
                    return num1-num2;
                }catch (NumberFormatException e){
                    return val1.compareTo(val2);
                }
            }
        });
        table.setRowSorter(sorter);
        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
        sorter.setSortKeys(sortKeys);
        sorter.sort();
    }
}