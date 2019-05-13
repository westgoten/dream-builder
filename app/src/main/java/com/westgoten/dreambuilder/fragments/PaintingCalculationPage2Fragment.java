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
import com.westgoten.dreambuilder.PaintingCalculationData;
import com.westgoten.dreambuilder.R;
import com.westgoten.dreambuilder.UserInputValidator;

import java.util.ArrayList;
import java.util.List;

public class PaintingCalculationPage2Fragment extends Fragment implements UserInputValidator {
    private FragmentManager fragmentManager;
    private Bundle arguments;

    private EditText totalWallsLengthWidget;
    private EditText totalGapAreaWidget;
    private List<EditText> inputFields = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        arguments = getArguments();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentManager = getFragmentManager();

        ScrollView rootView = (ScrollView) inflater.inflate(R.layout.fragment_painting_calculation_page2, container, false);
        RelativeLayout viewContainer = (RelativeLayout) rootView.getChildAt(0);

        totalWallsLengthWidget = (EditText) viewContainer.getChildAt(2);
        totalGapAreaWidget = (EditText) viewContainer.getChildAt(5);
        inputFields.add(totalGapAreaWidget);
        inputFields.add(totalWallsLengthWidget);

        Button nextButton = (Button) viewContainer.getChildAt(6);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard(v);

                if (isUserInputValid()) {
                    PaintingCalculationData paintingCalculationData = (PaintingCalculationData) arguments.getSerializable(
                            CalculationTypeSelectionFragment.USER_INPUT);
                    paintingCalculationData.totalWallsLength = Double.parseDouble(totalWallsLengthWidget.getText().toString());
                    paintingCalculationData.totalGapArea = Double.parseDouble(totalGapAreaWidget.getText().toString());

                    arguments.putSerializable(CalculationTypeSelectionFragment.USER_INPUT, paintingCalculationData);
                    PaintingCalculationPage3Fragment paintingCalculationPage3Fragment = new PaintingCalculationPage3Fragment();
                    paintingCalculationPage3Fragment.setArguments(arguments);
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, paintingCalculationPage3Fragment)
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
        return !isUserInputEmpty();
    }

    private void hideSoftKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null)
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
