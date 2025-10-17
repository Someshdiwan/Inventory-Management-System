import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Files;

public class InventoryManagement {

    static ArrayList<String> productNames = new ArrayList<>();
    static ArrayList<Integer> productQuantities = new ArrayList<>();
    static ArrayList<Float> productPrices = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    // Helper: find product index by name (case-insensitive), -1 if not found
    public static int findIndexByName(String name) {
        if (name == null || name.isEmpty()) return -1;
        for (int i = 0; i < productNames.size(); i++) {
            if (productNames.get(i).equalsIgnoreCase(name.trim())) {
                return i;
            }
        }
        return -1;
    }

    // 1) Add Product
    public static void addProduct() {
        System.out.print("Enter product name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Product name cannot be empty!\n");
            return;
        }

        System.out.print("Enter quantity: ");
        String qtyInput = scanner.nextLine().trim();
        int quantity;

        try {
            quantity = Integer.parseInt(qtyInput);
            if (quantity < 0) {
                System.out.println("Quantity cannot be negative!\n");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid quantity. Please enter a whole number.\n");
            return;
        }

        System.out.print("Enter price per unit: ");
        String priceInput = scanner.nextLine().trim();
        float price;

        try {
            price = Float.parseFloat(priceInput);
            if (price < 0) {
                System.out.println("Price cannot be negative!\n");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid price. Please enter a numeric value.\n");
            return;
        }

        int idx = findIndexByName(name);
        if (idx >= 0) {
            // product exists: increase quantity and optionally update price to newest provided
            int newQty = productQuantities.get(idx) + quantity;
            productQuantities.set(idx, newQty);
            productPrices.set(idx, price); // update price to latest input
            System.out.println("Product '" + productNames.get(idx) + "' updated successfully! New quantity: " + newQty + "\n");
        } else {
            productNames.add(name);
            productQuantities.add(quantity);
            productPrices.add(price);
            System.out.println("Product '" + name + "' added successfully!\n");
        }
    }

    // 2) View Inventory
    public static void viewInventory() {
        if (productNames.isEmpty()) {
            System.out.println("Inventory is empty.\n");
            return;
        }

        System.out.println("Current Inventory:");
        System.out.printf("%-30s %-10s %-10s%n", "Name", "Quantity", "Price");
        System.out.println("----------------------------------------------------------------");
        for (int i = 0; i < productNames.size(); i++) {
            System.out.printf("%-30s %-10d $%-10.2f%n",
                    productNames.get(i),
                    productQuantities.get(i),
                    productPrices.get(i));
        }
        System.out.println();
    }

    // 3) Update Stock (set quantity)
    public static void updateProduct() {
        System.out.print("Enter product name to update: ");
        String name = scanner.nextLine().trim();
        int idx = findIndexByName(name);
        if (idx == -1) {
            System.out.println("Product not found!\n");
            return;
        }

        System.out.print("Enter new quantity: ");
        String qtyInput = scanner.nextLine().trim();
        int newQty;
        try {
            newQty = Integer.parseInt(qtyInput);
            if (newQty < 0) {
                System.out.println("Quantity cannot be negative!\n");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid quantity. Please enter a whole number.\n");
            return;
        }

        productQuantities.set(idx, newQty);
        System.out.println("Updated '" + productNames.get(idx) + "' stock to " + newQty + "\n");
    }

    // 4) Delete Product
    public static void deleteProduct() {
        System.out.print("Enter product name to delete: ");
        String name = scanner.nextLine().trim();
        int idx = findIndexByName(name);
        if (idx == -1) {
            System.out.println("Product not found!\n");
            return;
        }

        String removedName = productNames.get(idx);
        productNames.remove(idx);
        productQuantities.remove(idx);
        productPrices.remove(idx);
        System.out.println("Deleted '" + removedName + "' from inventory\n");
    }

    // 5) Search Product
    public static void searchProduct() {
        System.out.print("Enter product name to search: ");
        String name = scanner.nextLine().trim();
        int idx = findIndexByName(name);
        if (idx == -1) {
            System.out.println("Product not found!\n");
            return;
        }

        System.out.printf("%s: Quantity = %d, Price = $%.2f%n%n",
                productNames.get(idx),
                productQuantities.get(idx),
                productPrices.get(idx));
    }

    // Save Inventory to CSV
    public static void saveInventory() {
        String saveDir = "/Users/somesh/Side Hustle/Inventory-Management-System/InventoryManagement/src";
        String filename = "inventory.csv";
        String fullPath = Paths.get(saveDir, filename).toString();

        // ensure directory exists
        try {
            Files.createDirectories(Paths.get(saveDir));
        } catch (IOException e) {
            System.out.println("Unable to create directory: " + e.getMessage());
            return;
        }

        try (FileWriter writer = new FileWriter(fullPath)) {
            writer.write("Product Name,Quantity,Price\n");
            for (int i = 0; i < productNames.size(); i++) {
                // escape commas in product names by wrapping with quotes if needed
                String name = productNames.get(i);
                if (name.contains(",") || name.contains("\"")) {
                    name = "\"" + name.replace("\"", "\"\"") + "\"";
                }
                writer.write(name + "," + productQuantities.get(i) + "," + productPrices.get(i) + "\n");
            }
            System.out.println("Inventory saved to " + fullPath + "\n");
        } catch (IOException e) {
            System.out.println("Error saving inventory: " + e.getMessage() + "\n");
        }
    }

    public static void main(String[] args) {
        System.out.println("Welcome to Inventory Management System\n");
        while (true) {
            System.out.println("1. Add Product");
            System.out.println("2. View Inventory");
            System.out.println("3. Update Stock");
            System.out.println("4. Delete Product");
            System.out.println("5. Search Product");
            System.out.println("6. Save & Exit");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine().trim();

            if (choice.equals("1")) {
                addProduct();
            } else if (choice.equals("2")) {
                viewInventory();
            } else if (choice.equals("3")) {
                updateProduct();
            } else if (choice.equals("4")) {
                deleteProduct();
            } else if (choice.equals("5")) {
                searchProduct();
            } else if (choice.equals("6")) {
                saveInventory();
                System.out.println("Exiting program...");
                break;
            } else {
                System.out.println("Invalid choice! Please try again.\n");
            }
        }
        scanner.close();
    }
}
