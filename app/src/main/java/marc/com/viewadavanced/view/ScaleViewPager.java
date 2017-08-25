package marc.com.viewadavanced.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by 王成达
 * Date: 2017/8/25
 * Time: 14:47
 * Version: 1.0
 * Description:
 * Email:wangchengda1990@gmail.com
 **/
public class ScaleViewPager extends ViewPager implements ViewPager.PageTransformer {

	private ArrayList<Integer> childCenterXAbs = new ArrayList<>();//子view的X中心点距离父布局X的中心点的绝对距离的存放集合
	private SparseArray<Integer> childIndex = new SparseArray<>();//子view中心点作为key,存放的view最初的索引位置

	private float mReduceX = 0.0f;
	private float mItemWidth = 0;
	private float mOffsetPosition = 0f;

	private float mScaleMax = 1.0f;//最大的放大系数，建议是1.0
	private float mScaleMin = 0.914f;//最小的缩小系数，建议不要和mScaleMax 相差太多
	private float mCoverWidth = 40f;//中间的view覆盖两侧的view的大小


	public ScaleViewPager(Context context) {
		this(context,null);
	}

	public ScaleViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		setPageTransformer(true,this);
		setClipChildren(false);
		/*ViewGroup vg = (ViewGroup) getParent();
		vg.setClipChildren(false);*/
//		setCurrentItem(1,true);

	}

	@Override
	public void setAdapter(PagerAdapter adapter) {
		super.setAdapter(adapter);
		ValueAnimator animator = ValueAnimator.ofInt(0,getAdapter().getCount());
		Log.d("TAG", "onTouchEvent: "+getChildCount());
		animator.setDuration((getAdapter().getCount())*2000);
		animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				Log.d("TAG", "onAnimationUpdate: "+animation.getAnimatedValue());
				setItem((Integer) animation.getAnimatedValue());
			}
		});
		animator.setRepeatCount(-1);
		animator.setInterpolator(new LinearInterpolator());
		animator.setRepeatMode(ValueAnimator.REVERSE);
		animator.start();
	}

	@Override
	protected int getChildDrawingOrder(int childCount, int n) {
		if (n == 0 || childIndex.size() != childCount) {
			childCenterXAbs.clear();
			childIndex.clear();
			//先获得当前控件（ViewPager）的中心点在屏幕的位置
			int viewCenterX = getViewCenterX(this);
			for (int i = 0; i < childCount; ++i) {
				//计算每个子View中心点的X值和父布局（ViewPager）中心点的X值的距离
				int indexAbs = Math.abs(viewCenterX - getViewCenterX(getChildAt(i)));
				//两个距离相同，后来的那个做自增，从而保持abs不同
				if (childIndex.get(indexAbs) != null) {
					++indexAbs;
				}
				childCenterXAbs.add(indexAbs);
				childIndex.append(indexAbs, i);
			}
			Collections.sort(childCenterXAbs);//1,0,2  0,1,2
		}
		//哪个item距离中心点远一些，就先draw它。（最近的就是中间放大的item,最后draw）(n=0，draw第一个，即childCenterXAbs中最大的)
		return childIndex.get(childCenterXAbs.get(childCount - 1 - n));
	}

	/**
	 * 获取view中心点在屏幕上X的位置
	 * @param view
	 * @return
	 */
	private int getViewCenterX(View view) {
		int[] array = new int[2];
		view.getLocationOnScreen(array);
		return array[0] + view.getWidth() / 2;
	}

	@Override
	public void transformPage(View view, float position) {
		if (mOffsetPosition == 0f) {
			float paddingLeft = ScaleViewPager.this.getPaddingLeft();
			float paddingRight = ScaleViewPager.this.getPaddingRight();
			float width = ScaleViewPager.this.getMeasuredWidth();
			mOffsetPosition = paddingLeft / (width - paddingLeft - paddingRight);
		}
		Log.d("TAG", " offsetPosition:"+mOffsetPosition);
		float currentPos = position - mOffsetPosition;
		if (mItemWidth == 0) {
			mItemWidth = view.getWidth();
			//由于左右边的view缩小而与原始的view宽度相比减小的宽度值的一半
			//当mScaleMax = 1.0f时，reduceX =（mScaleMax  - mScaleMin)) * itemWidth / 2.0f;
			mReduceX = (((1.0f - mScaleMax) +(1.0f - mScaleMin)) * mItemWidth / 2.0f);
		}
		if (currentPos <= -1.0f) {
			view.setTranslationX(mReduceX + mCoverWidth);
			view.setScaleX(mScaleMin);
			view.setScaleY(mScaleMin);
		} else if (currentPos <= 1.0) {
			float scale = (mScaleMax - mScaleMin) * Math.abs(1.0f - Math.abs(currentPos));
			float translationX = currentPos * -mReduceX;
			if (currentPos <= -0.5) {//两个view中间的临界，一般是从右到左滑动时，这时两个view在同一层，左侧View需要往X轴正方向移动覆盖的值(即：mCoverWidth )
				view.setTranslationX(translationX + mCoverWidth * Math.abs(Math.abs(currentPos) - 0.5f) / 0.5f);
			} else if (currentPos <= 0.0f) {
				view.setTranslationX(translationX);
			} else if (currentPos >= 0.5) {//两个view中间的临界，这时两个view在同一层，一般是从左向右滑动时
				view.setTranslationX(translationX - mCoverWidth * Math.abs(Math.abs(currentPos) - 0.5f) / 0.5f);
			} else {
				view.setTranslationX(translationX);
			}
			view.setScaleX(scale + mScaleMin);
			view.setScaleY(scale + mScaleMin);
		} else {
			view.setScaleX(mScaleMin);
			view.setScaleY(mScaleMin);
			view.setTranslationX(-mReduceX - mCoverWidth);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()){
			case MotionEvent.ACTION_DOWN:
				Log.d("TAG", "onTouchEvent: x="+ev.getX()+",y="+ev.getY());
				for(int i=0;i<getChildCount();i++){
					if(isTouchPointInView(getChildAt(i),(int)ev.getX(),(int)ev.getY())){
						setCurrentItem(i);
					}
				}
				break;
		}

		return true;
	}

	/**
	 * 判断x，y是否在view内
	 * @param view
	 * @param x
	 * @param y
	 * @return
	 */
	private boolean isTouchPointInView(View view,int x,int y){
		if(view == null)
			return false;
		int[] xy = new int[2];
		view.getLocationOnScreen(xy);
		int left = xy[0];
		int top = xy[1];
		int right = left + view.getMeasuredWidth();
		int bottom = top + view.getMeasuredHeight();

		if(x>=left && x<=right && y>=top && y<=bottom){
			return true;
		}
		return false;
	}

	public void setItem(int index){
		setCurrentItem(index);
	}
}
