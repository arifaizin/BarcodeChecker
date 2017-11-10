package id.co.imastudio1.barcodevote;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import static id.co.imastudio1.barcodevote.DataActivity.DATA_CERT;
import static id.co.imastudio1.barcodevote.DataActivity.DATA_KIT;
import static id.co.imastudio1.barcodevote.DataActivity.DATA_REG;

public class MenuUtamaActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView ivReg, ivCert, ivKit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_utama);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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
                pindah.putExtra("MENU", DATA_REG);
                startActivity(pindah);
                break;
            }

            case R.id.iv_cert: {
                // do something for button 2 click
                Intent pindah = new Intent(this, MainActivity.class);
                pindah.putExtra("MENU", DATA_CERT);
                startActivity(pindah);
                break;
            }

            case R.id.iv_kit: {
                // do something for button 2 click
                Intent pindah = new Intent(this, MainActivity.class);
                pindah.putExtra("MENU", DATA_KIT);
                startActivity(pindah);
                break;
            }
            //.... etc
        }
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
        if (id == R.id.action_manual) {
            startActivity(new Intent(this, ManualActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

}
