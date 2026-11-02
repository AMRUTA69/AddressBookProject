import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

public class AddressBook {

    private ArrayList<Contact> contactList;
    private static final String FILE_NAME = "contacts.txt";
    
    // Step 3: Comprehensive Logging setup
    private static final Logger logger = Logger.getLogger(AddressBook.class.getName());

    public AddressBook() {
        contactList = new ArrayList<>();
    }

    // ---------- Add Contact ----------
    public void addContact(String name, String phone) {
        logger.info("Attempting to add contact: " + name + " | Phone: " + phone);
        
        // Step 2: Null-pointer check
        if (name == null || name.trim().isEmpty() || phone == null || phone.trim().isEmpty()) {
            logger.warning("Add contact failed: Name or Phone is empty.");
         
            return;
        }

        // Duplicate check
        for (Contact c : contactList) {
            if (c.getName().equalsIgnoreCase(name.trim())) {
                logger.warning("Add contact failed: Contact with name '" + name + "' already exists.");
                return;
            }
        }

        contactList.add(new Contact(name.trim(), phone.trim()));
        logger.info("Successfully added contact: " + name);
    }

    // ---------- View All Contacts ----------
    public void viewAllContacts() {
        logger.info("Fetching all contacts...");
        if (contactList.isEmpty()) {
            logger.warning("No contacts found.");
            return;
        }
        System.out.println("\n--- All Contacts (" + contactList.size() + ") ---");
        for (int i = 0; i < contactList.size(); i++) {
            System.out.println((i + 1) + ". " + contactList.get(i));
        }
        logger.info("Displayed " + contactList.size() + " contacts.");
    }

    // ---------- Delete Contact ----------
    public void deleteContact(String name) {
        logger.info("Attempting to delete contact: " + name);
        
        if (name == null || name.trim().isEmpty()) {
            logger.warning("Delete failed: Name is empty.");
            return;
        }

        boolean removed = false;
        for (int i = 0; i < contactList.size(); i++) {
            if (contactList.get(i).getName().equalsIgnoreCase(name.trim())) {
                contactList.remove(i);
                removed = true;
                logger.info("Successfully deleted contact: " + name);
                break;
            }
        }
        if (!removed) {
            logger.warning("Delete failed: Contact '" + name + "' not found.");
        }
    }

    // ---------- Save Contacts to File ----------
    public void saveContacts() {
        logger.info("Saving contacts to file: " + FILE_NAME);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Contact contact : contactList) {
                writer.write(contact.getName() + "," + contact.getPhoneNumber());
                writer.newLine();
            }
            logger.info("Successfully saved " + contactList.size() + " contacts.");
        } catch (IOException e) {
            logger.severe("Error saving contacts: " + e.getMessage());
        }
    }

    // ---------- Load Contacts with Exception Handling ----------
    public void loadContacts() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            logger.info("No existing address book file found. Starting fresh.");
            return;
        }

        logger.info("Loading contacts from file: " + FILE_NAME);
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int loadedCount = 0;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    contactList.add(new Contact(parts[0].trim(), parts[1].trim()));
                    loadedCount++;
                } else {
                    logger.warning("Skipping corrupted line: " + line);
                }
            }
            logger.info("Successfully loaded " + loadedCount + " contacts from file.");
        } catch (IOException e) {
            logger.severe("Error loading contacts: " + e.getMessage());
        }
    }
}