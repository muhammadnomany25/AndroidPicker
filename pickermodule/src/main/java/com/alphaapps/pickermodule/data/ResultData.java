package com.alphaapps.pickermodule.data;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Data Class that used to contain the gallery picked file (video or image) detailed info
 *
 * @Author: Muhammad Noamany
 * @Date: 1/13/2022
 * @Email: muhammadnoamany@gmail.com
 */
public class ResultData implements Parcelable {
    private String name, fileSize, filePath;
    private boolean isVideo;

    public ResultData() {
    }


    protected ResultData(Parcel in) {
        name = in.readString();
        fileSize = in.readString();
        filePath = in.readString();
        isVideo = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(fileSize);
        dest.writeString(filePath);
        dest.writeByte((byte) (isVideo ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ResultData> CREATOR = new Creator<ResultData>() {
        @Override
        public ResultData createFromParcel(Parcel in) {
            return new ResultData(in);
        }

        @Override
        public ResultData[] newArray(int size) {
            return new ResultData[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public boolean isVideo() {
        return isVideo;
    }

    public void setVideo(boolean video) {
        isVideo = video;
    }
}
