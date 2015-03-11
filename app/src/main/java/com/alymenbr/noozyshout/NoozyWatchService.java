package com.alymenbr.noozyshout;

import android.app.Notification;
import android.content.Intent;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

public class NoozyWatchService extends NotificationListenerService {

    private String packageName = "com.dogsbark.noozy";
    private String lastMusic = "";    
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {

    	if(packageName.equals(sbn.getPackageName()) ){

            String artist = sbn.getNotification().extras.getString(Notification.EXTRA_TEXT);
            String music = sbn.getNotification().extras.getString(Notification.EXTRA_TITLE);   
            
            if( !lastMusic.equals(music) ){

                // Tell my Pebble!
                Intent pebble = new Intent("com.android.music.metachanged"); 
                pebble.putExtra("artist", artist); 
                pebble.putExtra("album", ""); 
                pebble.putExtra("track", music); 
                sendBroadcast(pebble); 
            }
    	}
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification statusBarNotification) {
        // Do nothing :D
    }
}
