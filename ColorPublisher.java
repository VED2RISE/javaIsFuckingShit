import java.awt.Color;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ColorPublisher {
    private final List<ColorSubscriber> subscribers = new CopyOnWriteArrayList<>();
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public void addSubscriber(ColorSubscriber cs) {
        subscribers.add(cs);
    }

    public void publish(Color c) {
        executor.submit(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            for (ColorSubscriber subscriber : subscribers) {
                subscriber.notifyColorChange(c);
            }
        });
    }
}