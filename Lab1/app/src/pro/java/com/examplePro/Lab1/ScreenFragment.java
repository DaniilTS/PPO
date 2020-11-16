package com.examplePro.Lab1;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.lab1.ConverterViewModel;
import com.example.lab1.R;

import java.text.DecimalFormat;
import java.util.Objects;

import static androidx.core.content.ContextCompat.getSystemService;
import static java.security.AccessController.getContext;

public class ScreenFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_screen, container, false);

        Spinner measureSpinner = view.findViewById(R.id.spinnerMeasure);
        Spinner upperSpinner = view.findViewById(R.id.upperSpinner);
        Spinner downSpinner = view.findViewById(R.id.downSpinner);
        EditText upperTextField = view.findViewById(R.id.textField1);
        EditText downTextField = view.findViewById(R.id.textField2);

        ConverterViewModel model = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(ConverterViewModel.class);

        model.getFirstUnitValue().observe(getViewLifecycleOwner(), getVal -> model.getConverterCoefficient()
                .observe(getViewLifecycleOwner(), converterCoefficient -> {

                    upperTextField.setText(getVal);
                    if (!upperTextField.getText().toString().equals("")) {
                        Double convertedVal = Double.parseDouble(upperTextField.getText().toString().trim()) * converterCoefficient;
                        downTextField.setText(new DecimalFormat("#.###").format(convertedVal)
                                .replace(',', '.'));
                    } else {
                        model.setUnitValue("0");
                    }
                }));

        model.getCurrentCategory().observe(getViewLifecycleOwner(), val -> {
            model.getPreviousCategory().observe(getViewLifecycleOwner(), prevVal -> model.getChanged()
                    .observe(getViewLifecycleOwner(), isChanged -> {
                        if (!prevVal.equals(val) && isChanged.equals(false)) {
                            model.setPreviousCategory(val);
                            model.setCurrentFirstUnit(0);
                            model.setCurrentSecondUnit(0);
                            model.setChanged(true);
                        }
                    }));

            ArrayAdapter<?> adapter = null;
            switch (val) {
                case 0:
                    adapter = ArrayAdapter.createFromResource(getContext(), R.array.currencyOptions, android.R.layout.simple_spinner_item);
                    break;
                case 1:
                    adapter = ArrayAdapter.createFromResource(getContext(), R.array.distanceOptions, android.R.layout.simple_spinner_item);
                    break;
                case 2:
                    adapter = ArrayAdapter.createFromResource(getContext(), R.array.weightOptions, android.R.layout.simple_spinner_item);
                    break;
            }
            assert adapter != null;
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            upperSpinner.setAdapter(adapter);
            downSpinner.setAdapter(adapter);
            model.getCurrentFirstUnit().observe(getViewLifecycleOwner(), upperSpinner::setSelection);
            model.getCurrentSecondUnit().observe(getViewLifecycleOwner(), downSpinner::setSelection);
        });

        measureSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                model.setCurrentCategory(position, false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        upperSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                model.setCurrentFirstUnit(position);
                model.recountCoefficient(upperSpinner.getSelectedItem().toString(), downSpinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        downSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                model.setCurrentSecondUnit(position);
                model.recountCoefficient(upperSpinner.getSelectedItem().toString(), downSpinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        view.findViewById(R.id.swapUnit).setOnClickListener(i -> model.swapUnits());
        view.findViewById(R.id.swapValue).setOnClickListener(i -> model.swapValues());

        view.findViewById(R.id.save1ValueBtn).setOnClickListener(i->{
            model.saveValue(getContext(), upperTextField.getText().toString());
        });

        view.findViewById(R.id.save2ValueBtn).setOnClickListener(i->{
            model.saveValue(getContext(), downTextField.getText().toString());
        });
        return view;
    }
}