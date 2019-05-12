package com.westgoten.dreambuilder.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.westgoten.dreambuilder.R;
import com.westgoten.dreambuilder.UserInputValidator;
import com.westgoten.dreambuilder.WallsCalculationData;

public class WallsCalculationFragment extends Fragment implements UserInputValidator {
    private FragmentManager fragmentManager;
    private Bundle userInput = new Bundle();

    private EditText wallsLengthWidget;
    private EditText heightWidget;
    private EditText gapAreaWidget;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentManager = getFragmentManager();

        ScrollView rootView = (ScrollView) inflater.inflate(R.layout.fragment_walls_calculation, container, false);
        RelativeLayout viewContainer = (RelativeLayout) rootView.getChildAt(0);

        wallsLengthWidget = (EditText) viewContainer.getChildAt(3);
        heightWidget = (EditText) viewContainer.getChildAt(6);
        gapAreaWidget = (EditText) viewContainer.getChildAt(9);

        Button nextButton = (Button) viewContainer.getChildAt(10);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard(v);

                if (!isUserInputEmpty()) {
                    BrickSelectionFragment brickSelectionFragment = new BrickSelectionFragment();

                    WallsCalculationData wallsCalculationData = new WallsCalculationData();
                    wallsCalculationData.height = Double.valueOf(heightWidget.getText().toString());
                    wallsCalculationData.totalGapArea = Double.valueOf(gapAreaWidget.getText().toString());
                    wallsCalculationData.totalWallsLength = Double.valueOf(wallsLengthWidget.getText().toString());

                    wallsCalculationData.cementBags = (int) Math.ceil(((wallsCalculationData.totalWallsLength *
                            wallsCalculationData.height) - wallsCalculationData.totalGapArea) * 6.7 / 40);
                    wallsCalculationData.sand = ((wallsCalculationData.totalWallsLength * wallsCalculationData.height) -
                            wallsCalculationData.totalGapArea) *
                            20 / 1000.0;

                    userInput.putSerializable(CalculationTypeSelectionFragment.USER_INPUT, wallsCalculationData);

                    brickSelectionFragment.setArguments(userInput);

                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, brickSelectionFragment)
                            .addToBackStack(null)
                            .commit();
                }
            }
        });

        return rootView;
    }

    @Override
    public boolean isUserInputEmpty() {
        boolean isEmpty = false;

        if (wallsLengthWidget.getText().toString().isEmpty()) {
            isEmpty = true;
            wallsLengthWidget.setError(getString(R.string.input_error));
        }

        if (heightWidget.getText().toString().isEmpty()) {
            isEmpty = true;
            heightWidget.setError(getString(R.string.input_error));
        }

        if (gapAreaWidget.getText().toString().isEmpty()) {
            isEmpty = true;
            gapAreaWidget.setError(getString(R.string.input_error));
        }

        return isEmpty;
    }

    private void hideSoftKeyboard(View view) {
       InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
       if (inputMethodManager != null)
           inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
