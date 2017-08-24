package marc.com.viewadavanced.adapter;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 王成达
 * Date: 2017/8/24
 * Time: 16:41
 * Version: 1.0
 * Description:
 * Email:wangchengda1990@gmail.com
 **/
public abstract class DianDianBaseAdapter {
	public abstract int getCount();

	public abstract View getView(int position, ViewGroup parent);
}
