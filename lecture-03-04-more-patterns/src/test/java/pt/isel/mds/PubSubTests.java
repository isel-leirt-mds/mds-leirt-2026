package pt.isel.mds;

import org.junit.jupiter.api.Test;
import pt.isel.mds.news.NewsSubscriber;
import pt.isel.mds.news.PoliticsEmitter;
import pt.isel.mds.news.SportsEmitter;

public class PubSubTests {
    @Test
    public void pubSubManyToManyTests() {
        var sports = new SportsEmitter("sports feed");
        var politics = new PoliticsEmitter("politics feed");
        
        var all_reader = new NewsSubscriber("all");
        var sports_reader = new NewsSubscriber("sports");
        var politics_reader = new NewsSubscriber("politics");
        
        all_reader.start(sports, politics);
        sports_reader.start(sports);
        politics_reader.start(politics);
        
        sports.next("Benfica champion 2025/2026");
        politics.next("Trump buys Greenland");
        
        all_reader.stop(sports);
        sports.next("Luis Suarez sold to Manchester United");
    }
}
