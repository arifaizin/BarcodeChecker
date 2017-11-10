package id.co.imastudio1.barcodevote;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import id.co.imastudio1.barcodevote.rest.ApiService;
import id.co.imastudio1.barcodevote.rest.Client;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static id.co.imastudio1.barcodevote.MainActivity.checkConnection;

public class DataActivity extends AppCompatActivity {

    private static final String TAG = "DataActivity";
    public static final String DATA_ID = "id";
    public static final String DATA_NAMA = "nama";
    public static final String DATA_FOTO = "foto";
    public static final String DATA_MENU = "menu";
    public static final String DATA_REG = "Registrasi";
    public static final String DATA_CERT = "Sertifikat";
    public static final String DATA_KIT = "Seminar Kit";
    public static final String ID_SHEET = "1Ds_P7-PAFD4RuV9aG-EHJbsyJ7CiKK13BxzJca-fGCo";
    private TextView tvDataNama;
    private TextView tvDataId;
    private ImageView ivDataFoto;
    String dataid, datanama, datafoto, datamenu;
    private Call<Void> call;
    private Button btnKonfirmasi;
    //private String dataid, datanama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        initView();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        dataid = getIntent().getStringExtra(DATA_ID);
        datanama = getIntent().getStringExtra(DATA_NAMA);
        datafoto = getIntent().getStringExtra(DATA_FOTO);
        datamenu = getIntent().getStringExtra(DATA_MENU);

        tvDataId.setText(dataid);
        tvDataNama.setText(datanama);
            Picasso.with(DataActivity.this).load("https://drive.google.com/thumbnail?id=" + datafoto).placeholder(R.drawable.profile2).error(R.drawable.profile2).into(ivDataFoto);

        btnKonfirmasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateData(dataid);
            }
        });
//
    }

    private void initView() {
        tvDataNama = (TextView) findViewById(R.id.tv_data_nama);
        tvDataId = (TextView) findViewById(R.id.tv_data_id);
        ivDataFoto = (ImageView) findViewById(R.id.iv_data_foto);
        btnKonfirmasi = (Button) findViewById(R.id.btn_konfirmasi);
    }

    private void updateData(final String dataid) {
        final ProgressDialog progress = new ProgressDialog(DataActivity.this);
        progress.setTitle("Loading");
        progress.setMessage("Mohon Bersabar");
        progress.show();
        checkConnection(DataActivity.this);
        ApiService api = Client.getApiService();
//        Call call = api.updatedataReg(ID_SHEET, dataid, "1");
//        Call call = null;
        if (datamenu.equals(DATA_REG)) {
            call = api.updatedataReg(ID_SHEET, dataid, "1");
        } else if (datamenu.equals(DATA_CERT)) {
            call = api.updatedataCert(ID_SHEET, dataid, "1");
        } else if (datamenu.equals(DATA_KIT)) {
            call = api.updatedataKit(ID_SHEET, dataid, "1");
        }
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                progress.dismiss();
                Log.d(TAG, "onResponse: " + response.toString());
                Log.d(TAG, "onResponse: " + call.toString());

                if (response.isSuccessful()) {
                    finish();
                    Toast.makeText(DataActivity.this, "Update Success", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(DataActivity.this, "Response Not Succesfull", Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder builder = new AlertDialog.Builder(DataActivity.this);
                    builder.setTitle("Post Data Not Succesfull");
                    builder.setMessage("Silahkan coba lagi");
                    builder.setPositiveButton("Coba lagi", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                            updateData(dataid);
                        }
                    });
                    builder.create().show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                progress.dismiss();
//                        Toast.makeText(DataActivity.this, "http fail: " + t.getMessage(), Toast.LENGTH_LONG).show();
                Log.d(TAG, "onFailure: " + t.getMessage());
                finish();
                Toast.makeText(DataActivity.this, "Update Berhasil", Toast.LENGTH_LONG).show();

            }
        });
    }
}
