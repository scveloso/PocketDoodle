package com.sveloso.pocketdoodle;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PocketDoodleActivity extends SingleFragmentActivity {

    @Override
    public Fragment createFragment() {
        return PocketDoodleFragment.newInstance();
    }

}
