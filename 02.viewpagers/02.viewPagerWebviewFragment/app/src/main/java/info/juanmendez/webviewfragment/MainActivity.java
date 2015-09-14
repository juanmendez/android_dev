package info.juanmendez.webviewfragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.astuetz.PagerSlidingTabStrip;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        PageAdapter adapter = new PageAdapter(getSupportFragmentManager());

        adapter.addPage("http://www.newwavecoffee.com/", "new wave");
        adapter.addPage("http://www.lacatrinacafe.com/", "la catrina");
        adapter.addPage("http://www.perkolatorcoffee.com/", "perkolator");

        viewPager.setAdapter(adapter);

        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs );
        tabStrip.setViewPager(viewPager);
    }
}
