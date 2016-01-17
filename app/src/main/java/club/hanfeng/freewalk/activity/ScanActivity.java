package club.hanfeng.freewalk.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import club.hanfeng.freewalk.R;

public class ScanActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        String result = getIntent().getStringExtra("result");
        TextView textView = (TextView) findViewById(R.id.tv_scan_result);
        textView.setText(result);

    }


}
