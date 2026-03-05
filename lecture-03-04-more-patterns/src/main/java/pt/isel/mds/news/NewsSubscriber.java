package pt.isel.mds.news;

import pt.isel.mds.news.pub_sub.Publisher;
import pt.isel.mds.news.pub_sub.Subscriber;

import java.util.ArrayList;
import java.util.List;

public class NewsSubscriber implements Subscriber<String> {
    public final String name;
    private List<NewsEmitter> emitters;
    
    public NewsSubscriber(String name) {
        this.name = name;
        emitters = new ArrayList<>();
    }
    
    public void start(NewsEmitter ...emitters) {
       
        for(var e : emitters) {
            if (!this.emitters.contains(e)) {
                this.emitters.add(e);
                e.subscribe(this);
            }
        }
    }
    
    public void stop(NewsEmitter ...emitters) {
        for(var e : emitters) {
            if (this.emitters.remove(e)) {
                e.unSubscribe(this);
            }
        }
    }
    
    @Override
    public void onNext(Publisher<String> src, String value) {

        System.out.printf("received on %s:'%s'\n", name, value);
    }
    
    @Override
    public void onEnd(Publisher<String> src) {
        var srcName = ((NewsEmitter) src).name;
        System.out.printf("service from %s stopped on %s\n", srcName, name );
    }
}
