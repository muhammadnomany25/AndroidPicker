package com.alphaapps.androidpickerapp.ui.picked_details;

import android.graphics.Bitmap;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.alphaapps.pickermodule.data.ResultData;

/**
 * @Author: Muhammad Noamany
 * @Date: 1/16/2022
 * @Email: muhammadnoamany@gmail.com
 */
public class DetailsVM extends ViewModel {
    private MutableLiveData<ResultData> pickedFileData = new MutableLiveData<>(); // LiveData object to save picked file data
    private MutableLiveData<Bitmap> takenCameraPhoto = new MutableLiveData<>();// LiveData object to save camera taken photo

    /**
     * Save picked file data to live data object
     */
    public void setPickedFileData(ResultData data) {
        pickedFileData.setValue(data);
    }

    /**
     * Save picked camera photo to live data object
     */
    public void setTakenCameraPhoto(Bitmap bitmap) {
        takenCameraPhoto.setValue(bitmap);
    }

    /**
     * Return liveData object to observe the data
     */
    public MutableLiveData<ResultData> getPickedFileData() {
        return pickedFileData;
    }

    /**
     * Return liveData object to observe the data
     */
    public MutableLiveData<Bitmap> getTakenCameraPhoto() {
        return takenCameraPhoto;
    }
}
