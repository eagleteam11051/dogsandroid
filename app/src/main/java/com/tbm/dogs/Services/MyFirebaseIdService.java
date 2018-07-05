package com.tbm.dogs.Services;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.tbm.dogs.Helper.Var;

public class MyFirebaseIdService extends FirebaseInstanceIdService{
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        Var.currentToken = FirebaseInstanceId.getInstance().getToken();

    }
}
