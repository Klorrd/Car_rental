public enum ID2530635Category {
    // dailyFee, freeKm, extraKmCharge, taxRate %
    COMPACT_PETROL(5000, 100, 50, 0.10),
    HYBRID(7500, 150, 60, 0.12),
    ELECTRIC(10000, 200, 40, 0.08),
    LUXURY_SUV(15000, 250, 75, 0.15);

    public final double dailyFee;
    public final int freeKm;
    public final double extraKmCharge;
    public final double taxRate;

    ID2530635Category(double dailyFee, int freeKm, double extraKmCharge, double taxRate) {
        this.dailyFee = dailyFee;
        this.freeKm = freeKm;
        this.extraKmCharge = extraKmCharge;
        this.taxRate = taxRate;
    }
}
