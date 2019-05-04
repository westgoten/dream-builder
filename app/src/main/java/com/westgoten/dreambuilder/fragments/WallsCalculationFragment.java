package com.westgoten.dreambuilder.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.westgoten.dreambuilder.R;

public class WallsCalculationFragment extends Fragment {
    private FragmentManager fragmentManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentManager = getFragmentManager();

        ScrollView rootView = (ScrollView) inflater.inflate(R.layout.fragment_walls_calculation, container, false);
        RelativeLayout viewContainer = (RelativeLayout) rootView.getChildAt(0);

        Button nextButton = (Button) viewContainer.getChildAt(9);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, new BrickSelectionFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
