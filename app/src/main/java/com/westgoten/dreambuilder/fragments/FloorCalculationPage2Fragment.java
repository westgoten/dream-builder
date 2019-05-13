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
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.westgoten.dreambuilder.FloorCalculationData;
import com.westgoten.dreambuilder.R;
import com.westgoten.dreambuilder.UserInputValidator;

import java.util.ArrayList;
import java.util.List;

public class FloorCalculationPage2Fragment extends Fragment implements UserInputValidator {
    private FragmentManager fragmentManager;
    private Bundle arguments;

    private EditText tileWidthWidget;
    private EditText tileHeightWidget;
    private List<EditText> inputFields = new ArrayList<>();
    private CheckBox addFooter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        arguments = getArguments();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentManager = getFragmentManager();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.floor);

        ScrollView rootView = (ScrollView) inflater.inflate(R.layout.fragment_floor_calculation_page2, container, false);
        RelativeLayout viewContainer = (RelativeLayout) rootView.getChildAt(0);

        tileWidthWidget = (EditText) viewContainer.getChildAt(2);
        inputFields.add(tileWidthWidget);

        tileHeightWidget = (EditText) viewContainer.getChildAt(4);
        inputFields.add(tileHeightWidget);

        addFooter = (CheckBox) viewContainer.getChildAt(7);

        Button finishButton = (Button) viewContainer.getChildAt(8);
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
        boolean isEmpty = false;

        for (EditText editText : inputFields) {
            if (editText.getText().toString().isEmpty()) {
                isEmpty = true;
                editText.setError(getString(R.string.input_error));
            }
        }

        return isEmpty;
    }

    @Override
    public boolean isUserInputValid() {
        boolean isValid = true;

        if (!isUserInputEmpty()) {
            for (EditText editText : inputFields) {
                if (Double.parseDouble(editText.getText().toString()) == 0.0) {
                    isValid = false;
                    editText.setError(getString(R.string.input_error_division_by_zero));
                }
            }
        } else {
            isValid = false;
        }

        return isValid;
    }

    private void doCalculation() {
        FloorCalculationData floorCalculationData = (FloorCalculationData) arguments
                .getSerializable(CalculationTypeSelectionFragment.USER_INPUT);

        double tileWidth = Double.parseDouble(tileWidthWidget.getText().toString());
        double tileHeight = Double.parseDouble(tileHeightWidget.getText().toString());
        double tileArea = tileWidth * tileHeight;
        double footerArea = 0.0;
        if (addFooter.isChecked())
            footerArea = floorCalculationData.perimeter * FloorCalculationData.FOOTER_HEIGHT;

        floorCalculationData.quantityOfTiles = (int) Math.ceil(((floorCalculationData.floorTotalArea + footerArea) /
                tileArea) * 1.15);

        arguments.putSerializable(CalculationTypeSelectionFragment.USER_INPUT, floorCalculationData);
    }

    private void hideSoftKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null)
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
