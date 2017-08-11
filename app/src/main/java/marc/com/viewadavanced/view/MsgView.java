package marc.com.viewadavanced.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by 王成达
 * Date: 2017/8/11
 * Time: 15:00
 * Version: 1.0
 * Description:
 * Email:wangchengda1990@gmail.com
 **/
public class MsgView extends View {

	private Paint mPaint;

	public MsgView(Context context) {
		this(context,null);
	}

	public MsgView(Context context, @Nullable AttributeSet attrs) {
		this(context, attrs,0);
	}

	public MsgView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setColor(Color.RED);
		mPaint.setStyle(Paint.Style.FILL);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawCircle(300,300,50,mPaint);

		canvas.drawCircle(700,300,50,mPaint);

		Path p = new Path();
		p.moveTo(300,250);
		p.quadTo(500,300,700,250);

		p.lineTo(700,350);
		p.quadTo(500,300,300,350);
		p.close();

		canvas.drawPath(p,mPaint);
	}
}
