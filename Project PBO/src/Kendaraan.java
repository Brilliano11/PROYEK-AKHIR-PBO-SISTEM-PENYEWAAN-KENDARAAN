public class Kendaraan {
    private String nomorPolisi;
    private String merek;
    private String model;
    private int tahunProduksi;
    private double hargaSewaPerHari;
    private String statusKetersediaan;

    public Kendaraan(String nomorPolisi, String merek, String model, int tahunProduksi, double hargaSewaPerHari) {
        this.nomorPolisi = nomorPolisi;
        this.merek = merek;
        this.model = model;
        this.tahunProduksi = tahunProduksi;
        this.hargaSewaPerHari = hargaSewaPerHari;
        this.statusKetersediaan = "Tersedia";
    }

    public double hitungBiayaSewa(int jumlahHari) {
        return hargaSewaPerHari * jumlahHari;
    }

    public String getDetail() {
        return "No: " + nomorPolisi + " | " + merek + " " + model;
    }

    public String getNomorPolisi() { return nomorPolisi; }
    public String getMerek() { return merek; }
    public String getModel() { return model; }
    public double getHargaSewaPerHari() { return hargaSewaPerHari; }

    public String getStatusKetersediaan() { return statusKetersediaan; }
    public void setStatusKetersediaan(String status) { this.statusKetersediaan = status; }
}