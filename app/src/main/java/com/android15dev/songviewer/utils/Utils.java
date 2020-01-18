package com.android15dev.songviewer.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.webkit.MimeTypeMap;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Utils {

    public static String convertDate(String date) {
        String inputPattern = "yyyy-MM-dd'T'HH:mm:ss'Z'";
        String outputPattern = "yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date convertedDate = null;
        String convertedDateString = "";

        try {
            convertedDate = inputFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (convertedDate != null) {
            convertedDateString = outputFormat.format(convertedDate);
        }
        return convertedDateString;
    }

    public static String convertTime(long milliseconds) {
        SimpleDateFormat outputFormat = new SimpleDateFormat("mm:ss");
        outputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return outputFormat.format(new Date(milliseconds));
    }

    public static String getMimeType(Context context, String url) {
        String extension;
        Uri uri = Uri.parse(url);
        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            ContentResolver contentResolver = context.getContentResolver();
            final MimeTypeMap mime = MimeTypeMap.getSingleton();
            extension = mime.getExtensionFromMimeType(contentResolver.getType(uri));

        } else {
            extension = MimeTypeMap.getFileExtensionFromUrl(uri.toString());
        }
        return extension;
    }
}
