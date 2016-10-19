package com.example.ofirmonis.resttodo.objects;

/**
 * Created by programing on 13/03/2016.
 */
public class Event {
    private String date;
    private String restaurant;
    private String title;
    private String url;
    private int id;

    public Event() {
    }

    public Event(String date, String restaurant, String title, String url) {
        this.date = date;
        this.restaurant = restaurant;
        this.title = title;
        this.url = url;
        id = hashCode();
    }

    public String getUrl() {
        return url;
    }

    public String getDate() {
        return date;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "Event{" +
                "title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", restaurant='" + restaurant + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Event)) return false;

        Event event = (Event) o;

        if (getDate() != null ? !getDate().equals(event.getDate()) : event.getDate() != null)
            return false;
        if (getRestaurant() != null ? !getRestaurant().equals(event.getRestaurant()) : event.getRestaurant() != null)
            return false;
        return !(getTitle() != null ? !getTitle().equals(event.getTitle()) : event.getTitle() != null);

    }

    @Override
    public int hashCode() {
        int result = getDate() != null ? getDate().hashCode() : 0;
        result = 31 * result + (getRestaurant() != null ? getRestaurant().hashCode() : 0);
        result = 31 * result + (getTitle() != null ? getTitle().hashCode() : 0);
        return result;
    }
}
