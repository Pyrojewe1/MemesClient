package com.example.memes.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
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
import java.util.Date;
import java.util.List;

import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

public class GridViewAdapter extends BaseAdapter {
    private Context mcontext;
    private List<PictureEntity> images;

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
    public View getView(int position, View convertView, ViewGroup parent) {
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
                builder.setItems(new String[]{"保存图片"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.e("长按测试","123");
                        String sdcard = Environment.getExternalStorageDirectory().toString();

                        File file = new File(sdcard + "/Download");
                        if (!file.exists()) {
                            file.mkdirs();
                        }

                        File imageFile = new File(file.getAbsolutePath(),new Date().getTime()+".jpg");
                        FileOutputStream outStream = null;
                        try {
                            outStream = new FileOutputStream(imageFile);
                            Bitmap image = createBitmapFromByteData(bytes,op);
                            image.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
                            outStream.flush();
                            outStream.close();
                            Toast.makeText(StaticVar.mainContext,"保存成功",Toast.LENGTH_LONG).show();
                        } catch (java.io.IOException e) {
                            e.printStackTrace();
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
                        builder.setItems(new String[]{"保存图片","分享图片"}, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(which == 0) {
                                    Log.e("长按测试", "123");
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
                                        Toast.makeText(StaticVar.mainContext, "保存成功", Toast.LENGTH_LONG).show();
                                    } catch (java.io.IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                else{
                                   showShare(QQ.NAME);
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
        //指定分享的平台，如果为空，还是会调用九宫格的平台列表界面
        if (platform != null) {
            oks.setPlatform(platform);
        }
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("标题");
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl("https://leafee98.com/plik/file/ZWaSCVP9R9dOOKEn/2C767h40KJBKr0iR/regex-help.png");
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        //启动分享
        oks.show(MobSDK.getContext());
    }

}
