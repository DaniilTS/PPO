package com.example.lab1;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;


public class KeyboardFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_keyboard, container, false);
        ConverterViewModel model = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(ConverterViewModel.class);

        view.findViewById(R.id.btn0).setOnClickListener(i -> model.addNumber("0"));
        view.findViewById(R.id.btn1).setOnClickListener(i -> model.addNumber("1"));
        view.findViewById(R.id.btn2).setOnClickListener(i -> model.addNumber("2"));
        view.findViewById(R.id.btn3).setOnClickListener(i -> model.addNumber("3"));
        view.findViewById(R.id.btn4).setOnClickListener(i -> model.addNumber("4"));
        view.findViewById(R.id.btn5).setOnClickListener(i -> model.addNumber("5"));
        view.findViewById(R.id.btn6).setOnClickListener(i -> model.addNumber("6"));
        view.findViewById(R.id.btn7).setOnClickListener(i -> model.addNumber("7"));
        view.findViewById(R.id.btn8).setOnClickListener(i -> model.addNumber("8"));
        view.findViewById(R.id.btn9).setOnClickListener(i -> model.addNumber("9"));

        view.findViewById(R.id.btn_coma).setOnClickListener(i -> model.setComa());
        view.findViewById(R.id.btn_coma).setOnLongClickListener(v -> {
            model.setComa();
            return false;
        });
        view.findViewById(R.id.btn_backspace).setOnClickListener(i->model.backspace());
        return view;
    }
}