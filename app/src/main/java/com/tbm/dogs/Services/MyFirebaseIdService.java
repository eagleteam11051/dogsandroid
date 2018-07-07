package com.tbm.dogs.Services;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.tbm.dogs.Helper.Shared;
import com.tbm.dogs.Helper.Var;

public class MyFirebaseIdService extends FirebaseInstanceIdService{
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        Shared shared = new Shared(this);
        shared.saveTokenFCM(FirebaseInstanceId.getInstance().getToken());
        Var.currentTokenFCM = shared.getTokenFCM();
        Log.e("currentToken:",Var.currentTokenFCM);
    }
}
