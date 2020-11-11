package com.example.lab1;

import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.lang.reflect.Array;
import java.util.concurrent.atomic.AtomicBoolean;

public class ScreenFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_screen, container, false);

        Spinner measureSpinner = view.findViewById(R.id.spinnerMeasure);
        Spinner upperSpinner = view.findViewById(R.id.upperSpinner);
        Spinner downSpinner = view.findViewById(R.id.downSpinner);

        EditText upperTextField = view.findViewById(R.id.textField1);
        EditText downTextField = view.findViewById(R.id.textField2);

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

            model.getUnit().observe(getViewLifecycleOwner(), getVal->{
                model.getConverterCoefficient().observe(getViewLifecycleOwner(), converterCoefficient->{
                    if(!upperTextField.getText().toString().equals("")){
                        String convertedVal = Double.toString(Double.parseDouble(getVal.trim())*converterCoefficient);
                        downTextField.setText(convertedVal);
                    }
                    else{
                        model.setUnitValue("0");
                    }
                });
                upperTextField.setText(getVal);
            });

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

            ArrayAdapter<?> adapter = null;
            switch (val){
                case 0: adapter = ArrayAdapter.createFromResource(getContext(), R.array.currencyOptions, android.R.layout.simple_spinner_item);break;
                case 1: adapter = ArrayAdapter.createFromResource(getContext(), R.array.distanceOptions, android.R.layout.simple_spinner_item);break;
                case 2: adapter = ArrayAdapter.createFromResource(getContext(), R.array.weightOptions, android.R.layout.simple_spinner_item);break;
            }
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            upperSpinner.setAdapter(adapter);
            downSpinner.setAdapter(adapter);


            model.getCurrentFirstUnit().observe(getViewLifecycleOwner(), upperSpinner::setSelection);
            model.getCurrentSecondUnit().observe(getViewLifecycleOwner(), downSpinner::setSelection);
        });

        upperSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                model.setCurrentFirstUnit(position);
                //model.recountCoefficient(upperSpinner.getSelectedItem().toString(), downSpinner.getSelectedItem().toString());
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