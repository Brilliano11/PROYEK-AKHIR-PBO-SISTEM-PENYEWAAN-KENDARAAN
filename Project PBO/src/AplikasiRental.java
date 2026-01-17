import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class AplikasiRental extends JFrame {

    private ArrayList<Kendaraan> listKendaraan = new ArrayList<>();
    private ArrayList<Pelanggan> listPelanggan = new ArrayList<>();

    private JTabbedPane tabs;

    private DefaultTableModel modelTabelKendaraan;
    private DefaultTableModel modelTabelPelanggan;
    private DefaultTableModel modelTabelPengembalian;

    private JComboBox<String> comboPelanggan;
    private JComboBox<String> comboJenisFilter;
    private JComboBox<String> comboKendaraan;
    private JTextArea areaStruk;
    private JTextField txtHari;

    public AplikasiRental() {
        setTitle("Sistem Manajemen Rental - OOP Project");
        setSize(900, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        isiDataAwal();

        tabs = new JTabbedPane();
        tabs.addTab("Stok Kendaraan", panelKendaraan());
        tabs.addTab("Data Pelanggan", panelPelanggan());
        tabs.addTab("Transaksi Sewa", panelTransaksi());
        tabs.addTab("Pengembalian Unit", panelPengembalian());

        tabs.addChangeListener(e -> refreshSemuaData());

        add(tabs);
    }

    private JPanel panelKendaraan() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel form = new JPanel(new GridLayout(6, 2, 5, 5));
        JTextField txtNopol = new JTextField();
        JTextField txtMerek = new JTextField();
        JTextField txtHarga = new JTextField();
        String[] jenis = {"Mobil", "Motor"};
        JComboBox<String> cbJenis = new JComboBox<>(jenis);

        JTextField txtKhusus = new JTextField();
        JLabel lblKhusus = new JLabel("Jml Penumpang :");

        cbJenis.addActionListener(e -> {
            if(cbJenis.getSelectedItem().equals("Mobil")) lblKhusus.setText("Jml Penumpang :");
            else lblKhusus.setText("Kapasitas CC :");
        });

        form.add(new JLabel("Jenis:")); form.add(cbJenis);
        form.add(new JLabel("No Polisi:")); form.add(txtNopol);
        form.add(new JLabel("Merek/Model:")); form.add(txtMerek);
        form.add(new JLabel("Harga Sewa:")); form.add(txtHarga);
        form.add(lblKhusus); form.add(txtKhusus);

        JButton btnAdd = new JButton("Tambah Unit Baru");
        form.setBorder(BorderFactory.createTitledBorder("Input Kendaraan"));

        String[] header = {"No Polisi", "Jenis", "Info Detail", "Harga", "Status"};
        modelTabelKendaraan = new DefaultTableModel(header, 0);
        JTable tabel = new JTable(modelTabelKendaraan);


        btnAdd.addActionListener(e -> {
            try {
                String nopol = txtNopol.getText();
                String merek = txtMerek.getText();
                double harga = Double.parseDouble(txtHarga.getText());
                int khusus = Integer.parseInt(txtKhusus.getText());

                Kendaraan k;
                if (cbJenis.getSelectedItem().equals("Mobil")) {
                    k = new Mobil(nopol, merek, "", 2024, harga, khusus, "Manual");
                } else {
                    k = new Motor(nopol, merek, "", 2024, harga, khusus, "Matic");
                }

                listKendaraan.add(k);
                refreshSemuaData();
                JOptionPane.showMessageDialog(this, "Berhasil tambah unit!");
                txtNopol.setText(""); txtMerek.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Pastikan input angka benar!");
            }
        });

        JPanel panelAtas = new JPanel(new BorderLayout());
        panelAtas.add(form, BorderLayout.CENTER);
        panelAtas.add(btnAdd, BorderLayout.SOUTH);

        panel.add(panelAtas, BorderLayout.NORTH);
        panel.add(new JScrollPane(tabel), BorderLayout.CENTER);
        return panel;
    }


    private JPanel panelPelanggan() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(4, 2, 5, 5));
        JTextField txtNama = new JTextField();
        JTextField txtKTP = new JTextField();
        JTextField txtTelp = new JTextField();

        form.add(new JLabel("Nama:")); form.add(txtNama);
        form.add(new JLabel("No KTP:")); form.add(txtKTP);
        form.add(new JLabel("No Telp:")); form.add(txtTelp);

        JButton btnAdd = new JButton("Simpan Pelanggan");
        form.setBorder(BorderFactory.createTitledBorder("Input Pelanggan"));

        String[] header = {"Nama", "KTP", "Telepon"};
        modelTabelPelanggan = new DefaultTableModel(header, 0);
        JTable tabel = new JTable(modelTabelPelanggan);

        btnAdd.addActionListener(e -> {
            if(!txtNama.getText().isEmpty()) {
                listPelanggan.add(new Pelanggan(txtNama.getText(), txtKTP.getText(), txtTelp.getText()));
                refreshSemuaData();
                txtNama.setText(""); txtKTP.setText("");
            }
        });

        panel.add(form, BorderLayout.NORTH);
        panel.add(new JScrollPane(tabel), BorderLayout.CENTER);
        panel.add(btnAdd, BorderLayout.SOUTH);
        return panel;
    }


    private JPanel panelTransaksi() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel input = new JPanel(new GridLayout(5, 2, 8, 8));
        input.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        comboPelanggan = new JComboBox<>();

        String[] filter = {"-- Pilih Jenis --", "Mobil", "Motor"};
        comboJenisFilter = new JComboBox<>(filter);

        comboKendaraan = new JComboBox<>();

        txtHari = new JTextField();
        JButton btnSewa = new JButton("PROSES SEWA");

        input.add(new JLabel("Pilih Pelanggan:")); input.add(comboPelanggan);
        input.add(new JLabel("Filter Jenis:")); input.add(comboJenisFilter);
        input.add(new JLabel("Pilih Unit Tersedia:")); input.add(comboKendaraan);
        input.add(new JLabel("Lama Sewa (Hari):")); input.add(txtHari);
        input.add(new JLabel("")); input.add(btnSewa);

        areaStruk = new JTextArea();
        areaStruk.setFont(new Font("Monospaced", Font.PLAIN, 12));
        areaStruk.setBorder(BorderFactory.createTitledBorder("Struk Transaksi"));

        comboJenisFilter.addActionListener(e -> {
            String selectedType = (String) comboJenisFilter.getSelectedItem();
            comboKendaraan.removeAllItems();

            if (selectedType != null && !selectedType.equals("-- Pilih Jenis --")) {
                for (Kendaraan k : listKendaraan) {
                    if (k.getStatusKetersediaan().equals("Tersedia")) {
                        boolean isMobil = k instanceof Mobil;
                        boolean isMotor = k instanceof Motor;

                        if ((selectedType.equals("Mobil") && isMobil) ||
                                (selectedType.equals("Motor") && isMotor)) {
                            comboKendaraan.addItem(k.getNomorPolisi() + " - " + k.getMerek());
                        }
                    }
                }
            }
        });

        btnSewa.addActionListener(e -> {
            try {
                if (comboKendaraan.getItemCount() == 0 || comboPelanggan.getItemCount() == 0) {
                    JOptionPane.showMessageDialog(this, "Data tidak lengkap!");
                    return;
                }

                String namaP = (String) comboPelanggan.getSelectedItem();
                String nopolK = ((String) comboKendaraan.getSelectedItem()).split(" - ")[0];

                Pelanggan p = cariPelanggan(namaP);
                Kendaraan k = cariKendaraan(nopolK);
                int hari = Integer.parseInt(txtHari.getText());

                if (p != null && k != null) {
                    Penyewaan trans = new Penyewaan(p, k, hari);
                    k.setStatusKetersediaan("Disewa");

                    areaStruk.setText(trans.getInfoTransaksi());

                    JOptionPane.showMessageDialog(this, "Transaksi Berhasil!");
                    refreshSemuaData();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: Pastikan input hari berupa angka.");
            }
        });

        panel.add(input, BorderLayout.NORTH);
        panel.add(new JScrollPane(areaStruk), BorderLayout.CENTER);
        return panel;
    }

    private JPanel panelPengembalian() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] header = {"No Polisi", "Merek", "Jenis", "Status"};
        modelTabelPengembalian = new DefaultTableModel(header, 0);
        JTable table = new JTable(modelTabelPengembalian);

        JButton btnKembali = new JButton("PROSES PENGEMBALIAN (UNIT KEMBALI)");
        btnKembali.setFont(new Font("Arial", Font.BOLD, 14));
        btnKembali.setFont(new Font("Arial", Font.BOLD, 14));
        btnKembali.setBackground(new Color(200, 255, 200));

        btnKembali.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                String nopol = (String) modelTabelPengembalian.getValueAt(row, 0);
                Kendaraan k = cariKendaraan(nopol);

                if (k != null) {
                    int confirm = JOptionPane.showConfirmDialog(this,
                            "Kembalikan unit " + k.getMerek() + " (" + nopol + ")?",
                            "Konfirmasi", JOptionPane.YES_NO_OPTION);

                    if (confirm == JOptionPane.YES_OPTION) {
                        k.setStatusKetersediaan("Tersedia");
                        refreshSemuaData();
                        JOptionPane.showMessageDialog(this, "Unit berhasil dikembalikan!");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Pilih unit dari tabel di atas!");
            }
        });

        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(btnKembali, BorderLayout.SOUTH);

        JLabel info = new JLabel("  *Tabel ini hanya menampilkan kendaraan yang sedang DISEWA.");
        info.setPreferredSize(new Dimension(800, 30));
        panel.add(info, BorderLayout.NORTH);

        return panel;
    }

    private void refreshSemuaData() {
        modelTabelKendaraan.setRowCount(0);
        for (Kendaraan k : listKendaraan) {
            String jenis = (k instanceof Mobil) ? "Mobil" : "Motor";
            modelTabelKendaraan.addRow(new Object[]{
                    k.getNomorPolisi(), jenis, k.getDetail(), k.getHargaSewaPerHari(), k.getStatusKetersediaan()
            });
        }

        modelTabelPelanggan.setRowCount(0);
        for (Pelanggan p : listPelanggan) {
            modelTabelPelanggan.addRow(new Object[]{p.getNama(), p.getNoKTP(), p.getNoTelepon()});
        }

        comboPelanggan.removeAllItems();
        for (Pelanggan p : listPelanggan) comboPelanggan.addItem(p.getNama());
        comboJenisFilter.setSelectedIndex(0);
        comboKendaraan.removeAllItems();
        modelTabelPengembalian.setRowCount(0);
        for (Kendaraan k : listKendaraan) {
            if (k.getStatusKetersediaan().equals("Disewa")) {
                String jenis = (k instanceof Mobil) ? "Mobil" : "Motor";
                modelTabelPengembalian.addRow(new Object[]{
                        k.getNomorPolisi(), k.getMerek() + " " + k.getModel(), jenis, "SEDANG DISEWA"
                });
            }
        }
    }

    private void isiDataAwal() {
        listKendaraan.add(new Mobil("AB-101-XX", "Toyota", "Avanza", 2022, 350000, 7, "Manual"));
        listKendaraan.add(new Mobil("B-8899-KL", "Honda", "Brio", 2023, 300000, 5, "Matic"));
        listKendaraan.add(new Motor("AB-202-ZZ", "Honda", "Vario", 2023, 75000, 150, "Matic"));
        listKendaraan.add(new Motor("AB-4455-UY", "Yamaha", "NMAX", 2024, 120000, 155, "Matic"));

        listPelanggan.add(new Pelanggan("Diky Alfiansyah", "34040xxx", "0812xxx"));
        listPelanggan.add(new Pelanggan("Anggita Ramadanis", "34050xxx", "0813xxx"));
    }

    private Kendaraan cariKendaraan(String nopol) {
        for(Kendaraan k : listKendaraan) if(k.getNomorPolisi().equals(nopol)) return k;
        return null;
    }

    private Pelanggan cariPelanggan(String nama) {
        for(Pelanggan p : listPelanggan) if(p.getNama().equals(nama)) return p;
        return null;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> new AplikasiRental().setVisible(true));
    }
}