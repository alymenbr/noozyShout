package com.alymenbr.noozyshout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

    private TextView txtView;
    private TextView txtViewStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtView = (TextView) findViewById(R.id.textView);
        txtViewStatus = (TextView) findViewById(R.id.textViewStatus);
    }

    @Override
    protected void onResume(){
        updateStatus();

        super.onResume();
    }

    public void updateStatus(){
        txtViewStatus.setText("disabled");

        String enabledAppList = Settings.Secure.getString( this.getContentResolver(), "enabled_notification_listeners");
        boolean isEnabled = enabledAppList.contains( NoozyWatchService.class.getSimpleName() );

        if( isEnabled )
            txtViewStatus.setText("enabled");

    }

    public void buttonClicked(View v){

        if(v.getId() == R.id.btnOpenServices){
            startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
        }
    }
}





