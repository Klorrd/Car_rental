import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ID2530635EcoRideSystem {
    private final List<ID2530635Vehicle> vehicles = new ArrayList<>();
    private final List<ID2530635Customer> customers = new ArrayList<>();
    private final List<ID2530635Reservation> reservations = new ArrayList<>();

    // Vehicle CRUD
    public void addVehicle(ID2530635Vehicle v) { vehicles.add(v); }
    public boolean updateVehicle(String carId, String newModel, ID2530635Category newCategory, double newDailyRate) {
        Optional<ID2530635Vehicle> opt = findVehicleById(carId);
        if (!opt.isPresent()) return false;
        ID2530635Vehicle vehicle = opt.get();
        vehicle.setModel(newModel);
        vehicle.setCategory(newCategory);
        vehicle.setDailyRental(newDailyRate);
        return true;
    }
    public boolean removeVehicleById(String carId) {
        return vehicles.removeIf(v -> v.getCarId().equalsIgnoreCase(carId));
    }
    public Optional<ID2530635Vehicle> findVehicleById(String carId) {
        return vehicles.stream().filter(v -> v.getCarId().equalsIgnoreCase(carId)).findFirst();
    }
    public List<ID2530635Vehicle> listVehicles() { return vehicles; }

    // Customer
    public void registerCustomer(ID2530635Customer c) { customers.add(c); }
    public Optional<ID2530635Customer> findCustomerByNic(String nic) {
        return customers.stream().filter(c -> c.getNicOrPassport().equalsIgnoreCase(nic)).findFirst();
    }
    public List<ID2530635Customer> listCustomers() { return customers; }

    // Reservations
    public Optional<ID2530635Reservation> findReservationById(String id) {
        return reservations.stream().filter(r -> r.getReservationId().equalsIgnoreCase(id)).findFirst();
    }
    public List<ID2530635Reservation> searchReservationByName(String name) {
        List<ID2530635Reservation> out = new ArrayList<>();
        for (ID2530635Reservation r : reservations) {
            if (r.getCustomer().getName().toLowerCase().contains(name.toLowerCase())) out.add(r);
        }
        return out;
    }
    public List<ID2530635Reservation> viewByDate(LocalDate date) {
        List<ID2530635Reservation> out = new ArrayList<>();
        for (ID2530635Reservation r : reservations) {
            if (r.getRentalStartDate().equals(date)) out.add(r);
        }
        return out;
    }

    // Create reservation
    // Might need to inclde reservation start and end dates
    public String makeReservation(
        ID2530635Customer customer, 
        ID2530635Vehicle vehicle,
        int rentalDays, 
        double totalKm, 
        LocalDate 
        reservationDate,
        LocalDate rentalStartDate, 
        boolean depositPaid) {
        if (!"Available".equalsIgnoreCase(vehicle.getAvailability())) {
            return null; // cant reserve if not available
        }
        // Booking must be at least 3 days ahead
        if (reservationDate.plusDays(3).isAfter(rentalStartDate)) {
            return null;
        }
        ID2530635Reservation r = new ID2530635Reservation(customer, vehicle, rentalDays, totalKm, reservationDate, rentalStartDate, depositPaid);
        reservations.add(r);
        vehicle.setAvailability("Reserved");
        return r.getReservationId();
    }

    public boolean cancelReservation(String reservationId, LocalDate today) {
        Optional<ID2530635Reservation> opt = findReservationById(reservationId);
        if (!opt.isPresent()) return false;
        ID2530635Reservation r = opt.get();
        if (!r.isCancelable(today)) return false;
        reservations.remove(r);
        r.getVehicle().setAvailability("Available");
        return true;
    }

    public ID2530635Invoice generateInvoice(String reservationId) {
        Optional<ID2530635Reservation> opt = findReservationById(reservationId);
        if (!opt.isPresent()) return null;
        return new ID2530635Invoice(opt.get());
    }
}
