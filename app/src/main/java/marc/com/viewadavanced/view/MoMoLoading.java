package marc.com.viewadavanced.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by 王成达
 * Date: 2017/8/21
 * Time: 15:31
 * Version: 1.0
 * Description:
 * Email:wangchengda1990@gmail.com
 **/
public class MoMoLoading extends View {
	private int mDistance = 50;
	private Paint mPaint;
	private int mRadius = 30;
	private float mAngle = 0;

	public MoMoLoading(Context context) {
		this(context,null);
	}

	public MoMoLoading(Context context, @Nullable AttributeSet attrs) {
		this(context, attrs,0);
	}

	public MoMoLoading(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setColor(Color.parseColor("#abcdef"));

		ValueAnimator animator = ValueAnimator.ofFloat(0,(float) (Math.PI*2));
		animator.setDuration(9*1000);
		animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				setAngle((Float) animation.getAnimatedValue());
			}
		});
		animator.setInterpolator(new LinearInterpolator());
		animator.setRepeatCount(ValueAnimator.INFINITE);
		animator.setRepeatMode(ValueAnimator.RESTART);
		animator.start();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.save();
		mDistance = getWidth()/8;
		canvas.translate((getWidth()-mDistance)/2,getHeight()/2);
		DrawCircle(canvas);

		canvas.restore();
		canvas.translate((getWidth()+mDistance)/2,getHeight()/2);
		DrawCircle(canvas);

	}

	private void DrawCircle(Canvas canvas){
		canvas.drawCircle((float) (-mDistance*Math.cos(mAngle)), (float) (-mDistance*Math.sin(mAngle)),mRadius,mPaint);
		canvas.drawCircle((float) (mDistance*Math.cos(mAngle)), (float) (mDistance*Math.sin(mAngle)),mRadius,mPaint);
	}


	public void setAngle(float angle){
		this.mAngle = angle;
		invalidate();
	}
}
