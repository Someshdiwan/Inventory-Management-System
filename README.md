```
Inventory Management System - Flow

User Input & Action Handling:
Users select an option: Add Product, View Inventory, Update Stock, Delete Product, Search Product, Save & Exit.
The main() method handles the user’s choice using conditional checks.

Add Product (1):
User enters Product Name, Quantity, and Price.
If the product already exists: Increase the quantity of the existing product.
If new product: Add it to the inventory.
If successful: "Product 'name' added/updated successfully!".

View Inventory (2):
Display all products with their Name, Quantity, and Price.
If no products exist: "Inventory is empty.".

Update Stock (3):
User enters Product Name.
If found: Update the quantity to the new value.
If not found: "Product not found!".

Delete Product (4):
User enters Product Name.
If found: Remove the product from all three ArrayLists.
If not found: "Product not found!".

Search Product (5):
User enters Product Name.
If found: Show quantity and price of the product.
If not found: "Product not found!".

Save and Exit (6):
Save the current inventory to a CSV file named inventory.csv.
If successful: "Inventory saved to inventory.csv".
```