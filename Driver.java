
public abstract class Driver{
    
    protected String name,location,team;

    public Driver(String name1,String location1,String team1){
        this.name = name1;
        this.location=location1;
        this.team=team1;
    }
    
    public abstract void setStatistics();
    public abstract void getStatistics();

    public void setName(String name){
        this.name=name;
    }

    public void setLocation(String location){
        this.location=location;
    }

    public void setTeam(String team){
        this.team=team;
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
    
}
