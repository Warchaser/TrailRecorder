package com.warchaser.baselib.tools;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class CopyUtil {

    @SuppressWarnings("unchecked")
    public static <T> T copy(Parcelable input) {
        Parcel parcel = null;

        try {
            parcel = Parcel.obtain();
            parcel.writeParcelable(input, 0);

            parcel.setDataPosition(0);
            return parcel.readParcelable(input.getClass().getClassLoader());
        } finally {
            if(parcel != null){
                parcel.recycle();
            }
        }
    }

    public static <T extends Parcelable> ArrayList<T> copy(ArrayList<T> list){
        final ArrayList<T> result = new ArrayList<>();
        if(list == null || list.isEmpty()){
            return result;
        }

        for(T item : list){
            result.add(copy(item));
        }

        return result;
    }


}
