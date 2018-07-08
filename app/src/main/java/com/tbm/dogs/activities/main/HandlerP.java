package com.tbm.dogs.activities.main;

public class HandlerP {
    private Results results;
    private int l = 0;
    public HandlerP(Results results){
        this.results = results;
    }

    public void onBackPressed() {
        if(l>0){
            results.finishApp();
        }else{
            results.tapAgain();
            l++;
        }
    }
}
