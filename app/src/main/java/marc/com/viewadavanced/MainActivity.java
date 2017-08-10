package marc.com.viewadavanced;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import marc.com.viewadavanced.data.SpiderData;
import marc.com.viewadavanced.view.SpiderView;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		/*SpiderView spiderview = (SpiderView) findViewById(R.id.spider_view);
		List<SpiderData> datas = new ArrayList<>();
		for (int i=0;i<6;i++){
			datas.add(new SpiderData(""+i,i));
		}
		spiderview.setData(datas);*/
	}
}
