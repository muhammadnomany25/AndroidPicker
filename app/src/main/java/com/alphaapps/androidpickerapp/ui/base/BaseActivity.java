package com.alphaapps.androidpickerapp.ui.base;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.alphaapps.androidpickerapp.utils.LocalityUtil;

/**
 * Created by Muhammad Noamany
 * Email: muhammadnoamany@gmail.com
 */

public abstract class BaseActivity<Binding extends ViewDataBinding> extends AppCompatActivity {
    public static final short NO_LAYOUT = -1;

    protected Binding binding;
    protected boolean isArabic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inject();
        setLocality();
        bindView();
        restrictAppUiMode();
    }

    public void restrictAppUiMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }

    private void inject() {
//        AndroidInjection.inject(this);
    }

    void setLocality() {
//        setLocality(userSaver);
    }

//    public void setLocality(UserSaver userSaver) {
//        isArabic = userSaver.isArabic();
//        LocalityUtil.getInstance().setLocality(this, isArabic ? "ar" : "en");
//    }

    void setLocality(boolean isArabic) {
        LocalityUtil.getInstance().setLocality(this, isArabic ? "ar" : "en");
    }

    private void bindView() {
        if (getLayoutId() == NO_LAYOUT) return;
        binding = DataBindingUtil.setContentView(this, getLayoutId());
        binding.setVariable(getBindingVariable(), getVariableValue());
        binding.executePendingBindings();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    protected Object getVariableValue() {
        return isArabic;
    }


    /**
     * Override for reset Binding variable
     *
     * @return variable id
     */
    protected int getBindingVariable() {
        return -1;
    }

    /**
     * @return layout resource id
     */
    @LayoutRes
    protected abstract int getLayoutId();

}

