package marc.com.viewadavanced.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v7.view.menu.MenuAdapter;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by 王成达
 * Date: 2017/8/22
 * Time: 09:25
 * Version: 1.0
 * Description:
 * Email:wangchengda1990@gmail.com
 **/
public class MoMoLoadView extends View {

	private Paint mPaint;
	private int mDistance;
	private int mRadius = 20;
	private float mAngle = 0;
	private float PI = (float) Math.PI;

	public MoMoLoadView(Context context) {
		this(context,null);
	}

	public MoMoLoadView(Context context, @Nullable AttributeSet attrs) {
		this(context, attrs,0);
	}

	public MoMoLoadView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setColor(Color.RED);

		ValueAnimator animator = ValueAnimator.ofFloat(0,(float) Math.PI);
		animator.setDuration(9*1000);
		animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				setAngle((Float) animation.getAnimatedValue());
			}
		});
		animator.setInterpolator(new LinearInterpolator());
		/*animator.setRepeatCount(ValueAnimator.INFINITE);
		animator.setRepeatMode(ValueAnimator.RESTART);*/
		animator.start();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.translate(getWidth()/2,getHeight()/2);
		float scale = 0.0f;
		// 0 -> PI 0->PI/2 放大 PI/2->PI 缩小
		if(mAngle > PI/2){
			scale = 2 -  mAngle/(PI);//2-1
		}else{
			scale = 1 +  mAngle/(PI/2);//1-2
		}
		Log.d("TAG", "onDraw: scale->"+scale);

		canvas.scale(scale,scale);

		mDistance = getWidth()/8;

		canvas.drawCircle((float) (-(mDistance/2)*Math.cos(mAngle)),(float) (-(mDistance/2)*Math.sin(mAngle)),mRadius,mPaint);
		canvas.drawCircle((float) ((mDistance/2)*Math.cos(mAngle)),(float) ((mDistance/2)*Math.sin(mAngle)),mRadius,mPaint);
		canvas.drawCircle((float) (-(mDistance/2+mDistance)*Math.cos(mAngle)),(float) (-(mDistance/2+mDistance)*Math.sin(mAngle)),mRadius,mPaint);
		canvas.drawCircle((float) ((mDistance/2+mDistance)*Math.cos(mAngle)),(float) ((mDistance/2+mDistance)*Math.sin(mAngle)),mRadius,mPaint);
	}

	public void setAngle(float angle){
		this.mAngle = angle;
		invalidate();
	}
}
