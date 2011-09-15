
package jp.co.c_lis.sample.securefileshare.ui;

import jp.co.c_lis.sample.securefileshare.R;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

public class ViewerActivity extends Activity {

    public static final String KEY_FILE_NAME = "file_name";

    /*
     * (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.viewer);

        // 表示するファイル名
        String fileName = getIntent().getStringExtra(KEY_FILE_NAME);
        if(fileName != null) {
            
            // ファイルから画像を読み込み
            Bitmap image = BitmapFactory.decodeFile(fileName);
            
            // 画像を表示
            ImageView imageView = (ImageView) findViewById(R.id.iv_image);
            imageView.setImageBitmap(image);
        }
    }
}
