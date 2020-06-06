package com.trial.listsectionssample.core.platform

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.trial.listsectionssample.R
import com.trial.listsectionssample.core.utils.Utils
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import ibtikar.tania.user.core.platform.BaseViewModel
import javax.inject.Inject


abstract class BaseActivity<MBaseViewModel : BaseViewModel>
    : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    private lateinit var viewModel: MBaseViewModel

    private lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getBaseViewModel()
        viewModelFactory = getBaseViewModelFactory()
        viewModel.error.observe(this, androidx.lifecycle.Observer {
            hideLoading()
            showError(it)
        })
        viewModel.loading.observe(this, androidx.lifecycle.Observer {
            if (it) showLoading()
            else hideLoading()
        })
    }

    open fun showError(error: Status.Error) {
        when (error.errorType) {
            ErrorType.NoInternetConnection -> {
                showNoInternetConnection()
            }
            else -> {
                val errorMessage: String =
                    if (error.error != null && error.error.isNotEmpty()) error.error
                    else if (error.errorRes != null) getString(error.errorRes)
                    else getString(R.string.generic_error)

                showAlert(errorMessage, R.color.colorAccent)
            }
        }
    }

    abstract fun showNoInternetConnection()

    open fun showSuccess(message: String) {
        showAlert(message, R.color.success_message_color)
    }

    fun showAlert(message: String, color: Int) {
        Utils.showAlert(this, message, color)
    }

    open fun hideLoading() {
        val progressBar = findViewById<ProgressBar>(R.id.progress_circular)
        if (progressBar != null)
            progressBar.visibility = View.GONE
    }

    open fun showLoading() {
        val progressBar = findViewById<ProgressBar>(R.id.progress_circular)
        if (progressBar != null)
            progressBar.visibility = View.VISIBLE
    }


    abstract fun getBaseViewModel(): MBaseViewModel

    abstract fun getBaseViewModelFactory(): ViewModelFactory

    override fun supportFragmentInjector() = dispatchingAndroidInjector

    @CallSuper
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}
