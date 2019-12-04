package com.example.cnc_driver.net;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.webkit.MimeTypeMap;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class ImageFirebaseUtils {
    private static StorageReference mStorageReference = FirebaseStorage.getInstance().getReference("Images");
    private static String url_image;

    public static String fileUploader(Context context, Uri uri) {
        StorageReference Ref = mStorageReference.child(System.currentTimeMillis() + "." + getExtension(context,uri));
        Ref.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                url_image = uri.toString();
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        url_image = "https://cdn4.iconfinder.com/data/icons/ui-beast-4/32/Ui-12-512.png";
                    }
                });
        return url_image;
    }

    private static String getExtension(Context context, Uri uri) {
        assert uri != null;
        ContentResolver contentResolver = context.getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
}
