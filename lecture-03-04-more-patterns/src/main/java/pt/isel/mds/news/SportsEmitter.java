package pt.isel.mds.news;

public class SportsEmitter extends NewsEmitter{
    
    public SportsEmitter(String name) {
        super(name);
    }
    
    @Override
    public String getType() {
        return "Sports";
    }
}
