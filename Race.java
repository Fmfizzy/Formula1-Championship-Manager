import java.util.ArrayList;

public class Race {
    private String date;
    private ArrayList<String> racePositions = new ArrayList<String>();

    
    public Race(String date,ArrayList<String> pos){
        this.date = date;
        this.racePositions = pos;
    }

    public void setDate(String date){
        this.date=date;
    }

    public void setRacePosition(ArrayList<String> pos){
        this.racePositions = pos;
    }
    
    public String getDate(){
        return this.date;
    }

    public ArrayList<String> getRacePosition(){
        return this.racePositions;
    }

    public int getPosSize(){
        return racePositions.size();
    }

    public String getPosition(int place){
        for (int i = 0; i < racePositions.size(); i++) {
            String[] info = racePositions.get(i).split(":");
            if (info[1].equals(Integer.toString(place))) {
                return info[0];
            }
        }
        return "Null";
    }

    public String getDriver(String driver){
        for (int i = 0; i < racePositions.size(); i++) {
            String[] info = racePositions.get(i).split(":");
            if (info[0].equalsIgnoreCase(driver)) {
                return info[1];
            }
        }
        return "Null";
    }

}
