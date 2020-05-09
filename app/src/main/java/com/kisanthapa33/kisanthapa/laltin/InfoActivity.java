package com.kisanthapa33.kisanthapa.laltin;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class InfoActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);


        //Init Views
        ImageButton mCloseBtn = findViewById(R.id.btnClose);
        ConstraintLayout mPrivacyPolicy = findViewById(R.id.privacyPolicyLayout);
        ConstraintLayout mRateThisApp = findViewById(R.id.rateThisAppLayout);
        ConstraintLayout mShareThisApp = findViewById(R.id.shareThisAppLayout);


        mCloseBtn.setOnClickListener(this);
        mPrivacyPolicy.setOnClickListener(this);
        mRateThisApp.setOnClickListener(this);
        mShareThisApp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnClose:
                finish();
                break;

            case R.id.privacyPolicyLayout:
                String url = "https://kisanthapa.github.io/FlashLightApp/index.html";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                break;

            case R.id.rateThisAppLayout:
                Uri uri = Uri.parse("market://details?id=" + getApplicationContext().getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                // To count with Play market backstack, After pressing back button,
                // to taken back to our application, we need to add following flags to intent.
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://bit.ly/KisanFlashLightApp")));
                }
                break;

            case R.id.shareThisAppLayout:
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Flash Light - 2020");
                    String shareMessage = "\nDownload this ad free and smooth flashlight application.\n\n";
                    shareMessage = shareMessage + "https://bit.ly/KisanFlashLightApp" + " \n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "Choose One"));
                } catch (Exception e) {
                    //e.toString();
                }
                break;

            default:
        }

    }
}
