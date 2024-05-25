import java.util.Scanner;
import java.util.InputMismatchException;

public class FastFoodKitchenDriver {

    public static void main(String[] args) {

        // Create an instance of the FastFoodKitchen class
        FastFoodKitchen kitchen = new FastFoodKitchen();

        // Create a Scanner object for user input
        Scanner sc = new Scanner(System.in);

        try {
            // Continue processing until there are no pending orders
            while (kitchen.getNumOrdersPending() != 0) {
                // Display menu options
                System.out.println("Please select from the following menu of options, by typing a number:");
                System.out.println("\t 1. Order food");
                System.out.println("\t 2. Cancel last order");
                System.out.println("\t 3. Show the number of orders currently pending");
                System.out.println("\t 4. Complete order");
                System.out.println("\t 5. Check on order");
                System.out.println("\t 6. Cancel order");
                System.out.println("\t 7. Exit");

                int num = 0;

                try {
                    // Get the user's choice
                    num = sc.nextInt();
                } catch (InputMismatchException e) {
                    // Handle invalid input (non-integer)
                    System.out.println("Invalid input. Please enter a valid number.");
                    sc.nextLine(); // consume the invalid input
                    continue; // skip the rest of the loop and start over
                }

                // Process the user's choice using a switch statement
                switch (num) {
                    case 1:
                        // Order food: Get details from the user and add the order to the kitchen
                        System.out.println("How many hamburgers do you want?");
                        int ham = sc.nextInt();
                        System.out.println("How many cheeseburgers do you want?");
                        int cheese = sc.nextInt();
                        System.out.println("How many veggieburgers do you want?");
                        int veggie = sc.nextInt();
                        System.out.println("How many sodas do you want?");
                        int sodas = sc.nextInt();
                        System.out.println("Is your order to go? (Y/N)");
                        char letter = sc.next().charAt(0);
                        boolean TOGO = false;
                        if (letter == 'Y' || letter == 'y') {
                            TOGO = true;
                        }
                        int orderNum = kitchen.addOrder(ham, cheese, veggie, sodas, TOGO);
                        System.out.println("Thank you. Your order number is " + orderNum);
                        System.out.println();
                        break;
                    case 2:
                        // Cancel last order: Attempt to cancel the last order in the kitchen
                        boolean ready = kitchen.cancelLastOrder();
                        if (ready) {
                            System.out.println("Thank you. The last order has been canceled");
                        } else {
                            System.out.println("Sorry. There are no orders to cancel.");
                        }
                        System.out.println();
                        break;
                    case 3:
                        // Show the number of orders currently pending
                        System.out.println("There are " + kitchen.getNumOrdersPending() + " pending orders");
                        break;
                    case 4:
                        // Complete order: Mark a specific order as completed
                        System.out.println("Enter order number to complete?");
                        int order = sc.nextInt();
                        kitchen.completeSpecificOrder(order);
                        System.out.println("Your order is ready. Thank you!");
                        break;
                    case 5:
                        // Check on order: Check if a specific order is done
                        System.out.println("What is your order number?");
                        order = sc.nextInt();
                        ready = kitchen.isOrderDone(order);
                        if (ready) {
                            System.out.println("Sorry, no order with this number was found.");
                        } else {
                            System.out.println("No, it's not ready, but it should be up soon. Sorry for the wait.");
                        }
                        System.out.println();
                        break;
                    case 6:
                        // Cancel order: Cancel a specific order
                        System.out.println("What is your order number?");
                        order = sc.nextInt();
                        boolean cancel = kitchen.cancelOrder(order);
                        if (cancel) {
                            System.out.println("Your order has been successfully cancelled ");
                        } else {
                            System.out.println("Sorry, we can't find your order number in the system");
                        }
                        System.out.println();
                        break;
                    case 7:
                        // Exit: Write orders to a CSV file and generate an end-of-day report before exiting
                        System.out.println("System Exiting... Please check the receipt file.");
                        kitchen.writeOrdersToCSV("orders2.csv");
                        kitchen.generateEndDayReport("EndOfDayReport.txt");
                        System.exit(0);
                        break;
                    default:
                        // Handle invalid menu choices
                        System.out.println("Sorry, but you need to enter a 1, 2, 3, 4, 5, 6, or a 7");
                } // end switch
            } // end while loop
        } catch (Exception e) {
            // Handle unexpected errors
            System.out.println("An unexpected error occurred: " + e.getMessage());
        } finally {
            // Close the Scanner to prevent resource leaks
            sc.close();
        }

    } // end main
}
