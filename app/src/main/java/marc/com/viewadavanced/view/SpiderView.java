package marc.com.viewadavanced.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import java.util.List;

import marc.com.viewadavanced.R;
import marc.com.viewadavanced.data.SpiderData;

/**
 * Created by 王成达
 * Date: 2017/8/9
 * Time: 15:06
 * Version: 1.0
 * Description:蜘蛛网视图
 * Email:wangchengda1990@gmail.com
 **/
public class SpiderView extends View {

	private Paint mPaint,mValuePaint,mCirclePaint,mTextPaint;

	private float mRadius,mCenterX,mCenterY;

	private int mCount = 6;

	private float mAngle = (float) (Math.PI*2/mCount);

	private List<SpiderData> mDatas;

	private float mSumValue,mMaxValue=0;

	private int mValueColor = Color.parseColor("#abcdef"),mPointColor=Color.BLACK;
	private int mValueTextSize = 30;

	public SpiderView(Context context) {
		this(context,null);
	}

	public SpiderView(Context context, @Nullable AttributeSet attrs) {
		this(context, attrs,0);
	}

	public SpiderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		TypedArray array = context.obtainStyledAttributes(attrs,R.styleable.SpiderView);

		mValueColor = array.getColor(R.styleable.SpiderView_valueColor,mValueColor);
		mPointColor = array.getColor(R.styleable.SpiderView_pointColor,mPointColor);
		mValueTextSize = array.getDimensionPixelOffset(R.styleable.SpiderView_valueTextSize,mValueTextSize);

		array.recycle();

		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setColor(Color.GRAY);
		mPaint.setStyle(Paint.Style.STROKE);

		mValuePaint = new Paint();
		mValuePaint.setAntiAlias(true);
		mValuePaint.setColor(mValueColor);
		mValuePaint.setAlpha(140);

		mCirclePaint = new Paint();
		mCirclePaint.setAntiAlias(true);
		mCirclePaint.setColor(mPointColor);
		mCirclePaint.setAlpha(170);

		mTextPaint = new Paint();
		mTextPaint.setAntiAlias(true);
		mTextPaint.setColor(Color.BLACK);
		mTextPaint.setTextSize(dip2px(mValueTextSize,context.getResources().getDisplayMetrics()));
	}

	@Override
	protected void onDraw(Canvas canvas) {
		mCenterX = getWidth()/2;
		mCenterY = getHeight()/2;
		canvas.translate(mCenterX,mCenterY);

		mRadius = (float) (Math.min(getWidth()/2,getHeight()/2)*0.8);

		drawPolygon(canvas);

		drawLines(canvas);

		darwData(canvas);

		drawText(canvas);

	}

	private void drawPolygon(Canvas canvas){
		Path p = new Path();
		float r = mRadius/(mCount-1);

		for(int i=1;i<mCount;i++){
			float curR = r*i;
			p.reset();
			for (int j=0;j<mCount;j++){
				if(j == 0){
					p.moveTo(curR,0);
				}else{
					float x = (float) (curR*Math.cos(mAngle*j));
					float y = (float) (curR*Math.sin(mAngle*j));
					p.lineTo(x,y);
				}
			}
			p.close();
			canvas.drawPath(p,mPaint);
		}
	}

	private void drawLines(Canvas canvas){
//		Path p = new Path();
		for (int i=0;i<mCount;i++){
			float x = (float) (mRadius * Math.cos(mAngle*i));
			float y = (float) (mRadius * Math.sin(mAngle*i));
			canvas.drawLine(0,0,x,y,mPaint);
		}
	}

	private void darwData(Canvas canvas){

		Path p = new Path();
		for (int i=0;i<mCount;i++){
			float x = (float) ((mRadius*(mDatas.get(i).getValue()/mMaxValue)) * Math.cos(mAngle*i));
			float y = (float) ((mRadius*(mDatas.get(i).getValue()/mMaxValue)) * Math.sin(mAngle*i));
			if(i == 0){
				p.moveTo(x,y);
			}else{
				p.lineTo(x,y);
			}
			canvas.drawCircle(x,y,10,mCirclePaint);
		}
		p.close();
		canvas.drawPath(p,mValuePaint);
	}

	private void drawText(Canvas canvas){
		Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
		float fontHeight = fontMetrics.descent - fontMetrics.ascent;
		for(int i=0;i<mCount;i++){
			float x = (float) ((mRadius+fontHeight/2)*Math.cos(mAngle*i));
			float y = (float) ((mRadius+fontHeight/2)*Math.sin(mAngle*i));
			if(mAngle*i>=0&&mAngle*i<=Math.PI/2){//第4象限
				canvas.drawText(mDatas.get(i).getName(), x,y,mTextPaint);
			}else if(mAngle*i>=3*Math.PI/2&&mAngle*i<=Math.PI*2){//第3象限
				canvas.drawText(mDatas.get(i).getName(), x,y,mTextPaint);
			}else if(mAngle*i>Math.PI/2&&mAngle*i<=Math.PI){//第2象限
				float dis = mTextPaint.measureText(mDatas.get(i).getName());//文本长度
				canvas.drawText(mDatas.get(i).getName(), x-dis,y,mTextPaint);
			}else if(mAngle*i>=Math.PI&&mAngle*i<3*Math.PI/2){//第1象限
				float dis = mTextPaint.measureText(mDatas.get(i).getName());//文本长度
				canvas.drawText(mDatas.get(i).getName(), x-dis,y,mTextPaint);
			}
		}
	}

	public void setData(List<SpiderData> data) {
		if(null == data || data.size()==0){
			throw new RuntimeException("please set valid values!");
		}
		this.mDatas = data;
		for (SpiderData d : mDatas) {
			mSumValue += d.getValue();
			if(d.getValue() > mMaxValue){
				mMaxValue = d.getValue();
			}
		}
		this.mCount = data.size();

		invalidate();
	}
	public float dip2px(int dip,DisplayMetrics metrics){
		return  TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dip,metrics);
	}
}
