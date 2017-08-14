package com.cpm.gskgtmerchandiser.constant;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

/**
 * Created by deepakp on 8/10/2017.
 */

public class AlertandMessages {

    public static void showSnackbarMsg(View view,String message)
    {
        Snackbar.make(view,message,Snackbar.LENGTH_SHORT).show();
    }
    public static void showToastMsg(Context context, String message)
    {
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }
}
