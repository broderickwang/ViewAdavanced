package marc.com.viewadavanced.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by 王成达
 * Date: 2017/8/17
 * Time: 15:33
 * Version: 1.0
 * Description:
 * Email:wangchengda1990@gmail.com
 **/
public class LebalImage extends ImageView {
	private Paint mPaint ;

	private String mText = "星标";

	private Paint mTextPaint;

	public LebalImage(Context context) {
		this(context,null);
	}

	public LebalImage(Context context, @Nullable AttributeSet attrs) {
		this(context, attrs,0);
	}

	public LebalImage(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setColor(Color.RED);

		mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mTextPaint.setColor(Color.WHITE);
		mTextPaint.setTextSize(60);
	}

	@Override
	public void onDrawForeground(Canvas canvas) {
		super.onDrawForeground(canvas);

		Path p = new Path();
		p.moveTo(0,getHeight()/8);
		p.lineTo(getWidth()/5,getHeight()/8);
		p.lineTo(getWidth()/5,getHeight()/4);
		p.lineTo(0,getHeight()/4);
		p.close();
		canvas.drawPath(p,mPaint);

		Rect rect = new Rect();
		mTextPaint.getTextBounds(mText,0,mText.length(),rect);
		int y = rect.bottom-rect.top/2-rect.bottom;
		Log.d("TAG", "onDrawForeground: baseline->"+y);

		canvas.drawText(mText,0,y+(getHeight()/4-getHeight()/8),mTextPaint);
	}
}
