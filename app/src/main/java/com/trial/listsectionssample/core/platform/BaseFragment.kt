package ibtikar.tania.user.core.platform

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.trial.listsectionssample.R
import com.trial.listsectionssample.core.di.Injectable
import com.trial.listsectionssample.core.platform.ErrorType
import com.trial.listsectionssample.core.platform.Status
import com.trial.listsectionssample.core.platform.ViewModelFactory
import com.trial.listsectionssample.core.utils.Utils


abstract class BaseFragment<MBaseViewModel : BaseViewModel>
    : Fragment(), Injectable {

    private lateinit var vm: MBaseViewModel
    private lateinit var vmf: ViewModelFactory

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        vm = getBaseViewModel()
        vmf = getBaseViewModelFactory()
        vm.error.observe(this.viewLifecycleOwner, Observer {
            hideLoading()
            showError(it)
        })
        vm.loading.observe(this.viewLifecycleOwner, Observer {
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
        Utils.showAlert(requireActivity(), message, color)
    }

    abstract fun getBaseViewModel(): MBaseViewModel

    abstract fun getBaseViewModelFactory(): ViewModelFactory

    open fun hideLoading() {
        if (activity != null) {
            val progressBar = requireActivity().findViewById<ProgressBar>(R.id.progress_circular)
            if (progressBar != null)
                progressBar.visibility = View.GONE
        }
    }

    open fun showLoading() {
        if (activity != null) {
            val progressBar = requireActivity().findViewById<ProgressBar>(R.id.progress_circular)
            if (progressBar != null)
                progressBar.visibility = View.VISIBLE
        }
    }
}