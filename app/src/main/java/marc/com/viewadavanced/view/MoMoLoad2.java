package marc.com.viewadavanced.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by 王成达
 * Date: 2017/8/24
 * Time: 16:22
 * Version: 1.0
 * Description:
 * Email:wangchengda1990@gmail.com
 **/
public class MoMoLoad2 extends View {

	private Paint mPaint;
	private int mRadus = 20;

	private int mDistance = 80;
	private float mScale = 1;

	private float mRoateAngle = (float) (Math.PI/2);

	public MoMoLoad2(Context context) {
		this(context,null);
	}

	public MoMoLoad2(Context context, @Nullable AttributeSet attrs) {
		this(context, attrs,0);
	}

	public MoMoLoad2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setColor(Color.GRAY);

		ValueAnimator animator = ValueAnimator.ofFloat(0,1f);
		animator.setDuration(2000);
		animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				float f = (float) animation.getAnimatedValue();// 0 - 1
				setScale(1-f);
			}
		});
		animator.setRepeatCount(-1);
		animator.setRepeatMode(ValueAnimator.REVERSE);
		animator.start();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.translate(getWidth()/2,getHeight()/2);

		if(mScale == 0){
			canvas.drawCircle(0,0,mRadus,mPaint);
		}else{
			canvas.scale(mScale,mScale);
			canvas.drawCircle(0,0,mRadus,mPaint);
			canvas.drawCircle(mDistance,0,mRadus,mPaint);
			canvas.drawCircle(-mDistance,0,mRadus,mPaint);
			canvas.drawCircle(0,mDistance,mRadus,mPaint);
			canvas.drawCircle(0,-mDistance,mRadus,mPaint);
			canvas.drawCircle(mDistance,-mDistance,mRadus,mPaint);
			canvas.drawCircle(mDistance,mDistance,mRadus,mPaint);
			canvas.drawCircle(-mDistance,-mDistance,mRadus,mPaint);
			canvas.drawCircle(-mDistance,mDistance,mRadus,mPaint);
		}
	}

	public void setScale(float scale){
		this.mScale = scale;
		invalidate();
	}
}
