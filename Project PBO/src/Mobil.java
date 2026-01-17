public class Mobil extends Kendaraan {
    private int kapasitasPenumpang;
    private String jenisTransmisi;

    public Mobil(String nomorPolisi, String merek, String model, int tahunProduksi, double hargaSewaPerHari, int kapasitasPenumpang, String jenisTransmisi) {
        super(nomorPolisi, merek, model, tahunProduksi, hargaSewaPerHari);
        this.kapasitasPenumpang = kapasitasPenumpang;
        this.jenisTransmisi = jenisTransmisi;
    }

    @Override
    public double hitungBiayaSewa(int jumlahHari) {
        double biayaDasar = super.hitungBiayaSewa(jumlahHari);
        double biayaLayanan = 50000;
        double asuransi = biayaDasar * 0.05;
        return biayaDasar + biayaLayanan + asuransi;
    }

    @Override
    public String getDetail() {
        return super.getDetail() + " (Mobil " + jenisTransmisi + ", " + kapasitasPenumpang + " Seat)";
    }
}