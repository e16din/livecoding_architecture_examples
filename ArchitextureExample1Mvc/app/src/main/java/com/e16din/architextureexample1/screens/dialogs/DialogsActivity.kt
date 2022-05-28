package com.e16din.architextureexample1.screens.dialogs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.e16din.architextureexample1.R
import com.e16din.architextureexample1.data.DialogData
import com.e16din.architextureexample1.databinding.ActivityDialogsBinding
import com.e16din.architextureexample1.subjects.*

// MVC

interface DialogsActivitySystem : System {
  fun openDialogScreen(dialogData: DialogData)
}

class DialogsActivity : AppCompatActivity(R.layout.activity_dialogs), DialogsActivitySystem {

  lateinit var viewController: MvcViewController

  //  lateinit var controller: MvcController
  lateinit var model: MvcModel
  lateinit var backend: MvcBackend

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding = ActivityDialogsBinding.inflate(layoutInflater)
    setContentView(binding.root)

    model = MvcModel()
    backend = MvcBackend()
//    controller = MvcController(model)
    viewController = MvcViewController(binding, model)

    model.init(this, backend, viewController)
    model.onCreate()
  }

  override fun openDialogScreen(dialogData: DialogData) {
    //todo: open dialog screen
//    val intent = Intent(this, DialogActivity::class.java)
//    startActivity(intent)

    Toast.makeText(this, "Open dialog with '${dialogData.name}'", Toast.LENGTH_SHORT)
      .show()
  }

  class MvcViewController(
    val binding: ActivityDialogsBinding,
//    val controller: MvcController,
    val model: MvcModel
  ) : View, Controller {

    private val adapter = DialogsAdapter(emptyList(),
      onDialogClick = { dialogData ->
        model.onDialogClick(dialogData)
//        controller.onDialogClick(dialogData)
      })

    init {
      with(binding) {
        dialogsList.setHasFixedSize(true)
        val context = binding.root.context
        dialogsList.layoutManager =
          LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        dialogsList.adapter = adapter
      }
    }

    fun updateDialogs(dialogs: List<DialogData>) {
      adapter.updateItems(dialogs)
    }
  }

  // Other controllers signals (a fitness brace for example)
//  class MvcController(val model: MvcModel) : Controller {
//    fun onDialogClick(dialogData: DialogData) {
//      model.onDialogClick(dialogData)
//    }
//  }

  // Use Case, Interactor, ViewModel, Presenter
  class MvcModel : Model {

    lateinit var system: DialogsActivitySystem
    lateinit var backend: MvcBackend
    lateinit var view: MvcViewController

    fun init(
      system: DialogsActivitySystem,
      backend: MvcBackend,
      viewController: MvcViewController
    ) {
      this.system = system
      this.backend = backend
      this.view = viewController
    }

    fun onCreate() {
      val dialogs = backend.getAllDialogs()
      view.updateDialogs(dialogs)
    }

    fun onDialogClick(dialogData: DialogData) {
      // ... process data

      system.openDialogScreen(dialogData)
    }
  }

  // Repository
  class MvcBackend : Backend {
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