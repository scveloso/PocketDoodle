package com.sveloso.pocketdoodle;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.UUID;

/**
 * Created by Veloso on 6/15/2016.
 */
public class ColorPaletteActivity extends SingleFragmentActivity{

    private static final String EXTRA_COLOR_INT = "com.sveloso.pocketdoodle.color_int";
    private int mCurrentColorInt;

    @Override
    protected Fragment createFragment() {
        int currentColor = getIntent().getIntExtra(EXTRA_COLOR_INT, -1);

        mCurrentColorInt = currentColor;

        return ColorPaletteFragment.newInstance(currentColor);
    }

    public static Intent newIntent(Context packageContext, int currentColor) {
        Intent intent = new Intent(packageContext, ColorPaletteActivity.class);
        intent.putExtra(EXTRA_COLOR_INT, currentColor);
        return intent;
    }
}
