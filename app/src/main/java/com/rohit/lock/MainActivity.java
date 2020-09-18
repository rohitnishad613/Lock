package com.rohit.lock;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends Activity {

    public static final int RESULT_ENABLE = 11;
    private DevicePolicyManager devicePolicyManager;
    private ComponentName compName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        devicePolicyManager = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);
        compName = new ComponentName(this, DeviceAdmin.class);
        boolean active = devicePolicyManager.isAdminActive(compName);

        // check if permission allowed or not
        if (active) {
            // lock the screen
            devicePolicyManager.lockNow();

            // close the activity
            finish();

            // exit the application
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);


        } else {
            // request for permission

            Toast.makeText(this, "You need to enable the Admin Device Features", Toast.LENGTH_LONG).show();

            // open settings for user to enable this

            Intent myintent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            myintent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, compName);
            myintent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "We need Admin Device Features to lock (Screen off) your mobile");

            startActivityForResult(myintent, RESULT_ENABLE);


        }
    }

}
