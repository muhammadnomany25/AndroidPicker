package com.alphaapps.pickermodule.constants;

/**
 * @Usage: defines the targeted picker type
 *
 * Created by Muhammad Noamany
 * Email: muhammadnoamany@gmail.com
 */
public enum PickType {
    GALLERY(0), CAMERA(1);
    private int value;

    PickType(int value) {
        this.value = value;
    }

   public int getValue() {
        return value;
    }
}