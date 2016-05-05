package kale.appbar;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import kale.ui.view.AppBar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppBar appBar = (AppBar) findViewById(R.id.app_bar);

        //左Toolbar自己的Title
        TextView title = appBar.getTitleView();
        ViewGroup.LayoutParams lp=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        title.setGravity(Gravity.CENTER);

        if (title!=null) {
            title.setTextColor(Color.WHITE);
            title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, "title-Click", Toast.LENGTH_SHORT).show();
                }
            });
        }


        //左侧导航按钮
        ImageButton imageButton = appBar.getNavButton();
        if (imageButton!=null) {
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, "NavButton-Click", Toast.LENGTH_SHORT).show();
                }
            });
        }


        //左侧Subtitle
//        appBar.setSubtitle("Subtitle");
        TextView subTitleText = appBar.getSubtitleView();

        if (subTitleText != null) {
            subTitleText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, "subTitleText-Click", Toast.LENGTH_SHORT).show();
                }
            });
        }


        //显示Logo
//        appBar.setLogo(R.mipmap.ic_launcher);
//        ImageView logoImageView = appBar.getLogoView();
//        logoImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "logoImageView-Click", Toast.LENGTH_SHORT).show();
//            }
//        });

//        ImageButton collapseButton = appBar.getCollapseButton();
//        collapseButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "collapseButton-Click", Toast.LENGTH_SHORT).show();
//            }
//        });

//        View right1 = appBar.getMenu01(); // 可以通过appbar来获得menu对象
//        if (right1 != null) {
//            ((TextView)right1).setTextColor(Color.WHITE);
//            right1.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(MainActivity.this, "right1-Click", Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
//        appBar.canFinishActivity(); // 调用此方法后，点击toolbar左边按钮会让activity finish
        // 还有各种toolbar本身的方法……

        View customMenu = appBar.getMenu03();
        if (customMenu != null) {
            ((TextView) customMenu.findViewById(R.id.menu_tv1)).setText("kale1");
            ((TextView) customMenu.findViewById(R.id.menu_tv2)).setText("kale2");
        }


        View radiogroup = appBar.getMenu01();
        if (customMenu != null) {
            ((RadioButton) radiogroup.findViewById(R.id.rb1)).setText("rb1");
            ((RadioButton) radiogroup.findViewById(R.id.rb2)).setText("rb2");
        }


//        appBar.addMenu(R.drawable.nav_icon_add_red);
        appBar.inflateMenu(R.menu.menu_main); // 因为本身就是toolbar，所以仍旧可以装入menu资源
        appBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                return id == R.id.action_settings;
            }
        });
    }

}
