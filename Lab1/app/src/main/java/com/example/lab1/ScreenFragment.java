package com.example.lab1;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.DecimalFormat;
import java.util.Objects;

public class ScreenFragment extends Fragment{

    public void ChangeMeasureValue(Spinner measureSpinner, ConverterViewModel model){
        measureSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                model.setCurrentCategory(position, false);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {}
        });
    }

    public void ChangeUpperSpinnerValue(Spinner upperSpinner, Spinner downSpinner, ConverterViewModel model){
        upperSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                model.setCurrentFirstUnit(position);
                model.recountCoefficient(upperSpinner.getSelectedItem().toString(), downSpinner.getSelectedItem().toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {}
        });
    }

    public void ChangeDownSpinnerValue(Spinner upperSpinner, Spinner downSpinner, ConverterViewModel model){
        downSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                model.setCurrentSecondUnit(position);
                model.recountCoefficient(upperSpinner.getSelectedItem().toString(), downSpinner.getSelectedItem().toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    public void UpdateTextFields(EditText upperTextField, EditText downTextField, ConverterViewModel model){
        model.getFirstUnitValue().observe(getViewLifecycleOwner(), getVal-> model.getConverterCoefficient()
                .observe(getViewLifecycleOwner(), converterCoefficient->{

                    upperTextField.setText(getVal);
                    if(!upperTextField.getText().toString().equals("")){
                        Double convertedVal = Double.parseDouble(upperTextField.getText().toString().trim()) * converterCoefficient;
                        downTextField.setText(new DecimalFormat("#.###").format(convertedVal)
                                .replace(',','.'));
                    }
                    else{
                        model.setUnitValue("0");
                    }
                }));
    }

    public void UpdateSpinners(Spinner upperSpinner, Spinner downSpinner, ConverterViewModel model){
        model.getCurrentCategory().observe(getViewLifecycleOwner(), val->{
            model.getPreviousCategory().observe(getViewLifecycleOwner(), prevVal-> model.getChanged()
                    .observe(getViewLifecycleOwner(), isChanged->{
                        if(!prevVal.equals(val) && isChanged.equals(false)){
                            model.setPreviousCategory(val);
                            model.setCurrentFirstUnit(0);
                            model.setCurrentSecondUnit(0);
                            model.setChanged(true);
                        }
                    }));

            ArrayAdapter<?> adapter = null;
            switch (val){
                case 0: adapter = ArrayAdapter.createFromResource(getContext(), R.array.currencyOptions, android.R.layout.simple_spinner_item);break;
                case 1: adapter = ArrayAdapter.createFromResource(getContext(), R.array.distanceOptions, android.R.layout.simple_spinner_item);break;
                case 2: adapter = ArrayAdapter.createFromResource(getContext(), R.array.weightOptions, android.R.layout.simple_spinner_item);break;
            }
            assert adapter != null;
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            upperSpinner.setAdapter(adapter);
            downSpinner.setAdapter(adapter);
            model.getCurrentFirstUnit().observe(getViewLifecycleOwner(), upperSpinner::setSelection);
            model.getCurrentSecondUnit().observe(getViewLifecycleOwner(), downSpinner::setSelection);
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_screen, container, false);

        Spinner measureSpinner = view.findViewById(R.id.spinnerMeasure);
        Spinner upperSpinner = view.findViewById(R.id.upperSpinner);
        Spinner downSpinner = view.findViewById(R.id.downSpinner);
        EditText upperTextField = view.findViewById(R.id.textField1);
        EditText downTextField = view.findViewById(R.id.textField2);

        ConverterViewModel model = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(ConverterViewModel.class);

        UpdateTextFields(upperTextField, downTextField, model);
        UpdateSpinners(upperSpinner, downSpinner, model);
        ChangeMeasureValue(measureSpinner, model);
        ChangeUpperSpinnerValue(upperSpinner, downSpinner, model);
        ChangeDownSpinnerValue(upperSpinner, downSpinner, model);

        return view;
    }
}