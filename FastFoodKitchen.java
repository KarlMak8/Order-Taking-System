import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The FastFoodKitchen class models a fast-food kitchen that manages burger orders.
 * It includes methods for reading orders from a file, generating end-of-day reports,
 * managing order lists, completing orders, and sorting orders based on the number of burgers.
 */
public class FastFoodKitchen {

    // Instance variables
    private ArrayList<BurgerOrder> orderList = new ArrayList();
    private ArrayList<BurgerOrder> completedList = new ArrayList();
    private static int nextOrderNum = 1;

    // Constructor: Reads orders from a file when an instance is created
    FastFoodKitchen() {
        readOrdersFromFile("orders.csv");
    }

    // Generates an end-of-day report and writes it to a file
    public void generateEndDayReport(String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            // Writing header and order details for pending orders
            writer.write("-----------------\n");
            writer.write("End of Day Report:\n");
            writer.write("-----------------\n");
            for (BurgerOrder order : orderList) {
                String line =
                        "Order Number: " + order.getOrderNum() +
                                "\nHamburgers: " + order.getNumHamburger() +
                                "\nCheeseburgers: " + order.getNumCheeseburgers() +
                                "\nVeggieburgers: " + order.getNumVeggieburgers() +
                                "\nSodas: " + order.getNumSodas() +
                                "\nTOGO: " + order.isOrderToGo() + "\n\n";
                writer.write(line);
            }

            // Writing header and order details for completed orders
            writer.write("-----------------\n");
            writer.write("Completed Orders:\n");
            writer.write("-----------------\n");
            for (BurgerOrder order : completedList) {
                String line =
                        "Order Number: " + order.getOrderNum() +
                                "\nHamburgers: " + order.getNumHamburger() +
                                "\nCheeseburgers: " + order.getNumCheeseburgers() +
                                "\nVeggieburgers: " + order.getNumVeggieburgers() +
                                "\nSodas: " + order.getNumSodas() + 
                                "\nTOGO: " + order.isOrderToGo() + "\n\n";
                writer.write(line);
            }

        } catch (IOException e) {
            System.out.println("Error writing to CSV file: " + e.getMessage());
        }
    }

    // Reads orders from a CSV file and populates the orderList
    public void readOrdersFromFile(String fileName) {
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);

            // Skip the header row
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            // Reading orders and adding them to the orderList
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] values = line.split(",");

                int ham = Integer.parseInt(values[0]);
                int cheese = Integer.parseInt(values[1]);
                int veggie = Integer.parseInt(values[2]);
                int sodas = Integer.parseInt(values[3]);
                boolean toGo = Boolean.parseBoolean(values[4]);
                int orderNum = addOrder(ham, cheese, veggie, sodas, toGo);
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
        }
    }

    // Writes order details to a CSV file
    public void writeOrdersToCSV(String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            // Write header
            writer.write("numHamburgers,numCheeseburgers,numVeggieburgers,numSodas,orderToGo\n");

            // Write each order
            for (BurgerOrder order : orderList) {
                writer.write(
                        order.getNumHamburger() + "," +
                                order.getNumCheeseburgers() + "," +
                                order.getNumVeggieburgers() + "," +
                                order.getNumSodas() + "," +
                                order.isOrderToGo() + "\n"
                );
            }

            System.out.println("Orders written to " + fileName);
        } catch (IOException e) {
            System.out.println("Error writing to CSV file: " + e.getMessage());
        }
    }

    // Getter for the next order number
    public static int getNextOrderNum() {
        return nextOrderNum;
    }

    // Increment the next order number
    private void incrementNextOrderNum() {
        nextOrderNum++;
    }

    // Adds a new order to the orderList
    public int addOrder(int ham, int cheese, int veggie, int soda, boolean toGo) {
        int orderNum = getNextOrderNum();
        orderList.add(new BurgerOrder(ham, cheese, veggie, soda, toGo, orderNum));
        incrementNextOrderNum();
        return orderNum;
    }

    // Checks if a specific order is done based on the order ID
    public boolean isOrderDone(int orderID) {
        for (int i = 0; i < orderList.size(); i++) {
            if (orderList.get(i).getOrderNum() == orderID) {
                return false;
            }
        }
        return true;
    }

    // Cancels a specific order based on the order ID
    public boolean cancelOrder(int orderID) {
        for (int i = 0; i < orderList.size(); i++) {
            if (orderList.get(i).getOrderNum() == orderID) {
                orderList.remove(i);
                return true;
            }
        }
        return false;
    }

    // Returns the number of pending orders
    public int getNumOrdersPending() {
        return orderList.size();
    }

    // Cancels the last order in the orderList
    public boolean cancelLastOrder() {
        if (!orderList.isEmpty()) { // same as  if (orderList.size() > 0) 
            orderList.remove(orderList.size() - 1);
            return true;
        }
        return false;
    }

    // Helper method to print out the details of a specific order
    private void orderCallOut(BurgerOrder order) {
        if (order.getNumCheeseburgers() > 0) {
            System.out.println("You have " + order.getNumHamburger() + " hamburgers");
        }
        if (order.getNumCheeseburgers() > 0) {
            System.out.println("You have " + order.getNumCheeseburgers() + " cheeseburgers");
        }
        if (order.getNumVeggieburgers() > 0) {
            System.out.println("You have " + order.getNumVeggieburgers() + " veggieburgers");
        }
        if (order.getNumSodas() > 0) {
            System.out.println("You have " + order.getNumSodas() + " sodas");
        }
    }

    // Completes a specific order based on the order ID
    public void completeSpecificOrder(int orderID) {
        for (int i = 0; i < orderList.size(); i++) {
            if (orderList.get(i).getOrderNum() == orderID) {
                System.out.println("Order number " + orderID + " is done!");
                if (orderList.get(i).isOrderToGo()) {
                    orderCallOut(orderList.get(i));
                }
                completedList.add(orderList.get(i));
                orderList.remove(i);
            }
        }
    }

    // Completes the next order in the orderList
    public void completeNextOrder() {
        int nextOrder = orderList.get(0).getOrderNum();
        completeSpecificOrder(nextOrder);
    }

    // Part 2

    // Getter for the orderList
    public ArrayList<BurgerOrder> getOrderList() {
        return orderList;
    }

    // Finds the index of a specific order in the orderList using sequential search
    public int findOrderSeq(int whatWeAreLookingFor) {
        for (int j = 0; j < orderList.size(); j++) {
            if (orderList.get(j).getOrderNum() == whatWeAreLookingFor) {
                return j;
            }
        }
        return -1;
    }

    // Finds the index of a specific order in the orderList using binary search
    public int findOrderBin(int orderID) {
        int left = 0;
        int right = orderList.size() - 1;
        while (left <= right) {
            int middle = ((left + right) / 2);
            if (orderID < orderList.get(middle).getOrderNum()) {
                right = middle - 1;
            } else if (orderID > orderList.get(middle).getOrderNum()) {
                left = middle + 1;
            } else {
                return middle;
            }
        }
        return -1;
    }

    // Performs selection sort on the orderList based on the total number of burgers
    public void selectionSort() {
        for (int i = 0; i < orderList.size() - 1; i++) {
            int minIndex = i;
            for (int k = i + 1; k < orderList.size(); k++) {
                if (orderList.get(minIndex).getTotalBurgers() > orderList.get(k).getTotalBurgers()) {
                    minIndex = k;
                }
            }
            BurgerOrder temp = orderList.get(i);
            orderList.set(i, orderList.get(minIndex));
            orderList.set(minIndex, temp);
        }
    }

    // Performs insertion sort on the orderList based on the total number of burgers
    public void insertionSort() {
        for (int j = 1; j < orderList.size(); j++) {
            BurgerOrder temp = orderList.get(j);
            int possibleIndex = j;
            while (possibleIndex > 0 && temp.getTotalBurgers() < orderList.get(possibleIndex - 1).getTotalBurgers()) {
                orderList.set(possibleIndex, orderList.get(possibleIndex - 1));
                possibleIndex--;
            }
            orderList.set(possibleIndex, temp);
        }
    }
}
