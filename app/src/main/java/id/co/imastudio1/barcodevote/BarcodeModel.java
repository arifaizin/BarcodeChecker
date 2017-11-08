package id.co.imastudio1.barcodevote;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by idn on 10/5/2017.
 */

public class BarcodeModel {
    @SerializedName("no")
    @Expose
    private String no;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("barcode")
    @Expose
    private String barcode;
    @SerializedName("bayar")
    @Expose
    private String bayar;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("nama_lengkap")
    @Expose
    private String namaLengkap;
    @SerializedName("alamat_lengkap")
    @Expose
    private String alamatLengkap;
    @SerializedName("nomor_kta")
    @Expose
    private String nomorKta;
    @SerializedName("wilayah")
    @Expose
    private String wilayah;
    @SerializedName("kota")
    @Expose
    private String kota;
    @SerializedName("nomor_telepon")
    @Expose
    private String nomorTelepon;
    @SerializedName("nomor_wa")
    @Expose
    private String nomorWa;
    @SerializedName("pasfoto")
    @Expose
    private String pasfoto;
    @SerializedName("registrasi")
    @Expose
    private String registrasi;
    @SerializedName("sertifikat")
    @Expose
    private String sertifikat;
    @SerializedName("seminar_kit")
    @Expose
    private String seminarKit;

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getBayar() {
        return bayar;
    }

    public void setBayar(String bayar) {
        this.bayar = bayar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }

    public String getAlamatLengkap() {
        return alamatLengkap;
    }

    public void setAlamatLengkap(String alamatLengkap) {
        this.alamatLengkap = alamatLengkap;
    }

    public String getNomorKta() {
        return nomorKta;
    }

    public void setNomorKta(String nomorKta) {
        this.nomorKta = nomorKta;
    }

    public String getWilayah() {
        return wilayah;
    }

    public void setWilayah(String wilayah) {
        this.wilayah = wilayah;
    }

    public String getKota() {
        return kota;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }

    public String getNomorTelepon() {
        return nomorTelepon;
    }

    public void setNomorTelepon(String nomorTelepon) {
        this.nomorTelepon = nomorTelepon;
    }

    public String getNomorWa() {
        return nomorWa;
    }

    public void setNomorWa(String nomorWa) {
        this.nomorWa = nomorWa;
    }

    public String getPasfoto() {
        return pasfoto;
    }

    public void setPasfoto(String pasfoto) {
        this.pasfoto = pasfoto;
    }

    public String getRegistrasi() {
        return registrasi;
    }

    public void setRegistrasi(String registrasi) {
        this.registrasi = registrasi;
    }

    public String getSertifikat() {
        return sertifikat;
    }

    public void setSertifikat(String sertifikat) {
        this.sertifikat = sertifikat;
    }

    public String getSeminarKit() {
        return seminarKit;
    }

    public void setSeminarKit(String seminarKit) {
        this.seminarKit = seminarKit;
    }
}
