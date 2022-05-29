package com.e16din.architextureexample2.screens.dialogs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.e16din.architextureexample2.R
import com.e16din.architextureexample2.data.DialogData
import com.e16din.architextureexample2.databinding.ActivityDialogsBinding
import com.e16din.architextureexample2.subjects.*

// Android MVVM

class DialogsActivity : AppCompatActivity(R.layout.activity_dialogs),
  System,
  View,
  Controller {

  //  lateinit var viewController: MvvmViewController
  lateinit var viewModel: AndroidMvvmViewModel
  lateinit var backend: MvvmBackend

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding = ActivityDialogsBinding.inflate(layoutInflater)
    setContentView(binding.root)

    viewModel = AndroidMvvmViewModel()
    backend = MvvmBackend()
//    viewController = MvvmViewController(binding, model)

    with(binding) {
      dialogsList.setHasFixedSize(true)
      val context = binding.root.context
      dialogsList.layoutManager =
        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
      dialogsList.adapter = adapter
    }

    viewModel.dialogsLiveData.observe(this) { dialogs ->
      updateDialogs(dialogs)
    }

    viewModel.openDialogLiveEvent.observe(this) { dialog ->
      openDialogScreen(dialog)
    }

    viewModel.init(backend)
    viewModel.onCreate()
  }

  fun openDialogScreen(dialogData: DialogData) {
    //todo: open dialog screen
//    val intent = Intent(this, DialogActivity::class.java)
//    startActivity(intent)

    Toast.makeText(this, "Open dialog with '${dialogData.name}'", Toast.LENGTH_SHORT)
      .show()
  }

  private val adapter = DialogsAdapter(emptyList(),
    onDialogClick = { dialogData ->
      viewModel.onDialogClick(dialogData)
    })

  fun updateDialogs(dialogs: List<DialogData>) {
    adapter.updateItems(dialogs)
  }

  // Use Case, Interactor, (Android)ViewModel, Presenter, Model(MVC & MicrosoftMVVM)
  class AndroidMvvmViewModel : ViewModel() {

    lateinit var backend: MvvmBackend

    // to View
    val dialogsLiveData = MutableLiveData<List<DialogData>>()

    // to System
    val openDialogLiveEvent = MutableLiveData<DialogData>()

    fun init(backend: MvvmBackend) {
      this.backend = backend
    }

    fun onCreate() {
      val dialogs = backend.getAllDialogs()
      dialogsLiveData.postValue(dialogs)
    }

    fun onDialogClick(dialogData: DialogData) {
      // ... process data

      openDialogLiveEvent.value = dialogData
    }
  }

  // Repository
  class MvvmBackend : Backend {
    fun getAllDialogs(): List<DialogData> {
      val result = listOf( // todo: use server api to get dialogs
        DialogData(
          id = "1",
          name = "Alexander",
          imageUrl = "https://imageUrl"
        )
      )

      // ... process data

      return result
    }
  }
}