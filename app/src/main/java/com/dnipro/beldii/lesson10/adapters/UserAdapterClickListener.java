package com.dnipro.beldii.lesson10.adapters;

import android.view.View;

public interface UserAdapterClickListener {
    void nameContactClick(View view);
    void deleteContactClick(View view);

    void editContactClick(View view);
}
