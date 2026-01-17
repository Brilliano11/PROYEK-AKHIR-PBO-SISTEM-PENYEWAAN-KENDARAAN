public class Motor extends Kendaraan {
    private int ccMesin;
    private String jenisMotor;

    public Motor(String nomorPolisi, String merek, String model, int tahunProduksi, double hargaSewaPerHari, int ccMesin, String jenisMotor) {
        super(nomorPolisi, merek, model, tahunProduksi, hargaSewaPerHari);
        this.ccMesin = ccMesin;
        this.jenisMotor = jenisMotor;
    }

    @Override
    public double hitungBiayaSewa(int jumlahHari) {
        double biayaDasar = super.hitungBiayaSewa(jumlahHari);
        double helmJasHujan = 25000 * jumlahHari;
        return biayaDasar + helmJasHujan;
    }

    @Override
    public String getDetail() {
        return super.getDetail() + " (Motor " + jenisMotor + ", " + ccMesin + "cc)";
    }
}