package com.example.divvy.Controllers;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImageSelector {

    private static double MAX_LINEAR_DIMENSION = 500;

    public static Bitmap getBitmapForUri(ContentResolver contentResolver, Uri imageUri){
        Bitmap bitmap = null;
        try{
            bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri);

        }catch(IOException e){
            e.printStackTrace();
        }
        return bitmap;
    }

    public static void selectImage(Activity activity){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*");
        activity.startActivityForResult(intent, 1);
    }

    public static Bitmap scaleImage(Bitmap bitmap){
        int originalHeight = bitmap.getHeight();
        int originalWidth = bitmap.getWidth();

        double scaleFactor = MAX_LINEAR_DIMENSION / (double)(originalHeight + originalWidth);

        if(scaleFactor < 1.0){
            int targetWidth = (int) Math.round(originalWidth * scaleFactor);
            int targetHeight = (int) Math.round(originalHeight * scaleFactor);
            return Bitmap.createScaledBitmap(bitmap, targetWidth, targetHeight, true);
        }else{
            return bitmap;
        }
    }

    public static Uri savePhotoImage(Bitmap bitmap, Activity activity){
        File photoFile = null;
        try{
            photoFile = createImageFile(activity);
        }catch (IOException e){
            e.printStackTrace();
        }

        if(photoFile == null){
            Log.d("ERROR: ", "Error creating media file");
            return null;
        }

        try{
            FileOutputStream fos = new FileOutputStream(photoFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, fos);
            fos.close();
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        return Uri.fromFile(photoFile);
    }

    public static File createImageFile(Activity activity) throws IOException{
        String timeStamp = new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date());
        String imageFileNamePrefix = "Divvy-" + timeStamp;
        File storageDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File imageFile = File.createTempFile(imageFileNamePrefix, ".jpg", storageDir);
        return imageFile;
    }

    public static Bitmap getBitmap(Intent data, Activity activity){
        Uri uri = data.getData();
        Bitmap bitmap = getBitmapForUri(activity.getContentResolver(), uri);
        Bitmap resizedBitmap = scaleImage(bitmap);
        if(bitmap != resizedBitmap){
            savePhotoImage(resizedBitmap, activity);
            bitmap = resizedBitmap;
        }
        return bitmap;
    }
}
