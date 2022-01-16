package com.alphaapps.androidpickerapp.ui.picked_details;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.alphaapps.androidpickerapp.R;
import com.alphaapps.androidpickerapp.databinding.ActivityPickedDetailsBinding;
import com.alphaapps.pickermodule.data.ResultData;

/**
 * Created by Muhammad Noamany
 * Email: muhammadnoamany@gmail.com
 */
public class PickedDetailsActivity extends AppCompatActivity {
    private ActivityPickedDetailsBinding binding;

    public static void start(Context context, Bitmap resultBitmap) {
        Intent intent = new Intent(context, PickedDetailsActivity.class);
        intent.putExtra("resultBitmap", resultBitmap);
        context.startActivity(intent);
    }

    public static void start(Context context, ResultData resultData) {
        Intent intent = new Intent(context, PickedDetailsActivity.class);
        intent.putExtra("resultData", resultData);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_picked_details);
        checkExtras();
    }

    private void checkExtras() {
        Bitmap resultBitmap = getIntent().getExtras().getParcelable("resultBitmap");
        ResultData resultData = getIntent().getExtras().getParcelable("resultData");
        if (resultBitmap != null)
            binding.imageContainer.setImageBitmap(resultBitmap);
        else if (resultData != null)
            binding.imageContainer.setImageBitmap(resultBitmap);
        binding.setDataModel(resultData);

    }
}
