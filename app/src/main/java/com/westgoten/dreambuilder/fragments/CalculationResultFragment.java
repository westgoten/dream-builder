package com.westgoten.dreambuilder.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.westgoten.dreambuilder.FloorCalculationData;
import com.westgoten.dreambuilder.PaintingCalculationData;
import com.westgoten.dreambuilder.R;
import com.westgoten.dreambuilder.WallsCalculationData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CalculationResultFragment extends Fragment {
    private FragmentManager fragmentManager;
    private Serializable calculationData;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null)
            calculationData = arguments.getSerializable(CalculationTypeSelectionFragment.USER_INPUT);
        else
            calculationData = new WallsCalculationData();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentManager = getFragmentManager();

        ScrollView rootView = (ScrollView) inflater.inflate(R.layout.fragment_calculation_result, container, false);
        LinearLayout viewContainer = (LinearLayout) rootView.getChildAt(0);
        TextView image = (TextView) viewContainer.getChildAt(0);

        CardView materialListContainer = (CardView) viewContainer.getChildAt(1);
        LinearLayout materialList = (LinearLayout) materialListContainer.getChildAt(0);

        if (calculationData instanceof WallsCalculationData) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.walls);

            WallsCalculationData wallsCalculationData = (WallsCalculationData) calculationData;
            image.setBackgroundResource(R.drawable.wall_calculation);

            List<LinearLayout> items = new ArrayList<>();
            for (int i = 0; i < 3; i++)
                items.add((LinearLayout) inflater.inflate(R.layout.result_item, materialList, false));

            TextView name = (TextView) items.get(0).getChildAt(0);
            TextView value = (TextView) items.get(0).getChildAt(1);

            name.setText(R.string.result_bricks);
            value.setText(String.format(getString(R.string.result_bricks_unit), wallsCalculationData.bricks));
            materialList.addView(items.get(0));

            TextView name1 = (TextView) items.get(1).getChildAt(0);
            TextView value1 = (TextView) items.get(1).getChildAt(1);

            name1.setText(R.string.result_cement);
            value1.setText(String.format(getString(R.string.result_cement_unit), wallsCalculationData.cementBags));
            materialList.addView(items.get(1));

            TextView name2 = (TextView) items.get(2).getChildAt(0);
            TextView value2 = (TextView) items.get(2).getChildAt(1);

            name2.setText(R.string.result_sand);
            value2.setText(String.format(getString(R.string.result_sand_unit), wallsCalculationData.sand));
            materialList.addView(items.get(2));

        } else if (calculationData instanceof FloorCalculationData) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.floor);

            FloorCalculationData floorCalculationData = (FloorCalculationData) calculationData;
            image.setBackgroundResource(R.drawable.floor_calculation);

            LinearLayout item = (LinearLayout) inflater.inflate(R.layout.result_item, materialList, false);
            TextView name = (TextView) item.getChildAt(0);
            TextView value = (TextView) item.getChildAt(1);

            name.setText(getString(R.string.result_tiles));
            value.setText(String.format(getString(R.string.result_tiles_unit), floorCalculationData.quantityOfTiles));
            materialList.addView(item);

        } else if (calculationData instanceof PaintingCalculationData) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.painting);

            PaintingCalculationData paintingCalculationData = (PaintingCalculationData) calculationData;
            image.setBackgroundResource(R.drawable.painting_calculation);

            LinearLayout item = (LinearLayout) inflater.inflate(R.layout.result_item, materialList, false);
            TextView name = (TextView) item.getChildAt(0);
            TextView value = (TextView) item.getChildAt(1);

            name.setText(getString(R.string.result_paint));
            value.setText(String.format(getString(R.string.result_paint_unit), paintingCalculationData.quantityOfBuckets));
            materialList.addView(item);
        }

        Button backToMenu = (Button) viewContainer.getChildAt(2);
        backToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                while (fragmentManager.getBackStackEntryCount() > 0) {
                    fragmentManager.popBackStackImmediate();
                }

                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, new CalculationTypeSelectionFragment())
                        .commit();
            }
        });

        return rootView;
    }
}
