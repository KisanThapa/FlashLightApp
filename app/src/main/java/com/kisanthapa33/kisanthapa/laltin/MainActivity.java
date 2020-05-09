package com.kisanthapa33.kisanthapa.laltin;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;


public class MainActivity extends Activity {

    private Camera camera;
    private ImageButton mPowerButton;
    private boolean isFlashOn;
    private boolean hasFlash;
    Camera.Parameters params;
    MediaPlayer mp;


    //App defined int constant
    private int MY_PERMISSIONS_REQUEST_CAMERA = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        mPowerButton = (ImageButton) findViewById(R.id.imageButton);
        // First check if device is supporting flashlight or not
        hasFlash = getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        if (!hasFlash) {
            // device doesn't support flash
            // Show alert message and close the application
            AlertDialog alert = new AlertDialog.Builder(MainActivity.this)
                    .create();
            alert.setTitle("Error");
            alert.setMessage("Sorry, your device doesn't support flash light!");
            alert.setButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            alert.show();
            return;
        }

        // get the camera
        getCamera();

        // displaying button image
        toggleButtonImage();

        // Switch button click event to toggle flash on/off
        mPowerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (isFlashOn) {
                    // turn off flash
                    turnOffFlash();
                } else {
                    // turn on flash
                    turnOnFlash();
                }
            }
        });

    }

    // Get the camera
    private void getCamera() {
        if (camera == null) {
            try {
                camera = Camera.open();
                params = camera.getParameters();
            } catch (RuntimeException e) {
                //Log.e("Camera Error. Failed to Open. Error: ", e.getMessage());
            }
        }
    }

    // Turning On flash
    private void turnOnFlash() {
        if (!isFlashOn) {
            if (camera == null || params == null) {
                return;
            }
            // play sound
            playSound();

            params = camera.getParameters();
            params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(params);
            camera.startPreview();
            isFlashOn = true;

            // changing button/switch image
            toggleButtonImage();
        }

    }


    // Turning Off flash
    private void turnOffFlash() {
        if (isFlashOn) {
            if (camera == null || params == null) {
                return;
            }
            // play sound
            playSound();

            params = camera.getParameters();
            params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(params);
            camera.stopPreview();
            isFlashOn = false;

            // changing button/switch image
            toggleButtonImage();
        }
    }

    // Playing sound
    // will play button toggle sound on flash on / off
    private void playSound() {
        if (isFlashOn) {
            mp = MediaPlayer.create(MainActivity.this, R.raw.on_sound);
        } else {
            mp = MediaPlayer.create(MainActivity.this, R.raw.off_sound);
        }
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                // TODO Auto-generated method stub
                mp.release();
            }
        });
        mp.start();
    }

    /*
     * Toggle switch button images
     * changing image states to on / off
     * */
    private void toggleButtonImage() {
        if (isFlashOn) {
            mPowerButton.setImageResource(R.drawable.on);
        } else {
            mPowerButton.setImageResource(R.drawable.off);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();

        // on pause turn off the flash
        turnOffFlash();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // on resume turn on the flash
        if (hasFlash)
            turnOnFlash();
    }

    @Override
    protected void onStart() {
        super.onStart();

        // on starting the app get the camera params
        getCamera();
    }

    @Override
    protected void onStop() {

        super.onStop();

        // on stop release the camera
        if (camera != null) {
            camera.release();
            camera = null;
        }
    }


    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        AlertDialog.Builder exit_Alert = new AlertDialog.Builder(MainActivity.this);
        exit_Alert.setMessage("Are you sure want to exit?");
        exit_Alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        exit_Alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        AlertDialog dialog = exit_Alert.create();
        dialog.show();
    }

}
