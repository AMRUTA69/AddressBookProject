
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class AddressBook {

    // List to store all contacts
    private ArrayList<Contact> contactList;
    private static final String FILE_NAME = "contacts.txt";

    // Constructor
    public AddressBook() {
        contactList = new ArrayList<>();
    }

    // ---------- Add Contact ----------
    public void addContact(String name, String phone) {
        Contact newContact = new Contact(name, phone);
        contactList.add(newContact);
        System.out.println("Contact added: " + name + " (" + phone + ")");
    }

    // ---------- View All Contacts ----------
    public void viewAllContacts() {
        if (contactList.isEmpty()) {
            System.out.println("No contacts found.");
            return;
        }
        System.out.println("\n--- All Contacts ---");
        for (int i = 0; i < contactList.size(); i++) {
            System.out.println((i + 1) + ". " + contactList.get(i).getName()
                    + " - " + contactList.get(i).getPhoneNumber());
        }
    }

    // ---------- Delete Contact ----------
    public void deleteContact(String name) {
        boolean removed = false;
        for (int i = 0; i < contactList.size(); i++) {
            if (contactList.get(i).getName().equalsIgnoreCase(name)) {
                contactList.remove(i);
                removed = true;
                System.out.println("Contact deleted: " + name);
                break;
            }
        }
        if (!removed) {
            System.out.println("Contact not found: " + name);
        }
    }

    // ---------- Step 1: Save Contacts to File ----------
    public void saveContacts() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Contact contact : contactList) {
                writer.write(contact.getName() + "," + contact.getPhoneNumber());
                writer.newLine();
            }
            System.out.println("Contacts saved successfully to " + FILE_NAME);
        } catch (IOException e) {
            System.out.println("Error saving contacts: " + e.getMessage());
        }
    }

    // ---------- Step 2 & 3: Load Contacts with Exception Handling ----------
    public void loadContacts() {
        File file = new File(FILE_NAME);

        // If file doesn't exist, just start fresh (user-friendly)
        if (!file.exists()) {
            System.out.println("No existing address book found. Starting a new one.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int loadedCount = 0;
            int skippedCount = 0;

            while ((line = reader.readLine()) != null) {
                // Skip empty lines
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split(",");

                // Step 4: Handle corrupted lines gracefully
                if (parts.length == 2) {
                    contactList.add(new Contact(parts[0].trim(), parts[1].trim()));
                    loadedCount++;
                } else {
                    System.out.println("Skipping corrupted line: " + line);
                    skippedCount++;
                }
            }

            System.out.println("Loaded " + loadedCount + " contact(s) from file.");
            if (skippedCount > 0) {
                System.out.println("Skipped " + skippedCount + " corrupted line(s).");
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found. Starting fresh.");
        } catch (IOException e) {
            System.out.println("Error loading contacts: " + e.getMessage());
        }
    }

    // Helper method (optional)
    public int getContactCount() {
        return contactList.size();
    }
}