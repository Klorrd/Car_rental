import java.util.UUID;

public class ID2530635Invoice {
    private final String invoiceId;
    private final ID2530635Reservation reservation;
    private double basePrice;
    private double extraKmCharge;
    private double discountAmount;
    private double taxAmount;
    private double finalAmount;
    public static final double DEPOSIT = 5000.0;

    public ID2530635Invoice(ID2530635Reservation reservation) {
        this.invoiceId = "INV-" + UUID.randomUUID().toString().substring(0,8).toUpperCase();
        this.reservation = reservation;
        calculate();
    }

    private void calculate() {
        ID2530635Vehicle v = reservation.getVehicle();
        ID2530635Category c = v.getCategory();

        basePrice = c.dailyFee * reservation.getRentalDays();

        double allowedKmTotal = c.freeKm * reservation.getRentalDays();
        double extraKm = Math.max(0, reservation.getTotalKm() - allowedKmTotal);
        extraKmCharge = extraKm * c.extraKmCharge;

        // Discount of 10% given for rentals >= 7 days 
        discountAmount = (reservation.getRentalDays() >= 7) ? (0.10 * basePrice) : 0.0;

        // Check for validity of final amount calculation
        double subtotal = basePrice - discountAmount + extraKmCharge;
        taxAmount = subtotal * c.taxRate;
        finalAmount = subtotal + taxAmount - (reservation.isDepositPaid() ? DEPOSIT : 0.0);
    }

    public String getInvoiceId() { return invoiceId; }
    public ID2530635Reservation getReservation() { return reservation; }
    public double getFinalAmount() { return finalAmount; }

    public void printInvoice() {
        ID2530635Vehicle v = reservation.getVehicle();
        ID2530635Category c = v.getCategory();
        double allowedKmTotal = c.freeKm * reservation.getRentalDays();
        double extraKm = Math.max(0, reservation.getTotalKm() - allowedKmTotal);
        
        System.out.println("==== EcoRide Invoice ====");
        System.out.println("Invoice ID: " + invoiceId);
        System.out.println("Reservation ID: " + reservation.getReservationId());
        System.out.println("Customer: " + reservation.getCustomer().getName());
        System.out.println("Car Details:");
        System.out.println("  - Car ID: " + v.getCarId());
        System.out.println("  - Model: " + v.getModel());
        System.out.println("  - Category: " + c.name());
        System.out.printf("  - Daily Rate: LKR %.2f\n", c.dailyFee);
        System.out.println("Rental Duration: " + reservation.getRentalDays() + " days");
        System.out.println("Mileage Used: " + reservation.getTotalKm() + " km");
        System.out.printf("  - Free KM Allowance: %.0f km\n", allowedKmTotal);
        System.out.printf("  - Extra KM: %.0f km\n", extraKm);
        System.out.printf("Base Price: LKR %.2f\n", basePrice);
        System.out.printf("Extra KM Charge: LKR %.2f\n", extraKmCharge);
        System.out.printf("Discount Applied: LKR %.2f\n", discountAmount);
        System.out.printf("Tax Added: LKR %.2f\n", taxAmount);
        System.out.printf("Deposit Handled: LKR %.2f\n", reservation.isDepositPaid() ? DEPOSIT : 0.0);
        System.out.printf("Final Payable Amount: LKR %.2f\n", finalAmount);
        System.out.println("=========================");
    }
}
