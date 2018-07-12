package com.example.baizhu.travelloralongthewind;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import java.util.ArrayList;
import java.util.List;
/*
 *
 * 崔杨2018-7-10
 */
public class MainActivity extends Activity implements ViewSwitcher.ViewFactory,
        View.OnTouchListener {

    private ListView listview;

    private List<Thing> lists;//适配器相关

    //    ImageSwitcher imageSwitcher;//动画播放
    //    private int index=0; //图片序号
    //    private int[ ] images;//存放图片id

    //图标图片数组(从数据库取)
    private int[] iconPictures = {R.drawable.a1, R.drawable.a2,
            R.drawable.a3, R.drawable.a4, R.drawable.a5, R.drawable.a6};
    private String[] title = {"abc", "acb", "bac", "bca", "cab", "cba"};//name
    private String[] introduction = {"abc say what??", "acb say what??",
            "bac say what??", "bca say what??", "cab say what??", "cba say what??"};
    // 背景图片数组(从数据库取)
    private int[] arrayPictures = {R.drawable.o1, R.drawable.o2,
            R.drawable.o3};
    // 要显示的图片在图片数组中的Index
    private int pictureIndex;
    // 左右滑动时手指按下的X坐标
    private float touchDownX;
    // 左右滑动时手指松开的X坐标
    private float touchUpX;
    private ImageSwitcher imageSwicher;

    Context context;
    //    Handler handler = new Handler() {
    //        @Override
    //        public void handleMessage(Message msg) {
    //            if (msg.what == 123) {
    //                imageSwitcher.setImageResource(images[msg.arg1]);
    //                //设置动画：如淡入和淡出
    //                imageSwitcher.setInAnimation(AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.fade_in));
    //                imageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.fade_out));
    //
    //            }
    //        }
    //    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //跳转到刘演超的页面
       // Intent intent = new Intent(MainActivity.this,personal_pageActivity.class);
        //Bundle bundle=new Bundle();

        listview = (ListView) findViewById(R.id.listview);
        //初始化数据
        lists = getLists();
        //设置适配器
        listview.setAdapter(new MyAdapter(this, lists));
        //设置监听
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getApplicationContext(), "选中了：" + title[position] + " 他(她)的id=" + id, Toast.LENGTH_SHORT).show();
                //bundle.putString("name", title[position]); //给Bundle添加key-value值对
                //startActivity(intent);
            }
        });

        //线程方法难以控制图片大小，换成手动触发事件
        //创建线程实现动画播放效果
        //        images = new int[]{
        //                R.drawable.o1,
        //                R.drawable.o2,
        //                R.drawable.o3
        //        };
        //        imageSwitcher=(ImageSwitcher) findViewById(R.id.imageSwicher);
        //        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() { //设置工厂
        //            @Override
        //            public View makeView() { //重写makeView
        //                ImageView imageView=new ImageView(MainActivity.this);
        //                imageView.setBackgroundColor(0xFFFFFFFF); //白色背景
        //                imageView.setScaleType(ImageView.ScaleType.CENTER); //居中显示
        //                imageView.setLayoutParams(new ImageSwitcher.LayoutParams(
        //                        ViewGroup.LayoutParams.MATCH_PARENT,
        //                        ViewGroup.LayoutParams.MATCH_PARENT) ); //定义布局
        //                return imageView;
        //            }
        //        });
        //        imageSwitcher.setImageResource(R.drawable.o1); //必须放在工厂后面，否则空指针错
        //
        //        new Thread(new Runnable() {
        //            @Override
        //            public void run() {
        //                while (index <= images.length) {
        //                    Message msg = new Message();
        //                    msg.what = 123;
        //                    msg.arg1 = index;
        //                    handler.sendMessage(msg);
        //                    index++;
        //                    if (index >= images.length) {
        //                        index = 0;
        //                    }
        //                    try {
        //                        Thread.sleep(2000); //暂停2秒继续
        //                    } catch (InterruptedException e) {
        //                        e.printStackTrace();
        //                    }
        //                }
        //            }
        //        }).start();

        imageSwicher = (ImageSwitcher) findViewById(R.id.imageSwicher);

        // 为ImageSwicher设置Factory，用来为ImageSwicher制造ImageView
        imageSwicher.setFactory(this);
        // 设置ImageSwitcher左右滑动事件
        imageSwicher.setOnTouchListener(this);

    }

    //返回数据
    // 添加右上角三个点
    public boolean onCreateOptionsMenu(Menu menu) {
        //这里是调用menu文件夹中的overflow.xml，在登陆界面label右上角的三角里显示其他功能
        getMenuInflater().inflate(R.menu.overflow, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        /*
         * 根据选择的子选项(通过item ID来区分)，进行不同的响应
         *
         * */
        switch (item.getItemId()) {
            case R.id.ITEM1:
                show1();
                break;
            case R.id.ITEM2:
                Toast.makeText(getApplicationContext(), "为实现设置选项TAT", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    private List<Thing> getLists() {

        List<Thing> lists = new ArrayList<Thing>();

        for (int i = 0; i < 20; i++) {

            Thing thing = new Thing();

            thing.setPicture(iconPictures[i % 6]);

            thing.setTitle(title[i % 6]);

            thing.setIntroduce(introduction[i % 6]);

            lists.add(thing);

        }

        return lists;

    }

    @Override
    public View makeView() {
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(arrayPictures[pictureIndex]);
        return imageView;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // 取得左右滑动时手指按下的X坐标
            touchDownX = event.getX();
            return true;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            // 取得左右滑动时手指松开的X坐标
            touchUpX = event.getX();
            // 从左往右，看前一张
            if (touchUpX - touchDownX > 100) {
                // 取得当前要看的图片的index
                pictureIndex = pictureIndex == 0 ? arrayPictures.length - 1
                        : pictureIndex - 1;
                // 设置图片切换的动画
                imageSwicher.setInAnimation(AnimationUtils.loadAnimation(this,
                        android.R.anim.slide_in_left));
                imageSwicher.setOutAnimation(AnimationUtils.loadAnimation(this,
                        android.R.anim.slide_out_right));
                // 设置当前要看的图片
                imageSwicher.setImageResource(arrayPictures[pictureIndex]);
                // 从右往左，看下一张
            } else if (touchDownX - touchUpX > 100) {
                // 取得当前要看的图片的index
                pictureIndex = pictureIndex == arrayPictures.length - 1 ? 0
                        : pictureIndex + 1;
                // 设置图片切换的动画
                // 由于Android没有提供slide_out_left和slide_in_right，所以仿照slide_in_left和slide_out_right编写了slide_out_left和slide_in_right
                //                imageSwicher.setInAnimation(AnimationUtils.loadAnimation(this,
                //                        R.anim.slide_out_left));
                //                imageSwicher.setOutAnimation(AnimationUtils.loadAnimation(this,
                //                        R.anim.slide_in_right));
                // 设置当前要看的图片
                imageSwicher.setImageResource(arrayPictures[pictureIndex]);
            }
            return true;
        }
        return false;

    }

    private void show1() {
        Dialog bottomDialog = new Dialog(this, R.style.BottomAnimDialog);
        View contentView = LayoutInflater.from(this).inflate(R.layout.confirm_dialog, null);
        bottomDialog.setContentView(contentView);
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
        contentView.setLayoutParams(layoutParams);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomAnimDialog_Animation);
        bottomDialog.show();
    }


}
