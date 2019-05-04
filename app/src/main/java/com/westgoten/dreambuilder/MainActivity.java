package com.westgoten.dreambuilder;

import android.view.MenuItem;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import com.westgoten.dreambuilder.fragments.CalculationTypeSelectionFragment;

public class MainActivity extends AppCompatActivity {
    private FragmentManager fragmentManager;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                decideNavigationUpButtonVisibility();
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();

        if (savedInstanceState == null) {
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_container, new CalculationTypeSelectionFragment())
                    .commit();
        } else {
            decideNavigationUpButtonVisibility();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            fragmentManager.popBackStack();
            return true;
        }

        return false;
    }

    private void decideNavigationUpButtonVisibility() {
        if (actionBar != null) {
            if (fragmentManager.getBackStackEntryCount() > 0) {
                actionBar.setDisplayHomeAsUpEnabled(true);
            } else {
                actionBar.setDisplayHomeAsUpEnabled(false);
            }
        }
    }
}
