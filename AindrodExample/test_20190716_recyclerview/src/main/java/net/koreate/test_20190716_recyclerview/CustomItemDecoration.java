package net.koreate.test_20190716_recyclerview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

public class CustomItemDecoration extends RecyclerView.ItemDecoration {
    Context context;

    public CustomItemDecoration(Context context){
        this.context = context;
    }


    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int index = parent.getChildAdapterPosition(view)+1;
        if(index % 3 ==0){
            // left - top - right - bottom
            outRect.set(20,20,20,60);
        }else{
            outRect.set(20,20,20,20);
        }

        view.setBackgroundColor(context.getResources().getColor(R.color.over_lay));
        ViewCompat.setElevation(view,20);

    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);

        int width = parent.getWidth();
        int height = parent.getHeight();

        System.out.println("전체 넓이와 높이 : " + width+"/"+height);

        Drawable dr
        = ResourcesCompat.getDrawable(context.getResources(),R.drawable.puppy,null);

        int drWidth = dr.getIntrinsicWidth();
        int drHeight = dr.getIntrinsicHeight();
        System.out.println("이미지 넓이와 높이 : "+drWidth+" / "+drHeight);

        int left = (width/2)-(drWidth/2);
        int top =  (height/2)-(drHeight/2);


        Bitmap b = BitmapFactory.decodeResource(context.getResources(),R.drawable.puppy);
        c.drawBitmap(b,left,top,null);
    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

        int width = parent.getWidth();
        int height = parent.getHeight();

        System.out.println("전체 넓이와 높이 : " + width+"/"+height);

        Drawable dr
                = ResourcesCompat.getDrawable(context.getResources(),R.drawable.android,null);

        int drWidth = dr.getIntrinsicWidth();
        int drHeight = dr.getIntrinsicHeight();
        System.out.println("이미지 넓이와 높이 : "+drWidth+" / "+drHeight);

        int left = (width/2)-(drWidth/2);
        int top =  (height)-(drHeight);


        Bitmap b = BitmapFactory.decodeResource(context.getResources(),R.drawable.android);
        c.drawBitmap(b,left,top,null);
    }
}
