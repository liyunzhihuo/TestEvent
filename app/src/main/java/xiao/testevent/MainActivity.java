package xiao.testevent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import xiao.testevent.base.BaseActivity;
import xiao.testevent.event.ObjectEvent;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (!EventBus.getDefault().isRegistered(this)) {
            Log.e("MinActivity", "register");
            EventBus.getDefault().register(this);
        }
        initUI();

    }

    private void initUI() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_fragment, new TestFragment()).commit();
        findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new ObjectEvent("test", "event"));
            }
        });
        findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,TestActivity.class));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            Log.e("MinActivity", "unregister");
            EventBus.getDefault().unregister(this);
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ObjectEvent event) {
        if (event != null) {
            Log.e("MainActivity", "event" + event.getMsg());
        }
    }
}
