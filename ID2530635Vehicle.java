public class ID2530635Vehicle {
    private final String carId;
    private String model;
    private ID2530635Category category;
    private double dailyRental;
    private String availability; 

    public ID2530635Vehicle(String carId, String model, ID2530635Category category, double dailyRental) {
        this.carId = carId;
        this.model = model;
        this.category = category;
        this.dailyRental = dailyRental;
        this.availability = "Available";
    }

    public String getCarId() { return carId; }
    public String getModel() { return model; }
    public ID2530635Category getCategory() { return category; }
    public double getDailyRental() { return dailyRental; }
    public String getAvailability() { return availability; }

    public void setModel(String model) { this.model = model; }
    public void setCategory(ID2530635Category category) { this.category = category; }
    public void setDailyRental(double dailyRental) { this.dailyRental = dailyRental; }
    public void setAvailability(String availability) { this.availability = availability; }

    @Override
    public String toString() {
        return String.format("%s | %s | %s | LKR %.2f | %s",
                carId, model, category.name(), dailyRental, availability);
    }
}