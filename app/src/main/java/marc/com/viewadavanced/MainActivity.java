package marc.com.viewadavanced;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import marc.com.viewadavanced.view.CircleRoateView;

public class MainActivity extends AppCompatActivity {

	private marc.com.viewadavanced.view.CircleRoateView test;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
//		this.test = (CircleRoateView) findViewById(R.id.test);

		/*SpiderView spiderview = (SpiderView) findViewById(R.id.spider_view);
		List<SpiderData> datas = new ArrayList<>();
		for (int i=0;i<6;i++){
			datas.add(new SpiderData(""+i,i));
		}
		spiderview.setData(datas);*/
	}
}
