import java.util.ArrayList;

public class Denom {
    private double value;
    private int quantity;
    private ArrayList<Double> bills;

    /**
     * Constructor to create a Denom obejct.
     * @param value    the value of a denomination
     * @param quantity the quantity of a denomination
     */
    public Denom(double value, int quantity, ArrayList<Double> bills) {
        this.value = value;
        this.quantity = quantity;
        this.bills = bills;
    }

    /**
     * Gets the value of the denomiantion.
     * @return the value of the denomiantion
     */
    public double getValue() {
        return value;
    }

    /**
     * Gets the quantity of the denomination.
     * @return the quantity of the denomination
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of the specified denomination.
     * @param quantity the quantity to be set to the specified denomination
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void addBill(double value) {
        this.bills.add(value);
    }

    public void removeBill(int index) {
        this.bills.remove(index);
    }

    public void displayBills() {
        System.out.println(bills);
    }

    /**
     * Initializes the value of the denominations
     * @return the different denominations into an array
     */
    public static Denom[] initializeDenominations() {
        double[] values = { 0.01, 0.05, 0.25, 1, 5, 10, 20, 50, 100, 200, 500, 1000 };
        Denom[] denoms = new Denom[values.length];
        ArrayList<Double> money = new ArrayList<Double>();

        for (int i = 0; i < values.length; i++) {
            for (int j=0; j < 10; j++) {
                money.add(values[i]);
            }
            Denom denom = new Denom(values[i], 10, money);
            denoms[i] = denom;
        }

        return denoms;
    }

    /** 
     * Get the denominations array.
     * @return the denom array
     */
    public static Denom[] getDenominations() {
        return initializeDenominations();
    }
}