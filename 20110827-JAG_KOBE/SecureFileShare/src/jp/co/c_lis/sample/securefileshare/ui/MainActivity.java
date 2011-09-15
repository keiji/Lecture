
package jp.co.c_lis.sample.securefileshare.ui;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import jp.co.c_lis.sample.securefileshare.R;
import jp.co.c_lis.sample.securefileshare.provider.ImageProvider;
import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity implements OnClickListener {
    private static final String LOG_TAG = "MainActivity";

    private static final String TARGET_FILE_NAME = "testimage";

    private File mSaveTo = null;

    /*
     * (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        findViewById(R.id.btn_show_picture).setOnClickListener(this);

        // SDカードへ保存
        // downloadToExternal();

        // アプリケーション内部に保存
        downloadToInternal();
    }

    private void downloadToExternal() {
        mSaveTo = new File(Environment.getExternalStorageDirectory(), TARGET_FILE_NAME);
        downloadFile(getAssets(), TARGET_FILE_NAME, mSaveTo);
    }

    private void downloadToInternal() {
        mSaveTo = new File(getDir("pictures", MODE_PRIVATE), TARGET_FILE_NAME);
        downloadFile(getAssets(), TARGET_FILE_NAME, mSaveTo);
    }

    private void showPicture() {
        String targetPathName = mSaveTo.getAbsolutePath();

        Intent intent = new Intent(this, ViewerActivity.class);
        intent.putExtra(ViewerActivity.KEY_FILE_NAME, targetPathName);
        startActivity(intent);
    }

    private void showPictureWithActionView() {
        String targetPathName = mSaveTo.getAbsolutePath();

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse("file://" + targetPathName), "image/jpeg");

        startActivity(intent);
    }

    private void showPictureWithContentProvider() {
        String targetPathName = mSaveTo.getName();

        Log.d(LOG_TAG, targetPathName);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(
                Uri.parse("content://" + ImageProvider.AUTHORITY + "/" + targetPathName),
                "image/jpeg");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION
                | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        startActivity(intent);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_show_picture:
                // showPicture();
                // showPictureWithActionView();
                showPictureWithContentProvider();
                break;
        }
    }

    /**
     * ファイルをダウンロードして保存.
     * 
     * @param am
     * @param fileName
     * @param saveTo
     */
    private static void downloadFile(AssetManager am, String fileName, File saveTo) {

        InputStream in = null;
        OutputStream out = null;
        if (!saveTo.exists()) {
            try {
                saveTo.createNewFile();

                in = am.open(fileName);
                out = new FileOutputStream(saveTo);
                byte[] buffer = new byte[512];

                int len = 0;
                while ((len = in.read(buffer)) > -1) {
                    out.write(buffer, 0, len);
                }
            } catch (IOException e) {
                Log.d(LOG_TAG, "IOException", e);
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        Log.d(LOG_TAG, "IOException", e);
                    }
                }
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        Log.d(LOG_TAG, "IOException", e);
                    }
                }
            }
            Log.d(LOG_TAG, "complete");
        }
    }
}
