package id.co.imastudio1.barcodevote;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by idn on 10/5/2017.
 */

public class SheetModel {
    @SerializedName("anggota")
    @Expose
    private  ArrayList<BarcodeModel> anggota = new ArrayList<>();

    public  ArrayList<BarcodeModel> getAnggota() {
        return anggota;
    }

    public void setAnggota( ArrayList<BarcodeModel> anggota) {
        this.anggota = anggota;
    }

}
