package marc.com.viewadavanced.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.BounceInterpolator;

/**
 * Created by 王成达
 * Date: 2017/8/11
 * Time: 10:36
 * Version: 1.0
 * Description:拖动曲线
 * Email:wangchengda1990@gmail.com
 **/
public class PathMorphBezier extends View {

	private Paint mPaint;

	private PointF mCotrolPoint;

	public PathMorphBezier(Context context) {
		this(context,null);
	}

	public PathMorphBezier(Context context, @Nullable AttributeSet attrs) {
		this(context, attrs,0);
	}

	public PathMorphBezier(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setColor(Color.BLUE);
		mPaint.setStrokeWidth(5);
		mPaint.setStyle(Paint.Style.STROKE);


	}

	@Override
	protected void onDraw(Canvas canvas) {
//		canvas.translate(getWidth()/2,getHeight()/2);

//		canvas.drawLine(-getWidth()/4,0,getWidth()/4,0,mPaint);
		if(mCotrolPoint == null){
			mCotrolPoint = new PointF(getWidth()/2,getHeight()/2);
		}
		Path p = new Path();
		p.moveTo(getWidth()/4,getHeight()/2);
		p.quadTo(mCotrolPoint.x,mCotrolPoint.y,getWidth()-getWidth()/4,getHeight()/2);
		canvas.drawPath(p,mPaint);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()){
			case MotionEvent.ACTION_DOWN:
//				mCotrolPoint = new PointF(getWidth()/2,getHeight()/2);
				break;
			case MotionEvent.ACTION_MOVE:
				mCotrolPoint.set(getWidth()/2,event.getY());
				Log.d("TAG", "onTouchEvent: "+event.getY());
				invalidate();
				break;
			case MotionEvent.ACTION_UP:
				ValueAnimator animator =  ValueAnimator.ofFloat(mCotrolPoint.y,getHeight()/2);
				animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
					@Override
					public void onAnimationUpdate(ValueAnimator animation) {
						mCotrolPoint.set(getWidth()/2, (Float) animation.getAnimatedValue());
						invalidate();
					}
				});
				animator.setDuration(2000);
				animator.setInterpolator(new BounceInterpolator());
				animator.start();
				break;
		}
		return true;
	}
}
