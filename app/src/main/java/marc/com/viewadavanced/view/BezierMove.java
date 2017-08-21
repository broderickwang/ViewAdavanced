package marc.com.viewadavanced.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import marc.com.viewadavanced.util.BezierEvaluator;

/**
 * Created by 王成达
 * Date: 2017/8/21
 * Time: 16:55
 * Version: 1.0
 * Description:
 * Email:wangchengda1990@gmail.com
 **/
public class BezierMove extends View {

	private PointF mPoint;
	private Paint mPaint;

	public BezierMove(Context context) {
		this(context,null);
	}

	public BezierMove(Context context, @Nullable AttributeSet attrs) {
		this(context, attrs,0);
	}

	public BezierMove(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setColor(Color.BLUE);
		mPoint = new PointF(10,10);
		ValueAnimator animator = ValueAnimator.ofObject(new BezierEvaluator()
				,new PointF(10,10),new PointF(400,800));
		animator.setDuration(6000);
		animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				setPoint((PointF) animation.getAnimatedValue());
			}
		});
		animator.start();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawCircle(mPoint.x,mPoint.y,10,mPaint);
	}

	public void setPoint(PointF point){
		this.mPoint = point;
		invalidate();
	}
}
