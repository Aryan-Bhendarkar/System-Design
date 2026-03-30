package moviebooking.models;

import java.util.ArrayList;
import java.util.List;

public class Theatre {
    private String id;
    private String name;
    private String city;
    private String address;
    private List<Screen> screens;

    public Theatre(String id, String name, String city, String address) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.address = address;
        this.screens = new ArrayList<>();
    }

    public void addScreen(Screen screen) {
        screens.add(screen);
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getCity() { return city; }
    public String getAddress() { return address; }
    public List<Screen> getScreens() { return screens; }

    @Override
    public String toString() {
        return "Theatre{id='" + id + "', name='" + name + "', city='" + city + "'}";
    }
}
