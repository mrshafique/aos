package com.aos.shafik.commons

import android.app.Dialog
import android.content.Context
import android.view.Window
import com.aos.shafik.R

/**
 * @Author: Shafik Shaikh
 * @Date: 09-10-2022
 */
class CustomLoadingDialog(context: Context) {
    private val pd: Dialog

    init {
        pd = Dialog(context)
        pd.requestWindowFeature(Window.FEATURE_NO_TITLE)
        pd.setCancelable(false)
        pd.setContentView(R.layout.dialog_loading)
    }

    /**
     * @Usage: show progress dialog
     * @Author: Shafik Shaikh
     */
    fun show() {
        try {
            pd.show()
        } catch (e: Exception) {
        }
    }

    /**
     * @Usage: dismiss progress dialog
     * @Author: Shafik Shaikh
     */
    fun dismiss() {
        try {
            pd.dismiss()
        } catch (e: Exception) {
        }
    }
}