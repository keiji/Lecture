
package jp.co.c_lis.sample.securefileshare.provider;

import java.io.File;
import java.io.FileNotFoundException;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.util.Log;

public class ImageProvider extends ContentProvider {

    private static final boolean DEBUG_FLG = false;
    private static final String LOG_TAG = "ContentProvider";

    public static final String AUTHORITY = "jp.co.c_lis.sample.securefileshare.pictures";

    /*
     * (non-Javadoc)
     * @see android.content.ContentProvider#getType(android.net.Uri)
     */
    @Override
    public String getType(Uri uri) {
        String fileName = uri.getLastPathSegment();

        if (DEBUG_FLG) {
            Log.d(LOG_TAG, "file name " + fileName);
        }
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /*
     * (non-Javadoc)
     * @see android.content.ContentProvider#openFile(android.net.Uri,
     * java.lang.String)
     */
    @Override
    public ParcelFileDescriptor openFile(Uri uri, String mode) throws FileNotFoundException {
        String fileName = uri.getLastPathSegment();
        Log.d(LOG_TAG, fileName);

        return ParcelFileDescriptor.open(
                new File(getContext().getDir("pictures", Context.MODE_PRIVATE), fileName),
                ParcelFileDescriptor.MODE_READ_ONLY);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public boolean onCreate() {
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
            String sortOrder) {
        return null;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

}
