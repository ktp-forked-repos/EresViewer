package com.example.maciek.eresviewer;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class ConfigActivity extends FragmentActivity {

    private static final int NUM_PAGES = 3;

    ArrayList<SubjectFragment> configFragments = new ArrayList<SubjectFragment>();

    private CustomViewPager mPager;

    private PagerAdapter mPagerAdapter;

    private ProgressBar progressBar;

    private Button back, next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (CustomViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        back = (Button) findViewById(R.id.nav_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });
        next = (Button) findViewById(R.id.nav_next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goNext();
            }
        });
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setProgress(100 / NUM_PAGES);
    }

    private void goNext() {
        if (mPager.getCurrentItem() == NUM_PAGES) {
            return;
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() + 1);
            progressBar.incrementProgressBy(100 / NUM_PAGES);
        }
    }

    private void goBack(){
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
            progressBar.incrementProgressBy(-100 / NUM_PAGES);
        }
    }

    @Override
    public void onBackPressed() {
        DialogInterface.OnClickListener discardButtonClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                };
        showUnsavedChangesDialog(discardButtonClickListener);
    }

    private void showUnsavedChangesDialog(
            DialogInterface.OnClickListener discardButtonClickListener) {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the positive and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Na pewno chcesz wyjść stąd?");
        builder.setPositiveButton("Uwolnij mnie", discardButtonClickListener);
        builder.setNegativeButton("Zostaję", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Keep editing" button, so dismiss the dialog
                // and continue editing the pet.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void greyOutButton(Button button) {
        button.setAlpha(0.5f);
        button.setEnabled(false);
    }

    private void enableButton(Button button) {
        button.setAlpha(1);
        button.setEnabled(true);
    }


    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
                switch(position) {
                    case 0:
                        return new ConfigOneFragment();
                    case 1:
                        return new ConfigTwoFragment();
                    case 2:
                        return new ConfigThreeFragment();
                    default:
                        return null;
                }
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }


}
