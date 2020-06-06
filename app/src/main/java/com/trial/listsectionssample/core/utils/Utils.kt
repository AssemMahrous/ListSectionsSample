package com.trial.listsectionssample.core.utils

import android.app.Activity
import android.net.Uri
import android.view.Gravity
import android.view.View
import com.facebook.common.util.UriUtil
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.request.ImageRequestBuilder
import com.tapadoo.alerter.Alerter
import com.trial.listsectionssample.R

object Utils {

    fun loadImage(imgUrl: Any?, imageView: SimpleDraweeView) {
        val uri: Uri = when (imgUrl) {
            is String -> Uri.parse(imgUrl)
            null -> {
                Uri.Builder()
                    .scheme(UriUtil.LOCAL_RESOURCE_SCHEME)
                    .path(R.mipmap.ic_launcher.toString())
                    .build()
            }
            else -> Uri.Builder()
                .scheme(UriUtil.LOCAL_RESOURCE_SCHEME)
                .path(imgUrl.toString())
                .build()
        }
        imageView.hierarchy.setProgressBarImage(CircleProgressDrawable())
        val imageRequest = ImageRequestBuilder
            .newBuilderWithSource(uri)
            .build()
        imageView.controller = Fresco.newDraweeControllerBuilder()
            .setOldController(imageView.controller)
            .setImageRequest(imageRequest)
            .setAutoPlayAnimations(true)
            .build()
    }

    fun showAlert(activity: Activity, text: String, color: Int) {
        Alerter.create(activity)
            .setTitle(text)
            .setBackgroundColorRes(color)
            .setDuration(2000)
            .hideIcon()
            .setContentGravity(Gravity.CENTER)
            .setOnClickListener(View.OnClickListener {
                Alerter.hide()
            })
            .show()
    }
}
