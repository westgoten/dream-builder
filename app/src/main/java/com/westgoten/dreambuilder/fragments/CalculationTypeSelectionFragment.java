package com.westgoten.dreambuilder.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.westgoten.dreambuilder.R;

public class CalculationTypeSelectionFragment extends Fragment {
    private FragmentManager fragmentManager;

    public static String USER_INPUT = CalculationTypeSelectionFragment.class.getSimpleName() + ".USER_INPUT";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentManager = getFragmentManager();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.app_name);

        LinearLayout rootView = (LinearLayout) inflater.inflate(R.layout.fragment_calculation_type_selection, container, false);
        ScrollView scrollView = (ScrollView) rootView.getChildAt(0);
        RelativeLayout viewContainer = (RelativeLayout) scrollView.getChildAt(0);

        Button walls = (Button) viewContainer.getChildAt(0);
        walls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, new WallsCalculationFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        Button floor = (Button) viewContainer.getChildAt(2);
        floor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, new FloorCalculationPage1Fragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        Button painting = (Button) viewContainer.getChildAt(4);
        painting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, new PaintingCalculationPage1Fragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        return rootView;
    }
}
