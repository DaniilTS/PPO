package com.examplePro.Lab1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.lifecycle.ViewModelProvider;

import com.example.lab1.ConverterViewModel;
import com.example.lab1.R;

import java.util.Objects;

public class ScreenFragmentPro extends com.example.lab1.ScreenFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_screen, container, false);

        Spinner measureSpinner = view.findViewById(R.id.spinnerMeasure);
        Spinner upperSpinner = view.findViewById(R.id.upperSpinner);
        Spinner downSpinner = view.findViewById(R.id.downSpinner);
        EditText upperTextField = view.findViewById(R.id.textField1);
        EditText downTextField = view.findViewById(R.id.textField2);

        ConverterViewModel model = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(ConverterViewModel.class);

        super.UpdateTextFields(upperTextField, downTextField, model);
        super.UpdateSpinners(upperSpinner, downSpinner, model);
        super.ChangeMeasureValue(measureSpinner, model);
        super.ChangeDownSpinnerValue(upperSpinner, downSpinner, model);
        super.ChangeDownSpinnerValue(upperSpinner, downSpinner, model);

        view.findViewById(R.id.swapUnit).setOnClickListener(i -> model.swapUnits());
        view.findViewById(R.id.swapValue).setOnClickListener(i -> model.swapValues());

        view.findViewById(R.id.save1ValueBtn).setOnClickListener(i-> model.saveValue(getContext(), upperTextField.getText().toString()));
        view.findViewById(R.id.save2ValueBtn).setOnClickListener(i-> model.saveValue(getContext(), downTextField.getText().toString()));

        return view;
    }
}