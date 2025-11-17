import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;
import java.util.Optional;

public class Main {
    private static ID2530635EcoRideSystem system = new ID2530635EcoRideSystem();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initializeSampleData();
        
        // Main menu 
        while (true) {
            showMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1 -> addVehicle();
                case 2 -> updateVehicle();
                case 3 -> removeVehicle();
                case 4 -> viewVehicles();
                case 5 -> registerCustomer();
                case 6 -> viewCustomers();
                case 7 -> makeReservation();
                case 8 -> searchReservationsByName();
                case 9 -> searchReservationById();
                case 10 -> viewBookingsByDate();
                case 11 -> generateInvoice();
                case 12 -> cancelReservation();
                case 0 -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void initializeSampleData() {
        // Dummy vehicles
        system.addVehicle(new ID2530635Vehicle("CAR001", "Toyota Prius", ID2530635Category.HYBRID, 3500.0));
        system.addVehicle(new ID2530635Vehicle("CAR002", "Honda Civic", ID2530635Category.COMPACT_PETROL, 4500.0));
        system.addVehicle(new ID2530635Vehicle("CAR003", "BMW X5", ID2530635Category.LUXURY_SUV, 8500.0));
        system.addVehicle(new ID2530635Vehicle("CAR004", "Tesla Model 3", ID2530635Category.ELECTRIC, 6000.0));
        
        // Dummy customers
        system.registerCustomer(new ID2530635Customer("123456789V", "Anuradhi Wijekoon", "0771234567", "anuradhi@email.com"));
        system.registerCustomer(new ID2530635Customer("987654321V", "Kalindu Matharage", "0779876543", "kalindu@email.com"));
    }

    private static void showMenu() {
        System.out.println("\n==== EcoRide Car Rental System ====");
        System.out.println("1. Add Vehicle");
        System.out.println("2. Update Vehicle");
        System.out.println("3. Remove Vehicle");
        System.out.println("4. View Vehicles");
        System.out.println("5. Register Customer");
        System.out.println("6. View Customers");
        System.out.println("7. Make Reservation");
        System.out.println("8. Search Reservations by Name");
        System.out.println("9. Search Reservation by Booking ID");
        System.out.println("10. View Reservation by Date");
        System.out.println("11. Generate Invoice");
        System.out.println("12. Cancel Reservation");
        System.out.println("0. Exit");
        System.out.print("Please choose an option: ");
    }

    private static void addVehicle() {
        System.out.print("Enter Car ID: ");
        String carId = scanner.nextLine();
        System.out.print("Enter Model: ");
        String model = scanner.nextLine();
        System.out.print("Enter Category (COMPACT_PETROL/HYBRID/ELECTRIC/LUXURY_SUV): ");
        String categoryStr = scanner.nextLine();
        System.out.print("Enter Daily Rental Rate: ");
        double dailyRate = scanner.nextDouble();
        scanner.nextLine();

        try {
            ID2530635Category category = ID2530635Category.valueOf(categoryStr.toUpperCase());
            Optional<ID2530635Vehicle> existing = system.findVehicleById(carId);
            if (existing.isPresent()) {
                System.out.println("Vehicle with this ID already exists!");
                return;
            }
            system.addVehicle(new ID2530635Vehicle(carId, model, category, dailyRate));
            System.out.println("Vehicle added successfully!");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid category. Please use COMPACT_PETROL, HYBRID, ELECTRIC, or LUXURY_SUV.");
        }
    }

    private static void registerCustomer() {
        System.out.print("Enter NIC/Passport: ");
        String nic = scanner.nextLine();
        System.out.print("Enter Customer Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Phone: ");
        String phone = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();

        // Check if customer already exists
        System.out.println("Checking if customer already exists...");
        Optional<ID2530635Customer> existing = system.findCustomerByNic(nic);
        if (existing.isPresent()) {
            ID2530635Customer existingCustomer = existing.get();
            System.out.println("❌ Customer with NIC/Passport '" + nic + "' already exists!");
            System.out.println("📋 Existing customer: " + existingCustomer.getName() + " - " + existingCustomer.getContact());
            System.out.println("💡 Use this NIC/Passport to make reservations directly");
            return;
        }

        system.registerCustomer(new ID2530635Customer(nic, name, phone, email));
        System.out.println("Customer registered successfully!");
    }

    private static void viewCustomers() {
        List<ID2530635Customer> customers = system.listCustomers();
        if (customers.isEmpty()) {
            System.out.println("No customers registered. Please register using the menu.");
        } else {
            System.out.println("\n--- Registered Customers ---");
            for (ID2530635Customer c : customers) {
                System.out.println(c);
            }
        }
    }

    private static void makeReservation() {
        System.out.print("Enter Customer NIC/Passport: ");
        String nic = scanner.nextLine();
        
        // Search for existing customer
        System.out.println("Searching for customer with NIC/Passport: " + nic + "...");
        Optional<ID2530635Customer> customerOpt = system.findCustomerByNic(nic);
        
        if (!customerOpt.isPresent()) {
            System.out.println("❌ No customer exists with NIC/Passport: " + nic);
            System.out.println("💡 Use option 6 'View Customers' to see all registered customers");
            System.out.println("💡 Or use option 5 'Register Customer' to add a new customer");
            return;
        }
        
        // Customer found > show confirmation
        ID2530635Customer foundCustomer = customerOpt.get();
        System.out.println("✅ Customer found: " + foundCustomer.getName() + " (" + foundCustomer.getContact() + ")");

        System.out.print("Enter Car ID: ");
        String carId = scanner.nextLine();
        
        // Search for existing vehicle
        System.out.println("Searching for vehicle with ID: " + carId + "...");
        Optional<ID2530635Vehicle> vehicleOpt = system.findVehicleById(carId);
        
        if (!vehicleOpt.isPresent()) {
            System.out.println("❌ No vehicle exists with ID: " + carId);
            System.out.println("💡 Use option 4 'View Vehicles' to see all available vehicles");
            return;
        }
        
        // Vehicle found > show confirmation and availability
        ID2530635Vehicle foundVehicle = vehicleOpt.get();
        System.out.println("✅ Vehicle found: " + foundVehicle.getModel() + " (" + foundVehicle.getCategory().name() + ")");
        System.out.println("📊 Status: " + foundVehicle.getAvailability());

        // Might need to modify the system to allow picking reseravation start and end dates
        System.out.print("Enter rental days: ");
        int rentalDays = scanner.nextInt();
        System.out.print("Enter expected total KM: ");
        double totalKm = scanner.nextDouble();
        System.out.print("Is deposit paid? (true/false): ");
        boolean depositPaid = scanner.nextBoolean();
        scanner.nextLine();

        LocalDate today = LocalDate.now();
        LocalDate startDate = today.plusDays(3); 

        String reservationId = system.makeReservation(
            customerOpt.get(), 
            vehicleOpt.get(), 
            rentalDays, 
            totalKm, 
            today, 
            startDate, 
            depositPaid
        );

        if (reservationId != null) {
            System.out.println("Reservation created successfully! ID: " + reservationId);
        } else {
            System.out.println("Failed to create reservation. Vehicle may not be available or booking rules violated.");
        }
    }

    private static void searchReservationsByName() {
        System.out.print("Enter customer name to search (or press Enter to view all): ");
        String name = scanner.nextLine();
        
        List<ID2530635Reservation> reservations;
        if (name.trim().isEmpty()) {
            reservations = system.searchReservationByName("");
        } else {
            reservations = system.searchReservationByName(name);
        }

        if (reservations.isEmpty()) {
            System.out.println("No reservations found.");
        } else {
            System.out.println("\n--- Reservations ---");
            for (ID2530635Reservation r : reservations) {
                System.out.println(r);
            }
        }
    }

    private static void generateInvoice() {
        System.out.print("Enter Reservation ID: ");
        String id = scanner.nextLine();
        
        System.out.println("Searching for reservation with ID: " + id + "...");
        ID2530635Invoice invoice = system.generateInvoice(id);
        if (invoice != null) {
            System.out.println("✅ Reservation found! Generating invoice...");
            invoice.printInvoice();
        } else {
            System.out.println("❌ No reservation exists with ID: " + id);
            System.out.println("💡 Use option 8 'Search Reservations by Name' to find reservations");
        }
    }

    private static void cancelReservation() {
        System.out.print("Enter Reservation ID: ");
        String id = scanner.nextLine();
        
        System.out.println("Searching for reservation with ID: " + id + "...");
        
        // If not found, currently system takes the user to the main menu, no option given to try again
        boolean success = system.cancelReservation(id, LocalDate.now());
        if (success) {
            System.out.println("✅ Reservation cancelled successfully!");
        } else {
            System.out.println("❌ Failed to cancel reservation.");
            System.out.println("💡 Possible reasons:");
            System.out.println("   - Reservation ID doesn't exist");
            System.out.println("   - Past the 2-day cancellation deadline");
            System.out.println("💡 Use option 8 'Search Reservations by Name' to find valid reservations");
        }
    }

    private static void updateVehicle() {
        System.out.print("Enter Car ID to update: ");
        String carId = scanner.nextLine();
        
        System.out.println("Searching for vehicle with ID: " + carId + "...");
        Optional<ID2530635Vehicle> opt = system.findVehicleById(carId);
        if (!opt.isPresent()) {
            System.out.println("❌ No vehicle exists with ID: " + carId);
            System.out.println("💡 Use option 4 'View Vehicles' to see all available vehicles");
            return;
        }
        
        ID2530635Vehicle vehicle = opt.get();
        System.out.println("Current vehicle details: " + vehicle);
        
        System.out.print("Enter new Model (current: " + vehicle.getModel() + "): ");
        String newModel = scanner.nextLine();
        if (newModel.trim().isEmpty()) newModel = vehicle.getModel();
        
        // This might be buggy, check for stability
        System.out.print("Enter new Category (current: " + vehicle.getCategory().name() + ") - (COMPACT_PETROL/HYBRID/ELECTRIC/LUXURY_SUV): ");
        String categoryStr = scanner.nextLine();
        ID2530635Category newCategory = vehicle.getCategory();
        if (!categoryStr.trim().isEmpty()) {
            try {
                newCategory = ID2530635Category.valueOf(categoryStr.toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid category. Keeping current category.");
            }
        }
        
        System.out.print("Enter new Daily Rate (current: " + vehicle.getDailyRental() + "): ");
        String rateStr = scanner.nextLine();
        double newRate = vehicle.getDailyRental();
        if (!rateStr.trim().isEmpty()) {
            try {
                newRate = Double.parseDouble(rateStr);
            } catch (NumberFormatException e) {
                System.out.println("Invalid rate. Keeping current rate.");
            }
        }
        
        boolean success = system.updateVehicle(carId, newModel, newCategory, newRate);
        if (success) {
            System.out.println("Vehicle updated successfully!");
        } else {
            System.out.println("Failed to update vehicle.");
        }
    }

    private static void removeVehicle() {
        System.out.print("Enter Car ID to remove: ");
        String carId = scanner.nextLine();
        
        System.out.println("Searching for vehicle with ID: " + carId + "...");
        Optional<ID2530635Vehicle> opt = system.findVehicleById(carId);
        if (!opt.isPresent()) {
            System.out.println("❌ No vehicle exists with ID: " + carId);
            System.out.println("💡 Use option 4 'View Vehicles' to see all available vehicles");
            return;
        }
        
        System.out.println("Vehicle to remove: " + opt.get());
        System.out.print("Are you sure? (yes/no): ");
        String confirm = scanner.nextLine();
        
        if ("yes".equalsIgnoreCase(confirm.trim())) {
            boolean success = system.removeVehicleById(carId);
            if (success) {
                System.out.println("Vehicle removed successfully!");
            } else {
                System.out.println("Failed to remove vehicle.");
            }
        } else {
            System.out.println("Vehicle removal cancelled.");
        }
    }

    private static void viewVehicles() {
        System.out.println("\n--- Vehicle Viewing Options ---");
        System.out.println("1. View All Vehicles");
        System.out.println("2. View Available Vehicles Only");
        System.out.println("3. View by Category");
        System.out.print("Choose an option: ");
        
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        List<ID2530635Vehicle> vehicles = system.listVehicles();
        List<ID2530635Vehicle> filteredVehicles = new ArrayList<>();
        
        switch (choice) {
            case 1:
                filteredVehicles = vehicles;
                System.out.println("\n--- All Vehicles ---");
                break;
            case 2:
                for (ID2530635Vehicle v : vehicles) {
                    if ("Available".equalsIgnoreCase(v.getAvailability())) {
                        filteredVehicles.add(v);
                    }
                }
                System.out.println("\n--- Available Vehicles ---");
                break;
            case 3:
                // Show categories. Possible to simplify category names/ids?
                System.out.print("Enter Category (COMPACT_PETROL/HYBRID/ELECTRIC/LUXURY_SUV): ");
                String categoryStr = scanner.nextLine();
                try {
                    ID2530635Category category = ID2530635Category.valueOf(categoryStr.toUpperCase());
                    for (ID2530635Vehicle v : vehicles) {
                        if (v.getCategory() == category) {
                            filteredVehicles.add(v);
                        }
                    }
                    System.out.println("\n--- " + category + " Vehicles ---");
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid category!");
                    return;
                }
                break;
            default:
                System.out.println("Invalid choice!");
                return;
        }
        
        if (filteredVehicles.isEmpty()) {
            System.out.println("No vehicles found matching your criteria.");
        } else {
          // Alighments might get messed up here, check
            System.out.printf("%-10s %-15s %-15s %-12s %-15s%n", 
                "Car ID", "Model", "Category", "Daily Rate", "Availability");
            System.out.println("------------------------------------------------------------------------");
            for (ID2530635Vehicle v : filteredVehicles) {
                System.out.printf("%-10s %-15s %-15s LKR %-8.2f %-15s%n", 
                    v.getCarId(), 
                    v.getModel(), 
                    v.getCategory().name(), 
                    v.getCategory().dailyFee, 
                    v.getAvailability());
            }
        }
    }

    private static void searchReservationById() {
        System.out.print("Enter Booking/Reservation ID: ");
        String id = scanner.nextLine();
        
        System.out.println("Searching for reservation with ID: " + id + "...");
        Optional<ID2530635Reservation> opt = system.findReservationById(id);
        if (opt.isPresent()) {
            System.out.println("✅ Reservation Found!");
            System.out.println("\n--- Reservation Details ---");
            System.out.println(opt.get());
        } else {
            System.out.println("❌ No reservation exists with ID: " + id);
            System.out.println("💡 Use option 8 'Search Reservations by Name' to browse all reservations");
        }
    }

    private static void viewBookingsByDate() {
        System.out.print("Enter rental start date (YYYY-MM-DD): ");
        String dateStr = scanner.nextLine();
        
        try {
            LocalDate date = LocalDate.parse(dateStr);
            List<ID2530635Reservation> reservations = system.viewByDate(date);
            
            if (reservations.isEmpty()) {
                System.out.println("No bookings found for date: " + date);
            } else {
                System.out.println("\n--- Bookings for " + date + " ---");
                for (ID2530635Reservation r : reservations) {
                    System.out.println(r);
                }
            }
        } catch (Exception e) {
            System.out.println("Invalid date format. Please use YYYY-MM-DD format.");
        }
    }
}