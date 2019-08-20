package com.example.ass_andorid_net;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Objects;

public class ImageDetailActivity extends AppCompatActivity {
    FloatingActionMenu floatingActionMenu;
    FloatingActionButton fbtnSetAs, fbtnShare, fbtnSave, fbtnFavorites;
    int width, height;
    String link;
    private ImageView mImgImageDetail;
    private DisplayMetrics displayMetrics;
    private ProgressDialog progressBar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);

        mImgImageDetail = findViewById(R.id.imgImageDetail);
        floatingActionMenu = findViewById(R.id.fab);
        fbtnFavorites = findViewById(R.id.fbtnFavorites);
        fbtnSave = findViewById(R.id.fbtnSaveImage);
        fbtnSetAs = findViewById(R.id.fbtnSetAs);
        fbtnShare = findViewById(R.id.fbtnShare);

        fbtnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, String.valueOf(mImgImageDetail.getDrawable()));
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });

        fbtnSetAs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetScreenWidthHeight();
                final Bitmap bitmapImg = ((BitmapDrawable) mImgImageDetail.getDrawable()).getBitmap();
                final WallpaperManager wallManager = WallpaperManager.getInstance(getApplicationContext());
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        // On Android N and above use the new API to set both the general system wallpaper and
                        // the lock-screen-specific wallpaper
                        wallManager.setBitmap(bitmapImg, null, true, WallpaperManager.FLAG_SYSTEM);
                    } else {
                        wallManager.setBitmap(bitmapImg);

                    }
                } catch (IOException ex) {
                }
            }
        });

        fbtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DownloadFileFromURL(ImageDetailActivity.this).execute(link);
            }
        });

        fbtnFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ImageDetailActivity.this, "Favorites", Toast.LENGTH_SHORT).show();
            }
        });
        GetItem();
    }

    private void GetItem() {
        link = getIntent().getStringExtra("imageContent");
        Picasso.get().load(link)
                .placeholder(R.drawable.backgroundnavi)
                .error(R.drawable.backgroundnavi)
                .into(mImgImageDetail);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void GetScreenWidthHeight() {
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR2) {
            displayMetrics = new DisplayMetrics();
            width = displayMetrics.widthPixels;
            height = displayMetrics.heightPixels;
        } else {
            displayMetrics = new DisplayMetrics();
            Point size = new Point();
            WindowManager windowManager = getWindowManager();
            windowManager.getDefaultDisplay().getSize(size);
            width = size.x;
            height = size.y;
        }
    }
//    private class DownloadImage extends AsyncTask<String, Void, Bitmap> {
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            // Create a progressdialog
//            progressBar = new ProgressDialog(ImageDetailActivity.this);
//            // Set progressdialog title
//            progressBar.setTitle("Download Image");
//            // Set progressdialog message
//            progressBar.setMessage("Loading...");
//            progressBar.setIndeterminate(false);
//            // Show progressdialog
//            progressBar.show();
//        }
//
//        @Override
//        protected Bitmap doInBackground(String... URL) {
//
//            String imageURL = URL[0];
//
//            Bitmap bitmap = null;
//            try {
//                // Download Image from URL
//                InputStream input = new java.net.URL(imageURL).openStream();
//                // Decode Bitmap
//                bitmap = BitmapFactory.decodeStream(input);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return bitmap;
//        }
//
//        @Override
//        protected void onPostExecute(Bitmap result) {
//            // Set the bitmap into ImageView
////            image.setImageBitmap(result);
//            // Close progressdialog
//            Toast.makeText(ImageDetailActivity.this, "Dowload Successful", Toast.LENGTH_SHORT).show();
//            progressBar.dismiss();
//        }
//    }

    class DownloadFileFromURL extends AsyncTask<String, Integer, String> {
        public Context context;

        public DownloadFileFromURL(Context context) {
            this.context = context;
        }

        /**
         * Before starting background thread
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            System.out.println("Starting download");
            progressBar = new ProgressDialog(ImageDetailActivity.this);
            progressBar.setMessage("Loading... Please wait...");
            progressBar.setIndeterminate(false);
            progressBar.setCancelable(false);
            progressBar.show();
        }

        /**
         * Downloading file in background thread
         */
        @Override
        protected String doInBackground(String... f_url) {
            int count;
            String param = f_url[0];
            try {
                String root = Environment.getExternalStorageDirectory().toString();
                URL url = new URL(param);
                URLConnection connection = url.openConnection();
                connection.connect();
                int lenghtOfFile = connection.getContentLength();
                // input stream to read file - with 8k buffer
                InputStream input = new BufferedInputStream(url.openStream(), 8192);
                // Output stream to write file
                int start = link.length()-10;
                int end = link.length();

                OutputStream output = new FileOutputStream(root + "/" + link.substring(start, end));

                byte data[] = new byte[1024];

                long total = 0;
                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
                    publishProgress((int) (total * 100 / lenghtOfFile));
                    // writing data to file
                    output.write(data, 0, count);
                }
                // flushing output
                output.flush();
                // closing streams
                output.close();
                input.close();

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }
            return null;
        }

        /**
         * After completing background task
         **/
        @Override
        protected void onPostExecute(String file_url) {
            progressBar.dismiss();
            Toast.makeText(context, "Download Success", Toast.LENGTH_SHORT).show();
        }

    }
}