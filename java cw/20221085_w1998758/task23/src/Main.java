import java.io.*;
import java.util.Scanner;

public class Main {
    static Scanner input = new Scanner(System.in); // Declare the input object
    static FoodQueue waitingListQueue;
    static FoodQueue[] queues = new FoodQueue[3];
    static  int burgers = 50;
    static final int BURGER_PRICE = 650;
    static String[] queue_1 = {"X","X"};
    static String[] queue_2 = {"X","X","X"};
    static String[] queue_3 = {"X","X","X","X","X"};
    static int numcustomersq1=0;
    static int numcustomersq2=0;
    static int numcustomersq3=0;
    static class Customer implements Comparable<Customer> {
        private String firstName;
        private String lastName;
        public int burgersNeed;

        public Customer(String firstName, String lastName,int burgersNeed) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.burgersNeed=burgersNeed;
        }

        public String getFullName() {
            return firstName + " " + lastName;
        }
        public int getBurgersNeed() {
            return burgersNeed;
        }


        @Override
        public int compareTo(Customer other) {
            return this.getFullName().compareTo(other.getFullName());
        }
    }
    static class FoodQueue {
        public Customer[] customers;
        public int size;

        public FoodQueue(int capacity) {
            customers = new Customer[capacity];
            size = 0;
        }

        public void addqueue(Customer customer) {
            if (size <= customers.length) {
                customers[size] = customer;
                size++;
            }
        }

        public Customer removequeue(int index) {
            Customer removedCustomer = customers[index];

            for (int i = index; i < size - 1; i++) {
                customers[i] = customers[i + 1];
            }

            customers[size - 1] = null;
            size--;

            return removedCustomer;
        }

        public int size() {
            return size;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public String getCustomerName(int index) {
            if (index >= 0 && index < size) {
                return customers[index].getFullName();
            }
            return "";
        }

        public int copyCustomersToArray(Customer[] arr, int startIndex) {
            int count = 0;
            for (int i = 0; i < size; i++) {
                arr[startIndex + count] = customers[i];
                count++;
            }
            return count;
        }
    }
    public static void initializeQueues() {
        queues[0] = new FoodQueue(2);
        queues[1] = new FoodQueue(3);
        queues[2] = new FoodQueue(5);
        waitingListQueue=new FoodQueue(10);
    }
    public static String  consolemenu(){
        Scanner input=new Scanner(System.in);
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
        System.out.println("110 or IFQ: View incomes of each queue");
        System.out.println("999 or EXT: Exit the program");
        System.out.print("Enter the number: ");
        return input.next();
    }
    public static void viewEmptyQueues() {
        for (int i = 0; i < queues.length; i++) {
            if (queues[i].isEmpty()) {
                System.out.println("Queue " + (i + 1) + " is empty");
            }
        }
    }

    public static int findMinQueueIndex() {
        int minQueueIndex = -1;
        int minLength = Integer.MAX_VALUE;

        for (int i = 0; i < queues.length; i++) {
            if (queues[i].size() < minLength) {
                minLength = queues[i].size();
                minQueueIndex = i;
            }
        }

        if (minLength==2 && minQueueIndex==0){
            if (queues[1].size==3) {
                minQueueIndex=minQueueIndex + 2;
            }else if(queues[0].size==2){
                minQueueIndex=minQueueIndex + 1;
            }
        }

        return minQueueIndex;
    }



    public static void addCustomerToQueue(Scanner input) {
        if (burgers <= 5) {
            System.out.println("Insufficient stock. Cannot add customer.");
            return;
        }

        int minQueueIndex = findMinQueueIndex();


        System.out.print("Enter customer first name: ");
        String firstName = input.next();
        System.out.print("Enter customer last name: ");
        String lastName = input.next();
        System.out.print("Enter how many burgers you need: ");
        int burgersNeed = input.nextInt();

        if (minQueueIndex < 0 || minQueueIndex >= queues.length) {
            System.out.println("Invalid queue index.");
            return;
        }

        FoodQueue selectedQueue = queues[minQueueIndex];
        int remainingCapacity = selectedQueue.customers.length - selectedQueue.size();

        if (minQueueIndex == 0 && selectedQueue.size() >= 2) {
            // Queue 1 is full, add to Queue 2 or Queue 3
            if (queues[1].size() < queues[2].size()) {
                selectedQueue = queues[1];
                minQueueIndex = 1;
            } else {
                selectedQueue = queues[2];
                minQueueIndex = 2;
            }
            remainingCapacity = selectedQueue.customers.length - selectedQueue.size();
        }




        if (remainingCapacity > 0) {
            selectedQueue.addqueue(new Customer(firstName, lastName, burgersNeed));
            burgers -= burgersNeed; // Deduct burgersNeed from the total burgers

            if (minQueueIndex == 0) {
                numcustomersq1++;
                for (int i = 0; i < numcustomersq1; i++) {
                    queue_1[i] = "O";
                }
            } else if (minQueueIndex == 1) {
                numcustomersq2++;
                for (int i = 0; i < numcustomersq2; i++) {
                    queue_2[i] = "O";
                }
            } else if (minQueueIndex == 2) {
                numcustomersq3++;
                for (int i = 0; i < numcustomersq3; i++) {
                    queue_3[i] = "O";
                }
            }

            System.out.println(firstName + " " + lastName + " added to Queue " + (minQueueIndex + 1));
        } else {
                System.out.println("All queues are full. Adding customer to the waiting list.");
                System.out.print("Enter customer first name: ");
                firstName = input.next();
                System.out.print("Enter customer last name: ");
                 lastName = input.next();
                System.out.print("Enter how many burgers you need: ");
                burgersNeed = input.nextInt();
                waitingListQueue.addqueue(new Customer(firstName, lastName, burgersNeed));
                System.out.println(firstName + " " + lastName + " added to the waiting list.");


        }
    }




    public static void removeCustomerFromQueue(Scanner input) {
        System.out.print("Enter the queue number: ");
        int queueNumber = input.nextInt();

        if (queueNumber < 1 || queueNumber > queues.length) {
            System.out.println("Invalid queue number. Queue numbers are 1, 2, 3.");
            return;
        }

        FoodQueue queue = queues[queueNumber - 1];

        if (queue.isEmpty()) {
            System.out.println("Queue " + queueNumber + " is empty. Cannot remove customer.");
            return;
        }

        System.out.print("Enter the customer index to remove: ");
        int customerIndex = input.nextInt();

        if (customerIndex < 0 || customerIndex >= queue.size()) {
            System.out.println("Invalid customer index.");
            return;
        }


        Customer removedCustomer = queue.removequeue(customerIndex);
        burgers += removedCustomer.getBurgersNeed();

        System.out.println("Removed customer: " + removedCustomer.getFullName());
        if (queueNumber == 1) {
            queue_1[customerIndex] = "X";
        } else if (queueNumber == 2) {
            queue_2[customerIndex] = "X";
        } else if (queueNumber == 3) {
            queue_3[customerIndex] = "X";
        }

    }
    private static void removeServedCustomer() {
        for (FoodQueue queue : queues) {
            if (!queue.isEmpty()) {
                Customer servedCustomer = queue.removequeue(0);
                System.out.println("Removed served customer: " + servedCustomer.getFullName());
                if (!waitingListQueue.isEmpty()){
                    Customer nextCustomerWaiting=waitingListQueue.removequeue(0);
                    queue.addqueue(nextCustomerWaiting);
                    System.out.println("Next customer in the waiting list added to the queue.");
                }
                return;
            }
        }
        System.out.println("No served customer found. All queues are empty.");
    }
    private static void viewCustomersSorted() {
        Customer[] sortedCustomers = new Customer[burgers];

        int count = 0;
        for (FoodQueue queue : queues) {
            count += queue.copyCustomersToArray(sortedCustomers, count);
        }

        if (count == 0) {
            System.out.println("No customers found.");
            return;
        }

        // Bubble sort
        for (int i = 0; i < count - 1; i++) {
            for (int j = 0; j < count - i - 1; j++) {
                if (sortedCustomers[j].compareTo(sortedCustomers[j + 1]) > 0) {
                    Customer temp = sortedCustomers[j];
                    sortedCustomers[j] = sortedCustomers[j + 1];
                    sortedCustomers[j + 1] = temp;
                }
            }
        }

        System.out.println("Sorted Customers:");
        for (int i = 0; i < count; i++) {
            System.out.println(sortedCustomers[i].getFullName());
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

            for (int i = 0; i < queues.length; i++) {
                FoodQueue queue = queues[i];

                bufferedWriter.write("Queue " + (i + 1));
                bufferedWriter.newLine();

                for (int j = 0; j < queue.size(); j++) {
                    String customerName = queue.getCustomerName(j);
                    bufferedWriter.write(customerName != null ? customerName : "X");
                    bufferedWriter.newLine();
                }
            }

            // Close the BufferedWriter to flush any buffered characters and release resources
            bufferedWriter.close();
            System.out.println("Program data stored");
        } catch (IOException e) {
            // Print the stack trace if an exception occurs during file operations
            e.printStackTrace();
        }
    }
    public static void clearQueue(FoodQueue queue) {
        for (int i = 0; i < queue.size(); i++) {
            queue.customers[i] = null;
        }
        queue.size = 0;
    }
    public static void loadProgramData() {
        try {
            // Create a file object to represent the "storedata.txt" file
            File storedata = new File("storedata.txt");

            if (!storedata.exists()) {
                System.out.println("Program data file does not exist.");
                return;
            }

            // Create a FileReader object to read from the file
            FileReader fileReader = new FileReader(storedata);
            // Create a BufferedReader object to read characters from the FileReader
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            // Clear all queues before loading the data
            for (FoodQueue queue : queues) {
                clearQueue(queue);
            }

            String line;
            int queueIndex = -1;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.startsWith("Queue")) {
                    // Extract the queue number from the line
                    int endIndex = line.indexOf(' ');
                    if (endIndex != -1) {
                        queueIndex = Integer.parseInt(line.substring(endIndex + 1)) - 1;
                    }
                } else {
                    // Add the customer to the corresponding queue
                    if (queueIndex >= 0 && queueIndex < queues.length) {
                        queues[queueIndex].addqueue(new Customer(line, "",burgers));
                    }
                }
            }

            // Close the BufferedReader to release resources
            bufferedReader.close();
            System.out.println("Program data loaded");

        } catch (IOException e) {
            // Print the stack trace if an exception occurs during file operations
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        initializeQueues();
        while (true){
            String op=consolemenu();
            if (op.equalsIgnoreCase("100") || op.equalsIgnoreCase("VFQ")){
                System.out.println("    ***********");
                System.out.println("    * Cashier *");
                System.out.println("    ***********");
                System.out.println(queue_1[0]+"      "+queue_2[0]+"       "+queue_3[0]);
                System.out.println(queue_1[1]+"      "+queue_2[1]+"       "+queue_3[1]);
                System.out.println("       "+queue_2[2]+"       "+queue_3[2]);
                System.out.println("               "+queue_3[3]);
                System.out.println("               "+queue_3[4]);

            }else if (op.equalsIgnoreCase("101")||op.equalsIgnoreCase("VEQ")){
                viewEmptyQueues();
            }else if (op.equalsIgnoreCase("102") || op.equalsIgnoreCase("ACQ")){
                addCustomerToQueue(input);
            }else if (op.equalsIgnoreCase("103")||op.equalsIgnoreCase("RCQ")){
                removeCustomerFromQueue(input);
            }else if (op.equalsIgnoreCase("104")||op.equalsIgnoreCase("PCQ")){
                removeServedCustomer();
            }else if (op.equalsIgnoreCase("105")||op.equalsIgnoreCase("VCS")){
                viewCustomersSorted();
            }else if (op.equalsIgnoreCase("106")||op.equalsIgnoreCase("SPD")){
                    file();
            }else if (op.equalsIgnoreCase("107")||op.equalsIgnoreCase("LPD")){
                loadProgramData();
            }else if (op.equalsIgnoreCase("108")||op.equalsIgnoreCase("STK")){
                System.out.println("Remaining burger stock "+ burgers);

            }else if (op.equalsIgnoreCase("109")||op.equalsIgnoreCase("AFS")){
                System.out.println("Enter how many burgers do you want to add- ");
                int addburgers=input.nextInt();
                int newstock=0;
                if ((newstock=burgers+addburgers)>50){
                    System.out.println("Cannot add burgers.Maximum number of burgers exceeded.");
                }else {
                    System.out.println("Burgers added.New stock is "+newstock);
                }
            } else if (op.equalsIgnoreCase("110")||op.equalsIgnoreCase("IFQ")) {
                int[] queueIncomes = new int[3];

                for (int i = 0; i < queues.length; i++) {
                    queueIncomes[i] = queues[i].size() * BURGER_PRICE;
                    System.out.println("Queue " + (i+1) + " income: $" + queueIncomes[i]);
                }

            } else if (op.equalsIgnoreCase("999")||op.equalsIgnoreCase("EXT")) {
                break;
            }else {
                System.out.println("Invalid option");
                break;
            }
        }
        }
        }
        
