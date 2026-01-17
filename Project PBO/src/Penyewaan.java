public class Penyewaan {
    private Pelanggan pelanggan;
    private Kendaraan kendaraan;
    private int lamaSewa;
    private double totalBiaya;

    public Penyewaan(Pelanggan pelanggan, Kendaraan kendaraan, int lamaSewa) {
        this.pelanggan = pelanggan;
        this.kendaraan = kendaraan;
        this.lamaSewa = lamaSewa;
        this.totalBiaya = kendaraan.hitungBiayaSewa(lamaSewa);
    }

    public String getInfoTransaksi() {
        return "Penyewa: " + pelanggan.getNama() +
                "\nUnit: " + kendaraan.getDetail() +
                "\nLama: " + lamaSewa + " Hari" +
                "\nTotal: Rp " + (long)totalBiaya;
    }

    public Pelanggan getPelanggan() { return pelanggan; }
    public Kendaraan getKendaraan() { return kendaraan; }
}