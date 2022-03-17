
import java.util.Comparator;
import java.util.Scanner;

public class Formula1Driver extends Driver{

    private int positions[] = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
    private int points = 0;
    private int noOfRaces=0;

    public Formula1Driver(String name, String location, String team) {
        super(name, location, team); 
         
    }

    @Override
    public void setStatistics() {
        Scanner sc = new Scanner(System.in);
        String flag = "Yes";
        int place, frequency,totalFreq=0;

        
        System.out.println("How many races has " + this.name + " taken part in?");
        while(true){                                                 //Validation for noOfRaces
            try{
                noOfRaces=Integer.parseInt(sc.nextLine());          /*https://stackoverflow.com/questions/35936799/validation-so-input-is-only-integer-in-java/35937125*/
                if (noOfRaces<0) {
                    System.out.println("Please key in a postive value!");
                    continue;
                }
                break;
            }catch(NumberFormatException ex){
                System.out.println("Please type a valid number!");
                continue;
            }
        }
        if (noOfRaces != 0) {              //As long as racer has taken part in a race check for the rest
            System.out.println("Has " + this.name + " placed (1-10) in any or multiple races this season?(yes/no/y/n)");
            flag = sc.nextLine();       //Validation for flag
            while (!flag.equalsIgnoreCase("no") && !flag.equalsIgnoreCase("n") && !flag.equalsIgnoreCase("y") && !flag.equalsIgnoreCase("yes")) {
                System.out.println("Has " + this.name + " placed (1-10) in any or multiple races this season?(yes/no/y/n)");
                flag = sc.nextLine();
            }
            while (!flag.equalsIgnoreCase("no") && !flag.equalsIgnoreCase("n")){
                System.out.println("key in a position he has placed in(1-10): "); // validation for place
                while(true){
                    try{
                        place=Integer.parseInt(sc.nextLine());          
                        if (place<1 || place>10) {
                            System.out.println("Please key in a proper value!");
                            continue;
                        }
                        break;
                    }catch(NumberFormatException ex){
                        System.out.println("Please type a valid number!");
                        continue;
                    }
                }
                System.out.println("Key in how many times he has placed in this position: ");  // validation for frequency
                while(true){
                    try{
                        frequency=Integer.parseInt(sc.nextLine());         
                        if (frequency<0) {
                            System.out.println("Please key in a postive value!");
                            continue;
                        }
                        break;
                    }catch(NumberFormatException ex){
                        System.out.println("Please type a valid number!");
                        continue;
                    }
                }
                totalFreq+=frequency;
                this.positions[place - 1] = frequency;
                switch (place) {
                case 1:
                    this.points = this.points + (25 * frequency);
                    break;
                case 2:
                    this.points = this.points + (18 * frequency);
                    break;
                case 3:
                    this.points = this.points + (15 * frequency);
                    break;
                case 4:
                    this.points = this.points + (12 * frequency);
                    break;
                case 5:
                    this.points = this.points + (10 * frequency);
                    break;
                case 6:
                    this.points = this.points + (8 * frequency);
                    break;
                case 7:
                    this.points = this.points + (6 * frequency);
                    break;
                case 8:
                    this.points = this.points + (4 * frequency);
                    break;
                case 9:
                    this.points = this.points + (2 * frequency);
                    break;
                case 10:
                    this.points = this.points + (1 * frequency);
                    break;
                default:
                    break;
                }
                if (totalFreq>noOfRaces) {
                    System.out.println("Driver has gotten more positions than number of races please delete and reinput data again!!");
                }
                System.out.println("Does he have anymore positions (1-10) this season?(yes/no/y/n)");
                flag=sc.nextLine(); //validation for flag
                while (!flag.equalsIgnoreCase("no") && !flag.equalsIgnoreCase("n") && !flag.equalsIgnoreCase("y") && !flag.equalsIgnoreCase("yes")) {
                    System.out.println("Has " + this.name + " placed (1-10) in any or multiple races this season?(yes/no/y/n)");
                    flag = sc.nextLine();
                }
                

            }
        }

    }

    public void getStatistics(){
        System.out.println(this.name+" has participated in "+ this.noOfRaces +" races \nAccumalated "+ this.points+ " points so far \nAnd placed first " + this.positions[0] + " times \nSecond "+ this.positions[1] + " times \nand third "+ this.positions[2] + " times so far");
    }

    public void setNoOfRaces(int noOfRace){
        this.noOfRaces=noOfRace;
    }

    public void setPoints(int point){
        this.points=point;
    }

    public void setPosition(int[] pos){
        this.positions=pos;
    }

    public void setPositionWithPlace(int place){
        this.positions[place]+=1;
    }

    public void setPosWithPlaceNFreq(int place,int freq){
        this.positions[place]=freq;
    }

    public void setPointsWithPos(){
        this.points=0;
        for (int i = 0; i < positions.length; i++) {
            int frequency=this.positions[i];
            switch (i+1) {
            case 1:
                this.points = this.points + (25 * frequency);
                break;
            case 2:
                this.points = this.points + (18 * frequency);
                break;
            case 3:
                this.points = this.points + (15 * frequency);
                break;
            case 4:
                this.points = this.points + (12 * frequency);
                break;
            case 5:
                this.points = this.points + (10 * frequency);
                break;
            case 6:
                this.points = this.points + (8 * frequency);
                break;
            case 7:
                this.points = this.points + (6 * frequency);
                break;
            case 8:
                this.points = this.points + (4 * frequency);
                break;
            case 9:
                this.points = this.points + (2 * frequency);
                break;
            case 10:
                this.points = this.points + (1 * frequency);
                break;
            default:
                break;
            }
        }
    }

    public String getName(){
        return this.name;
    }

    public String getLocation(){
        return this.location;
    }

    public String getTeam(){
        return this.team;
    }
 
    public int getPoints(){
        return this.points;
    }

    public int getNoOfRaces(){
        return this.noOfRaces;
    }

    public int[] getPosition(){
        return this.positions ;
    }

    public int getPosFreq(int place){
        return this.positions[place-1];
    }

    public void incNoOfRaces(){
        this.noOfRaces+=1;
    }

    public static Comparator<Formula1Driver> pointsComparator = new Comparator<Formula1Driver>() {

        @Override
        public int compare(Formula1Driver p1, Formula1Driver p2) { 
            int point1 = p1.getPoints();
            int point2 = p2.getPoints();
            return point2-point1;
        }
        
    };



}