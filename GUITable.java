import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.awt.event.ActionEvent;

public class GUITable extends JPanel {
    private Formula1ChampionshipManager f1Manager = new Formula1ChampionshipManager();
    private RaceTable raceTable = new RaceTable();
    private static ArrayList<Formula1Driver> driverValues = new ArrayList<Formula1Driver>();
    private static String[] header = { "Name", "Location", "Team", "1st Places", "2nd Places", "3rd Places", "Points",
            "No: of Races" };
    private DefaultTableModel tableModel = new DefaultTableModel(null, header); // https://stackoverflow.com/questions/19471404/create-dynamic-table-to-add-new-entry-with-button
    private JTable table = new JTable(tableModel);
    private JScrollPane scrollPane = new JScrollPane(table);
    private JScrollBar vScroll = scrollPane.getVerticalScrollBar();
    private boolean isAutoScroll;
    private static JFrame f = new JFrame();

    public void display() {
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(this);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    public GUITable() {
        this.setLayout(new BorderLayout());
        Dimension frameSize = new Dimension(800, 400);
        table.setPreferredScrollableViewportSize(frameSize);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        vScroll.addAdjustmentListener(new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                isAutoScroll = !e.getValueIsAdjusting();
            }
        });// https://stackoverflow.com/questions/19471404/create-dynamic-table-to-add-new-entry-with-button
        this.add(scrollPane, BorderLayout.CENTER);
        JPanel panel = new JPanel();
        JButton randomButton = new JButton("Generate Random Race");
        JButton probableButton = new JButton("Calculate Race according to Starting Pos");
        JButton racesButton = new JButton("Display Race");
        JButton searchButton = new JButton("Search Driver");
        racesButton.addActionListener((ActionEvent e) -> {  
            if (raceTable != null) {
                raceTable.setRaceValues(f1Manager.getRaceList());
                raceTable.setSize(800, 400);
                raceTable.setVisible(true);
                raceTable.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            }
        });//https://stackoverflow.com/questions/284899/how-do-you-add-an-actionlistener-onto-a-jbutton-in-java
        randomButton.addActionListener((ActionEvent e) -> {
            if (f1Manager != null) {
                if (f1Manager.getArrayList().size() > 9) {
                    f1Manager.addRandomRace();
                    raceTable.setRaceValues(f1Manager.getRaceList());
                    raceTable.setSize(800, 400);
                    raceTable.setVisible(true);
                    raceTable.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                } else {
                    System.out.println("Please Make sure there are 10 drivers added before randomizing a race!!");
                }
            }
        });
        probableButton.addActionListener((ActionEvent e) -> {
            if (f1Manager != null) {
                if (f1Manager.getArrayList().size() > 9) {
                    f1Manager.addProbableRace();
                    raceTable.setRaceValues(f1Manager.getRaceList());
                    raceTable.setSize(800, 400);
                    raceTable.setVisible(true);
                    raceTable.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                } else {
                    System.out.println("Please Make sure there are 10 drivers added before randomizing a race!!");
                }
            }
        });
        searchButton.addActionListener((ActionEvent e) -> {
            if (f1Manager.getRaceList().size() > 0) {
                SearchDriver searchDriver = new SearchDriver();
                searchDriver.setRaceValues(f1Manager.getRaceList());
                searchDriver.setSize(400, 400);
                searchDriver.setVisible(true);
                searchDriver.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            } else{
                System.out.println("There are currently no races to display!!");
            }
        });

        panel.add(randomButton);
        panel.add(probableButton);
        panel.add(racesButton);
        panel.add(searchButton);
        this.add(panel, BorderLayout.SOUTH);

    }

    private void addRow(Formula1Driver driverRow) {
        tableModel.addRow(new Object[] {
                String.valueOf(driverRow.getName()),
                String.valueOf(driverRow.getLocation()),
                String.valueOf(driverRow.getTeam()),
                String.valueOf(driverRow.getPosFreq(1)),
                String.valueOf(driverRow.getPosFreq(2)),
                String.valueOf(driverRow.getPosFreq(3)),
                String.valueOf(driverRow.getPoints()),
                String.valueOf(driverRow.getNoOfRaces())
        });

    }

    public void setDriverValues(ArrayList<Formula1Driver> driverVals) {
        emptyDriverValues();
        for (int i = 0; i < driverVals.size(); i++) {
            GUITable.driverValues.add(driverVals.get(i));
            addRow(driverVals.get(i));
        }
        sortPoints();
    }

    //so that previous rows will get deleted
    public void emptyDriverValues() {
        tableModel.setRowCount(0);
        driverValues.clear();

    }

    public void sortPoints() {
        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(table.getModel());

        sorter.setComparator(6, new Comparator<String>() { // overrides current comparison which only compares string
                                                           // values
            @Override
            public int compare(String val1, String val2) {
                try {
                    int num1 = Integer.parseInt(val1);
                    int num2 = Integer.parseInt(val2);
                    return num1 - num2;
                } catch (NumberFormatException e) {
                    return val1.compareTo(val2);
                }
            }
        });
        table.setRowSorter(sorter);
        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        sortKeys.add(new RowSorter.SortKey(6, SortOrder.ASCENDING));
        sorter.setSortKeys(sortKeys);
        sorter.sort();

    }

    public void getChampionshipManager(Formula1ChampionshipManager f1Manage) {
        f1Manager = f1Manage;
    }

}
