package com.tbm.dogs.activities.login;

import android.os.Bundle;

public interface Results {
    void saveInfoUser(String response1);

    void startMain(Bundle user);

    void showError(String s, int lengthLong);
}
