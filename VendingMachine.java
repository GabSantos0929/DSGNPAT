import java.util.Formatter;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class VendingMachine {
    private ItemSlot[] itemSlots;
    private Denom[] denoms;

    /**
     * Constructor to create a VendingMachine object.
     * @param itemSlots array containing the different products
     * @param denoms    array containing the different denominations
     */
    public VendingMachine(ItemSlot[] itemSlots, Denom[] denoms) {
        this.itemSlots = itemSlots;
        this.denoms = denoms;
    }

    /**
     * Converts the amount the user paid into different denominations and returns the 2d array of the amount consisting of the quantity and index of the denomination.
     * @param change the amount the user paid
     * @return       the 2d array of the amount consisting of the quantity and index of the denomination
     */
    public int[][] convertToDenominations(double change) {
        Denom[] denoms = Denom.getDenominations();
        int[][] values = new int[12][2];
        int j=0, remainingAmount;

        remainingAmount = (int) (change * 100);

        for (int i = denoms.length - 1; i >= 0; i--) {
            Denom denomination = denoms[i];
            double value = denomination.getValue();
            int intValue = (int) (value * 100);

            if (remainingAmount >= intValue) {
                int count = remainingAmount / intValue;
                int k=0;
                remainingAmount %= intValue;
                values[j][k++] = count;
                values[j][k] = i;
                j++;
            }
        }
        return values;
    }

    /**
     * Displays the products in the vending machine and their respective quantities and prices.
     */
    public void displayAvailableItems(JTextArea outputTextArea) {
        for (int i = 0; i < itemSlots.length; i++) {
            ItemSlot item = itemSlots[i];
            String name = item.getName();
            int quantity = item.getQuantity();
            double price = item.getPrice();
            outputTextArea.append("Slot (" + (i+1) + "): " + name + " (Quantity: " + quantity + ")" + " (Price: " + price + ")\n");
        }
    }

    /**
     * Displays the available money denominations the user can pick from.
     * @param inp
     */
    public void displayDenominations(int inp) {
        System.out.println("Please select the value you wish to use");
        for (int i = 0; i < denoms.length; i++) {
            Denom money = denoms[i];
            double value = money.getValue();
            int quantity = money.getQuantity();
            if (inp == 4)
                System.out.println("[" + (i + 1) + "] P" + value + " (Quantity: " + quantity + ")");
            else System.out.println("[" + (i + 1) + "] P" + value);
        }
    }

    /**
     * Determines and returns false if user hasn't paid enough cash based on the item selected or selected item has no stock left.
     * @param payment the amount the user paid
     * @param slot    the slot of the item the user chose
     * @return        false if user hasn't paid enough cash based on the item selected or selected item has no stock left
     */
    public boolean isValid(double payment, int[] slot) {
        ItemSlot item;
        double bill = 0;
        for (int i=0; slot[i] != 0; i++) {
            item = itemSlots[slot[i]-1];
            bill += item.getPrice();
            int quantity = item.getQuantity();
            if (quantity == 0)
                return false;
        }

        if (bill > payment)
            return false;
        return true;
    }

    /**
     * Gets the converted change array and inserts it to their respective denominations in the vending machine if a transaction was successful.
     * Otherwise, gets the converted change array, as well as the converted payment array,
     * and returns the quantity of each denomination on the vending machine before the failed purchase ever happened.
     * @param change        excess cash the user paid
     * @param payment       the amount the user paid
     * @param slot          the slot of the item the user chose
     * @param changeValues  change, converted into a 2d array consisting of the quantity and index of different denominations
     * @param paymentValues payment, converted into a 2d array consisting of the quantity and index of different denominations
     * @param cancelled     determines if a purchase was cancelled and contains either a true or false value
     */
    public void getChange(double change, double payment, int[] slot, int[][] changeValues, int[][] paymentValues, boolean cancelled, int fail) {
        Denom money;
        ItemSlot item;
        double sales=0;
        boolean vaultNotEmpty = false;
        int[] changeIndexes = new int[12];
        int[] changeQuantity = new int[12];
        int[] paymentIndexes = new int[12];
        int[] paymentQuantity = new int[12];
        int denomQuantity=0, success=0, num=0, index=0, sold=0;
        int itemQuantity;
        StringBuilder message = new StringBuilder();

        for (int i=0; changeValues[i][0] != 0; i++) {
            int j=0;
            changeQuantity[i] = changeValues[i][j];
            j++;
            changeIndexes[i] = changeValues[i][j];  
        }

        for (int i=0; paymentValues[i][0] != 0; i++) {
            int j=0;
            paymentQuantity[i] = paymentValues[i][j];
            j++;
            paymentIndexes[i] = paymentValues[i][j];  
        }
       
        do {
            index = changeIndexes[num];
            money = denoms[index];
            denomQuantity  = money.getQuantity();
            
            if (changeQuantity[num] <= denomQuantity) {
                denomQuantity -= changeQuantity[num];
                money.setQuantity(denomQuantity);
                money.removeBill(denomQuantity);
                success++;
                vaultNotEmpty = true;
            }
            num++;
        } while (changeQuantity[num] != 0);

        if (success == num && cancelled == false) {
            for (int i=0; slot[i] != 0; i++) {
                item = itemSlots[slot[i]-1];
                itemQuantity = item.getQuantity();
                itemQuantity -= 1;
                item.removeItem(itemQuantity);
                item.setQuantity(itemQuantity);
                sold = item.getSold() + 1;
                sales = item.getSales() + item.getPrice();
                item.setSold(sold);
                item.setSales(sales);

                if (itemQuantity == 0) {
                    removeItemAtIndex(slot[i] - 1);
                }
            }
            message.append("Your change is: P" + change);
        }

        else {
            num=0;
            do {
                index = changeIndexes[num];
                money = denoms[index];

                if (vaultNotEmpty == true) {
                    denomQuantity = money.getQuantity() + changeQuantity[num];
                    for (int j=0; j < changeQuantity[num]; j++)
                        money.addBill(money.getValue());
                    money.setQuantity(denomQuantity);
                }
                num++;
            } while (changeQuantity[num] != 0);
            num=0;
            do {
                index = paymentIndexes[num];
                money = denoms[index];
                denomQuantity = money.getQuantity() - paymentQuantity[num];
                money.removeBill(denomQuantity);
                money.setQuantity(denomQuantity);
                num++;
            } while (paymentQuantity[num] != 0);
            if (fail == 1)
                message.append("Insufficient change left in machine. Transaction cancelled.\n");  
            else message.append("Transaction cancelled.\n"); 
            message.append("Your change is: P" + payment);
        }
        JOptionPane.showMessageDialog(null, message.toString(), "Vending Machine says...", JOptionPane.INFORMATION_MESSAGE);
    }

    
    /** 
     * Incerements the index
     * @param index variable to increment
     * @return      the updated index
     */
    public int incrementIndex(int index) {
        index += 1;
        return index;
    }

    /**
     * Sets the bill equal to the price of the selected item.
     * @param bill variable which would be assigned to a value based on the price of the product
     * @param slot the slot of the item the user chose
     * @return     the bill equal to the price of the product
     */
    public double getBill(double bill, int[] slot) {
        ItemSlot item;
        for (int i=0; slot[i] != 0; i++) {
            item = itemSlots[slot[i]-1];
            bill += item.getPrice();
        }
        return bill;
    }

    /**
     * Returns total payment user has inserted based on the denominations selected by the user.
     * @param sc used to take iput
     * @return   the amount the user paid
     */
    public double receivePayment(int slot) {
        Denom money;
        int quantity, value;
        double payment = 0;

        value = slot;
        money = denoms[value];
        quantity = money.getQuantity();
        quantity += 1;
        money.setQuantity(quantity);
        payment = money.getValue();
        money.addBill(payment);

        return payment;
    }

    /**
     * Restocks the selected item.
     * @param slot  the slot of the item the user chose
     * @param stock the quantity of items being restocked
     */
    public void restock(int slot, int stock) {
        ItemSlot item = itemSlots[slot - 1];
        int quantity = item.getQuantity();

        int restockQuantity = stock;
        item.addItem(restockQuantity);
        quantity += restockQuantity;
        item.setQuantity(quantity);
        String message = "Successfully restocked " + restockQuantity + " items of " + item.getName() + "\nNew Quantity: " + quantity;
        item.displayItems();
        JOptionPane.showMessageDialog(null, message, "Restock Successful", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Prints the quantity of each item sold and the total amount collected in the sales starting from the previous stocking.
     */
    public void summaryOfTransactions() {
        ItemSlot item;
        double sales, totalSales=0;
        int sold, totalSold=0;
        String name;


        System.out.println("Summary of Transactions");
        System.out.println("");


        for (int i=0; i < itemSlots.length; i++) {
            item = itemSlots[i];
            sales = item.getSales();
            sold = item.getSold();
            name = item.getName();
            totalSales += sales;
            totalSold += sold;
            System.out.println("Product: " + name);
            System.out.println("Money Collected: P " + sales);
            System.out.println("Item Sold: " + sold);
            System.out.println("");
        }
        System.out.println("Overall Sales: P " + totalSales);
        System.out.println("Overall Items Sold: " + totalSold);
        System.out.println("");
    }

    /**
     * Sets the price of the selected item.
     * @param slot  the slot of the item the user chose
     * @param price the price of the item
     */
    public void setPrice(int slot, double price) {
        int slotIndex = slot - 1; 
        if (slotIndex >= 0 && slotIndex < itemSlots.length) {
            ItemSlot item = itemSlots[slotIndex];
            double newPrice = price;
            item.setPrice(newPrice);
            String message = "Successfully set price" + "\nThe new price of " + item.getName() + " is " + newPrice;
            JOptionPane.showMessageDialog(null, message, "Setting new price", JOptionPane.INFORMATION_MESSAGE);
        } else {
            String errorMessage = "Invalid slot number. Please enter a valid slot number.";
            JOptionPane.showMessageDialog(null, errorMessage, "Invalid Slot Number", JOptionPane.ERROR_MESSAGE);
        }
    }
    

    /**
     * Collects all the money in the vending machine.
     */
    public void collectMoney() {
        Denom money;
        int empty = 0;
        double totalMoney = 0.0;
        StringBuilder message = new StringBuilder();

        for (int i = 0; i < denoms.length; i++) {
            money = denoms[i];
            int quantity = money.getQuantity();
            

            if (quantity == 0)
                empty++;
            else
                totalMoney += quantity * money.getValue();

            for (int j=1; j <= quantity; j++) {
                money.removeBill(quantity-j);
            }
            quantity = 0;
            money.setQuantity(quantity);
        }

        if (empty == 12)
            message.append("No more money to collect!\n");
        else {
            message.append("All money collected!\n");
            message.append("Total amount collected: P" + totalMoney);
        }
        JOptionPane.showMessageDialog(null, message.toString(), "Vending Machine says...", JOptionPane.INFORMATION_MESSAGE);
    }
   
    /**
     * Increases the quantity of the selected denomination and returns the new quantity.
     * @param quantity the quantity to be replenished of the selected denomination
     * @param value    the index of the denomination
     * @return
     */
    public int replenishChange(int quantity, int value) {
        Denom money = denoms[value];
        double bill = money.getValue();

        for (int i=0; i < quantity; i++) {
            money.addBill(bill);
        }

        quantity += money.getQuantity();
        money.setQuantity(quantity);
        money.displayBills();
        String message = "Successfully replenished change" + "\nNew change amount: " + quantity;
        JOptionPane.showMessageDialog(null, message, "Replenishing change", JOptionPane.ERROR_MESSAGE);
        return quantity;
    }

    /**
     * Compares the inventories of the items of the previous restocking and the current restocking.
     * @param oldSlots array containing the slot of the item of the previous restocking
     */
    public void compareInventory(ItemSlot[] oldSlots) {
        StringBuilder message = new StringBuilder();
        Formatter formatter = new Formatter(message);
    
        message.append("Inventory Comparison:\n");
        formatter.format("Slot  Item Name          Old Quantity  New Quantity  Old Sales  New Sales\n");
    
        for (int i = 0; i < itemSlots.length; i++) {
            ItemSlot oldSlot = oldSlots[i];
            ItemSlot newSlot = itemSlots[i];
    
            String itemName = oldSlot.getName();
            int oldQuantity = oldSlot.getQuantity();
            double oldSales = oldSlot.getSales();
            int newQuantity = newSlot.getQuantity();
            double newSales = newSlot.getSales();
    
            formatter.format("%-5d %-26s %-23d %-24d %-15.2f %-21.2f\n",(i + 1), itemName, oldQuantity, newQuantity, oldSales, newSales);
        }
    
        JOptionPane.showMessageDialog(null, message.toString(), "Inventory Comparison", JOptionPane.INFORMATION_MESSAGE);
    }
}
