import java.util.ArrayList;

public class ItemSlot {
    private String name;
    private double price;
    private double sales;
    private int calories;
    private int quantity;
    private int sold;
    private ArrayList<String> items;

    /**
     * Constructor to create an ItemSlot object.
     * @param name     the name of the product
     * @param price    the price of the product
     * @param sales    the total sales value of the product
     * @param calories the amount of calories in the product
     * @param quantity the quantity of the product
     * @param sold     the number of items sold of the product
     */
    public ItemSlot(String name, double price, double sales, int calories, int quantity, int sold, ArrayList<String> items) {
        this.name = name;
        this.price = price;
        this.sales = sales;
        this.calories = calories;
        this.quantity = quantity;
        this.sold = sold;
        this.items = items;
    }

    /**
     * Gets the name of the product.
     * @return the name of the product
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the price of the product.
     * @return the price of the product
     */
    public double getPrice() {
        return price;
    }

    /**
     * Gets the sales value of the product.
     * @return the sales value of the product
     */
    public double getSales() {
        return sales;
    }

    /**
     * Gets the calories of the product.
     * @return the calories of the product
     */
    public int getCalories() {
        return calories;
    }

    /**
     * Gets the quantity of the product.
     * @return the quantity of the product
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Gets the quantity of sold products.
     * @return the quantity of sold products
     */
    public int getSold() {
        return sold;
    }

    /**
     * Sets the price of the product.
     * @param price the amount to be set to the product
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Sets the total sales value of the product.
     * @param sales the sales value to be set to the product
     */
    public void setSales(double sales) {
        this.sales = sales;
    }

    /**
     * Sets the quantity of the product.
     * @param quantity the quantity to be set to the product
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Sets the number of items sold of the product.
     * @param sold the number of items sold to be set to the product
     */
    public void setSold(int sold) {
        this.sold = sold;
    }

    public void addItem(int stock) {
        for (int i=0; i < stock; i++)
            this.items.add(name);
    }

    public void removeItem(int index) {
        this.items.remove(index);
    }

    public void displayItems() {
        System.out.println(items);
    }

}

