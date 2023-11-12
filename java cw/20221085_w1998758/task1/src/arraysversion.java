import java.io.*;
import java.util.Scanner;

public class arraysversion {
    // Define the queues as arrays
    static String[] queue1=new String[2];
    static String[] queue2=new String[2];
    static String[] queue3=new String[2];



    // Additional queues used for displaying 'O' to represent customers
        static String[] queue_1 = {"X","X"};
        static String[] queue_2 = {"X","X","X"};
        static String[] queue_3 = {"X","X","X","X","X"};

    // Function to display the console menu and get user input
        public static String  consolemenu(){
            Scanner sc=new Scanner(System.in);
            System.out.println("100 or VFQ: View all queues");
            System.out.println("101 or VEQ: View all empty queues");
            System.out.println("102 or ACQ: Add customer to a queue");
            System.out.println("103 or RCQ: Remove customer from a queue");
            System.out.println("104 or PCQ: Remove a served customer");
            System.out.println("105 or VCS: View customers sorted in alphabetical order");
            System.out.println("106 or SPD: Store program data into file");
            System.out.println("107 or LPD: Load program data from a file");
            System.out.println("108 or STK: View remaining burger stock");
            System.out.println("109 or AFS: Add burgers to stock");
            System.out.println("999 or EXT: Exit the program");
            System.out.print("Enter the number: ");
            String input= sc.next();
            return input;
        }

    // Function to add a customer to a queue
    public static void addCustomer(String[] queue, String customerName) {
        for (int i = 0; i < queue.length; i++) {
            if (queue[i] == null) {
                queue[i] = customerName;
                break;
            }
        }
    }

    // Function to check if a queue is full
    public static boolean isFullQueue(String[] queue) {

            return queue[queue.length - 1] != null;
    }

    // Function to remove a customer from a queue
    public static void removeCustomer(String[] queue, int position) {
        if (position >= 1 && position <= queue.length) {
            queue[position - 1] = null;
            for (int i = position - 1; i < queue.length - 1; i++) {
                queue[i] = queue[i + 1];
                queue[i + 1] = null;
            }
        } else {
            System.out.println("Invalid position");
        }
    }

    // Function to check if a queue is empty
    public static boolean isEmptyQueue(String[] queue) {

            return queue[0] == null;
    }

    // Function to display customers in alphabetical order
    public static void vcsalphabeticalorder() {
        // Merge all queues into a single array
        String[] allCustomers = new String[queue1.length + queue2.length + queue3.length];
        System.arraycopy(queue1, 0, allCustomers, 0, queue1.length);
        System.arraycopy(queue2, 0, allCustomers, queue1.length, queue2.length);
        System.arraycopy(queue3, 0, allCustomers, queue1.length + queue2.length, queue3.length);
        // checking if there is any customers
        if (allCustomers.length == 0) {
            System.out.println("No customers in the queue");
        } else {
            // Sort the customers in alphabetical order using bubble sort algorithm
            for (int i = 0; i < allCustomers.length - 1; i++) {
                for (int j = 0; j < allCustomers.length - i - 1; j++) {
                    String customer1 = allCustomers[j];
                    String customer2 = allCustomers[j + 1];
                    // Compare customer names and swap if they are out of order
                    if (customer1 != null && customer2 != null && customer1.compareTo(customer2) > 0) {
                        allCustomers[j] = customer2;
                        allCustomers[j + 1] = customer1;
                    }
                }
            }
        }

        System.out.println("Customers in alphabetical order");
        // Print the sorted customers in alphabetical order
        for (String customer : allCustomers) {
            if (customer != null) {
                System.out.println(customer);
            }
        }
    }
    public static void file() {
        try {
            // Create a file object to represent the "storedata.txt" file
            File storedata = new File("storedata.txt");
            // Create a FileWriter object to write to the file
            FileWriter fileWriter = new FileWriter(storedata);
            // Create a BufferedWriter object to write characters to the FileWriter
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            // Write the data for Queue 1
            bufferedWriter.write("Queue 1");
            for (String customer : queue1) {
                // Write each customer's name or "X" if the position is empty
                bufferedWriter.write(customer != null ? customer : "X");
                bufferedWriter.newLine();
            }
            // Write the data for Queue 2
            bufferedWriter.write("Queue 2");
            for (String customer : queue2) {
                // Write each customer's name or "X" if the position is empty
                bufferedWriter.write(customer != null ? customer : "X");
                bufferedWriter.newLine();
            }
            // Write the data for Queue 3
            bufferedWriter.write("Queue 3");
            for (String customer : queue3) {
                // Write each customer's name or "X" if the position is empty
                bufferedWriter.write(customer != null ? customer : "X");
                bufferedWriter.newLine();
            }
            // Close the BufferedWriter to flush any buffered characters and release resources
            bufferedWriter.close();
            System.out.println("Program data stored");
        } catch (IOException e) {
            // Print the stack trace if an exception occurs during file operations
            e.printStackTrace();
        }
    }
    public static void moveCustomers(String[] sourceQueue, String[] targetQueue) {
        // Iterate through the sourceQueue array
            for (int i = 0; i < sourceQueue.length; i++) {
                // Check if the current position in the sourceQueue is not null
                // and the targetQueue is not already full
            if (sourceQueue[i] != null && !isFullQueue(targetQueue)) {
                // Move the customer from the sourceQueue to the targetQueue
                addCustomer(targetQueue, sourceQueue[i]);
                // Set the current position in the sourceQueue to null,
                // indicating that the customer has been moved
                sourceQueue[i] = null;
            }
        }
    }
    public static void clearQueue(String[] queue) {
        for (int i = 0; i < queue.length; i++) {
            queue[i] = null;
        }
    }



    public static void main(String args[]) {
            final int burgers = 50;
            int stock=burgers;
            int servedcust=0;
            String name="";
            int numelementsqueue1=0;
            int numelementsqueue2=0;
            int numelementsqueue3=0;



        while (true){
                String op = consolemenu();
                if (op.equalsIgnoreCase("100") || op.equalsIgnoreCase("VFQ")) {
                    System.out.println("    ***********");
                    System.out.println("    * Cashier *");
                    System.out.println("    ***********");
                    System.out.println(queue_1[0]+"      "+queue_2[0]+"       "+queue_3[0]);
                    System.out.println(queue_1[1]+"      "+queue_2[1]+"       "+queue_3[1]);
                    System.out.println("       "+queue_2[2]+"       "+queue_3[2]);
                    System.out.println("               "+queue_3[3]);
                    System.out.println("               "+queue_3[4]);
                } else if (op.equalsIgnoreCase("101")||op.equalsIgnoreCase("VEQ")) {
                    if (isEmptyQueue(queue1)) {
                        System.out.println("Cashier 1- Queue 1");
                    }
                    if (isEmptyQueue(queue2)) {
                        System.out.println("Cashier 2- Queue 2");
                    }
                    if (isEmptyQueue(queue3)) {
                        System.out.println("Cashier 3- Queue 3");
                    }
                } else if (op.equalsIgnoreCase("102") || op.equalsIgnoreCase("ACQ")) {
                    Scanner scanner = new Scanner(System.in);
                    System.out.print("Enter the queue number: ");
                    int queuenum = scanner.nextInt();

                    if (stock < 5) {
                        System.out.print("Insufficient stock. Cannot add customer: ");
                    } else if (stock <= 10) {
                        System.out.print("Warning!!! Low stock: ");
                    } else if (queuenum < 1 || queuenum > 3) {
                        System.out.println("Invalid queue number. Queue numbers are 1, 2, 3 ");
                    } else {
                        if (queuenum == 1) {
                            if (isFullQueue(queue1)) {
                                System.out.println("Maximum number of customers reached");
                            } else {
                                System.out.print("Enter customer name: ");

                                name = scanner.next();
                                addCustomer(queue1, name);
                                numelementsqueue1++;
                                    for (int i = 0; i < numelementsqueue1; i++) {
                                       queue_1[i]="O";
                                    }

                                System.out.println(name + " added to the queue 1");
                            }
                        } else if (queuenum == 2) {
                            if (isFullQueue(queue2)) {
                                System.out.println("Maximum number of customers reached");
                            } else {
                                System.out.print("Enter customer name: ");
                                name = scanner.next();
                                addCustomer(queue2, name);
                                numelementsqueue2++;
                                for (int i = 0; i < numelementsqueue2; i++) {
                                    queue_2[i]="O";
                                }
                                System.out.println(name + " added to the queue 2");
                            }
                        } else if (queuenum == 3) {
                            if (isFullQueue(queue3)) {
                                System.out.println("Maximum number of customers reached");
                            } else {
                                System.out.print("Enter customer name: ");
                                name = scanner.next();
                                addCustomer(queue3, name);
                                numelementsqueue3++;
                                for (int i = 0; i < numelementsqueue3; i++) {
                                    queue_3[i]="O";
                                }
                                System.out.println(name + " added to the queue 3");
                            }
                        }
                    }
                } else if (op.equalsIgnoreCase("103")||op.equalsIgnoreCase("RCQ")) {
                    Scanner RCQ = new Scanner(System.in);
                    System.out.print("Enter queue number: ");
                    int rcqqueue = RCQ.nextInt();
                    System.out.print("Enter the customer position you want to remove (Starting from 1): ");
                    int position = RCQ.nextInt();
                    if (rcqqueue == 1) {
                        removeCustomer(queue1, position);
                        System.out.println("Customer removed from position " + position);
                    } else if (rcqqueue == 2) {
                        removeCustomer(queue2, position);
                        System.out.println("Customer removed from position " + position);
                    } else if (rcqqueue == 3) {
                        removeCustomer(queue3, position);
                        System.out.println("Customer removed from position " + position);
                    }

                } else if (op.equalsIgnoreCase("104")||op.equalsIgnoreCase("PCQ")) {
                    if (!isEmptyQueue(queue1)) {
                        removeCustomer(queue1, 1);
                        servedcust++;
                    }
                    if (!isEmptyQueue(queue2)) {
                        removeCustomer(queue2, 1);
                        servedcust++;
                    }
                    if (!isEmptyQueue(queue3)) {
                        removeCustomer(queue3, 1);
                        servedcust++;
                    }
                    System.out.println("Served customers are removed from the queue");
                } else if (op.equalsIgnoreCase("105")||op.equalsIgnoreCase("VCS")) {
                    vcsalphabeticalorder();
                } else if (op.equalsIgnoreCase("106")||op.equalsIgnoreCase("SPD")) {
                    file();
                } else if (op.equalsIgnoreCase("107")||op.equalsIgnoreCase("LPD")) {
                    File storedata = new File("storedata.txt");
                    try {
                        FileReader fileReader = new FileReader(storedata);
                        BufferedReader bufferedReader = new BufferedReader(fileReader);
                        String line;

                        // Clear the existing queue data
                        clearQueue(queue1);
                        clearQueue(queue2);
                        clearQueue(queue3);

                        // Read the data from the file and update the queues
                        while ((line = bufferedReader.readLine()) != null) {
                            if (line.startsWith("Queue 1")) {
                                // Skip the "Queue 1" line
                                continue;
                            } else if (line.startsWith("Queue 2")) {
                                // Move to the next queue
                                moveCustomers(queue1, queue2);
                                continue;
                            } else if (line.startsWith("Queue 3")) {
                                // Move to the next queue
                                moveCustomers(queue2, queue3);
                                continue;
                            }

                            // Add the customer to the current queue
                            if (!isFullQueue(queue1)) {
                                addCustomer(queue1, line);
                            } else if (!isFullQueue(queue2)) {
                                addCustomer(queue2, line);
                            } else if (!isFullQueue(queue3)) {
                                addCustomer(queue3, line);
                            }
                        }

                        bufferedReader.close();
                        System.out.println("Program data loaded successfully.");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (op.equalsIgnoreCase("108")||op.equalsIgnoreCase("STK")) {
                    stock = burgers - (servedcust * 5);
                    System.out.println(stock + " burgers remaining");
                } else if (op.equalsIgnoreCase("109")||op.equalsIgnoreCase("AFS")) {
                    Scanner addburgers = new Scanner(System.in);
                    System.out.print("How many burgers do you want to add to the stock? ");
                    int newburgers = addburgers.nextInt();
                    stock += newburgers;
                    System.out.println(newburgers + " added to the stock");
                    System.out.println("New stock is " + stock); 
                } else if (op.equalsIgnoreCase("999")||op.equalsIgnoreCase("EXT")) {
                    System.out.println("Thank you");
                    break;
                }
            }
        }
}



