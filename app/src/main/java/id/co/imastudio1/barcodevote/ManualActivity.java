package id.co.imastudio1.barcodevote;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;

import id.co.imastudio1.barcodevote.rest.ApiService;
import id.co.imastudio1.barcodevote.rest.Client;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static id.co.imastudio1.barcodevote.DataActivity.DATA_CERT;
import static id.co.imastudio1.barcodevote.DataActivity.DATA_FOTO;
import static id.co.imastudio1.barcodevote.DataActivity.DATA_ID;
import static id.co.imastudio1.barcodevote.DataActivity.DATA_KIT;
import static id.co.imastudio1.barcodevote.DataActivity.DATA_MENU;
import static id.co.imastudio1.barcodevote.DataActivity.DATA_NAMA;
import static id.co.imastudio1.barcodevote.DataActivity.DATA_REG;
import static id.co.imastudio1.barcodevote.DataActivity.ID_SHEET;
import static id.co.imastudio1.barcodevote.MainActivity.checkConnection;

public class ManualActivity extends AppCompatActivity {

    private EditText edIdBarcode;
    private RadioButton radioReg;
    private RadioButton radioCert;
    private RadioButton radioKit;
    private Button btnCek;
    private RadioGroup radioGroupMenu;
    private ArrayList<BarcodeModel> arrayListData = new ArrayList<>();
    private static final String TAG = "ManualActivity";
    private int REQUEST_CODE = 100;
    private String menu;
    private String dataId;
    private Call<Void> call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual);
        initView();

        cekKoneksi();
        cekPermisi();
        ambilData();
        //ambil menu
        radioGroupMenu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                RadioButton rb = (RadioButton) findViewById(i);
//                Toast.makeText(ManualActivity.this, "ilih "+ rb.getText().toString(), Toast.LENGTH_SHORT).show();
                menu = rb.getText().toString();
            }
        });
        
        btnCek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cekData(edIdBarcode.getText().toString().toUpperCase());
            }
        });

//        ambilData(menu);
    }

    private void cekData(String idBarcode) {
        for (int i = 0; i < arrayListData.size(); i++) {
//            Log.d(TAG, "handleResult: " + arrayListData.get(i).getId() + "=" + rawResult.getText());
            if (arrayListData.get(i).getId().equals(idBarcode)) {
                Log.d(TAG, "handleResult: ada yg sama");
                Log.v(TAG, "Menu : " + menu);
                //menu

                String dataReg = arrayListData.get(i).getRegistrasi();
                String dataCert = arrayListData.get(i).getSertifikat();
                String dataKit = arrayListData.get(i).getSeminarKit();
                dataId = arrayListData.get(i).getId();


                if (menu.equals(DATA_REG)) {
                    String dataygdiambil = arrayListData.get(i).getRegistrasi();
                    tampilkanDialog(dataygdiambil, i, dataReg, dataCert, dataKit);
                } else if (menu.equals(DATA_CERT)) {
                    String dataygdiambil = arrayListData.get(i).getSertifikat();
                    tampilkanDialog(dataygdiambil, i, dataReg, dataCert, dataKit);
                } else if (menu.equals(DATA_KIT)) {
                    String dataygdiambil = arrayListData.get(i).getSeminarKit();
                    tampilkanDialog(dataygdiambil, i, dataReg, dataCert, dataKit);
                }
            }
        }


    }

    private void tampilkanDialog(String dataygdiambil, final int i, String dataReg, String dataCert, String dataKit) {
        Log.d(TAG, "tampilkanDialog: " + dataygdiambil);
        if (dataygdiambil.equals("1")) {
            Log.d(TAG, "Sudah: " + dataygdiambil);
            Toast.makeText(this, "Maaf, Anda sudah mengambil ini!", Toast.LENGTH_SHORT).show();
        } else if (dataygdiambil.equals("0")) {
            Log.d(TAG, "Belum : ");

            final Dialog dialog = new Dialog(ManualActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setContentView(R.layout.dialog_benar);

            dialog.show();
            Button btnScanLagi = (Button) dialog.findViewById(R.id.btn_scan_lagi);
            btnScanLagi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
//                    onResume();
                    // If you would like to resume scanning, call this method below:
                }
            });
            Button btnLihatDetail = (Button) dialog.findViewById(R.id.btn_detail);
            final int finalI = i;
            btnLihatDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                            Toast.makeText(ManualActivity.this, "Data Ditemukan", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    Intent pindah = new Intent(ManualActivity.this, DataActivity.class);
                    pindah.putExtra(DATA_ID, arrayListData.get(finalI).getId());
                    pindah.putExtra(DATA_NAMA, arrayListData.get(finalI).getNamaLengkap());
                    pindah.putExtra(DATA_FOTO, arrayListData.get(finalI).getPasfoto());
                    pindah.putExtra(DATA_MENU, menu);
                    startActivity(pindah);
                }
            });

            LottieAnimationView animationView = (LottieAnimationView) dialog.findViewById(R.id.animation_view);
            animationView.setAnimation("check.json");
            animationView.playAnimation();


            TextView tvNama = (TextView) dialog.findViewById(R.id.tv_nama);
            TextView tvId = (TextView) dialog.findViewById(R.id.tv_id);
            TextView tvReg = (TextView) dialog.findViewById(R.id.tv_reg);
            TextView tvCert = (TextView) dialog.findViewById(R.id.tv_cert);
            TextView tvKit = (TextView) dialog.findViewById(R.id.tv_kit);
            TextView btnKonfirmasi = (TextView) dialog.findViewById(R.id.btn_konfirmasi);

            tvNama.setText(arrayListData.get(i).getNamaLengkap());
            tvId.setText(arrayListData.get(i).getId());

            if (dataReg.equals("0")) {
                tvReg.setText("Registrasi   : Belum");
            } else {
                tvReg.setText("Registrasi   : Sudah");
            }

            if (dataCert.equals("0")) {
                tvCert.setText("Sertifikat     : Belum");
            } else {
                tvCert.setText("Sertifikat     : Sudah");
            }

            if (dataKit.equals("0")) {
                tvKit.setText("Seminar Kit : Belum");
            } else {
                tvKit.setText("Seminar Kit : Sudah");
            }

            btnKonfirmasi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: " + dataId);
                    dialog.dismiss();
                    updateData(dataId, i);
                }
            });
        } else {
            Toast.makeText(this, "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
        }
        

    }

    private void updateData(final String dataid, final int posisi) {
//        final ProgressDialog progress = new ProgressDialog(ManualActivity.this);
//        progress.setTitle("Loading");
//        progress.setMessage("Mohon Bersabar");
//        progress.show();
        checkConnection(ManualActivity.this);
        ApiService api = Client.getApiService();
//        Call call = api.updatedataReg(ID_SHEET, dataid, "1");
//        Call call = null;
        if (menu.equals(DATA_REG)) {
            call = api.updatedataReg(ID_SHEET, dataid, "1");
        } else if (menu.equals(DATA_CERT)) {
            call = api.updatedataCert(ID_SHEET, dataid, "1");
        } else if (menu.equals(DATA_KIT)) {
            call = api.updatedataKit(ID_SHEET, dataid, "1");
        }
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
//                progress.dismiss();
                Log.d(TAG, "onResponse: " + response.toString());
                Log.d(TAG, "onResponse: " + call.toString());
//                        Log.d(TAG, "onResponse: " + response.body().toString());
//                        Log.d(TAG, "onResponse: " + response.message().toString());
//                        Log.d(TAG, "onResponse: " + response.code());
                if (response.isSuccessful()) {
                    Toast.makeText(ManualActivity.this, "Update Success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ManualActivity.this, "Response Not Succesfull", Toast.LENGTH_SHORT).show();
//  \
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d(TAG, "onFailure: "+ t.getMessage());
                Toast.makeText(ManualActivity.this, "Update Berhasil", Toast.LENGTH_LONG).show();

            }
        });
    }

    private void initView() {
        edIdBarcode = (EditText) findViewById(R.id.ed_id_barcode);
        radioReg = (RadioButton) findViewById(R.id.radioReg);
        radioCert = (RadioButton) findViewById(R.id.radioCert);
        radioKit = (RadioButton) findViewById(R.id.radioKit);
        btnCek = (Button) findViewById(R.id.btn_cek);
        radioGroupMenu = (RadioGroup) findViewById(R.id.radioGroupMenu);
    }


    private void cekKoneksi() {
        if (!checkConnection(ManualActivity.this)) {
            AlertDialog.Builder alert = new AlertDialog.Builder(ManualActivity.this);
            alert.setTitle("Alert").setMessage("No Internet Connection");
            alert.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    cekKoneksi();
                    cekPermisi();
                    ambilData();
                }
            });
        }
    }

    private void ambilData() {
//        final ProgressDialog progress = new ProgressDialog(ManualActivity.this);
//        progress.setTitle("Loading");
//        progress.setMessage("Mohon Bersabar");
//        progress.show();
        ApiService api = Client.getApiService();
        Call<SheetModel> call = api.ambilData();

        call.enqueue(new Callback<SheetModel>() {
            @Override
            public void onResponse(Call<SheetModel> call, Response<SheetModel> response) {

                Integer statusCode = response.code();
                Log.v("status code: ", statusCode.toString());

                arrayListData = response.body().getAnggota();
                for (int i = 0; i < arrayListData.size(); i++) {
                    Log.d(TAG, "Networktask Response nama " + arrayListData.get(i).getNamaLengkap());
                    Log.d(TAG, "Networktask Response reg " + arrayListData.get(i).getRegistrasi());
                }
//                progress.dismiss();
                Toast.makeText(ManualActivity.this, "Data Siap", Toast.LENGTH_SHORT).show();
                //https://github.com/mathemandy/BakingApp/blob/master/app/src/main/java/com/ng/tselebro/bakingapp/recipe/ManualActivity.java
            }

            @Override
            public void onFailure(Call<SheetModel> call, Throwable t) {
                Log.v("http fail: ", t.getMessage());
                Toast.makeText(ManualActivity.this, "http fail: " + t.getMessage(), Toast.LENGTH_LONG).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(ManualActivity.this);
                builder.setTitle("Get Data Failure");
                builder.setMessage("Silahkan coba lagi");
                builder.setPositiveButton("Coba lagi", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
//                        progress.dismiss();
                        ambilData();
                    }
                });
                builder.create().show();
            }
        });
    }

    private void cekPermisi() {
        if (ContextCompat.checkSelfPermission(ManualActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // should we show an explanation
            if (ActivityCompat.shouldShowRequestPermissionRationale(ManualActivity.this, Manifest.permission.CAMERA)) {
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                Toast.makeText(this, "This camera permission is really needed", Toast.LENGTH_SHORT).show();
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(ManualActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CODE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Oops, you denied the permission", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
