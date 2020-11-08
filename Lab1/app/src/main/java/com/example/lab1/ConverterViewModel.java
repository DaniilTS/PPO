package com.example.lab1;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ConverterViewModel extends ViewModel {

    private MutableLiveData<Integer> currentCategory = new MutableLiveData<Integer>(0);
    private MutableLiveData<Integer> previousCategory = new MutableLiveData<Integer>(0);
    private MutableLiveData<Boolean> changed = new MutableLiveData<Boolean>(false);
    private MutableLiveData<Integer> currentFirstUnit = new MutableLiveData<Integer>(0);
    private MutableLiveData<Integer> currentSecondUnit = new MutableLiveData<Integer>(0);

    public MutableLiveData<Integer> getCurrentCategory() {
        if (currentCategory == null) {
            currentCategory = new MutableLiveData<Integer>();
        }
        return currentCategory;
    }

    public void setCurrentCategory(int categoryId) {
        this.currentCategory.setValue(categoryId);
    }

    public MutableLiveData<Integer> getCurrentFirstUnit() {
        if (currentFirstUnit == null) {
            currentFirstUnit = new MutableLiveData<Integer>();
        }
        return currentFirstUnit;
    }

    public void setCurrentFirstUnit(int firstUnitId) {
        this.currentFirstUnit.setValue(firstUnitId);
    }

    public MutableLiveData<Integer> getCurrentSecondUnit() {
        if (currentSecondUnit == null) {
            currentSecondUnit = new MutableLiveData<Integer>();
        }
        return currentSecondUnit;
    }

    public void setCurrentSecondUnit(int secondUnitId) {
        this.currentSecondUnit.setValue(secondUnitId);
    }

    public MutableLiveData<Integer> getPreviousCategory() {
        if (currentSecondUnit == null) {
            currentSecondUnit = new MutableLiveData<Integer>();
        }
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
}
