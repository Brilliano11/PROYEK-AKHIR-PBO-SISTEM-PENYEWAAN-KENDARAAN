public class Pelanggan {
    private String nama;
    private String noKTP;
    private String noTelepon;

    public Pelanggan(String nama, String noKTP, String noTelepon) {
        this.nama = nama;
        this.noKTP = noKTP;
        this.noTelepon = noTelepon;
    }

    public String getNama() { return nama; }
    public String getNoKTP() { return noKTP; }
    public String getNoTelepon() { return noTelepon; }
}