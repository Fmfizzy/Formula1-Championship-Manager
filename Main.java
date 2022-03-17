import java.io.IOException;
import java.util.Scanner;
import static java.lang.System.exit;

public class Main {
    static Scanner sc = new Scanner(System.in);
    static GUITable guiTable = new GUITable();
    static RaceTable raceTable = new RaceTable();


    public static void main(String[] args) {
        int menu_selector;
        guiTable.display();
        do {
            System.out.println(
                    "--------------------------------------------------------------\nSelect a function you want to perform\n-------------------------------------------------------------- \n 1:Create new driver \n 2:Delete driver and team \n 3:Change Driver \n 4:Display a drivers statistics \n 5:Display all drivers \n 6:Add completed race \n 7:Save information to text file \n 8:Retrieve information from previous session \n 9:Exit menu selector");
            while (true) { // validation for menu selector
                try {
                    menu_selector = Integer.parseInt(sc.nextLine());
                    if (menu_selector < 0 || menu_selector > 9) {
                        System.out.println("Please key in a Proper value!");
                        continue;
                    }
                    break;
                } catch (NumberFormatException ex) {
                    System.out.println("Please type a valid number!");
                    continue;
                }
            }
        Formula1ChampionshipManager instance = new Formula1ChampionshipManager();
            switch (menu_selector) {
                case 1:
                    instance.addDriver();
                    instance.sortDescending();
                    guiTable.setDriverValues(instance.getArrayList());
                    break;
                case 2:
                    instance.deleteDriver();
                    instance.sortDescending();
                    guiTable.setDriverValues(instance.getArrayList());
                    break;
                case 3:
                    instance.changeDriver();
                    instance.sortDescending();
                    guiTable.setDriverValues(instance.getArrayList());
                    break;
                case 4:
                    instance.displayDriverStats();
                    break;
                case 5:
                    instance.displayAllStats();
                    break;
                case 6:
                    instance.raceCompleted();
                    instance.sortDescending();
                    guiTable.getChampionshipManager(instance);
                    guiTable.setDriverValues(instance.getArrayList());
                    break;
                case 7:
                    try {
                        instance.saveFile();
                    } catch (IOException fe) {
                        System.out.println("You ran into a Input/Output error when writing to file try again..");
                    }
                    break;
                case 8:
                    try {
                        instance.readFile();
                        instance.sortDescending();
                        guiTable.getChampionshipManager(instance);
                        guiTable.setDriverValues(instance.getArrayList());

                    } catch (IOException fe) {
                        System.out.println("You ran into a Input/Output error when writing to file try again..");
                    }
                    
                    break;
                    
            }

        } while (menu_selector != 9);
        exit(0);
    }


}
