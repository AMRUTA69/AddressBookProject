import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        AddressBook addressBook = new AddressBook();
        Scanner scanner = new Scanner(System.in);

        // ---------- Step 2: Load contacts at startup ----------
        System.out.println("=== Address Book Application ===");
        addressBook.loadContacts();

        boolean running = true;

        while (running) {
            System.out.println("\n--- MENU ---");
            System.out.println("1. Add Contact");
            System.out.println("2. View All Contacts");
            System.out.println("3. Delete Contact");
            System.out.println("4. Save Contacts");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter phone number: ");
                    String phone = scanner.nextLine();

                    if (name.trim().isEmpty() || phone.trim().isEmpty()) {
                        System.out.println("Name and phone cannot be empty.");
                    } else {
                        addressBook.addContact(name.trim(), phone.trim());
                    }
                    break;

                case "2":
                    addressBook.viewAllContacts();
                    break;

                case "3":
                    System.out.print("Enter name to delete: ");
                    String delName = scanner.nextLine();
                    addressBook.deleteContact(delName.trim());
                    break;

                case "4":
                    addressBook.saveContacts();
                    break;

                case "5":
                    // Auto-save before exit
                    System.out.println("Saving contacts before exit...");
                    addressBook.saveContacts();
                    running = false;
                    System.out.println("Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice. Please enter 1-5.");
            }
        }

        scanner.close();
    }
}