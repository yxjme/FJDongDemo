package com.example.fjdongdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    int minHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         minHeight = dpToPx(50);
        maxSearchViewScaleX = dpToPx(120);
        initView();
    }

    NestedScrollView mNestedScrollView;
    FrameLayout topView;
    LinearLayout lay_search;
    LinearLayout lay_add ;

    private void initView() {
        topView = findViewById(R.id.topView);
        lay_search = findViewById(R.id.lay_search);
        lay_add = findViewById(R.id.lay_add);
        mNestedScrollView = findViewById(R.id.mNestedScrollView);
        mNestedScrollView.setOnScrollChangeListener(onScrollChangeListener);
    }




    public int maxDy = 200 ;
    public int maxSearchViewScaleX ;


    private NestedScrollView.OnScrollChangeListener onScrollChangeListener= new NestedScrollView.OnScrollChangeListener() {
        @Override
        public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
            float scale = (float) (scrollY*1.0 / maxDy);
            if (scale>1) scale = 1 ;
            updateHeight(scale);
        }
    };


    /**
     * 实时更新高度
     * @param scale
     */
    public void updateHeight(float scale){
        if (topView==null)return;
        float currentH = (1-scale) * minHeight ;

        /*当前总高度*/
        ViewGroup.LayoutParams p = topView.getLayoutParams();
        p.height = (int) currentH+minHeight;
        topView.setLayoutParams(p);

        /*当前搜索框距离顶顶实时高度*/
        ViewGroup.MarginLayoutParams marginLayoutParams= (ViewGroup.MarginLayoutParams) lay_search.getLayoutParams();
        marginLayoutParams.topMargin = (int) currentH;
        marginLayoutParams.width = (int) (maxSearchViewScaleX * (1-scale) + (screenWidth()-maxSearchViewScaleX));
        lay_search.setLayoutParams(marginLayoutParams);

        lay_add.setAlpha(1-scale);

    }





    public int dpToPx(int dp){
        float density =getResources().getDisplayMetrics().density;
        float px =  dp * density + 0.5f;
        return (int) px;
    }


    public int screenWidth(){
        return getResources().getDisplayMetrics().widthPixels;
    }
}
