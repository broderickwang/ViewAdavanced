package marc.com.viewadavanced.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.RelativeLayout;

import marc.com.viewadavanced.adapter.DianDianBaseAdapter;

/**
 * Created by 王成达
 * Date: 2017/8/24
 * Time: 16:25
 * Version: 1.0
 * Description:
 * Email:wangchengda1990@gmail.com
 **/
public class MoMoDianDianView extends RelativeLayout {

	private DianDianBaseAdapter mAdapter = null;

	private int mMove = 20;

	public MoMoDianDianView(Context context) {
		this(context,null);
	}

	public MoMoDianDianView(Context context, @Nullable AttributeSet attrs) {
		this(context, attrs,0);
	}

	public MoMoDianDianView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		setGravity(CENTER_IN_PARENT);

	}

	private void initView() {
		removeAllViews();
		if (mAdapter != null){
			Log.d("TAG", "initView: "+mAdapter.getCount());
			for(int i=0;i<mAdapter.getCount();i++){
				View v = mAdapter.getView(i,this);
				addView(v);
				v.setTranslationY(-(i*mMove));
			}
		}
	}

	public void setAdapter(DianDianBaseAdapter adapter){
		if(adapter == null){
			throw new RuntimeException("DianDianBaseAdapter is null,please reset it!");
		}
		this.mAdapter = adapter;
		initView();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()){
			case MotionEvent.ACTION_MOVE:
				Log.d("TAG", "onTouchEvent: MOVINGGGGG");
				View v = getChildAt(getChildCount()-1);
				RotateAnimation a = new RotateAnimation(0,90, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
				a.setDuration(1000);
				v.setAnimation(a);
				a.start();
				break;
		}
		return true;
	}
}
