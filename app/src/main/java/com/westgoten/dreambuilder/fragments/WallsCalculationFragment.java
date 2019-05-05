package com.westgoten.dreambuilder.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.westgoten.dreambuilder.R;
import com.westgoten.dreambuilder.WallsCalculationData;

public class WallsCalculationFragment extends Fragment {
    private FragmentManager fragmentManager;
    private Bundle userInput = new Bundle();

    private EditText lengthWidget;
    private EditText widthWidget;
    private EditText heightWidget;
    private EditText gapAreaWidget;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentManager = getFragmentManager();

        ScrollView rootView = (ScrollView) inflater.inflate(R.layout.fragment_walls_calculation, container, false);
        RelativeLayout viewContainer = (RelativeLayout) rootView.getChildAt(0);

        lengthWidget = (EditText) viewContainer.getChildAt(3);
        widthWidget = (EditText) viewContainer.getChildAt(6);
        heightWidget = (EditText) viewContainer.getChildAt(9);
        gapAreaWidget = (EditText) viewContainer.getChildAt(12);

        Button nextButton = (Button) viewContainer.getChildAt(13);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BrickSelectionFragment brickSelectionFragment = new BrickSelectionFragment();

                WallsCalculationData inputData = new WallsCalculationData();
                inputData.height = Double.valueOf(heightWidget.getText().toString());
                inputData.totalGapArea = Double.valueOf(gapAreaWidget.getText().toString());
                double length = Double.valueOf(lengthWidget.getText().toString());
                double width = Double.valueOf(widthWidget.getText().toString());
                double perimeter = 2 * (width + length);

                inputData.totalLateralArea = perimeter * inputData.height;
                inputData.cementBags = (int) Math.ceil(inputData.totalLateralArea * 6.7 / 40);
                inputData.sand = inputData.totalLateralArea * 20;

                userInput.putSerializable(CalculationTypeSelectionFragment.USER_INPUT, inputData);

                brickSelectionFragment.setArguments(userInput);

                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, brickSelectionFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        return rootView;
    }
}
