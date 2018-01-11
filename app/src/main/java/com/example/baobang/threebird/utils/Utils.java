package com.example.baobang.threebird.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;


import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.Calendar;

public class Utils {
    public static boolean checkInput(String input){
        return (input == null || input.equals(""));
    }

    public static void openDialog(Activity context, String message) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(
                context);
        dialog.setTitle("Thông báo");

        dialog.setMessage(message);
        dialog.setPositiveButton("Ok",null);
        dialog.show();

    }

    public static String BitMapToString(Bitmap bitmap, int quality){

        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,quality, baos);
        byte [] b=baos.toByteArray();

        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    public static String BitMapToString(Bitmap bitmap){

        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    public static Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }

    public static Bitmap getRoundedRectBitmap(Bitmap bitmap) {
        final Bitmap output = Bitmap.createBitmap(Constants.AVATAR_WIDTH, Constants.AVATAR_HEIGHT, Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(output);

        final int color = Color.RED;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, Constants.AVATAR_WIDTH, Constants.AVATAR_HEIGHT);
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawOval(rectF, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        bitmap.recycle();

        return output;
    }

    public static void openDialogChosseImage(final Activity activity) {
        final CharSequence[] items = { "Camera", "Thư viện"};
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Thêm ảnh");
        builder.setItems(items, (dialog, item) -> {
            if (items[item].equals("Camera")) {
                    cameraIntent(activity);
            } else if (items[item].equals("Thư viện")) {
                    galleryIntent(activity);
            }
        });
        builder.show();
    }

    public static void cameraIntent(Activity activity) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        activity.startActivityForResult(intent, Constants.CAMERA_PIC_REQUEST);
    }

    public static void galleryIntent(Activity activity) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        activity.startActivityForResult(Intent.createChooser(intent, "Chọn ảnh"),Constants.SELECT_FILE);
    }

    public static Bitmap getBitmapFromUri(Activity activity, Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                activity.getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor == null ? null : parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        if(parcelFileDescriptor != null){
            parcelFileDescriptor.close();
        }
        return image;
    }

    public static Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }

    public static void getDate(Activity activity, final TextView txt){
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(activity,
                (datePicker, year, monthOfYear, dayOfMonth) -> txt.setText(new StringBuilder(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year)),
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View currentFocus = activity.getCurrentFocus();
        if (currentFocus != null) {
            inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    public static void hideKeyboardOutside(View view, final Activity activity) {
        //Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) view.setOnTouchListener(
                (v, event) -> {
                    Utils.hideSoftKeyboard(activity);
                    return false;
                });
        //If a layout container, iterate over children
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                hideKeyboardOutside(innerView, activity);
            }
        }
    }

}
