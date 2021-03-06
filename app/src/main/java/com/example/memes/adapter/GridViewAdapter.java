package com.example.memes.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Looper;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.memes.MainActivity;
import com.example.memes.PictureEntity;
import com.example.memes.R;
import com.example.memes.StaticVar;
import com.mob.MobSDK;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class GridViewAdapter extends BaseAdapter {
    private Context mcontext;
    public List<PictureEntity> images;
    public List<PictureEntity> images2;

    public GridViewAdapter(Context context){
        this.mcontext = context;
    }


    @Override
    public int getCount() {
        return this.images.size();
    }

    @Override
    public Object getItem(int position) {
        return images.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ImageView imageView ;
        if(convertView == null){
            convertView = LayoutInflater.from(mcontext).inflate(R.layout.gird_view_item, parent, false);
            imageView = convertView.findViewById(R.id.image);
            convertView.setTag(imageView);
        }
        else {
            imageView =(ImageView) convertView.getTag();
        }

        final byte[] bytes = Base64.decode(images.get(position).getPdetails(),Base64.DEFAULT);
        final BitmapFactory.Options op = new BitmapFactory.Options();
        op.inSampleSize = 2;
        imageView.setImageBitmap(createBitmapFromByteData(bytes,op));

        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(StaticVar.mainContext);
                builder.setItems(new String[]{"????????????","????????????"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which ==0) {
                            OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
                            RequestBody requestBody = new FormBody.Builder()
                                    .add("pid", String.valueOf(images.get(position).getPid()))
                                    .build();
                            Request request = new Request.Builder().url("http://" + StaticVar.currentIP + ":8080/picController/download").post(requestBody).build();
                            Call call = okHttpClient.newCall(request);
                            call.enqueue(new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {
                                    Log.e("whats happened", e.toString());
                                }

                                @Override
                                public void onResponse(Call call, Response response) throws IOException {
                                    Log.e("whats happened", response.body().string());
                                }
                            });
                            Log.e("????????????", "123");
                            String sdcard = Environment.getExternalStorageDirectory().toString();

                            File file = new File(sdcard + "/Download");
                            if (!file.exists()) {
                                file.mkdirs();
                            }

                            File imageFile = new File(file.getAbsolutePath(), new Date().getTime() + ".jpg");
                            FileOutputStream outStream = null;
                            try {
                                outStream = new FileOutputStream(imageFile);
                                Bitmap image = createBitmapFromByteData(bytes, op);
                                image.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
                                outStream.flush();
                                outStream.close();
                                Toast.makeText(StaticVar.mainContext, "????????????", Toast.LENGTH_LONG).show();
                            } catch (java.io.IOException e) {
                                e.printStackTrace();
                            }
                        }
                        else{
                            OkHttpClient okHttpClient = new OkHttpClient();
                            RequestBody requestBody = new FormBody.Builder()
                                    .add("uid",StaticVar.currentUserID.toString())
                                    .add("pid",String.valueOf(images.get(position).getPid()))
                                    .build();
                            Request request = new Request.Builder().post(requestBody).url("http://"+StaticVar.currentIP+":8080/CollectController/addCollect")
                                    .build();
                            Call call = okHttpClient.newCall(request);
                            call.enqueue(new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {

                                }

                                @Override
                                public void onResponse(Call call, Response response) throws IOException {
                                    Log.e("what happened",response.body().string());
                                    Looper.prepare();
                                    Toast.makeText(mcontext,"????????????",Toast.LENGTH_LONG).show();
                                    Looper.loop();
                                }
                            });
                        }

                    }
                });
                builder.show();
                return false;
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(StaticVar.mainContext);
                dialog.setContentView(R.layout.image_dialog);
                ImageView imageView1 = dialog.findViewById(R.id.image_dialog);
                imageView1.setImageBitmap(createBitmapFromByteData(bytes,op));
                imageView1.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(StaticVar.mainContext);
                        builder.setItems(new String[]{"????????????","????????????","????????????"}, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(which == 0) {
                                    OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
                                    RequestBody requestBody = new FormBody.Builder()
                                            .add("pid",String.valueOf(images.get(position).getPid()))
                                            .build();
                                    Request request = new Request.Builder().url("http://"+StaticVar.currentIP+":8080/picController/download").post(requestBody).build();
                                    Call call = okHttpClient.newCall(request);
                                    call.enqueue(new Callback() {
                                        @Override
                                        public void onFailure(Call call, IOException e) {
                                            Log.e("what happened",e.toString());
                                        }

                                        @Override
                                        public void onResponse(Call call, Response response) throws IOException {
                                            Log.e("what happened",response.body().string());
                                        }
                                    });
                                    Log.e("????????????", "123");
                                    String sdcard = Environment.getExternalStorageDirectory().toString();

                                    File file = new File(sdcard + "/Download");
                                    if (!file.exists()) {
                                        file.mkdirs();
                                    }

                                    File imageFile = new File(file.getAbsolutePath(), System.currentTimeMillis() + ".jpg");
                                    FileOutputStream outStream = null;
                                    try {
                                        outStream = new FileOutputStream(imageFile);
                                        Bitmap image = createBitmapFromByteData(bytes, op);
                                        image.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
                                        outStream.flush();
                                        outStream.close();
                                        Toast.makeText(StaticVar.mainContext, "????????????", Toast.LENGTH_LONG).show();
                                    } catch (java.io.IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                else if (which == 1){
                                   showShare(QQ.NAME);
                                }
                                else {
                                    OkHttpClient okHttpClient = new OkHttpClient();
                                    RequestBody requestBody = new FormBody.Builder()
                                            .add("uid",StaticVar.currentUserID.toString())
                                            .add("pid",String.valueOf(images.get(position).getPid()))
                                            .build();
                                    Request request = new Request.Builder().post(requestBody).url("http://"+StaticVar.currentIP+":8080/CollectController/addCollect")
                                            .build();
                                    Call call = okHttpClient.newCall(request);
                                    call.enqueue(new Callback() {
                                        @Override
                                        public void onFailure(Call call, IOException e) {

                                        }

                                        @Override
                                        public void onResponse(Call call, Response response) throws IOException {
                                            Log.e("what happened",response.body().string());
                                            Looper.prepare();
                                            Toast.makeText(mcontext,"????????????",Toast.LENGTH_LONG).show();
                                            Looper.loop();
                                        }
                                    });
                                }

                            }
                        });
                        builder.show();
                        return false;
                    }
                });
                dialog.show();
            }
        });
        return convertView;
    }


    public void setImages(List<PictureEntity> images) {
        this.images = images;
    }


    private Bitmap createBitmapFromByteData(byte[] data , BitmapFactory.Options options){

        Bitmap bitmap = null;
        if(options == null){
            bitmap = BitmapFactory.decodeByteArray(data,0, data.length);
        }else{
            bitmap = BitmapFactory.decodeByteArray(data,0, data.length, options);
        }
        return bitmap;
    }

    private void showShare(String platform) {
        final OnekeyShare oks = new OnekeyShare();
        //????????????????????????????????????????????????????????????????????????????????????
        if (platform != null) {
            oks.setPlatform(platform);
        }
        // title???????????????????????????????????????????????????????????????QQ????????????
        oks.setTitle("??????");
        // titleUrl?????????????????????????????????Linked-in,QQ???QQ????????????
        oks.setTitleUrl("http://sharesdk.cn");
        // text???????????????????????????????????????????????????
        oks.setText("??????????????????");
        //???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
        oks.setImageUrl("https://leafee98.com/plik/file/ZWaSCVP9R9dOOKEn/2C767h40KJBKr0iR/regex-help.png");
        // url???????????????????????????????????????????????????
        oks.setUrl("http://sharesdk.cn");
        //????????????
        oks.show(MobSDK.getContext());
    }




}
