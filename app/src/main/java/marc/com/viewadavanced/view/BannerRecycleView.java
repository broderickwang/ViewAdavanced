package marc.com.viewadavanced.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by 王成达
 * Date: 2017/8/25
 * Time: 09:45
 * Version: 1.0
 * Description:
 * Email:wangchengda1990@gmail.com
 **/
public class BannerRecycleView extends RecyclerView {

	public BannerRecycleView(Context context) {
		super(context);
	}

	public BannerRecycleView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
	}

	public BannerRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onMeasure(int widthSpec, int heightSpec) {
		
		super.onMeasure(widthSpec, heightSpec);
	}

	@Override
	public void smoothScrollToPosition(int position) {
		int move_width = 0;
		for(int i=0;i<position;i++){
			Log.d("TAG", "smoothScrollToPosition: "+position+" , "+String.valueOf((getChildAt(i)==null)));
			if(getChildAt(i) != null)
				move_width += getChildAt(i).getWidth();
		}
		scrollTo(move_width,0);
//		onScrollChanged();
	}
}
