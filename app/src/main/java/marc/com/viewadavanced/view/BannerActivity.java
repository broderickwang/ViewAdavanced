package marc.com.viewadavanced.view;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import marc.com.viewadavanced.R;
import marc.com.viewadavanced.adapter.BannerAdapter;

public class BannerActivity extends AppCompatActivity implements View.OnTouchListener ,GestureDetector.OnGestureListener{

	BannerRecycleView recyclerView;
	BannerAdapter adapter;
	private List<Drawable> list;
	GestureDetector mDetector;
	private int mCurrentPosition = 1;
	private SparseArray<Integer> childIndex = new SparseArray<>();//子view中心点作为key,存放的view最初的索引位置
	private ArrayList<Integer> childCenterXAbs = new ArrayList<>();//子view的X中心点距离父布局X的中心点的绝对距离的存放集合
	private float reduceX = 0.0f;
	private float itemWidth = 0;
	private float offsetPosition = 0f;
	private float mScaleMax = 1.0f;//最大的放大系数，建议是1.0
	private float mScaleMin = 0.914f;//最小的缩小系数，建议不要和mScaleMax 相差太多
	private float mCoverWidth = 40f;//中间的view覆盖两侧的view的大小

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_banner);
		recyclerView = (BannerRecycleView) findViewById(R.id.recycle_view);
		LinearLayoutManager manager = new LinearLayoutManager(this);
		manager.setOrientation(LinearLayoutManager.HORIZONTAL);
		recyclerView.setLayoutManager(manager);
		recyclerView.setChildDrawingOrderCallback(new RecyclerView.ChildDrawingOrderCallback() {
			@Override
			public int onGetChildDrawingOrder(int childCount, int n) {
				if (n == 0 || childIndex.size() != childCount) {
					childCenterXAbs.clear();
					childIndex.clear();
					//先获得当前控件（ViewPager）的中心点在屏幕的位置
					int viewCenterX = getViewCenterX(recyclerView);
					for (int i = 0; i < childCount; ++i) {
						//计算每个子View中心点的X值和父布局（ViewPager）中心点的X值的距离
						int indexAbs = Math.abs(viewCenterX - getViewCenterX(recyclerView.getChildAt(i)));
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
		});
		recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(RecyclerView view, int position) {
//				View view = recy.getChildAt(position);
				if (offsetPosition == 0f) {
					float paddingLeft = recyclerView.getPaddingLeft();
					float paddingRight = recyclerView.getPaddingRight();
					float width = recyclerView.getMeasuredWidth();
					offsetPosition = paddingLeft / (width - paddingLeft - paddingRight);
				}
				float currentPos = position - offsetPosition;
				if (itemWidth == 0) {
					itemWidth = view.getWidth();
					//由于左右边的view缩小而与原始的view宽度相比减小的宽度值的一半
					//当mScaleMax = 1.0f时，reduceX =（mScaleMax  - mScaleMin)) * itemWidth / 2.0f;
					reduceX = (((1.0f - mScaleMax) +(1.0f - mScaleMin)) * itemWidth / 2.0f);
				}
				if (currentPos <= -1.0f) {
					view.setTranslationX(reduceX + mCoverWidth);
					view.setScaleX(mScaleMin);
					view.setScaleY(mScaleMin);
				} else if (currentPos <= 1.0) {
					float scale = (mScaleMax - mScaleMin) * Math.abs(1.0f - Math.abs(currentPos));
					float translationX = currentPos * -reduceX;
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
					view.setTranslationX(-reduceX - mCoverWidth);
				}
			}

			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				Log.d("TAG", "onGetChildDrawingOrder: dx->"+dx+" - dy->"+dy);
				super.onScrolled(recyclerView, dx, dy);
			}
		});

		adapter = new BannerAdapter(this);
		recyclerView.setAdapter(adapter);
		list = new ArrayList<>();
		mDetector = new GestureDetector(this,this);

		loadViews();
	}
	//获取某个view的中心点在屏幕上的X值
	private int getViewCenterX(View view) {
		int[] array = new int[2];
		view.getLocationOnScreen(array);
		return array[0] + view.getWidth() / 2;
	}

	private void loadViews() {
		for (int i=0;i<4;i++){
			list.add(getResources().getDrawable(R.mipmap.test,null));
			list.add(getResources().getDrawable(R.mipmap.bank_innerpage,null));
		}
		adapter.setDrawables(list);
		adapter.notifyDataSetChanged();
		recyclerView.smoothScrollToPosition(mCurrentPosition);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		mDetector.onTouchEvent(event);
		return true;
	}

	@Override
	public boolean onDown(MotionEvent e) {
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {

	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

		if (e1.getX() - e2.getX() > 0 && mCurrentPosition < recyclerView.getChildCount()) {
			mCurrentPosition++;
			// 手向左滑动，图片下一张
		} else if (e2.getX() - e1.getX() > 0 && mCurrentPosition > 0) {
			// 向右滑动，图片上一张
			mCurrentPosition--;

		}
		recyclerView.smoothScrollToPosition(mCurrentPosition);

		return false;
	}
}
