import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GUI extends JFrame {
    private VendingMachine vendingMachine;
    private ItemSlot[] itemSlots;
    private Denom[] denoms;
    private JTextArea outputTextArea;
    private JButton createButton, testButton, exitButton;
    private double value = 0, totalCalories = 0.0;
    private List<String> itemNames;
    private boolean isSpecial = false;
    private List<ItemSlot> boughtItems;
    private List<String> createdMeals;
    private String s0;
    private LoadingScreen loadingScreen;

    /**
     * Contructor to create the GUI of the vending machine factory
     */
    public GUI() {
        this.itemNames = new ArrayList<>();
        this.boughtItems = new ArrayList<>();
        this.createdMeals = new ArrayList<>();
        loadingScreen = new LoadingScreen();
        
        loadingScreen.showLoadingScreen();
    
        Timer initializationTimer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                loadingScreen.closeLoadingScreen();
    
                setTitle("Vending Machine Management System");
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setLayout(new BorderLayout());
    
                JLabel titleLabel = new JLabel("Vending Machine Creator", SwingConstants.CENTER);
                titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
                add(titleLabel, BorderLayout.NORTH);
    
                outputTextArea = new JTextArea();
                outputTextArea.setEditable(false);
                JScrollPane scrollPane = new JScrollPane(outputTextArea);
                add(scrollPane, BorderLayout.SOUTH);
                s0 = "";
    
                JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                createButton = new JButton("Create a Vending Machine");
                testButton = new JButton("Test a Vending Machine");
                exitButton = new JButton("Exit");
    
                outputTextArea.setPreferredSize(new Dimension(300, 190));
                createButton.setPreferredSize(new Dimension(200, 100));
                testButton.setPreferredSize(new Dimension(200, 100));
                exitButton.setPreferredSize(new Dimension(200, 100));
    
                buttonPanel.add(createButton);
                buttonPanel.add(testButton);
                buttonPanel.add(exitButton);
                add(buttonPanel, BorderLayout.CENTER);
    
                createButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        createVendingMachineMenu();
                    }
                });
    
                testButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        testVendingMachine();
                    }
                });
    
                exitButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.exit(0);
                    }
                });
    
                pack();
                setSize(800, 400);
                setLocationRelativeTo(null);
                setVisible(true); 
            }
        });
        initializationTimer.setRepeats(false); 
        initializationTimer.start();
    }    

    /**
     * The method responsible for the main menu which the user can select to create a normal or special vending machine
     */
    private void createVendingMachineMenu() {
        JFrame createFrame = new JFrame("Create Vending Machine");
        createFrame.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Main Menu");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel questionLabel = new JLabel("What kind of vending machine would you like to create?");
        questionLabel.setFont(new Font("Arial", Font.BOLD, 20));
        questionLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton normalButton = new JButton("Normal Vending Machine");
        normalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createFrame.dispose();
                createVendingMachine();
                isSpecial = false;
            }
        });

        JButton specialButton = new JButton("Special Vending Machine");
        specialButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createFrame.dispose();
                createVendingMachine();
                isSpecial = true;
            }
        });

        createFrame.add(titleLabel, BorderLayout.NORTH);
        createFrame.add(questionLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        buttonPanel.add(normalButton);
        buttonPanel.add(specialButton);
        createFrame.add(buttonPanel, BorderLayout.SOUTH);

        createFrame.pack();
        createFrame.setSize(600, 200);
        createFrame.setLocationRelativeTo(null);
        createFrame.setVisible(true);
    }

    
    /** 
     * Setgs the calories of each item
     * @param itemName variable to know the item name and what to change its calorie variable to
     * @return         returns the new calorie value or zero if the item is not within the list of special vending machine products
     */
    private int setCaloriesBasedOnItem(String itemName) {
        switch (itemName.toLowerCase()) {
            case "hotdog":
                return 290;
            case "tapa":
                return 180;
            case "bacon":
                return 541;
            case "longganisa":
                return 220;
            case "spam":
                return 180;
            case "tocino":
                return 230;
            case "adobo":
                return 215;
            case "daing":
                return 325;
            case "corned beef":
                return 251;
            case "egg":
                return 155;
            case "rice":
                return 130;
            case "ketchup":
                return 112;
            case "mayonnaise":
                return 680;
            default:
                return 0;
        }
    }

    /**
     * Method to initialize the items of the vending machine
     */
    private void createVendingMachine() {
        int slotNum = promptSlotNumber();

        itemSlots = new ItemSlot[slotNum];
        for (int i = 0; i < slotNum; i++) {
            ItemInputDialog itemInputDialog = new ItemInputDialog(this, "Enter Item Details", true, itemNames);
            String itemName = itemInputDialog.getItemName();
            double price = itemInputDialog.getItemPrice();
            int quantity = itemInputDialog.getItemQuantity();
            int calories = setCaloriesBasedOnItem(itemName);

            itemNames.add(itemName.toLowerCase());
            ArrayList<String> items = new ArrayList<String>();

            for (int j=0; j < quantity; j++) {
                items.add(itemName);
            }

            itemSlots[i] = new ItemSlot(itemName, price, 0, calories, quantity, 0, items);
        }

        denoms = Denom.initializeDenominations();
        vendingMachine = new VendingMachine(itemSlots, denoms);

        outputTextArea.append("Vending Machine created!\n");
        displayAvailableItems();
    }

    /**
     * Method to handle the testing of vending machines
     */
    private void testVendingMachine() {
        if (vendingMachine == null) {
            outputTextArea.setText("No valid Vending Machines to test. Please create one first.\n");
            return;
        }

        JFrame createFrame = new JFrame("Test Vending Machine");
        createFrame.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Vending Machine");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel questionLabel = new JLabel("What would you like to do?\n");
        questionLabel.setFont(new Font("Arial", Font.BOLD, 20));
        questionLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton buyButton = new JButton("Buy item");
        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createFrame.dispose();
                int button = 0;
                promptPayment(button);
            }
        });

        JButton maintenanceButton = new JButton("Perform maintenance");
        maintenanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createFrame.dispose();
                promptMaintenance();
            }
        });

        createFrame.add(titleLabel, BorderLayout.NORTH);
        createFrame.add(questionLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        buttonPanel.add(buyButton);
        buttonPanel.add(maintenanceButton);
        createFrame.add(buttonPanel, BorderLayout.SOUTH);

        createFrame.pack();
        createFrame.setSize(600, 200);
        createFrame.setLocationRelativeTo(null);
        createFrame.setVisible(true);
    }

    /**
     * Method that creates the number of slots the vending machine will have
     * @return the number of slots
     */
    private int promptSlotNumber() {
        while (true) {
            String input = JOptionPane.showInputDialog(this,
                    "How many slots should the vending machine contain? (Minimum: 8)");
            try {
                int slotNum = Integer.parseInt(input);
                if (slotNum >= 8) {
                    return slotNum;
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid input. Enter an integer (minimum of 8).");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid input. Enter an integer (minimum of 8).");
            }
        }
    }

    /**
     * Method to display the available itmes from the vending machine
     */
    private void displayAvailableItems() {
        outputTextArea.append("Available Items:\n");
        vendingMachine.displayAvailableItems(outputTextArea);
    }

    /**
     * Creates the keypad used in various functions of the code.
     *
     * @param button The button number indicating the specific action (1 for restock, 2 for setting price, 0 for buying).
     * @param index  The index of the item slot to be processed.
     * @param slot   The array containing the current item slots in the vending machine.
     */
    private void promptKeypad(int button, int index, int[] slot) {
        JFrame createFrame = new JFrame("Vending Machine Keypad");
        createFrame.setLayout(new BorderLayout());

        JLabel questionLabel = new JLabel("Please enter the slot number of the item you wish to select.\n");
        questionLabel.setFont(new Font("Arial", Font.BOLD, 20));
        questionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JTextField screen = new JTextField(10);
        screen.setEditable(false);

        JButton zeroButton = new JButton("0");
        zeroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = e.getActionCommand();
                s0 += s;
                screen.setText(s0);
            }
        });

        JButton oneButton = new JButton("1");
        oneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = e.getActionCommand();
                s0 += s;
                screen.setText(s0);
            }
        });

        JButton twoButton = new JButton("2");
        twoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = e.getActionCommand();
                s0 += s;
                screen.setText(s0);
            }
        });

        JButton threeButton = new JButton("3");
        threeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = e.getActionCommand();
                s0 += s;
                screen.setText(s0);
            }
        });

        JButton fourButton = new JButton("4");
        fourButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = e.getActionCommand();
                s0 += s;
                screen.setText(s0);
            }
        });

        JButton fiveButton = new JButton("5");
        fiveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = e.getActionCommand();
                s0 += s;
                screen.setText(s0);
            }
        });        

        JButton sixButton = new JButton("6");
        sixButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = e.getActionCommand();
                s0 += s;
                screen.setText(s0);
            }
        });

        JButton sevenButton = new JButton("7");
        sevenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = e.getActionCommand();
                s0 += s;
                screen.setText(s0);
            }
        });

        JButton eightButton = new JButton("8");
        eightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = e.getActionCommand();
                s0 += s;
                screen.setText(s0);
            }
        });

        JButton nineButton = new JButton("9");
        nineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = e.getActionCommand();
                s0 += s;
                screen.setText(s0);
            }
        });

        JButton clearButton = new JButton("C");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = e.getActionCommand();
                s0 = "";
                screen.setText(s0);
            }
        });

        JButton cancelButton = new JButton("Cancel Purchase");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createFrame.dispose();
                boolean cancelled = true;
                promptBuyItem(slot, cancelled);
            }
        });

        JButton enterButton = new JButton("E");
        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createFrame.dispose();
                String s = e.getActionCommand();
                screen.setText(s0);
                
                if (button == 1) {
                    int slotNumber = Integer.parseInt(s0);
                    int stock = Integer.parseInt(JOptionPane.showInputDialog(GUI.this, "Enter the quantity to restock:"));
                    vendingMachine.restock(slotNumber, stock);
                    s0 = "";
                }
                else if (button == 2) {
                    int slot = Integer.parseInt(s0);
                    double newPrice = Double.parseDouble(JOptionPane.showInputDialog(GUI.this, "Enter the new price:"));
                    vendingMachine.setPrice(slot, newPrice);
                    s0 = "";
                }
                else {
                    slot[index] = Integer.parseInt(s0);
                    s0 = "";
                    doneBuying(index, slot);
                }
            }
        });

        createFrame.add(questionLabel, BorderLayout.NORTH);

        JPanel buttonPanel1 = new JPanel();
        JPanel buttonPanel2 = new JPanel();
        JPanel buttonPanel3 = new JPanel();
        JPanel buttonPanel4 = new JPanel();
        JPanel buttonPanel5 = new JPanel();
        JPanel buttonPanel6 = new JPanel();
        JPanel buttonPanel = new JPanel(new GridLayout(6, 1, 10, 10));
        screen.setPreferredSize(new Dimension(0,30));
        oneButton.setPreferredSize(new Dimension(50,30));
        twoButton.setPreferredSize(new Dimension(50,30));
        threeButton.setPreferredSize(new Dimension(50,30));
        fourButton.setPreferredSize(new Dimension(50,30));
        fiveButton.setPreferredSize(new Dimension(50,30));
        sixButton.setPreferredSize(new Dimension(50,30));
        sevenButton.setPreferredSize(new Dimension(50,30));
        eightButton.setPreferredSize(new Dimension(50,30));
        nineButton.setPreferredSize(new Dimension(50,30));
        clearButton.setPreferredSize(new Dimension(50,30));
        zeroButton.setPreferredSize(new Dimension(50,30));
        enterButton.setPreferredSize(new Dimension(50,30));
        buttonPanel1.add(screen);
        buttonPanel2.add(oneButton);
        buttonPanel2.add(twoButton);
        buttonPanel2.add(threeButton);
        buttonPanel3.add(fourButton);
        buttonPanel3.add(fiveButton);
        buttonPanel3.add(sixButton);
        buttonPanel4.add(sevenButton);
        buttonPanel4.add(eightButton);
        buttonPanel4.add(nineButton);
        buttonPanel5.add(clearButton);
        buttonPanel5.add(zeroButton);
        buttonPanel5.add(enterButton);
        buttonPanel6.add(cancelButton);
        buttonPanel.add(buttonPanel1);
        buttonPanel.add(buttonPanel2);
        buttonPanel.add(buttonPanel3);
        buttonPanel.add(buttonPanel4);
        buttonPanel.add(buttonPanel5);
        buttonPanel.add(buttonPanel6);
        createFrame.add(buttonPanel, BorderLayout.SOUTH);

        createFrame.pack();
        createFrame.setSize(300, 400);
        createFrame.setLocationRelativeTo(null);
        createFrame.setVisible(true);
    }

    private void donePaying() {
        JFrame createFrame = new JFrame("Finish Paying");
        createFrame.setLayout(new BorderLayout());

        JLabel questionLabel = new JLabel("Done paying?\n");
        questionLabel.setFont(new Font("Arial", Font.BOLD, 20));
        questionLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton yesButton = new JButton("YES");
        yesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createFrame.dispose();
                int button = 0;
                int index = 0;
                int[] slot = new int[5];
                promptKeypad(button, index, slot);
            }
        });
    
        
        JButton noButton = new JButton("NO");
        noButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createFrame.dispose();
                int button = 0;
                promptPayment(button);
            }
        });

        createFrame.add(questionLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        buttonPanel.add(yesButton);
        buttonPanel.add(noButton);
        createFrame.add(buttonPanel, BorderLayout.SOUTH);

        createFrame.pack();
        createFrame.setSize(600, 200);
        createFrame.setLocationRelativeTo(null);
        createFrame.setVisible(true);
    }

    private boolean hasAllIngredients(List<String> requiredIngredients) {
        List<String> boughtItemNames = new ArrayList<>();
        
        for (ItemSlot item : boughtItems) {
            boughtItemNames.add(item.getName().toLowerCase());
        }
        
        for (String ingredient : requiredIngredients) {
            if (!boughtItemNames.contains(ingredient)) {
                return false;
            }
        }
    
        return true;
    }

    /**
     * Method to display the procedures conducted in making a silog meal
     * @param mealName    the name of the silog meal to be used
     * @param ingredients the list of ingredients to be displayed
     */
    private void displayCookingProcess(String mealName, List<String> ingredients) {
        StringBuilder cookingProcess = new StringBuilder();
        for (String ingredient : ingredients) {
            if(ingredient.equalsIgnoreCase("ketchup")) {
                cookingProcess.append("Pouring ").append(ingredient).append("...\n");
            } 

            if(ingredient.equalsIgnoreCase("mayonnaise")) {
                cookingProcess.append("Pouring ").append(ingredient).append("...\n");
            } 

            if(!ingredient.equalsIgnoreCase("ketchup") && !ingredient.equalsIgnoreCase("mayonnaise")) {
                cookingProcess.append("Cooking ").append(ingredient).append("...\n");
            }
        }
        cookingProcess.append("Assembling...\n");
        cookingProcess.append("Enjoy your ").append(mealName).append("!");
        
        JOptionPane.showMessageDialog(null, cookingProcess.toString());
    }
    
    /**
     * Method to facilitate the items after being bought
     * @param index  the index of the item or group of items to be bought
     * @param slot   the array containing the current item slots in the vending machine
     */
    private void doneBuying(int index, int[] slot) {
        JFrame createFrame = new JFrame("Finish Buying");
        createFrame.setLayout(new BorderLayout());
    
        JLabel questionLabel = new JLabel("Done buying?\n");
        questionLabel.setFont(new Font("Arial", Font.BOLD, 20));
        questionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        boughtItems.add(itemSlots[slot[index]-1]);
    
        JButton yesButton = new JButton("YES");
        yesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createFrame.dispose();

                if (isSpecial) {
                    if (boughtItems.size() == 1 && (boughtItems.get(0).getName().equals("ketchup") || boughtItems.get(0).getName().equals("mayonnaise"))) {
                        JOptionPane.showMessageDialog(null, "Transaction rejected. You cannot buy ONLY ketchup or mayonnaise.", "Transaction Rejected", JOptionPane.ERROR_MESSAGE);
                        boughtItems.clear();
                        return;
                    } else {
                        List<String> requiredIngredientsHotsilog = Arrays.asList("rice", "hotdog", "egg", "ketchup", "mayonnaise");
                        List<String> requiredIngredientsTapasilog = Arrays.asList("rice", "tapa", "egg");
                        List<String> requiredIngredientsBacsilog = Arrays.asList("rice", "bacon", "egg");
                        List<String> requiredIngredientsLongsilog = Arrays.asList("rice", "longganisa", "egg");
                        List<String> requiredIngredientsSpamsilog = Arrays.asList("rice", "spam", "egg");
                        List<String> requiredIngredientsTosilog = Arrays.asList("rice", "tocino", "egg");
                        List<String> requiredIngredientsAdobosilog = Arrays.asList("rice", "adobo", "egg");
                        List<String> requiredIngredientsDaingsilog = Arrays.asList("rice", "daing", "egg");
                        List<String> requiredIngredientsCornsilog = Arrays.asList("rice", "corned beef", "egg");
        
                        if (hasAllIngredients(requiredIngredientsHotsilog)) {
                            displayCookingProcess("Hotsilog", Arrays.asList("rice", "hotdog", "egg", "ketchup", "mayonnaise"));
                            createdMeals.add("Hotsilog");
                        }
        
                        if (hasAllIngredients(requiredIngredientsTapasilog)) {
                            displayCookingProcess("Tapasilog", Arrays.asList("rice", "tapa", "egg"));
                            createdMeals.add("Tapsilog");
                        }
        
                        if (hasAllIngredients(requiredIngredientsBacsilog)) {
                            displayCookingProcess("Bacsilog", Arrays.asList("rice", "bacon", "egg"));
                            createdMeals.add("Bacsilog");
                        }
        
                        if (hasAllIngredients(requiredIngredientsLongsilog)) {
                            displayCookingProcess("Longsilog", Arrays.asList("rice", "longganisa", "egg"));
                            createdMeals.add("Longsilog");
                        }
        
                        if (hasAllIngredients(requiredIngredientsSpamsilog)) {
                            displayCookingProcess("Spamsilog", Arrays.asList("rice", "spam", "egg"));
                            createdMeals.add("Spamsilog");
                        }
        
                        if (hasAllIngredients(requiredIngredientsTosilog)) {
                            displayCookingProcess("Tosilog", Arrays.asList("rice", "tocino", "egg"));
                            createdMeals.add("Tosilog");
                        }
        
                        if (hasAllIngredients(requiredIngredientsAdobosilog)) {
                            displayCookingProcess("Adobosilog", Arrays.asList("rice", "adobo", "egg"));
                            createdMeals.add("Adobosilog");
                        }
        
                        if (hasAllIngredients(requiredIngredientsDaingsilog)) {
                            displayCookingProcess("Daingsilog", Arrays.asList("rice", "daing", "egg"));
                            createdMeals.add("Daingsilog");
                        }
        
                        if (hasAllIngredients(requiredIngredientsCornsilog)) {
                            displayCookingProcess("Cornsilog", Arrays.asList("rice", "corned beef", "egg"));
                            createdMeals.add("Cornsilog");
                        }

                        for (ItemSlot item : boughtItems) {
                            totalCalories += item.getCalories();
                        }

                        if (!createdMeals.isEmpty()) {
                            StringBuilder message = new StringBuilder("Enjoy your meals:\n");
                            for (String meal : createdMeals) {
                                message.append(meal).append("\n");
                            }
                            message.append("Total calories: " + totalCalories);
                            JOptionPane.showMessageDialog(null, message.toString());
                        }
                    }

                    boughtItems.clear();
                }
                boolean cancelled = false;
                promptBuyItem(slot, cancelled);
            }
        });
        
        JButton noButton = new JButton("NO");
        noButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createFrame.dispose();
                int button = 0;
                int indeX = vendingMachine.incrementIndex(index);
                promptKeypad(button, indeX, slot);
            }
        });
    
        createFrame.add(questionLabel, BorderLayout.NORTH);
    
        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        buttonPanel.add(yesButton);
        buttonPanel.add(noButton);
        createFrame.add(buttonPanel, BorderLayout.SOUTH);
    
        createFrame.pack();
        createFrame.setSize(600, 200);
        createFrame.setLocationRelativeTo(null);
        createFrame.setVisible(true);
    }  

    /**
     * Prompts the user to buy an item from the vending machine and handles the payment and change.
     * @param slot      The array containing the current item slots in the vending machine.
     * @param cancelled A boolean flag indicating if the purchase was cancelled by the user.
     */
    private void promptBuyItem(int[] slot, boolean cancelled) {
        double payment = value;
        double bill = 0;
        int fail = 1;
        s0 = "";
        boolean valid = false;
        do {
            valid = vendingMachine.isValid(payment, slot);
            if (!valid) {
                int choice = JOptionPane.showConfirmDialog(this, "Invalid payment. Do you want to try again?",
                        "Invalid Payment", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.NO_OPTION) {
                    cancelled = true;
                    break;
                }
            }
        } while (!valid);
    
        if (!cancelled) 
            bill = vendingMachine.getBill(bill, slot);
        else fail = 2;
        double change = Math.round((payment - bill) * 100.0) / 100.0;
        int[][] changeValues = vendingMachine.convertToDenominations(change);
        int[][] paymentValues = vendingMachine.convertToDenominations(payment);
        vendingMachine.getChange(change, payment, slot, changeValues, paymentValues, cancelled, fail);
        
        outputTextArea.append("\n");
    }

    /**
     * Prompts the user to select a payment method and handles the replenishment of change or payment for the vending machine.
     * @param button An integer indicating the context of the payment prompt (1 for replenishing change, 2 for payment).
     */
    private void promptPayment(int button) {
        JFrame createFrame = new JFrame("Select Denomination");
        createFrame.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Payment Method");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel questionLabel = new JLabel("Please select your payment method.\n");
        questionLabel.setFont(new Font("Arial", Font.BOLD, 20));
        questionLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton cent1Button = new JButton("P0.01");
        JButton cent5Button = new JButton("P0.05");
        JButton cent25Button = new JButton("P0.25");
        JButton peso1Button = new JButton("P1.00");
        JButton peso5Button = new JButton("P5.00");
        JButton peso10Button = new JButton("P10.00");
        JButton peso20Button = new JButton("P20.00");
        JButton peso50Button = new JButton("P50.00");
        JButton peso100Button = new JButton("P100.00");
        JButton peso200Button = new JButton("P200.00");
        JButton peso500Button = new JButton("P500.00");
        JButton peso1000Button = new JButton("P1000.00");

        cent1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createFrame.dispose();
                int slot = 0;
                
                if (button == 1) {
                    int quantity = Integer.parseInt(JOptionPane.showInputDialog(GUI.this, "Enter the quantity to replenish:"));
                    vendingMachine.replenishChange(quantity, slot);
                }
                else {
                    value += vendingMachine.receivePayment(slot);
                    donePaying();
                }
            }
        });

        cent5Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createFrame.dispose();
                int slot = 1;
                
                if (button == 1) {
                    int quantity = Integer.parseInt(JOptionPane.showInputDialog(GUI.this, "Enter the quantity to replenish:"));
                    vendingMachine.replenishChange(quantity, slot);
                }
                else {
                    value += vendingMachine.receivePayment(slot);
                    donePaying();
                }
            }
        });

        cent25Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createFrame.dispose();
                int slot = 2;
                
                if (button == 1) {
                    int quantity = Integer.parseInt(JOptionPane.showInputDialog(GUI.this, "Enter the quantity to replenish:"));
                    vendingMachine.replenishChange(quantity, slot);
                }
                else {
                    value += vendingMachine.receivePayment(slot);
                    donePaying();
                }
            }
        });

        peso1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createFrame.dispose();
                int slot = 3;
                
                if (button == 1) {
                    int quantity = Integer.parseInt(JOptionPane.showInputDialog(GUI.this, "Enter the quantity to replenish:"));
                    vendingMachine.replenishChange(quantity, slot);
                }
                else {
                    value += vendingMachine.receivePayment(slot);
                    donePaying();
                }
            }
        });

        peso5Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createFrame.dispose();
                int slot = 4;
                
                if (button == 1) {
                    int quantity = Integer.parseInt(JOptionPane.showInputDialog(GUI.this, "Enter the quantity to replenish:"));
                    vendingMachine.replenishChange(quantity, slot);
                }
                else {
                    value += vendingMachine.receivePayment(slot);
                    donePaying();
                }
            }
        });

        peso10Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createFrame.dispose();
                int slot = 5;
                
                if (button == 1) {
                    int quantity = Integer.parseInt(JOptionPane.showInputDialog(GUI.this, "Enter the quantity to replenish:"));
                    vendingMachine.replenishChange(quantity, slot);
                }
                else {
                    value += vendingMachine.receivePayment(slot);
                    donePaying();
                }
            }
        });

        peso20Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createFrame.dispose();
                int slot = 6;
                
                if (button == 1) {
                    int quantity = Integer.parseInt(JOptionPane.showInputDialog(GUI.this, "Enter the quantity to replenish:"));
                    vendingMachine.replenishChange(quantity, slot);
                }
                else {
                    value += vendingMachine.receivePayment(slot);
                    donePaying();
                }
            }
        });

        peso50Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createFrame.dispose();
                int slot = 7;
                
                if (button == 1) {
                    int quantity = Integer.parseInt(JOptionPane.showInputDialog(GUI.this, "Enter the quantity to replenish:"));
                    vendingMachine.replenishChange(quantity, slot);
                }
                else {
                    value += vendingMachine.receivePayment(slot);
                    donePaying();
                }
            }
        });

        peso100Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createFrame.dispose();
                int slot = 8;
                
                if (button == 1) {
                    int quantity = Integer.parseInt(JOptionPane.showInputDialog(GUI.this, "Enter the quantity to replenish:"));
                    vendingMachine.replenishChange(quantity, slot);
                }
                else {
                    value += vendingMachine.receivePayment(slot);
                    donePaying();
                }
            }
        });

        peso200Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createFrame.dispose();
                int slot = 9;
                
                if (button == 1) {
                    int quantity = Integer.parseInt(JOptionPane.showInputDialog(GUI.this, "Enter the quantity to replenish:"));
                    vendingMachine.replenishChange(quantity, slot);
                }
                else {
                    value += vendingMachine.receivePayment(slot);
                    donePaying();
                }
            }
        });

        peso500Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createFrame.dispose();
                int slot = 10;
                
                if (button == 1) {
                    int quantity = Integer.parseInt(JOptionPane.showInputDialog(GUI.this, "Enter the quantity to replenish:"));
                    vendingMachine.replenishChange(quantity, slot);
                }
                else {
                    value += vendingMachine.receivePayment(slot);
                    donePaying();
                }
            }
        });

        peso1000Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createFrame.dispose();
                int slot = 11;
                
                if (button == 1) {
                    int quantity = Integer.parseInt(JOptionPane.showInputDialog(GUI.this, "Enter the quantity to replenish:"));
                    vendingMachine.replenishChange(quantity, slot);
                }
                else {
                    value += vendingMachine.receivePayment(slot);
                    donePaying();
                }
            }
        });

        createFrame.add(titleLabel, BorderLayout.NORTH);
        createFrame.add(questionLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        buttonPanel.add(cent1Button);
        buttonPanel.add(cent5Button);
        buttonPanel.add(cent25Button);
        buttonPanel.add(peso1Button);
        buttonPanel.add(peso5Button);
        buttonPanel.add(peso10Button);
        buttonPanel.add(peso20Button);
        buttonPanel.add(peso50Button);
        buttonPanel.add(peso100Button);
        buttonPanel.add(peso200Button);
        buttonPanel.add(peso500Button);
        buttonPanel.add(peso1000Button);
        createFrame.add(buttonPanel, BorderLayout.SOUTH);

        createFrame.pack();
        createFrame.setSize(600, 200);
        createFrame.setLocationRelativeTo(null);
        createFrame.setVisible(true);
    }

    /**
     * Prompts the user to perform the various maintenance functions available
     */
    private void promptMaintenance() {
        JFrame createFrame = new JFrame("Peform maintenance");
        createFrame.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Maintenance");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel questionLabel = new JLabel("What type of maintenance would you like to perform?\n");
        questionLabel.setFont(new Font("Arial", Font.BOLD, 20));
        questionLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton restockButton = new JButton("Restock");
        restockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createFrame.dispose();
                vendingMachine.summaryOfTransactions();
                int button = 1;
                promptKeypad(button, -1, null);
            }
        });

        JButton setPriceButton = new JButton("Set price");
        setPriceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createFrame.dispose();
                displayAvailableItems();
                int button = 2;
                promptKeypad(button, -1, null);
            }
        });

        JButton collectButton = new JButton("Collect money");
        collectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createFrame.dispose();
                vendingMachine.collectMoney();
            }
        });

        JButton replenishButton = new JButton("Replenish change");
        replenishButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createFrame.dispose();
                displayDenominations();
                int button = 1;
                promptPayment(button);
            }
        });

        JButton inventoryButton = new JButton("Compare inventories");
        inventoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createFrame.dispose();
                vendingMachine.compareInventory(itemSlots);
            }
        });

        createFrame.add(titleLabel, BorderLayout.NORTH);
        createFrame.add(questionLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        buttonPanel.add(restockButton);
        buttonPanel.add(setPriceButton);
        buttonPanel.add(collectButton);
        buttonPanel.add(replenishButton);
        buttonPanel.add(inventoryButton);
        createFrame.add(buttonPanel, BorderLayout.SOUTH);

        createFrame.pack();
        createFrame.setSize(600, 200);
        createFrame.setLocationRelativeTo(null);
        createFrame.setVisible(true);
    }

    /**
     * Displays the denominations
     */
    private void displayDenominations() {
        outputTextArea.setText("");
        outputTextArea.append("Please select the denomination you wish to replenish:\n");
        vendingMachine.displayDenominations(4);
    }

}
