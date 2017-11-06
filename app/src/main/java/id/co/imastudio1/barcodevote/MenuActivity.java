package id.co.imastudio1.barcodevote;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView ivReg;
    private ImageView ivCert;
    private ImageView ivKit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });


        initView();
        ivReg.setOnClickListener(this);
        ivCert.setOnClickListener(this);
        ivKit.setOnClickListener(this);
    }

    private void initView() {
        ivReg = (ImageView) findViewById(R.id.iv_reg);
        ivCert = (ImageView) findViewById(R.id.iv_cert);
        ivKit = (ImageView) findViewById(R.id.iv_kit);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case  R.id.iv_reg: {
                // do something for button 1 click
                Intent pindah = new Intent(this, MainActivity.class);
                pindah.putExtra("MENU", "reg");
                startActivity(pindah);
                break;
            }

            case R.id.iv_cert: {
                // do something for button 2 click
                Intent pindah = new Intent(this, MainActivity.class);
                pindah.putExtra("MENU", "cert");
                startActivity(pindah);
                break;
            }

            case R.id.iv_kit: {
                // do something for button 2 click
                Intent pindah = new Intent(this, MainActivity.class);
                pindah.putExtra("MENU", "kit");
                startActivity(pindah);
                break;
            }
            //.... etc
        }
    }
}
