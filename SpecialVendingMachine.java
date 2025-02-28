import java.util.HashMap;

import javax.swing.JTextArea;

public class SpecialVendingMachine extends VendingMachine {
    private HashMap<String, Integer> itemStock;

    public SpecialVendingMachine(ItemSlot[] itemSlots, Denom[] denoms, HashMap<String, Integer> itemStock) {
        super(itemSlots, denoms);
        this.itemStock = itemStock;
    }

    // Method to initialize the stock of items
    public void initializeItemStock(HashMap<String, Integer> itemStock) {
        this.itemStock = itemStock;
    }

    // Method to display available items with quantities, prices, and calorie counts
    @Override
    public void displayAvailableItems(JTextArea outputTextArea) {
        super.displayAvailableItems(outputTextArea); // Call the super class method to display basic information
        // Add any additional information specific to the SpecialVendingMachine if needed
    }

    // Method to prepare the customizable product based on user choices
    public void prepareCustomProduct(String selectedItem) {
        // Check if all required ingredients are available
        if (!areIngredientsAvailable(selectedItem)) {
            System.out.println("Not enough ingredients to prepare " + selectedItem);
            return;
        }

        // Display the preparation messages based on the selected item
        switch (selectedItem.toLowerCase()) {
            case "hotsilog":
                prepareHotsilog();
                break;
            case "tapasilog":
                prepareTapasilog();
                break;
            case "bacsilog":
                prepareBacsilog();
                break;
            // Add cases for other items...
            default:
                System.out.println("Invalid item selected.");
                return;
        }

        double totalCalories = calculateTotalCalories(selectedItem);
        System.out.println("Silog done!");
        System.out.println("You have ordered " + selectedItem + " with a total calorie count of " + totalCalories);
    }

    private void prepareHotsilog() {
        System.out.println("Cooking rice...");
        System.out.println("Cooking hotdog...");
        System.out.println("Cooking egg...");
        System.out.println("Adding ketchup...");
        System.out.println("Adding mayonnaise...");
        System.out.println("Assembling hotsilog...");
    }

    private void prepareTapasilog() {
        System.out.println("Cooking rice...");
        System.out.println("Cooking tapa...");
        System.out.println("Cooking egg...");
        System.out.println("Assembling tapasilog...");
    }

    private void prepareBacsilog() {
        System.out.println("Cooking rice...");
        System.out.println("Cooking bacon...");
        System.out.println("Cooking egg...");
        System.out.println("Assembling bacsilog...");
    }
    
    private boolean areIngredientsAvailable(String selectedItem) {
        switch (selectedItem.toLowerCase()) {
            case "hotsilog":
                return isIngredientAvailable("rice", 1) && isIngredientAvailable("hotdog", 2)
                        && isIngredientAvailable("egg", 1) && isIngredientAvailable("ketchup", 1)
                        && isIngredientAvailable("mayonnaise", 1);
            case "tapasilog":
                return isIngredientAvailable("rice", 1) && isIngredientAvailable("tapa", 2)
                        && isIngredientAvailable("egg", 1);
            case "bacsilog":
                return isIngredientAvailable("rice", 1) && isIngredientAvailable("bacon", 2)
                        && isIngredientAvailable("egg", 1);
            // Add cases for other items...
            default:
                return false;
        }
    }

    private boolean isIngredientAvailable(String ingredientName, int quantityNeeded) {
        int availableQuantity = itemStock.getOrDefault(ingredientName.toLowerCase(), 0);
        return availableQuantity >= quantityNeeded;
    }

    private double calculateTotalCalories(String selectedItem) {
        switch (selectedItem.toLowerCase()) {
            case "hotsilog":
                return getItemCalories("rice") + getItemCalories("hotdog") * 2
                        + getItemCalories("egg") + getItemCalories("ketchup")
                        + getItemCalories("mayonnaise");
            case "tapasilog":
                return getItemCalories("rice") + getItemCalories("tapa") * 2
                        + getItemCalories("egg");
            case "bacsilog":
                return getItemCalories("rice") + getItemCalories("bacon") * 2
                        + getItemCalories("egg");
            // Add cases for other items...
            default:
                return 0;
        }
    }

    private int getItemCalories(String itemName) {
        for (ItemSlot item : itemSlots) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return item.getCalories();
            }
        }
        return 0; // Item not found, return 0 calories
    }
}