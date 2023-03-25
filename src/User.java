import javafx.beans.property.SimpleStringProperty;

public class User {
    private final SimpleStringProperty name;
    private final SimpleStringProperty dateAdded;

    public User(SimpleStringProperty name, SimpleStringProperty dateAdded) {
        this.name = name;
        this.dateAdded = dateAdded;
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getDateAdded() {
        return dateAdded.get();
    }

    public SimpleStringProperty dateAddedProperty() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded.set(dateAdded);
    }
}