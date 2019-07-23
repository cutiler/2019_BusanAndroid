package net.koreate.test_20190709_dialog.util;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import net.koreate.test_20190709_dialog.R;

public class LoadingDialog extends Dialog {

    Context context;

    public LoadingDialog(Context context) {
        super(context);
        this.context = context;
        setLayoutView();
    }

    public LoadingDialog(Context context,int layout) {
        super(context,layout);
        this.context= context;
        setLayoutView();
    }

    public void setLayoutView(){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        setContentView(R.layout.loading_dialog);

        ImageView progress = findViewById(R.id.imageView);

        Glide.with(context).load(R.drawable.main_loading).into(progress);

    }



    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
    }

}
