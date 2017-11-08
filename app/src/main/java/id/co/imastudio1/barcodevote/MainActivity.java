package id.co.imastudio1.barcodevote;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.zxing.Result;

import java.util.ArrayList;

import id.co.imastudio1.barcodevote.rest.ApiService;
import id.co.imastudio1.barcodevote.rest.Client;
import me.dm7.barcodescanner.zxing.ZXingScannerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static id.co.imastudio1.barcodevote.DataActivity.DATA_FOTO;
import static id.co.imastudio1.barcodevote.DataActivity.DATA_ID;
import static id.co.imastudio1.barcodevote.DataActivity.DATA_MENU;
import static id.co.imastudio1.barcodevote.DataActivity.DATA_NAMA;

public class MainActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private static final String TAG = "MainActivity";
    private static final int REQUEST_CODE = 21;
    private ZXingScannerView mScannerView;
    private ArrayList<BarcodeModel> arrayListData = new ArrayList<>();
    String menu;
    private static final String ID_SHEET = "1Ds_P7-PAFD4RuV9aG-EHJbsyJ7CiKK13BxzJca-fGCo";
    private String dataId;
    private Call<Void> call;


    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);// Set the scanner view as the content view

        menu = getIntent().getStringExtra("MENU");
        cekKoneksi();
        cekPermisi();
        ambilData(menu);

    }

    private void cekKoneksi() {
        if (!checkConnection(MainActivity.this)) {
            AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
            alert.setTitle("Alert").setMessage("No Internet Connection");
            alert.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    cekKoneksi();
                    cekPermisi();
                    ambilData(menu);
                }
            });
        }
    }

    private void ambilData(final String menu) {
        final ProgressDialog progress = new ProgressDialog(MainActivity.this);
        progress.setTitle("Loading");
        progress.setMessage("Mohon Bersabar");
        progress.show();
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
                progress.dismiss();
                Toast.makeText(MainActivity.this, "Siap Scan", Toast.LENGTH_SHORT).show();
                //https://github.com/mathemandy/BakingApp/blob/master/app/src/main/java/com/ng/tselebro/bakingapp/recipe/MainActivity.java
            }

            @Override
            public void onFailure(Call<SheetModel> call, Throwable t) {
                Log.v("http fail: ", t.getMessage());
                Toast.makeText(MainActivity.this, "http fail: " + t.getMessage(), Toast.LENGTH_LONG).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Get Data Failure");
                builder.setMessage("Silahkan coba lagi");
                builder.setPositiveButton("Coba lagi", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        progress.dismiss();
                        ambilData(menu);
                    }
                });
                builder.create().show();
            }
        });
    }

    private void cekPermisi() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // should we show an explanation
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.CAMERA)) {
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                Toast.makeText(this, "This camera permission is really needed", Toast.LENGTH_SHORT).show();
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CODE);
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

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
//        ambilData(menu);
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here
        Log.v(TAG, rawResult.getText()); // Prints scan results
        Log.v(TAG, rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode, pdf417 etc.)

//        Toast.makeText(this, "ID: "+rawResult.getText(), Toast.LENGTH_SHORT).show();
        for (int i = 0; i < arrayListData.size(); i++) {
//            Log.d(TAG, "handleResult: " + arrayListData.get(i).getId() + "=" + rawResult.getText());
            if (arrayListData.get(i).getId().equals(rawResult.getText())) {
                Log.d(TAG, "handleResult: ada yg sama");
                Log.v(TAG, "Menu : " + menu);
                //menu

                String dataReg = arrayListData.get(i).getRegistrasi();
                String dataCert = arrayListData.get(i).getSertifikat();
                String dataKit = arrayListData.get(i).getSeminarKit();
                dataId = arrayListData.get(i).getId();


                if (menu.equals("reg")) {
                    String dataygdiambil = arrayListData.get(i).getRegistrasi();
                    tampilkanDialog(dataygdiambil, i, dataReg, dataCert, dataKit);
                } else if (menu.equals("cert")) {
                    String dataygdiambil = arrayListData.get(i).getSertifikat();
                    tampilkanDialog(dataygdiambil, i, dataReg, dataCert, dataKit);
                } else if (menu.equals("kit")) {
                    String dataygdiambil = arrayListData.get(i).getSeminarKit();
                    tampilkanDialog(dataygdiambil, i, dataReg, dataCert, dataKit);
                }


            }
        }

    }

    private void tampilkanDialog(String dataygdiambil, final int i, String dataReg, String dataCert, String dataKit) {
        Log.d(TAG, "tampilkanDialog: " + dataygdiambil);
        mScannerView.stopCamera();
        if (dataygdiambil.equals("1")) {
            mScannerView.startCamera();
            Log.d(TAG, "Sudah: " + dataygdiambil);
            Toast.makeText(this, "Maaf, Anda sudah mengambil ini!", Toast.LENGTH_SHORT).show();
        } else if (dataygdiambil.equals("0")) {
            Log.d(TAG, "Belum : ");

            final Dialog dialog = new Dialog(MainActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setContentView(R.layout.dialog_benar);

            dialog.show();
            Button btnScanLagi = (Button) dialog.findViewById(R.id.btn_scan_lagi);
            btnScanLagi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mScannerView.startCamera();
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
//                            Toast.makeText(MainActivity.this, "Data Ditemukan", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    Intent pindah = new Intent(MainActivity.this, DataActivity.class);
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
                    mScannerView.startCamera();
                    updateData(dataId, i);
                }
            });
        }
        mScannerView.resumeCameraPreview(MainActivity.this);

    }

    private void updateData(final String dataid, final int posisi) {
//        final ProgressDialog progress = new ProgressDialog(MainActivity.this);
//        progress.setTitle("Loading");
//        progress.setMessage("Mohon Bersabar");
//        progress.show();
        checkConnection(MainActivity.this);
        ApiService api = Client.getApiService();
//        Call call = api.updatedataReg(ID_SHEET, dataid, "1");
//        Call call = null;
        if (menu.equals("reg")) {
            call = api.updatedataReg(ID_SHEET, dataid, "1");
        } else if (menu.equals("cert")) {
            call = api.updatedataCert(ID_SHEET, dataid, "1");
        } else if (menu.equals("kit")) {
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
                    Toast.makeText(MainActivity.this, "Update Success", Toast.LENGTH_SHORT).show();
//                    final Dialog dialog = new Dialog(MainActivity.this);
//                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                    dialog.setContentView(R.layout.dialog_sudahdikonfirmasi);
//
//                    dialog.show();
//                    Button btnScanLagi = (Button) dialog.findViewById(R.id.btn_scan_lagi);
//                    btnScanLagi.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            dialog.dismiss();
////                    onResume();
//                            // If you would like to resume scanning, call this method below:
//                            mScannerView.resumeCameraPreview(MainActivity.this);
//                        }
//                    });
//                    Button btnLihatDetail = (Button) dialog.findViewById(R.id.btn_detail);
//                    btnLihatDetail.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
////                            Toast.makeText(MainActivity.this, "Data Ditemukan", Toast.LENGTH_SHORT).show();
//                            dialog.dismiss();
//                            Intent pindah = new Intent(MainActivity.this, DataActivity.class);
//                            pindah.putExtra(DATA_ID, arrayListData.get(posisi).getId());
//                            pindah.putExtra(DATA_NAMA, arrayListData.get(posisi).getNamaLengkap());
//                            pindah.putExtra(DATA_FOTO, arrayListData.get(posisi).getPasfoto());
//                            startActivity(pindah);
//                        }
//                    });
//
//                    LottieAnimationView animationView = (LottieAnimationView) dialog.findViewById(R.id.animation_view);
//                    animationView.setAnimation("check.json");
//                    animationView.playAnimation();
//
//
//                    TextView tvNama = (TextView) dialog.findViewById(R.id.tv_nama);
//                    TextView tvId = (TextView) dialog.findViewById(R.id.tv_id);
//
//                    tvNama.setText(arrayListData.get(posisi).getNamaLengkap());
//                    tvId.setText(arrayListData.get(posisi).getId());
                } else {
                    Toast.makeText(MainActivity.this, "Response Not Succesfull", Toast.LENGTH_SHORT).show();
//                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//                    builder.setTitle("Post Data Not Succesfull");
//                    builder.setMessage("Silahkan coba lagi");
//                    builder.setPositiveButton("Coba lagi", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            dialog.dismiss();
//                            updateData(dataid, posisi);
//                        }
//                    });
//                    builder.create().show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
//                progress.dismiss();
//                        Toast.makeText(MainActivity.this, "http fail: " + t.getMessage(), Toast.LENGTH_LONG).show();
                Log.d(TAG, "onFailure: "+ t.getMessage());
                Toast.makeText(MainActivity.this, "Update Berhasil", Toast.LENGTH_LONG).show();
//                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//                builder.setTitle("Post Data Failure");
//                builder.setMessage("Silahkan coba lagi");
//                builder.setPositiveButton("Coba lagi", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        dialog.dismiss();
//                        updateData(dataid, posisi);
//                    }
//                });
//                builder.create().show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static boolean checkConnection(@NonNull Context context) {
        return ((ConnectivityManager) context.getSystemService
                (Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
    }
}
