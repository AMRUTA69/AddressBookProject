
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AddressBook {
    private List<Contact> contacts = new ArrayList<>();
    private final String FILE_NAME = "contacts.dat";

    public AddressBook() { loadFromFile(); }

    public void addContact(Contact c) { contacts.add(c); saveToFile(); }
    
    public void viewContacts() {
        if(contacts.isEmpty()) { System.out.println("Address book is empty."); return; }
        for(Contact c : contacts) { System.out.println(c); }
    }

    public void deleteContact(String name) {
        contacts.removeIf(c -> c.getName().equalsIgnoreCase(name));
        saveToFile();
        System.out.println("If the contact existed, it has been deleted.");
    }

    private void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(contacts);
        } catch (IOException e) { System.out.println("Error saving data."); }
    }

    @SuppressWarnings("unchecked")
    private void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            contacts = (List<Contact>) ois.readObject();
        } catch (Exception e) { System.out.println("Error loading data."); }
    }
}
