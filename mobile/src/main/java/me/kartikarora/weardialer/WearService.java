package me.kartikarora.weardialer;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.WearableListenerService;


/**
 * Developer: chipset
 * Package : me.kartikarora.weardialer
 * Project : WearDialer
 * Date : 1/31/17
 */

public class WearService extends WearableListenerService {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(WearService.class.getSimpleName(), "WEAR create");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(WearService.class.getSimpleName(), "WEAR destroy");
    }

    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {
        super.onDataChanged(dataEvents);
        Log.i(WearService.class.getSimpleName(), "WEAR Data changed ");
    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.i(WearService.class.getSimpleName(), "WEAR Message " + messageEvent.getPath());
        if (messageEvent.getPath().equals("/number")) {
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                startActivity(new Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:" + messageEvent.getPath())).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            } else {
                Toast.makeText(getApplicationContext(), "Calling permission not granted", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        }
        super.onMessageReceived(messageEvent);
    }

    @Override
    public void onPeerConnected(Node peer) {
        super.onPeerConnected(peer);
        Log.i(WearService.class.getSimpleName(), "WEAR Connected ");
    }

    @Override
    public void onPeerDisconnected(Node peer) {
        super.onPeerDisconnected(peer);
        Log.i(WearService.class.getSimpleName(), "WEAR Disconnected");
    }
}
