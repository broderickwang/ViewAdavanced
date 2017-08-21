package marc.com.viewadavanced.util;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

/**
 * Created by 王成达
 * Date: 2017/8/21
 * Time: 16:40
 * Version: 1.0
 * Description:三阶贝塞尔曲线的估值器
 * Email:wangchengda1990@gmail.com
 **/
public class BezierEvaluator implements TypeEvaluator<PointF> {
	@Override
	public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
		final float t = fraction;
		float oneMinusT = 1.0f - t;
		PointF point = new PointF();

		PointF point0 = (PointF)startValue;

		PointF point1 = new PointF();
		point1.set(320, 0);

		PointF point2 = new PointF();
		point2.set(0, 320);

		PointF point3 = (PointF)endValue;

		point.x = oneMinusT * oneMinusT * oneMinusT * (point0.x)
				+ 3 * oneMinusT * oneMinusT * t * (point1.x)
				+ 3 * oneMinusT * t * t * (point2.x)
				+ t * t * t * (point3.x);

		point.y = oneMinusT * oneMinusT * oneMinusT * (point0.y)
				+ 3 * oneMinusT * oneMinusT * t * (point1.y)
				+ 3 * oneMinusT * t * t * (point2.y)
				+ t * t * t * (point3.y);
		return point;
	}
}
