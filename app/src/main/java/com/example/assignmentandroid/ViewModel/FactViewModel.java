package com.example.assignmentandroid.ViewModel;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.assignmentandroid.MainActivity;
import com.example.assignmentandroid.Model.Country;
import com.example.assignmentandroid.Model.Fact;
import com.example.assignmentandroid.Model.FactRestService;
import com.example.assignmentandroid.Network.RetrofitClientInstance;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class FactViewModel extends ViewModel {

    //this is the data that we will fetch asynchronously 
    private MutableLiveData<List<Fact>> facList;

    //we will call this method to get the data
    public LiveData<List<Fact>> getFacts() {
        //if the list is null 
        if (facList == null) {
            facList =new MutableLiveData<>();
            //we will load it asynchronously from server in this method
            loadHeroes();
        }
        //finally we will return the list
        return facList;
    }

    @SuppressLint("CheckResult")
    private void loadHeroes() {

        /*Create handle for the RetrofitInstance interface*/
        FactRestService factRestService = RetrofitClientInstance.getRetrofitInstance().create(FactRestService.class);
        factRestService.getCountry()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Country>() {
                    @Override
                    public void onSuccess(Country country) {
                        Log.e("onSuccess","onSuccess");
                        List<Fact> fact = country.getRows();
                        facList.setValue(fact);
                        Log.e("onSuccess",country.getTitle());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("onError",e.getMessage());
                    }
                });
    }


}
