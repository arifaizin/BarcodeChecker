package id.co.imastudio1.barcodevote;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import id.co.imastudio1.barcodevote.rest.ApiService;
import id.co.imastudio1.barcodevote.rest.Client;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataActivity extends AppCompatActivity {

    private static final String TAG = "DataActivity";
    public static final String DATA_ID = "id";
    public static final String DATA_NAMA = "nama";
    public static final String DATA_FOTO = "foto";
    private static final String ID_SHEET = "1Ds_P7-PAFD4RuV9aG-EHJbsyJ7CiKK13BxzJca-fGCo";
    private TextView tvDataNama;
    private TextView tvDataId;
    private ImageView ivDataFoto;
    //private String dataid, datanama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        initView();

        String dataid = getIntent().getStringExtra(DATA_ID);
        String datanama = getIntent().getStringExtra(DATA_NAMA);
        String datafoto = getIntent().getStringExtra(DATA_FOTO);

        tvDataId.setText(dataid);
        tvDataNama.setText(datanama);
        Picasso.with(DataActivity.this).load("https://drive.google.com/thumbnail?id="+datafoto).placeholder(R.drawable.profile2).error(R.drawable.profile2).into(ivDataFoto);

        updateData(dataid, datanama);
    }

    private void updateData(String dataid, String datanama) {
        ApiService api = Client.getApiService();
        api.updatedata(ID_SHEET, dataid, datanama, "1")
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Log.d(TAG, "onResponse: " + response.toString());
                        Log.d(TAG, "onResponse: " + call.toString());
                        Log.d(TAG, "onResponse: " + response.body().toString());
                        Log.d(TAG, "onResponse: " + response.message().toString());
                        Log.d(TAG, "onResponse: " + response.code());
                        if (response.isSuccessful()) {
                            Toast.makeText(DataActivity.this, "Berhasil Update", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(DataActivity.this, "Response Not Succesfull", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(DataActivity.this, "Error koneksi", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void initView() {
        tvDataNama = (TextView) findViewById(R.id.tv_data_nama);
        tvDataId = (TextView) findViewById(R.id.tv_data_id);
        ivDataFoto = (ImageView) findViewById(R.id.iv_data_foto);
    }
}
