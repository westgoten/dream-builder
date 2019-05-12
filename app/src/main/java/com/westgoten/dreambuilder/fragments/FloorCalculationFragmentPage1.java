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
import com.westgoten.dreambuilder.FloorCalculationData;
import com.westgoten.dreambuilder.R;
import com.westgoten.dreambuilder.UserInputValidator;

import java.util.ArrayList;
import java.util.List;

public class FloorCalculationFragmentPage1 extends Fragment implements UserInputValidator {
    private FragmentManager fragmentManager;

    private EditText houseWidthWidget;
    private EditText houseLengthWidget;
    private List<EditText> inputFields = new ArrayList<>();
    private FloorCalculationData floorCalculationData = new FloorCalculationData();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentManager = getFragmentManager();

        ScrollView rootView = (ScrollView) inflater.inflate(R.layout.fragment_floor_calculation_page1, container, false);
        RelativeLayout viewContainer = (RelativeLayout) rootView.getChildAt(0);

        houseWidthWidget = (EditText) viewContainer.getChildAt(2);
        inputFields.add(houseWidthWidget);

        houseLengthWidget = (EditText) viewContainer.getChildAt(4);
        inputFields.add(houseLengthWidget);

        Button finishButton = (Button) viewContainer.getChildAt(7);
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard(v);

                if (!isUserInputEmpty()) {
                    doCalculation();

                    Bundle arguments = new Bundle();
                    arguments.putSerializable(CalculationTypeSelectionFragment.USER_INPUT, floorCalculationData);

                    FloorCalculationFragmentPage2 floorCalculationFragmentPage2 = new FloorCalculationFragmentPage2();
                    floorCalculationFragmentPage2.setArguments(arguments);

                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, floorCalculationFragmentPage2)
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

    private void doCalculation() {
        double houseWidth = Double.parseDouble(houseWidthWidget.getText().toString());
        double houseLength = Double.parseDouble(houseLengthWidget.getText().toString());

        floorCalculationData.perimeter = 2 * (houseWidth + houseLength);
        floorCalculationData.floorTotalArea = houseWidth * houseLength;
    }

    private void hideSoftKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null)
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
