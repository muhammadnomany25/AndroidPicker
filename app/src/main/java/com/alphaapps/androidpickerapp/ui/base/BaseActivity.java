package com.alphaapps.androidpickerapp.ui.base;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.alphaapps.androidpickerapp.data.shared_prefs.UserSaver;
import com.alphaapps.androidpickerapp.utils.LocalityUtil;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

/**
 * Created by Muhammad Noamany
 * Email: muhammadnoamany@gmail.com
 */

public abstract class BaseActivity<Binding extends ViewDataBinding> extends AppCompatActivity {
    public static final short NO_LAYOUT = -1;
    protected Binding binding;
    protected boolean isArabic;
    @Inject
    public UserSaver userSaver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inject();
        setLocality();
        bindView();
        restrictAppUiMode();
    }

    /**
     * Restrict Dark Mode in the app
     */
    public void restrictAppUiMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }

    /**
     * Initialization of injection stuff
     */
    private void inject() {
        AndroidInjection.inject(this);
    }

    /**
     * Set locality lang
     */
    public void setLocality() {
        isArabic = userSaver.isArabic();
        LocalityUtil.getInstance().setLocality(this, isArabic ? "ar" : "en");
    }

    /**
     * Set the binding views to the activity
     */
    private void bindView() {
        if (getLayoutId() == NO_LAYOUT) return;
        binding = DataBindingUtil.setContentView(this, getLayoutId());
        binding.setVariable(getBindingVariable(), getVariableValue());
        binding.executePendingBindings();
    }

    /**
     * Setup the activity toolbar
     */
    protected void setUpToolbar(Toolbar toolbar, int indicatorResId, String title, boolean displayHomeAsUp) {
        toolbar.setTitle(title != null ? title : "");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(displayHomeAsUp);
        if (indicatorResId != -1)
            getSupportActionBar().setHomeAsUpIndicator(indicatorResId);
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

