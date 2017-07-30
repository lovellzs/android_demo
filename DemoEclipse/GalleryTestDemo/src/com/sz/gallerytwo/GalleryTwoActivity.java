package com.sz.gallerytwo;

import com.sz.gallery.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class GalleryTwoActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallerytwo);

        Gallery g = (Gallery) findViewById(R.id.gallery0);
        ImageAdapter a = new ImageAdapter(this);
        g.setAdapter(a);

        g = (Gallery) findViewById(R.id.gallery1);
        g.setAdapter(a);
    }

    public static class ImageAdapter extends BaseAdapter {
        private Context mContext;
        public ImageAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return mThumbIds.length;
        }
      //获取图片在库中的位置  
        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new Gallery.LayoutParams(100, 100));
                imageView.setAdjustViewBounds(false);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(18, 18, 18, 18);
            } else {
                imageView = (ImageView) convertView;
            }
          //给ImageView设置资源 
            imageView.setImageResource(mThumbIds[position]);

            return imageView;
        }
      //定义整型数组 即图片源 
        private Integer[] mThumbIds = {
        		R.drawable.img1, R.drawable.img2, R.drawable.img3, R.drawable.img4,
                R.drawable.img5, R.drawable.img6, R.drawable.img7, R.drawable.img8
        };
    }
}