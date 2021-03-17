package com.myapp.instantfoodsreviewapp.utils;


import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class Const {
    private static final String MULTIPART_FORM_JPG = "image/jpg";
    private static final String MULTIPART_FORM_GIF = "image/gif";
    private static final String FILE_SPLIT_PART = "\\.";
    private static final int MAX_WIDTH = 640;
    private static final int MAX_HEIGHT = 640;

    public static boolean isNullOrEmptyString( String... msgs) {
        for (String item : msgs) {
            if (item == null || item.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    //model : 이메일 형식 체크
    // null empty 공통으로 util로

    public static MultipartBody.Part bitmapConvertToFile(Context context, Bitmap bitmap, int type){
        FileOutputStream fileOutputStream = null;
        File bitmapFile = null;

        try {
            File file = null;
            file = new File(context.getCacheDir(),"");
            if (!file.exists()){
                file.mkdir();
            }

            String fileName = System.currentTimeMillis()+".jpg";
            bitmapFile = new File(file, fileName);

            fileOutputStream = new FileOutputStream(bitmapFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream);
            MediaScannerConnection.scanFile(context,new String[]{bitmapFile.getAbsolutePath()}, null, new MediaScannerConnection.MediaScannerConnectionClient() {
                @Override
                public void onMediaScannerConnected() {

                }

                @Override
                public void onScanCompleted(String path, Uri uri) {

                }
            });
            return Const.prepareFilePart(bitmapFile,type);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            if (fileOutputStream != null){
                try {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static MultipartBody.Part prepareFilePart(File file, int type){
        try {
            String MULTIPART_FORM_DATA = MULTIPART_FORM_JPG;
            String fileName = file.getName();
            int size = fileName.split(FILE_SPLIT_PART).length;
            String extension = fileName.split(FILE_SPLIT_PART)[size-1];
            if(isNullOrEmptyString(fileName) && isContainSmallGif(file)||isContainBigGIF(file)){
                MULTIPART_FORM_DATA = MULTIPART_FORM_GIF;
            }
            RequestBody requestFile = RequestBody.create(file, MediaType.parse(MULTIPART_FORM_DATA));
            MultipartBody.Part result =  MultipartBody.Part.createFormData("files", type + "." + extension, requestFile);
            return result;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isContainSmallGif(File file){
        return file.getName().split(FILE_SPLIT_PART)[1].contains("gif");
    }

    private static boolean isContainBigGIF(File file){
        return file.getName().split(FILE_SPLIT_PART)[1].contains("GIF");
    }

    public static Bitmap resizedThumbnail(Bitmap bitmap, int width, int height) {

        if (width > MAX_WIDTH || height > MAX_HEIGHT) {
            if (width > height) { //가로가 길면
                height *= (640F / width);
                width = (int) 640F;

            } else { //세로가 길면
                width *= (640F / height);
                height = (int) 640F;
            }
        }
        Bitmap convertedBitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
        if (convertedBitmap.getRowBytes() > bitmap.getRowBytes()) {
            return bitmap;
        } else {
            return convertedBitmap;
        }
    }




}
