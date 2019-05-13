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
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.westgoten.dreambuilder.PaintingCalculationData;
import com.westgoten.dreambuilder.R;
import com.westgoten.dreambuilder.UserInputValidator;

public class PaintingCalculationPage3Fragment extends Fragment implements UserInputValidator {
    private FragmentManager fragmentManager;
    private Bundle arguments;

    private EditText heightWidget;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        arguments = getArguments();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentManager = getFragmentManager();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.painting);

        ScrollView rootView = (ScrollView) inflater.inflate(R.layout.fragment_painting_calculation_page3, container, false);
        RelativeLayout viewContainer = (RelativeLayout) rootView.getChildAt(0);

        heightWidget = (EditText) viewContainer.getChildAt(2);

        Button finishButton = (Button) viewContainer.getChildAt(3);
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard(v);

                if (isUserInputValid()) {
                    doCalculation();

                    CalculationResultFragment calculationResultFragment = new CalculationResultFragment();
                    calculationResultFragment.setArguments(arguments);
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, calculationResultFragment)
                            .addToBackStack(null)
                            .commit();
                }
            }
        });

        return rootView;
    }

    @Override
    public boolean isUserInputEmpty() {
        if (heightWidget.getText().toString().isEmpty()) {
            heightWidget.setError(getString(R.string.input_error));
            return true;
        }
        return false;
    }

    @Override
    public boolean isUserInputValid() {
        return !isUserInputEmpty();
    }

    private void doCalculation() {
        PaintingCalculationData paintingCalculationData = (PaintingCalculationData) arguments
                .getSerializable(CalculationTypeSelectionFragment.USER_INPUT);

        double height = Double.parseDouble(heightWidget.getText().toString());
        double totalWallArea = paintingCalculationData.totalWallsLength * height;
        double quantityOfBuckets = ((totalWallArea - paintingCalculationData.totalGapArea) / paintingCalculationData
                .paintEfficiencyValue) * PaintingCalculationData.NUMBER_OF_COATS;

        if (paintingCalculationData.paintEfficiencyUnitViewId == R.id.paint_efficiency_m2L)
            quantityOfBuckets /= paintingCalculationData.paintBucketVolume;

        paintingCalculationData.quantityOfBuckets = (int) Math.ceil(quantityOfBuckets);

        arguments.putSerializable(CalculationTypeSelectionFragment.USER_INPUT, paintingCalculationData);
    }

    private void hideSoftKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null)
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
