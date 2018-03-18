package dk.sdu.mmmi.cbse.common.data;

import dk.sdu.mmmi.cbse.common.events.Event;
import dk.sdu.mmmi.cbse.common.services.IGameCoreInitialized;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameData {

    private float delta;
    private int displayWidth;
    private int displayHeight;
    private final GameKeys keys = new GameKeys();
    private List<Event> events = new CopyOnWriteArrayList<>();
    private List<IGameCoreInitialized> gameCoreInitializedListeners = new CopyOnWriteArrayList<>();

    public void addEvent(Event e) {
        events.add(e);
    }

    public void removeEvent(Event e) {
        events.remove(e);
    }

    public List<Event> getEvents() {
        return events;
    }

    public GameKeys getKeys() {
        return keys;
    }

    public void setDelta(float delta) {
        this.delta = delta;
    }

    public float getDelta() {
        return delta;
    }

    public void setDisplayWidth(int width) {
        this.displayWidth = width;
    }

    public int getDisplayWidth() {
        return displayWidth;
    }

    public void setDisplayHeight(int height) {
        this.displayHeight = height;
    }

    public int getDisplayHeight() {
        return displayHeight;
    }

    public <E extends Event> List<Event> getEvents(Class<E> type, String sourceID) {
        List<Event> r = new ArrayList();
        for (Event event : events) {
            if (event.getClass().equals(type) && event.getSource().getID().equals(sourceID)) {
                r.add(event);
            }
        }

        return r;
    }
    
    public void addGameCoreInitializedListener(IGameCoreInitialized listener) {
        System.out.println("Added IGameCoreInitialized listener: " + listener);
        this.gameCoreInitializedListeners.add(listener);
    }
    
    public void notifyGameCoreInitializedListeners(GameData data, World world) {
        if (this.gameCoreInitializedListeners != null) {
            System.out.println("Notifying gameCoreInitialized listeners");
            for (IGameCoreInitialized listener : this.gameCoreInitializedListeners) {
                System.out.println("Notified listener: " + listener);
                listener.gameCoreInitialized(data, world);
            }
        } else {
            System.out.println("No gameCoreInitialized listeners");
        }
    }
}
