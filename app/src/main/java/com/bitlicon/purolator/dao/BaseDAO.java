package com.bitlicon.purolator.dao;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

/**
 * Created by dianewalls on 30/07/2015.
 */
public class BaseDAO {

    private Context context;

    public BaseDAO(Context context) {
        this.context = context;
    }


    public int bulkInsert(Uri uri, ContentValues[] contentValues) {
        ContentResolver resolver = context.getContentResolver();
        int numeroRows = resolver.bulkInsert(uri, contentValues);
        return numeroRows;
    }
}
