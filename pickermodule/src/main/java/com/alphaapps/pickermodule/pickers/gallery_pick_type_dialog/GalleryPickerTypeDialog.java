package com.alphaapps.pickermodule.pickers.gallery_pick_type_dialog;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.alphaapps.pickermodule.R;
import com.alphaapps.pickermodule.callbacks.IGalleryPickType;
import com.alphaapps.pickermodule.databinding.DialogGalleryPickTypeBinding;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

/**
 * @usage: Helps to identify the gallery picker target ( Images or Videos )
 * @Author: Muhammad Noamany
 * @Date: 1/16/2022
 * @Email: muhammadnoamany@gmail.com
 */
public class GalleryPickerTypeDialog extends BottomSheetDialog {
    private IGalleryPickType iGalleryPickType;
    private DialogGalleryPickTypeBinding binding;

    public GalleryPickerTypeDialog(@NonNull Context context, IGalleryPickType iGalleryPickType) {
        super(context, R.style.BottomSheetDialogTheme);
        this.iGalleryPickType = iGalleryPickType;
        setDialogView();
    }

    /**
     * Init the sheet dialog view
     */
    private void setDialogView() {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.dialog_gallery_pick_type, null, false);
        setContentView(binding.getRoot());
        setListeners();
    }

    /**
     * Handle pick types listeners
     */
    private void setListeners() {
        binding.imagePicker.setOnClickListener(v -> {
            iGalleryPickType.onImagePick();
            dismiss();
        });
        binding.videoPicker.setOnClickListener(v -> {
            iGalleryPickType.onVideoPick();
            dismiss();
        });
    }


    @Override
    public void show() {
        super.show();
        View view = findViewById(R.id.design_bottom_sheet);
        BottomSheetBehavior behavior = BottomSheetBehavior.from(view);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }
}
