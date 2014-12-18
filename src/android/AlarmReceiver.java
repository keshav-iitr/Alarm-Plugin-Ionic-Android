package com.kk.alarmplugin;

import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.os.SystemClock;
import android.util.Log;
import android.os.Vibrator;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("AlarmPlugin", "AlarmReceived");
        
/*        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        pm.wakeUp(SystemClock.uptimeMillis());
*/
        PowerManager pm = (PowerManager)context.getSystemService(Context.POWER_SERVICE);
        WakeLock wakeLock = pm.newWakeLock((PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP), "TAG");
        wakeLock.acquire();
 
        KeyguardManager keyguardManager = (KeyguardManager)context.getSystemService(Context.KEYGUARD_SERVICE); 
        KeyguardLock keyguardLock =  keyguardManager.newKeyguardLock("TAG");
        keyguardLock.disableKeyguard();

        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(2000);
		Intent alarmIntent = new Intent("android.intent.action.MAIN");

			alarmIntent.setClass(context, AlarmPopUp.class);
			alarmIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			
			 
//			Uri alert = fromFile(File file);
			
			// Pass on the alarm ID as extra data
			alarmIntent.putExtra("AlarmID", intent.getIntExtra("AlarmID", -1));
			alarmIntent.putExtra("AlarmTime",
					intent.getStringExtra("AlarmTime"));

			// Start the popup activity
			context.startActivity(alarmIntent);
   /*
        intent = new Intent();
        intent.setAction("com.uniclau.alarmplugin.ALARM");

        intent.setPackage(context.getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
		*/
    }
}
