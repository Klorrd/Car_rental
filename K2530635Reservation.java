import java.time.LocalDate;
import java.util.UUID;

public class K2530635Reservation {
    private final String reservationId;
    private final K2530635Customer customer;
    private final K2530635Vehicle vehicle;
    private final int rentalDays;
    private final double totalKm; 
    private final LocalDate reservationDate;
    private final LocalDate rentalStartDate;
    private boolean depositPaid;

    public K2530635Reservation(K2530635Customer customer,
                                K2530635Vehicle vehicle,
                                int rentalDays,
                                double totalKm,
                                LocalDate reservationDate,
                                LocalDate rentalStartDate,
                                boolean depositPaid) {
        this.reservationId = UUID.randomUUID().toString().substring(0,8).toUpperCase();
        this.customer = customer;
        this.vehicle = vehicle;
        this.rentalDays = rentalDays;
        this.totalKm = totalKm;
        this.reservationDate = reservationDate;
        this.rentalStartDate = rentalStartDate;
        this.depositPaid = depositPaid;
    }

    public String getReservationId() { return reservationId; }
    public K2530635Customer getCustomer() { return customer; }
    public K2530635Vehicle getVehicle() { return vehicle; }
    public int getRentalDays() { return rentalDays; }
    public double getTotalKm() { return totalKm; }
    public LocalDate getReservationDate() { return reservationDate; }
    public LocalDate getRentalStartDate() { return rentalStartDate; }
    public boolean isDepositPaid() { return depositPaid; }
    public void setDepositPaid(boolean paid) { this.depositPaid = paid; }

    public boolean isCancelable(LocalDate today) {
        // Cant update or cancel after 2 days from reservation 
        return !today.isAfter(reservationDate.plusDays(2));
    }

    @Override
    public String toString() {
        return String.format("%s | %s | %s | days:%d | km:%.1f | start:%s | resDate:%s",
                reservationId, customer.getName(), vehicle.getCarId(), rentalDays, totalKm,
                rentalStartDate.toString(), reservationDate.toString());
    }
}
