package marc.com.viewadavanced.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by 王成达
 * Date: 2017/8/11
 * Time: 09:04
 * Version: 1.0
 * Description:
 * Email:wangchengda1990@gmail.com
 **/
public class CircleRoateView extends View {

	String[] colors = {"#FFDEAD","#FF83FA","#FAFAD2","#9400D3","#6C7B8B","#0000EE"};

	private Paint mPaint;
	private float mR;

	private int mRoateAngle = 0;
	private int mScaler = 0;

	private float mAngle = (float) (Math.PI/3);

	public CircleRoateView(Context context) {
		this(context,null);
	}

	public CircleRoateView(Context context, @Nullable AttributeSet attrs) {
		this(context, attrs,0);
	}

	public CircleRoateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		mPaint = new Paint();
		mPaint.setAntiAlias(true);

		ValueAnimator animator =  ValueAnimator.ofInt(0,360);
		animator.setDuration(36*100);
		animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				setAngle((Integer) animation.getAnimatedValue());
			}
		});
		animator.setInterpolator(new LinearInterpolator());
		animator.setRepeatCount(ValueAnimator.INFINITE);
		animator.setRepeatMode(ValueAnimator.RESTART);
		animator.start();


	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.translate(getWidth()/2,getHeight()/2);

		mR = Math.min(getWidth(),getHeight())/4;

		canvas.rotate(mRoateAngle);
		float scale;
		if(mRoateAngle>180){
			scale = 1 - (float)mRoateAngle/180f; //(1-0)
		}else{
			int roateX = mRoateAngle - 180;
			scale = (float) roateX / 180f;
		}
		Log.d("TAG", "onAnimationUpdate: "+mRoateAngle+"---"+scale);
		canvas.scale(scale,scale);
		for (int i=0;i<6;i++){
			mPaint.setColor(Color.parseColor(colors[i]));
			float x = (float) (Math.cos(mAngle*i)*mR);
			float y = (float) (Math.sin(mAngle*i)*mR);
			canvas.drawCircle(x,y,mR/8,mPaint);
		}
	}

	public void setAngle(int angle){
		this.mRoateAngle = angle;
		invalidate();
	}
}
