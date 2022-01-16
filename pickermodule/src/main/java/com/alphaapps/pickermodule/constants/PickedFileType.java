package com.alphaapps.pickermodule.constants;

/**
 * @Usage: defines the picked file type
 *
 * Created by Muhammad Noamany
 * Email: muhammadnoamany@gmail.com
 */

public enum PickedFileType {
    IMAGE(0), VIDEO(1);
    private int value;

    PickedFileType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}