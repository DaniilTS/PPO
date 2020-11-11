package com.example.lab1;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ConverterViewModel extends ViewModel {

    private final MutableLiveData<Integer> currentCategory = new MutableLiveData<Integer>(0);
    private final MutableLiveData<Integer> previousCategory = new MutableLiveData<Integer>(0);
    private final MutableLiveData<Boolean> changed = new MutableLiveData<Boolean>(true);
    private final MutableLiveData<Integer> currentFirstUnit = new MutableLiveData<Integer>(0);
    private final MutableLiveData<Integer> currentSecondUnit = new MutableLiveData<Integer>(0);
    private final MutableLiveData<String> unit = new MutableLiveData<String>("0");
    private final MutableLiveData<Double> converterCoefficient = new MutableLiveData<Double>(1.0);

    public MutableLiveData<Integer> getCurrentCategory() {
        return currentCategory;
    }

    public void setCurrentCategory(int categoryId) {
        this.currentCategory.setValue(categoryId);
    }

    public MutableLiveData<Integer> getCurrentFirstUnit() {
        return currentFirstUnit;
    }

    public void setCurrentFirstUnit(int firstUnitId) {
        this.currentFirstUnit.setValue(firstUnitId);
    }

    public MutableLiveData<Integer> getCurrentSecondUnit() {
        return currentSecondUnit;
    }

    public void setCurrentSecondUnit(int secondUnitId) {
        this.currentSecondUnit.setValue(secondUnitId);
    }

    public MutableLiveData<Integer> getPreviousCategory() {
        return previousCategory;
    }

    public void setPreviousCategory(int previousCategoryId) {
        this.previousCategory.setValue(previousCategoryId);
    }

    public MutableLiveData<Boolean> getChanged() {
        return changed;
    }

    public void setChanged(boolean isChanged) {
        this.changed.setValue(isChanged);
    }

    public MutableLiveData<Double> getConverterCoefficient() {
        return converterCoefficient;
    }

    public void setConverterCoefficient(double coefficient) {
        this.converterCoefficient.setValue(coefficient);
    }

    public MutableLiveData<String> getUnit() {
        return unit;
    }

    public void setUnitValue(String unitValue) {
        this.unit.setValue(unitValue);
    }

    public void addNumber(String number){
        if(unit.getValue().equals("0")){
            unit.setValue(number);
        }
        else{
            unit.setValue(unit.getValue()+number);
        }
    }

    public void setComa(){
        if(!unit.getValue().contains(".")){
            unit.setValue(unit.getValue()+".");
        }
    }

    public void backspace(){
        if(!unit.getValue().equals("0")&& unit.getValue().length()==1){
            unit.setValue("0");
        }
        if(!unit.getValue().equals("")&&!unit.getValue().equals("0")){
            unit.setValue(unit.getValue().substring(0,unit.getValue().length() - 1));
        }
    }

    public void recountCoefficient(String firstUnit, String secondUnit){
        String value = firstUnit.concat(secondUnit);
        switch (value){
            case "USDEUR"   : setConverterCoefficient(0.84);    break;
            case "USDBLR"   : setConverterCoefficient(2.59);    break;
            case "EURUSD"   : setConverterCoefficient(1.17);    break;
            case "EURBLR"   : setConverterCoefficient(3.02);    break;
            case "BLRUSD"   : setConverterCoefficient(0.39);    break;
            case "BLREUR"   : setConverterCoefficient(0.33);    break;
            case "MKM"      : setConverterCoefficient(0.001);   break;
            case "MCM"      : setConverterCoefficient(100);     break;
            case "KMM"      : setConverterCoefficient(1000);    break;
            case "KMCM"     : setConverterCoefficient(1000000); break;
            case "CMM"      : setConverterCoefficient(0.01);    break;
            case "CMKM"     : setConverterCoefficient(0.00001); break;
            case "FUNTKG"   : setConverterCoefficient(0.453592);break;
            case "FUNTOUNCE": setConverterCoefficient(16);      break;
            case "KGFUNT"   : setConverterCoefficient(2.204);   break;
            case "KGOUNCE"  : setConverterCoefficient(35.274);  break;
            case "OUNCEFUNT": setConverterCoefficient(0.0625);  break;
            case "OUNCEKG"  : setConverterCoefficient(0.0283);  break;
            default         : setConverterCoefficient(1.0);     break;
        }
    }
}
