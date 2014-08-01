package com.kpbird.nlsexample;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

public class NLService extends NotificationListenerService {

    private String TAG = this.getClass().getSimpleName();
    private NLServiceReceiver nlservicereciver;
    @Override
    public void onCreate() {
        super.onCreate();
        nlservicereciver = new NLServiceReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.kpbird.nlsexample.NOTIFICATION_LISTENER_SERVICE_EXAMPLE");
        registerReceiver(nlservicereciver,filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(nlservicereciver);
    }

    
    private String packageName = "com.dogsbark.noozy";
    private String lastMusic = "";    
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {

    	if(packageName.equals(sbn.getPackageName()) ){

            String artist = sbn.getNotification().extras.getString(Notification.EXTRA_TEXT);
            String music = sbn.getNotification().extras.getString(Notification.EXTRA_TITLE);   
            
            if( !lastMusic.equals(music) ){
            	// App Main Activity
                Intent i = new  Intent("com.kpbird.nlsexample.NOTIFICATION_LISTENER_EXAMPLE");        
                i.putExtra("notification_event","onNotificationPosted :" + sbn.getPackageName() + "\n" +  artist + "\n" + music);
                sendBroadcast(i);    
                
                // My Pebble!
                Intent pebble = new Intent("com.android.music.metachanged"); 
                pebble.putExtra("artist", artist); 
                pebble.putExtra("album", ""); 
                pebble.putExtra("track", music); 
                sendBroadcast(pebble); 
            }
    	}
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
/*    	
        Log.i(TAG,"********** onNOtificationRemoved");
        Log.i(TAG,"ID :" + sbn.getId() + "\t" + sbn.getNotification().tickerText +"\t" + sbn.getPackageName());
        Intent i = new  Intent("com.kpbird.nlsexample.NOTIFICATION_LISTENER_EXAMPLE");
        i.putExtra("notification_event","onNotificationRemoved :" + sbn.getPackageName() + "\n");

        sendBroadcast(i);
*/        
    }

    class NLServiceReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getStringExtra("command").equals("clearall")){
                    NLService.this.cancelAllNotifications();
            }
            else if(intent.getStringExtra("command").equals("list")){
                Intent i1 = new  Intent("com.kpbird.nlsexample.NOTIFICATION_LISTENER_EXAMPLE");
                i1.putExtra("notification_event","=====================");
                sendBroadcast(i1);
                int i=1;
                for (StatusBarNotification sbn : NLService.this.getActiveNotifications()) {
                    Intent i2 = new  Intent("com.kpbird.nlsexample.NOTIFICATION_LISTENER_EXAMPLE");
                    i2.putExtra("notification_event",i +" " + sbn.getPackageName() + "\n");
                    sendBroadcast(i2);
                    i++;
                }
                Intent i3 = new  Intent("com.kpbird.nlsexample.NOTIFICATION_LISTENER_EXAMPLE");
                i3.putExtra("notification_event","===== Notification List ====");
                sendBroadcast(i3);

            }

        }
    }

}
