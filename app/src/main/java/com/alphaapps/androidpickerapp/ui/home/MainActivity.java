package com.alphaapps.androidpickerapp.ui.home;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.alphaapps.androidpickerapp.R;
import com.alphaapps.androidpickerapp.databinding.ActivityMainBinding;
import com.alphaapps.androidpickerapp.ui.base.BaseActivity;
import com.alphaapps.androidpickerapp.ui.picked_details.PickedDetailsActivity;
import com.alphaapps.pickermodule.Picker;
import com.alphaapps.pickermodule.callbacks.IPickerResult;
import com.alphaapps.pickermodule.data.ResultData;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

/**
 * Created by Muhammad Noamany
 * Email: muhammadnoamany@gmail.com
 */
public class MainActivity extends BaseActivity<ActivityMainBinding> implements IPickerResult {
    private Picker picker;

    /**
     * start activity helper method
     */
    public static void start(Context context, Boolean clearTask) {
        Intent intent = new Intent(context, MainActivity.class);
        if (clearTask)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpToolbar(binding.appBarLayout.toolbar, R.drawable.ic_arrow_back, getString(R.string.home), false);
        picker = new Picker(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void onCameraResult(Bitmap bitmap) {
        PickedDetailsActivity.start(this, bitmap);
    }

    @Override
    public void onGalleryResult(ResultData resultObject) {
        PickedDetailsActivity.start(this, resultObject);
    }

    /**
     * On Pick from camera btn clicked
     */
    public void onCameraPickClicked(View view) {
        picker.startCameraPicker();
    }

    /**
     * On Pick from gallery btn clicked
     */
    public void onGalleryPickClicked(View view) {
        picker.startGalleryPicker();
    }

    /**
     * Inflate the settings gear icon on the toolbar
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                showLanguageDialog();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Show change language dialog
     */
    private void showLanguageDialog() {
        new MaterialAlertDialogBuilder(this)
                .setMessage(R.string.change_app_lang_msg)
                .setTitle(R.string.change_app_lang_title)
                .setPositiveButton(R.string.change_app_lang_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onChangeLangBtnClicked(dialog);
                    }

                    private void onChangeLangBtnClicked(DialogInterface dialog) {
                        userSaver.setAppLanguage(isArabic ? "en" : "ar");
                        restart();
                        dialog.dismiss();
                    }
                }).setNegativeButton(R.string.change_app_lang_no, (dialog, which) -> dialog.dismiss())
                .show();
    }

    /**
     * Restart app to refresh locale
     */
    private void restart() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            finish();
            start(this, true);
        } else {
            recreate();
        }
    }
}