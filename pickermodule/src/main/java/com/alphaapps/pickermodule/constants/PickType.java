package com.alphaapps.pickermodule.constants;

/**
 * @Usage: defines the targeted picker type
 * <p>
 * Created by Muhammad Noamany
 * Email: muhammadnoamany@gmail.com
 */
public enum PickType {
    GALLERY_IMAGE(0), GALLERY_VIDEO(1), CAMERA(2);
    private int value;

    PickType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}