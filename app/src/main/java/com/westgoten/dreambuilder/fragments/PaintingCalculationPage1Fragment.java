package com.westgoten.dreambuilder.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.westgoten.dreambuilder.PaintingCalculationData;
import com.westgoten.dreambuilder.R;
import com.westgoten.dreambuilder.UserInputValidator;

public class PaintingCalculationPage1Fragment extends Fragment implements UserInputValidator {
    private FragmentManager fragmentManager;

    private EditText efficiencyValueWidget;
    private EditText bucketVolumeWidget;
    private RadioGroup efficiencyUnitWidget;
    private LinearLayout bucketVolumeContainer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentManager = getFragmentManager();

        ScrollView rootView = (ScrollView) inflater.inflate(R.layout.fragment_painting_calculation_page1, container, false);
        RelativeLayout viewContainer = (RelativeLayout) rootView.getChildAt(0);

        efficiencyValueWidget = (EditText) viewContainer.getChildAt(2);
        efficiencyUnitWidget = (RadioGroup) viewContainer.getChildAt(4);
        bucketVolumeContainer = (LinearLayout) viewContainer.getChildAt(5);
        bucketVolumeWidget = (EditText) bucketVolumeContainer.getChildAt(1);

        efficiencyUnitWidget.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.paint_efficiency_m2L)
                    bucketVolumeContainer.setVisibility(View.VISIBLE);
                else
                    bucketVolumeContainer.setVisibility(View.GONE);
            }
        });

        Button nextButton = (Button) viewContainer.getChildAt(6);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard(v);

                if (isUserInputValid()) {
                    Bundle arguments = new Bundle();
                    PaintingCalculationData paintingCalculationData = new PaintingCalculationData();
                    PaintingCalculationPage2Fragment paintingCalculationPage2Fragment = new PaintingCalculationPage2Fragment();

                    paintingCalculationData.paintEfficiencyValue = Double.parseDouble(efficiencyValueWidget.getText()
                            .toString());
                    paintingCalculationData.paintEfficiencyUnitViewId = efficiencyUnitWidget.getCheckedRadioButtonId();
                    if (bucketVolumeContainer.getVisibility() == View.VISIBLE)
                        paintingCalculationData.paintBucketVolume = Double.parseDouble(bucketVolumeWidget.getText()
                                .toString());

                    arguments.putSerializable(CalculationTypeSelectionFragment.USER_INPUT, paintingCalculationData);
                    paintingCalculationPage2Fragment.setArguments(arguments);
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, paintingCalculationPage2Fragment)
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

        if (efficiencyValueWidget.getText().toString().isEmpty()) {
            isEmpty = true;
            efficiencyValueWidget.setError(getString(R.string.input_error));
        }

        if (bucketVolumeContainer.getVisibility() == View.VISIBLE && bucketVolumeWidget.getText().toString().isEmpty()) {
            isEmpty = true;
            bucketVolumeWidget.setError(getString(R.string.input_error));
        }

        return isEmpty;
    }

    @Override
    public boolean isUserInputValid() {
        boolean isValid = true;

        if (!isUserInputEmpty()) {
            if (Double.parseDouble(efficiencyValueWidget.getText().toString()) == 0.0) {
                isValid = false;
                efficiencyValueWidget.setError(getString(R.string.input_error_division_by_zero));
            }

            if (bucketVolumeContainer.getVisibility() == View.VISIBLE
                    && Double.parseDouble(bucketVolumeWidget.getText().toString()) == 0.0) {
                isValid = false;
                bucketVolumeWidget.setError(getString(R.string.input_error_division_by_zero));
            }
        } else {
            isValid = false;
        }

        return isValid;
    }

    private void hideSoftKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null)
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
