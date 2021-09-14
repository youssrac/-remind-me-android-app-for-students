package com.example.pavilion.remind2;

import android.support.annotation.NonNull;

/**
 * Created by PAVILION on 17/05/2018.
 */

public class notificationID {
    public String notifid;

    public <T extends notificationID> T withId(@NonNull final String id ){
        this.notifid=id;
        return (T) this;
    }
}