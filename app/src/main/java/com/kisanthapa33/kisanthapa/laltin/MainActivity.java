package com.kisanthapa33.kisanthapa.laltin;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity {

    private CardView mLargeCircularCardView;
    private ToggleButton mSwitchButton;
    private TextView mTextPowerOnMessage;
    private ImageView mInfoButton;

    //Button Animator
    //private Animator circleGrow;

    //Animation instances
    private RotateAnimation rotateAnimation;
    private ObjectAnimator objectAnimator;

    //Camera Feature
    private boolean hasCameraFeature;

    //Camera permission code
    //private static final int REQUEST_CAMERA_PERMISSION_CODE = 11;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLargeCircularCardView = findViewById(R.id.circularCardView);

        mSwitchButton = findViewById(R.id.switchButton);
        mTextPowerOnMessage = findViewById(R.id.txtPowerOnMessage);
        mInfoButton = findViewById(R.id.btnInfo);

        //Checking camera feature available
        hasCameraFeature = getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        mSwitchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (hasCameraFeature) {

                    CameraManager mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

                    if (isChecked) mTextPowerOnMessage.setText("ON");

                    else mTextPowerOnMessage.setText("OFF");

                    try {
                        assert mCameraManager != null;
                        String mCameraId = mCameraManager.getCameraIdList()[0];

                        mCameraManager.setTorchMode(mCameraId, isChecked);
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }

                } else
                    showNoFlashError();
            }
        });

//        mSwitchButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(), "Switch On", Toast.LENGTH_SHORT).show();
//
//                //circularGrowCardView(mCircularCardView);
//
////                Camera cam = Camera.open();
////                Camera.Parameters p = cam.getParameters();
////                p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
////                cam.setParameters(p);
////                cam.startPreview();
//
////                checkPermission(Manifest.permission.CAMERA, REQUEST_CAMERA_PERMISSION_CODE);
//
//                if (hasCameraFeature) {
//
//                    CameraManager mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
//
//                    if (true) {
//                        isFlashOn = true;
//                        mTextPowerOnMessage.setText("ON");
//
//                        try {
//                            assert mCameraManager != null;
//                            String mCameraId = mCameraManager.getCameraIdList()[0];
//
//                            mCameraManager.setTorchMode(mCameraId, true);
//                        } catch (CameraAccessException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                } else
//                    showNoFlashError();
//            }
//        });

        mInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(MainActivity.this, "TEST", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, InfoActivity.class));
            }
        });

        animateLargeCardView(mLargeCircularCardView);

        animateSunIcon();

    }

    private void animateSunIcon() {
        rotateAnimation = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(12000);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        ImageView mSunIcon = findViewById(R.id.sunIcon);
        mSunIcon.startAnimation(rotateAnimation);
    }

    private void animateLargeCardView(CardView mCircularCardView) {
        objectAnimator = ObjectAnimator.ofPropertyValuesHolder(
                mCircularCardView,
                PropertyValuesHolder.ofFloat("scaleX", 1.2f),
                PropertyValuesHolder.ofFloat("scaleY", 1.2f));
        objectAnimator.setDuration(2200);

        objectAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        objectAnimator.setRepeatMode(ObjectAnimator.REVERSE);

        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        objectAnimator.start();
    }


    public void showNoFlashError() {
        AlertDialog alert = new AlertDialog.Builder(this)
                .create();
        alert.setTitle("Oops!");
        alert.setMessage("Flash not available in this device...");
        alert.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        alert.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    // Function to check and request permission.
//    public void checkPermission(String permission, int requestCode) {
//        if (ContextCompat.checkSelfPermission(MainActivity.this, permission)
//                == PackageManager.PERMISSION_DENIED) {
//
//            // Requesting the permission
//            ActivityCompat.requestPermissions(MainActivity.this,
//                    new String[]{permission},
//                    requestCode);
//        } else {
//            Toast.makeText(MainActivity.this,
//                    "Permission already granted",
//                    Toast.LENGTH_SHORT).show();
//        }
//    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode,
//                                           @NonNull String[] permissions,
//                                           @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode,
//                permissions,
//                grantResults);
//
//        if (requestCode == REQUEST_CAMERA_PERMISSION_CODE) {
//
//            // Checking whether user granted the permission or not.
//            if (grantResults.length > 0
//                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//                // Showing the toast message
//                Toast.makeText(MainActivity.this,
//                        "Camera Permission Granted",
//                        Toast.LENGTH_SHORT)
//                        .show();
//            } else {
//                Toast.makeText(MainActivity.this,
//                        "Camera Permission Denied",
//                        Toast.LENGTH_SHORT).show();
//            }
//        }
//    }

//    @Override
//    protected void onPause() {
//        if (objectAnimator.isRunning())
//            objectAnimator.end();
//        super.onPause();
//    }
//
//    @Override
//    protected void onDestroy() {
//        if (objectAnimator.isRunning())
//            objectAnimator.end();
//        super.onDestroy();
//    }
//
//    @Override
//    protected void onStop() {
//        if (objectAnimator.isRunning())
//            objectAnimator.end();
//
//        super.onStop();
//    }

//    private void circularGrowCardView(final View view) {
//
//        //float finalRadius = Math.max(view.getWidth(), view.getHeight());
//        final long delay = 2000;
//
//        // create the animator for this view (the start radius is zero)
//        circleGrow = ViewAnimationUtils.createCircularReveal(
//                view,
//                view.getHeight() / 2,
//                view.getWidth() / 2,
//                0,
//                view.getHeight());
//
//        circleGrow.setDuration(delay);
//
//        if (view.getVisibility() == View.INVISIBLE)
//            view.setVisibility(View.VISIBLE);
//
//        circleGrow.start();
//
//        circleGrow.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animation) {
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                //view.setVisibility(View.INVISIBLE);
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animation) {
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animation) {
//            }
//        });
//
//    }
//


}
