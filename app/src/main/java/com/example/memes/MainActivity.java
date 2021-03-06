package com.example.memes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.memes.adapter.GridViewAdapter;
import com.example.memes.adapter.ViewPagerAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.mob.MobSDK;


import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private List<View> views ;
    private List<TextView> textViewList;
    private View view1;
    private View view2;
    private View view3;
    private View view4;
    private View view5;
    private View view6;
    private View view7;
    private View view8;
    private View view9;
    private ArrayList<ImageItem> selImageList;
    private int maxImgCount = 1;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;
    private OkHttpClient okHttpClient;
    private static String currentIP = StaticVar.currentIP;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MobSDK.submitPolicyGrantResult(true, null);
        StaticVar.mainContext = this;
        okHttpClient = new OkHttpClient().newBuilder().connectTimeout(60, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS).build();
        initViewPager();
        textViewSetOnClickListener();
        selImageList=new ArrayList<>();
        initImagePicker();
    }

    public void initViewPager() {
        viewPager =findViewById(R.id.viewpager);
        views = new ArrayList<>();
        LayoutInflater layoutInflater = getLayoutInflater();
        view1 = layoutInflater.inflate(R.layout.girdview_layout,null);
        view2 = layoutInflater.inflate(R.layout.girdview_layout,null);
        view3 = layoutInflater.inflate(R.layout.girdview_layout,null);
        view4 = layoutInflater.inflate(R.layout.girdview_layout,null);
        view5 = layoutInflater.inflate(R.layout.girdview_layout,null);
        view6 = layoutInflater.inflate(R.layout.girdview_layout,null);
        view7 = layoutInflater.inflate(R.layout.girdview_layout,null);
        view8 = layoutInflater.inflate(R.layout.girdview_layout,null);
        view9 = layoutInflater.inflate(R.layout.girdview_layout,null);

        initGetGridView((GridView) view1,"http://"+currentIP+":8080/picController/findAllPic");
        initGridView((GridView) view2,"http://"+currentIP+":8080/picController/findPicByCid",(long)2);
        initGridView((GridView) view3,"http://"+currentIP+":8080/picController/findPicByCid",(long)3);
        initGridView((GridView) view4,"http://"+currentIP+":8080/picController/findPicByCid",(long)4);
        initGridView((GridView) view5,"http://"+currentIP+":8080/picController/findPicByCid",(long)5);
        initGridView((GridView) view6,"http://"+currentIP+":8080/picController/findPicByCid",(long)6);
        initGridView((GridView) view7,"http://"+currentIP+":8080/picController/findPicByCid",(long)1);
        initGetGridView((GridView)view8,"http://"+currentIP+":8080/picController/querydownload");
        initGridView2((GridView) view9,"http://"+currentIP+":8080/picController/findPicByUid",StaticVar.currentUserID);


        views.add(view1);
        views.add(view2);
        views.add(view3);
        views.add(view4);
        views.add(view5);
        views.add(view6);
        views.add(view7);
        views.add(view8);
        views.add(view9);

        viewPager.setAdapter(new ViewPagerAdapter(views));
        viewPager.setCurrentItem(0);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==0){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            initGetGridView((GridView) view1,"http://"+currentIP+":8080/picController/findAllPic");
                        }
                    });
                }
                else if(position==1){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            initGridView((GridView) view2,"http://"+currentIP+":8080/picController/findPicByCid",(long)2);
                        }
                    });
                }
                else if(position==2){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            initGridView((GridView) view3,"http://"+currentIP+":8080/picController/findPicByCid",(long)3);
                        }
                    });
                }
                else if(position==3){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            initGridView((GridView) view4,"http://"+currentIP+":8080/picController/findPicByCid",(long)4);
                        }
                    });
                }
                else if(position==4){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            initGridView((GridView) view5,"http://"+currentIP+":8080/picController/findPicByCid",(long)5);
                        }
                    });
                }
                else if(position==5){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            initGridView((GridView) view6,"http://"+currentIP+":8080/picController/findPicByCid",(long)6);
                        }
                    });
                }
                else if(position==6){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            initGridView((GridView) view7,"http://"+currentIP+":8080/picController/findPicByCid",(long)1);
                        }
                    });
                }
                else if(position==7){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            initGetGridView((GridView)view8,"http://"+currentIP+":8080/picController/querydownload");
                        }
                    });
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void initGridView(final GridView gridView, String url, Long cid)  {
        final Gson gson = new Gson();
        RequestBody requestBody = new FormBody.Builder()
                .add("cid",String.valueOf(cid))
                .build();
        Request request = new Request.Builder().url(url).post(requestBody).build();
        final String[] res = new String[1];
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("here", e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e("here","post????????????");
                res[0] = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Type type = new TypeToken<List<PictureEntity>>(){}.getType();
                        List<PictureEntity> pictureEntityList = gson.fromJson(res[0],type);
                        GridViewAdapter gridViewAdapter = new GridViewAdapter(MainActivity.this);
                        gridViewAdapter.setImages(pictureEntityList);
                        gridView.setAdapter(gridViewAdapter);
                    }
                });
            }
        });


    }

    public void initGridView2(final GridView gridView, String url, Long uid)  {
        final Gson gson = new Gson();
        RequestBody requestBody = new FormBody.Builder()
                .add("uid",String.valueOf(uid))
                .build();
        Request request = new Request.Builder().url(url).post(requestBody).build();
        final String[] res = new String[1];
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("here", e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e("here","post????????????");
                res[0] = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Type type = new TypeToken<List<PictureEntity>>(){}.getType();
                        List<PictureEntity> pictureEntityList = gson.fromJson(res[0],type);
                        GridViewAdapter gridViewAdapter = new GridViewAdapter(MainActivity.this);
                        gridViewAdapter.setImages(pictureEntityList);
                        gridView.setAdapter(gridViewAdapter);
                    }
                });
            }
        });


    }

    public void initGetGridView(final GridView gridView, String url)  {
        final Gson gson = new Gson();
        Request request = new Request.Builder().url(url).build();
        final String[] res = new String[1];
        System.out.println("initGridView??????");
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("onFailure??????");
                Toast.makeText(MainActivity.this,"??????????????????ip",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("onResponse??????");
                res[0] = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Type type = new TypeToken<List<PictureEntity>>(){}.getType();
                        List<PictureEntity> pictureEntityList = gson.fromJson(res[0],type);
                        GridViewAdapter gridViewAdapter = new GridViewAdapter(MainActivity.this);
                        gridViewAdapter.setImages(pictureEntityList);
                        gridView.setAdapter(gridViewAdapter);
                    }
                });
            }
        });
    }

    public void textViewSetOnClickListener(){
        textViewList = new ArrayList<>();
        textViewList.add((TextView) findViewById(R.id.All));
        textViewList.add((TextView)findViewById(R.id.PandaHead));
        textViewList.add((TextView)findViewById(R.id.Dinosaur));
        textViewList.add((TextView)findViewById(R.id.Hamster));
        textViewList.add((TextView)findViewById(R.id.Penguin));
        textViewList.add((TextView)findViewById(R.id.Cat));
        textViewList.add((TextView)findViewById(R.id.other));
        textViewList.add((TextView)findViewById(R.id.rklist));
        textViewList.add((TextView)findViewById(R.id.favorite));
        for(int i =0 ; i<textViewList.size();i++){
            final int finalI = i;
            textViewList.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewPager.setCurrentItem(finalI);
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addPic:
                ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
                Intent intent1 = new Intent(MainActivity.this, ImageGridActivity.class);
                startActivityForResult(intent1, REQUEST_CODE_SELECT);
                break;
            default:
        }
            return true;
    }

    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //?????????????????????
        imagePicker.setShowCamera(true);                      //??????????????????
        imagePicker.setCrop(true);                            //?????????????????????????????????
        imagePicker.setSaveRectangle(true);                   //??????????????? ????????????
        imagePicker.setSelectLimit(1);                         //??????????????????
        imagePicker.setMultiMode(false);                      //??????
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //??????????????????
        imagePicker.setFocusWidth(1000);                       //?????????????????????????????????????????????????????????????????????
        imagePicker.setFocusHeight(1000);                      //?????????????????????????????????????????????????????????????????????
        imagePicker.setOutPutX(1000);                         //????????????????????????????????????
        imagePicker.setOutPutY(1000);                         //????????????????????????????????????
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            //??????????????????
            if (data != null && requestCode == REQUEST_CODE_SELECT) {
                final ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images != null){
                    selImageList.addAll(images);
                    RequestBody requestBody = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("file", "file",
                                    RequestBody.create(MediaType.parse("multipart/form-data"), new File(images.get(0).path)))
                            .build();
                    Request request = new Request.Builder().url("http://"+currentIP+":8080/picController/addPic").post(requestBody).build();
                    Call call = okHttpClient.newCall(request);
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.e("????????????",e.toString());
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            Log.e("tag","????????????");
                            Looper.prepare();
                            Toast ts = Toast.makeText(MainActivity.this,"????????????",Toast.LENGTH_LONG);
                            ts.show();
                            Looper.loop();
                            images.clear();
                            selImageList.clear();
                        }
                    });

                }
            }
        } else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
            //??????????????????
            if (data != null && requestCode == REQUEST_CODE_PREVIEW) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
                if (images != null){
                    selImageList.clear();
                    selImageList.addAll(images);
                }
            }
        }
    }

}
