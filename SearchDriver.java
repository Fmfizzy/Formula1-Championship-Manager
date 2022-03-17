import java.util.ArrayList;
import java.util.Comparator;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import javax.swing.table.TableRowSorter;
import javax.swing.table.TableModel;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import java.util.List;


public class SearchDriver extends JFrame {
    ArrayList<Race> raceValues = new ArrayList<Race>();
    private static String[] header = {"Date (yyyy/mm/dd)","Position"};
    private DefaultTableModel tableModel = new DefaultTableModel(null, header);
    private JTable table = new JTable(tableModel);
    private JScrollPane scrollPane = new JScrollPane(table);
    private static JTextField field = new JTextField(20);
    private static JLabel label = new JLabel("Search Driver");
    private static JButton searchButton = new JButton("Search");
    private static String driverPos,date,driverName;
    private static boolean driverCheck=false;
    
    
    public SearchDriver(){
        JPanel panel = new JPanel();
        this.setLayout(new BorderLayout());
        this.add(scrollPane, BorderLayout.CENTER);
        searchButton.addActionListener((ActionEvent e)->{
            if (raceValues != null) {
                tableModel.setRowCount(0);
                driverName=field.getText();
                for (int i = 0; i < raceValues.size(); i++) {
                    driverPos=raceValues.get(i).getDriver(driverName);
                    date=raceValues.get(i).getDate();
                    if (driverPos!="Null") {
                        driverCheck=true;
                        addRow(date,driverPos);
                    }
                }
                if (driverCheck==false) {
                    System.out.println("driver does not exist!");
                }
            }
        });
        panel.add(label);
        panel.add(field);
        panel.add(searchButton);
        panel.setPreferredSize(new Dimension(400,60));
        this.add(panel, BorderLayout.SOUTH);
    }

    
    private void addRow(String date,String position){
        tableModel.addRow(new Object[]{
            String.valueOf(date),
            String.valueOf(position)
        });
    }

    public void setRaceValues(ArrayList<Race> raceVal){
        emptyDriverValues();
        for (int i = 0; i < raceVal.size(); i++) {
            this.raceValues.add(raceVal.get(i));
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
