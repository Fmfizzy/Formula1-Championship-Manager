import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.ArrayList;
import java.lang.Math;

interface ChampionshipManager {
    static ArrayList<Formula1Driver> driverTable = new ArrayList<Formula1Driver>();
    static Set<String> teams = new HashSet<String>();
    static ArrayList<Race> races = new ArrayList<Race>();
    

    void addDriver();

    void deleteDriver();

    void changeDriver();

    void displayDriverStats();

    void displayAllStats();

    void raceCompleted();

    void saveFile() throws IOException;

    void readFile() throws IOException;

}

public class Formula1ChampionshipManager implements ChampionshipManager {
    static Scanner sc = new Scanner(System.in);


    @Override
    public void addDriver() {
        String name, location, team; // error occurs (illiegal modifier) if set to private or protected

        System.out.println("Enter Driver Name");
        name = sc.nextLine();
        System.out.println("Enter Driver Location");
        location = sc.nextLine();
        System.out.println("Enter Driver team");
        team = sc.nextLine();
        String uniqueTeam = team.toLowerCase();
        while (teams.contains(uniqueTeam) == true) {
            System.out.println("Enter a Unique driver team name please");
            uniqueTeam = sc.nextLine().toLowerCase();
        }
        Formula1Driver newDriver = new Formula1Driver(name, location, team);
        teams.add(uniqueTeam); // adds team name to Set so as to keep team names unique

        newDriver.setStatistics();

        driverTable.add(newDriver);
    }

    @Override
    public void deleteDriver() {
        int deletedIndex;

        if (driverTable.size() == 0) {
            System.out.println("There are no Drivers to delete");
        } else {
            for (int i = 0; i < driverTable.size(); i++) {
                System.out.println("Driver " + driverTable.get(i).getName() + " of Index :" + i);
            }
            System.out.println("Which Driver do you want deleted?(Type in Index value)");
            while (true) { // Validation for deletedIndex
                try {
                    deletedIndex = Integer.parseInt(sc.nextLine()); // https://stackoverflow.com/questions/35936799/validation-so-input-is-only-integer-in-java/35937125
                    if (deletedIndex < 0 || deletedIndex > driverTable.size()) {
                        System.out.println("Please key in a proper value!");
                        continue;
                    }
                    break;
                } catch (NumberFormatException ex) {
                    System.out.println("Please type a valid number!");
                    continue;
                }
            }
            String deleteteam = driverTable.get(deletedIndex).getTeam();
            teams.remove(deleteteam);
            driverTable.remove(deletedIndex);

        }
    }

    @Override
    public void changeDriver() {
        int changedIndex;

        if (driverTable.size() == 0) {
            System.out.println("Please add drivers in before trying to change"); // checks if driver list is empty
        } else {
            for (int i = 0; i < driverTable.size(); i++) {
                System.out.println("Driver " + driverTable.get(i).getName() + " of Index :" + i);
            }
            System.out.println("Which Driver do you want to be replaced?(Enter in Index number)");
            while (true) { // Validation for changedIndex
                try {
                    changedIndex = Integer.parseInt(sc.nextLine()); // https://stackoverflow.com/questions/35936799/validation-so-input-is-only-integer-in-java/35937125
                    if (changedIndex < 0 || changedIndex > driverTable.size()) {
                        System.out.println("Please key in a proper value!");
                        continue;
                    }
                    break;
                } catch (NumberFormatException ex) {
                    System.out.println("Please type a valid number!");
                    continue;
                }
            }
            System.out.println("Enter replacement driver name :");
            String replacement = sc.nextLine();
            driverTable.get(changedIndex).setName(replacement);
        }
    }

    @Override
    public void displayDriverStats() {
        int viewIndex;
        if (driverTable.size() == 0) {
            System.out.println("First add drivers to display");
        } else {
            for (int i = 0; i < driverTable.size(); i++) {
                System.out.println("Driver " + driverTable.get(i).getName() + " of Index :" + i);
            }
            System.out.println("Which drivers Statistics do you want to review?(Enter Index number)");
            while (true) { // Validation for viewIndex
                try {
                    viewIndex = Integer.parseInt(sc.nextLine()); // https://stackoverflow.com/questions/35936799/validation-so-input-is-only-integer-in-java/35937125
                    if (viewIndex < 0 || viewIndex > driverTable.size()) {
                        System.out.println("Please key in a proper value!");
                        continue;
                    }
                    break;
                } catch (NumberFormatException ex) {
                    System.out.println("Please type a valid number!");
                    continue;
                }
            }
            driverTable.get(viewIndex).getStatistics();
        }
    }

    @Override
    public void displayAllStats() {
        sortDescending();
        System.out.println("---------------------------------------------------------------------------------------");
        System.out.printf("%15s %15s %15s %15s %15s", "Driver", "Team", "Location", "Points", "No Of Races");
        System.out.println();
        System.out.println("---------------------------------------------------------------------------------------");
        for (Formula1Driver info : driverTable) {
            System.out.format("%15s %15s %15s %15s %15s", info.getName(), info.getTeam(), info.getLocation(),
                    info.getPoints(), info.getNoOfRaces());
            System.out.println();
        }
        System.out.println("---------------------------------------------------------------------------------------");
    }// https://www.delftstack.com/howto/java/print-a-table-in-java/

    @Override
    public void raceCompleted() {
        Set<Integer> checkPos = new HashSet<Integer>(); // to check if driver positions repeat
        ArrayList<String> racePos = new ArrayList<String>();
        String date, driverPlace, name;
        int place;

        System.out.println("Type in the date of the race in this format (yyyy/mm/dd)");
        date = sc.nextLine();
        while (date.length() != 10) {
            System.out.println("Type in the date of the race in this format please (yyyy/mm/dd)");
            date = sc.nextLine();
        }
        for (int i = 0; i < driverTable.size(); i++) {
            System.out.println("What Position did driver " + driverTable.get(i).getName()
                    + " get?(type -1 if driver didnt participate)");
            // validation for place
            while (true) {
                try {
                    place = Integer.parseInt(sc.nextLine());
                    if (place == 0 || place < -1) {
                        System.out.println("Please key in a proper value!");
                        continue;
                    }
                    if (checkPos.contains(place) == true) {   
                        System.out.println("Position has been selected before!!");//checks if place has been selected before
                        continue;
                    }
                    break;
                } catch (NumberFormatException ex) {
                    System.out.println("Please type a valid number!");
                    continue;
                }
            }
            if (place != -1) {
                checkPos.add(place);
            }
            if (place >= 1 && place <= 10) {
                driverTable.get(i).setPositionWithPlace(place - 1); // increments the the drivers position
                driverTable.get(i).setPointsWithPos();// sets the points of the driver appropriately
            }
            driverTable.get(i).incNoOfRaces(); // increments the no of races of the driver
            driverPlace = driverTable.get(i).getName() + ":" + place;
            racePos.add(driverPlace);
        }
        // if 1st, 2nd or 3rd undecided place in values
        for (int i = 1; i < 4; i++) {
            if (checkPos.contains(i) == false) {
                System.out.println("Which driver came in" + i + "place?");
                name = sc.nextLine();
                driverPlace = name + ":" + i;
                racePos.add(driverPlace);
            }
        }

        Race newRace = new Race(date, racePos);
        races.add(newRace);
    }

    @Override
    public void saveFile() throws IOException {
        String str = "";
        FileWriter driverFile = new FileWriter("DriverInfo.txt");
        FileWriter raceFile = new FileWriter("RaceInfo.txt");
        ArrayList<String> racePos = new ArrayList<String>();
        for (int i = 0; i < driverTable.size(); i++) {
            str = driverTable.get(i).getName() + "/" + driverTable.get(i).getLocation() + "/"
                    + driverTable.get(i).getTeam() + "/" + driverTable.get(i).getNoOfRaces() + "/"
                    + driverTable.get(i).getPoints() + "/";
            int[] pos = driverTable.get(i).getPosition();
            for (int j = 0; j < 10; j++) {
                str += pos[j] + ",";
            }
            driverFile.write(str + "\n");
        }
        if (races.size() != 0) {
            str = "";
            for (int i = 0; i < races.size(); i++) {
                str = races.get(i).getDate() + "%";
                racePos = races.get(i).getRacePosition();
                for (int j = 0; j < races.get(i).getPosSize(); j++) {
                    str += racePos.get(j) + ",";
                }
                raceFile.write(str + "%" + races.get(i).getPosSize() + "\n");
            }
        }
        driverFile.close();
        raceFile.close();
        System.out.println("file successfully created!");

    }

    @Override
    public void readFile() throws IOException {
        String text = "", name, location, team, date;
        int freq, teamValues;

        driverTable.clear();
        races.clear();
        try {
            Scanner s1 = new Scanner(new File("DriverInfo.txt"));
            while (s1.hasNextLine()) {
                text = s1.nextLine();
                String[] info = text.split("/");
                name = info[0];
                location = info[1];
                team = info[2];
                teams.add(team); // adds team name to Set so as to keep team names unique
                Formula1Driver newDriver = new Formula1Driver(name, location, team);
                newDriver.setNoOfRaces(Integer.parseInt(info[3]));
                newDriver.setPoints(Integer.parseInt(info[4]));
                String[] posValues = info[5].split(",");
                for (int i = 0; i < 10; i++) {
                    freq = Integer.parseInt(posValues[i]);
                    newDriver.setPosWithPlaceNFreq(i, freq);
                }
                driverTable.add(newDriver);
            }

        } catch (FileNotFoundException e) {
            System.out.println("\nFile not found, please create one first.");
        }
        try {
            Scanner s2 = new Scanner(new File("RaceInfo.txt"));
            while (s2.hasNextLine()) {
                text = s2.nextLine();
                String[] info = text.split("%");
                date = info[0];
                String[] teamData = info[1].split(",");
                teamValues = Integer.parseInt(info[2]);
                ArrayList<String> racePlace = new ArrayList<String>();
                for (int i = 0; i < teamValues; i++) {
                    racePlace.add(teamData[i]);
                }
                Race newRace = new Race(date, racePlace);
                races.add(newRace);
            }
        } catch (FileNotFoundException e) {
            System.out.println("\nFile not found, please create one first.");
        }
    }

    public ArrayList<Formula1Driver> getArrayList() {
        return driverTable;
    }

    public ArrayList<Race> getRaceList() {
        return races;
    }

    //sorts driver array list in descending order
    public void sortDescending() {
        Collections.sort(driverTable, Formula1Driver.pointsComparator);
        for (int i = 0; i < (driverTable.size() - 1); i++) {
            int j = i + 1;
            if (driverTable.get(i).getPoints() == driverTable.get(j).getPoints()) { // if points are similar
                int[] pos1 = driverTable.get(i).getPosition();
                int[] pos2 = driverTable.get(j).getPosition();
                if (pos1[0] < pos2[0]) { // if the latter driver has most 1st positions
                    Collections.swap(driverTable, i, j);
                }
            }
        }

    }

    // Only the first drivers are selected for a randomized race at all times
    public void addRandomRace() {
        int year, month, day, pos;
        String date, name, namePos;
        Set<Integer> positions = new HashSet<Integer>();
        ArrayList<String> racePositions = new ArrayList<String>();

        if (driverTable.size() < 10) {
            System.out.println("Sorry cannot randomize a race without at least 10 drivers"); // validation for the 10
                                                                                             // drivers
        } else {
            year = (int) (Math.random() * (2021 - 1950 + 1) + 1950);
            month = (int) (Math.random() * (12 - 1 + 1) + 1);
            day = (int) (Math.random() * (31 - 1 + 1) + 1);
            date = Integer.toString(year) + "/" + Integer.toString(month) + "/" + Integer.toString(day);
            for (int i = 0; i < 10; i++) { // This wont work unless there are 10 drivers in the arrayList
                name = driverTable.get(i).getName();
                pos = (int) (Math.random() * (10 - 1 + 1) + 1);
                while (positions.contains(pos)) {
                    pos = (int) (Math.random() * (10 - 1 + 1) + 1);
                }
                positions.add(pos);
                namePos = name + ":" + Integer.toString(pos);
                racePositions.add(namePos);
                
            }
            Race race = new Race(date, racePositions);
            races.add(race);
        }
    }

    public void addProbableRace() {
        int startPos, year, month, day, probable, place=-1;
        String date, name , namePos;
        boolean firstPlace = false;
        Set<Integer> startPositions = new HashSet<Integer>();
        Set<Integer> places = new HashSet<Integer>();
        ArrayList<String> racePositions = new ArrayList<String>();

        if (driverTable.size() < 10) {
            System.out.println("Sorry cannot randomize a race without at least 10 drivers"); // validation for the 10
                                                                                             // drivers
        } else {
            year = (int) (Math.random() * (2021 - 1950 + 1) + 1950);
            month = (int) (Math.random() * (12 - 1 + 1) + 1);
            day = (int) (Math.random() * (31 - 1 + 1) + 1);
            date = Integer.toString(year) + "/" + Integer.toString(month) + "/" + Integer.toString(day);
            for (int i = 0; i < 10; i++) {
                name = driverTable.get(i).getName();
                startPos = (int) (Math.random() * (10 - 1 + 1) + 1);
                while (startPositions.contains(startPos)) {
                    startPos = (int) (Math.random() * (10 - 1 + 1) + 1);
                }
                startPositions.add(startPos); //to not allow the position to repeat

                if (firstPlace == false) {
                    probable = (int) (Math.random() * (10 - 1 + 1) + 1);
                    switch (startPos) {
                        case 1:
                            if (probable >= 1 && probable <= 4) {   // 4/10 chance
                                place = 1;
                                firstPlace = true;
                            } else {
                                place = (int) (Math.random() * (10 - 2 + 1) + 2);
                                while (places.contains(place)==true) {
                                    place = (int) (Math.random() * (10 - 2 + 1) + 2);
                                }
                            }
                            break;
                        case 2:
                            if (probable >= 1 && probable <= 3) {  // 3/10 chance
                                place = 1;
                                firstPlace = true;
                            } else {
                                place = (int) (Math.random() * (10 - 2 + 1) + 2);
                                while (places.contains(place)==true) {
                                    place = (int) (Math.random() * (10 - 2 + 1) + 2);
                                }
                            }
                            break;
                        case 3, 4:
                            if (probable == 1) {    // 2/10 chance
                                place = 1;
                                firstPlace = true;
                            } else {
                                place = (int) (Math.random() * (10 - 2 + 1) + 2);
                                while (places.contains(place)==true) {
                                    place = (int) (Math.random() * (10 - 2 + 1) + 2);
                                }
                            }
                            break;
                        case 5, 6, 7, 8, 9:
                            int chance = (int) (Math.random() * (10 - 1 + 1) + 1);  
                            if (probable == 1 && (chance >= 1 && chance <= 2)) { // 1/50 chance(0.02 or 2%)
                                place = 1;
                                firstPlace = true;
                            } else {
                                place = (int) (Math.random() * (10 - 2 + 1) + 2);
                                while (places.contains(place)==true) {
                                    place = (int) (Math.random() * (10 - 2 + 1) + 2); 
                                }
                            }

                        default:
                            break;
                    }
                }else{
                    place = (int) (Math.random() * (10 - 2 + 1) + 2);
                    while (places.contains(place)==true) {
                        place = (int) (Math.random() * (10 - 2 + 1) + 2);//not allow the final place to be repeated
                    }
                }
                if (i==9 && firstPlace==false) {
                    place = 1;
                }

                places.add(place);
                namePos = name + ":" + Integer.toString(place);
                racePositions.add(namePos);
            }
            Race race = new Race(date, racePositions);
            races.add(race);

        }
    }

}
