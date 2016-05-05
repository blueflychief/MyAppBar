package kale.ui.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.CheckResult;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.annotation.StyleableRes;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import kale.lib.appbar.R;

/**
 * @author Jack Tony
 * @date 2015/7/25
 */
public class AppBar extends Toolbar {

    private static final String TAG = "AppBar";

    private final LayoutParams MENU_LP;

    private final Context context;

    private static final List<View> MENUS = new ArrayList<>();

    public AppBar(Context context) {
        this(context, null);
    }

    public AppBar(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.toolbarStyle);
    }

    public AppBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        MENU_LP = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT, Gravity.CENTER);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AppBar, defStyleAttr, 0);
        final String navBtnGravity = a.getString(R.styleable.AppBar_navigationGravity);
        final int[] styleableResIds = {
                R.styleable.AppBar_menu1,
                R.styleable.AppBar_menu2,
                R.styleable.AppBar_menu3,
                R.styleable.AppBar_menu4,
                R.styleable.AppBar_menu5};
        List<Integer> menuIds = getResIds(styleableResIds, a);
        a.recycle();

        // 1.set nav button
        ImageButton navButton = getNavButton();
        if (navButton != null) {
            Toolbar.LayoutParams lp = (LayoutParams) getNavButton().getLayoutParams();
            if (!TextUtils.equals(navBtnGravity, "0")) {
                lp.gravity = Gravity.CENTER_VERTICAL;
            }
            getNavButton().setLayoutParams(lp);
        }

        // 2.set menu views
        for (int i = 0; i < menuIds.size(); i++) {
            int menuId = menuIds.get(i);
            if (menuId == 0) {
                continue;
            }
            final View menuV = initMenuVIew(context, menuId);
            MENUS.add(menuV);
            addView(menuV, MENU_LP);
        }
    }

    public AppBar addMenu(View v) {
        addView(v, MENU_LP);
        return this;
    }

    /**
     * 通过资源的id添加menu
     *
     * @param menuId [layoutResId,DrawableResId,StringResId]
     */
    public <T extends View> T addMenu(@LayoutRes @DrawableRes @StringRes int menuId) {
        final View menuV = initMenuVIew(context, menuId);
        addView(menuV, MENU_LP);
        return (T) menuV;
    }

    @NonNull
    private View initMenuVIew(Context context, int menuId) {
        final View menuV;

        String str = getResources().getString(menuId);
        if (str.startsWith("res/drawable") || str.startsWith("res/mipmap")) {
            // 是图片
            menuV = new ImageView(context, null, R.attr.toolbarMenuImageStyle);
            ((ImageView) menuV).setImageResource(menuId);
        } else if (str.startsWith("res/layout")) {
            // 是view的布局文件
            menuV = LayoutInflater.from(context).inflate(menuId, null);
        } else {
            // 是文本
            menuV = new TextView(context, null, R.attr.toolbarMenuTextStyle);
            ((TextView) menuV).setText(str);

            /**
             * Just for preview
             */
            if (isInEditMode()) {
                if (str.endsWith(".xml") || str.endsWith(".png")
                        || str.endsWith(".jpg")) {
                    ((TextView) menuV).setText("★");
                }
            }
        }
        return menuV;
    }

    /**
     * @return menu视图的id数组
     */
    private List<Integer> getResIds(@StyleableRes int[] styleResIds, TypedArray a) {
        List<Integer> ids = new ArrayList<>();
        for (int resId : styleResIds) {
            ids.add(a.getResourceId(resId, 0));
        }
        return ids;
    }


    public void canFinishActivity() {
        if (context instanceof Activity) {
            setNavigationOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Activity) context).finish();
                }
            });
        }
    }

    public
    @CheckResult
    <T extends View> T getMenu01() {
        if (MENUS.size() > 0) {
            return (T) MENUS.get(0);
        }
        return null;
    }

    public
    @CheckResult
    <T extends View> T getMenu02() {
        if (MENUS.size() > 1) {
            return (T) MENUS.get(1);
        }
        return null;
    }

    public
    @CheckResult
    <T extends View> T getMenu03() {
        if (MENUS.size() > 2) {
            return (T) MENUS.get(2);
        }
        return null;
    }

    public
    @CheckResult
    <T extends View> T getMenu04() {
        if (MENUS.size() > 3) {
            return (T) MENUS.get(3);
        }
        return null;
    }

    public
    @CheckResult
    <T extends View> T getMenu05() {
        if (MENUS.size() > 4) {
            return (T) MENUS.get(4);
        }
        return null;
    }

    /**
     * 得到标题按钮
     */
    public
    @CheckResult
    TextView getTitleView() {
        return (TextView) getSubView("mTitleTextView");
    }

    /**
     * 得到子标题
     */
    public
    @CheckResult
    TextView getSubtitleView() {
        return ((TextView) getSubView("mSubtitleTextView"));
    }

    /**
     * 得到左边的导航按钮
     */
    public
    @CheckResult
    ImageButton getNavButton() {
        return (ImageButton) getSubView("mNavButtonView");
    }

    /**
     * 得到logo的视图
     */
    public
    @CheckResult
    ImageView getLogoView() {
        return ((ImageView) getSubView("mLogoView"));
    }

    /**
     * 得到最右边的可折叠按钮视图
     */
    public
    @CheckResult
    ImageButton getCollapseButton() {
        return (ImageButton) getSubView("mCollapseButtonView");
    }

    private View getSubView(String name) {
        Field field;
        try {
            field = Toolbar.class.getDeclaredField(name);
            field.setAccessible(true);
            View v = (View) field.get(this);
            field.setAccessible(false);
            return v;
        } catch (Exception e) {
            Log.e(TAG, "getSubView: 反射错误，请尽快上报给开发者", e);
        }
        return null;
    }

}