package com.example.lab1;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.widget.EditText;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import static androidx.core.content.ContextCompat.getSystemService;
import static java.security.AccessController.getContext;

public class ConverterViewModel extends ViewModel {

    private final MutableLiveData<Integer> currentCategory = new MutableLiveData<Integer>(0);
    private final MutableLiveData<Integer> previousCategory = new MutableLiveData<Integer>(0);
    private final MutableLiveData<Boolean> changed = new MutableLiveData<Boolean>(true);
    private final MutableLiveData<Integer> currentFirstUnit = new MutableLiveData<Integer>(0);
    private final MutableLiveData<Integer> currentSecondUnit = new MutableLiveData<Integer>(0);
    private final MutableLiveData<String> firstUnitValue = new MutableLiveData<String>("0");
    private final MutableLiveData<Double> converterCoefficient = new MutableLiveData<Double>(1.0);

    public MutableLiveData<Integer> getCurrentCategory() {
        return currentCategory;
    }

    public void setCurrentCategory(int categoryId, boolean bool) {
        this.currentCategory.setValue(categoryId);
        setChanged(bool);
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

    public MutableLiveData<String> getFirstUnitValue() {
        return firstUnitValue;
    }

    public void setUnitValue(String unitValue) {
        this.firstUnitValue.setValue(unitValue);
    }

    public void addNumber(String number){
        if(firstUnitValue.getValue().equals("0")){
            firstUnitValue.setValue(number);
        }
        else{
            firstUnitValue.setValue(firstUnitValue.getValue()+number);
        }
    }

    public void setComa(){
        if(!firstUnitValue.getValue().contains(".")){
            firstUnitValue.setValue(firstUnitValue.getValue()+".");
        }
    }

    public void backspace(){
        if(!firstUnitValue.getValue().equals("0")&& firstUnitValue.getValue().length()==1) {
            firstUnitValue.setValue("0");
        }
        if(!firstUnitValue.getValue().equals("")&&!firstUnitValue.getValue().equals("0")){
            firstUnitValue.setValue(firstUnitValue.getValue().substring(0, firstUnitValue.getValue().length() - 1));
        }
    }

    public void swapUnits(){
        Integer firstUnit = currentFirstUnit.getValue();
        currentFirstUnit.setValue(currentSecondUnit.getValue());
        currentSecondUnit.setValue(firstUnit);
    }

    public void swapValues(){
        double secondUnitValue = Double.parseDouble(firstUnitValue.getValue())*converterCoefficient.getValue();
        firstUnitValue.setValue(Double.toString(secondUnitValue));
    }

    public void saveValue(Context context, String text){
        ClipboardManager clipboard = getSystemService(context, ClipboardManager.class);
        ClipData clip = ClipData.newPlainText("label", text);
        clipboard.setPrimaryClip(clip);
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
