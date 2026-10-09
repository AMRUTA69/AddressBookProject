import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        AddressBook book = new AddressBook();
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println("\n1. Add Contact | 2. View Contacts | 3. Delete Contact | 4. Exit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();

            if (choice.equals("1")) {
                System.out.print("Enter Name: "); String name = scanner.nextLine();
                System.out.print("Enter Phone: "); String phone = scanner.nextLine();
                book.addContact(new Contact(name, phone));
                System.out.println("Contact added successfully!");
            } else if (choice.equals("2")) {
                book.viewContacts();
            } else if (choice.equals("3")) {
                System.out.print("Enter name to delete: "); String name = scanner.nextLine();
                book.deleteContact(name);
            } else if (choice.equals("4")) {
                System.out.println("Exiting application.");
                break;
            } else {
                System.out.println("Invalid choice. Try again.");
            }
        }
        scanner.close();
    }
}

