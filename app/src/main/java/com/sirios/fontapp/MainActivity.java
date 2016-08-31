package com.sirios.fontapp;

import android.Manifest;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Settings.System.canWrite(this)) {
                Toast.makeText(MainActivity.this, "Can write", Toast.LENGTH_SHORT).show();
                writeFont();
            } else {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                startActivityForResult(intent, 10);
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 10) {
            writeFont();
        }
    }

    private void writeFont() {
        ContentResolver resolver = getBaseContext().getContentResolver();
        //verifyStoragePermissions(this);

        Settings.System.putFloat(resolver, Settings.System.FONT_SCALE, 1.0f);

        /*float font = 0.0f;
        try {
            font = Settings.System.getFloat(resolver, Settings.System.FONT_SCALE);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }*/
        /*Configuration config = new Configuration();
        config.fontScale = 2.0f;
        getResources().getConfiguration().setTo(config);*/

        float font = 0.0f;
        try {
            font = Settings.System.getFloat(resolver, Settings.System.FONT_SCALE);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }

        Log.d("Font", String.valueOf(font));
    }

    public static void verifyStoragePermissions(AppCompatActivity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_SETTINGS);
        String[] permissionsToRequest = {Manifest.permission.WRITE_SETTINGS};

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    permissionsToRequest,
                    5000
            );
        }
    }
}
