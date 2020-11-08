package com.example.lab1;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.concurrent.atomic.AtomicBoolean;

public class ScreenFragment extends Fragment{

    Spinner measureSpinner;
    Spinner upperSpinner;
    Spinner downSpinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_screen, container, false);
        final int[] previousCategory = {0};
        measureSpinner = view.findViewById(R.id.spinnerMeasure);
        upperSpinner = view.findViewById(R.id.upperSpinner);
        downSpinner = view.findViewById(R.id.downSpinner);

        ConverterViewModel model = ViewModelProviders.of(getActivity()).get(ConverterViewModel.class);

        measureSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                model.setCurrentCategory(position);
                model.setChanged(false);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {}
        });

        model.getCurrentCategory().observe(getViewLifecycleOwner(), val->{
            ArrayAdapter<?> adapter = null;
            model.getPreviousCategory().observe(getViewLifecycleOwner(), prevVal->{
                model.getChanged().observe(getViewLifecycleOwner(), isChanged->{
                    if(!prevVal.equals(val) && isChanged.equals(false)){
                        model.setPreviousCategory(val);
                        model.setCurrentFirstUnit(0);
                        model.setCurrentSecondUnit(0);
                        model.setChanged(true);
                    }
                });
            });

            if(val.equals(0)){
                adapter = ArrayAdapter.createFromResource(getContext(),
                        R.array.currencyOptions, android.R.layout.simple_spinner_item);
            }
            else if(val.equals(1)){
                adapter = ArrayAdapter.createFromResource(getContext(),
                        R.array.distanceOptions, android.R.layout.simple_spinner_item);
            }
            else if(val.equals(2)){
                adapter = ArrayAdapter.createFromResource(getContext(),
                        R.array.weightOptions, android.R.layout.simple_spinner_item);
            }

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            upperSpinner.setAdapter(adapter);
            downSpinner.setAdapter(adapter);

            model.getCurrentFirstUnit().observe(getViewLifecycleOwner(), val1->{
                upperSpinner.setSelection(val1);
            });

            model.getCurrentSecondUnit().observe(getViewLifecycleOwner(), val2->{
                downSpinner.setSelection(val2);
            });
        });

        upperSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                model.setCurrentFirstUnit(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {}
        });

        downSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                model.setCurrentSecondUnit(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {}
        });

        return view;
    }
}