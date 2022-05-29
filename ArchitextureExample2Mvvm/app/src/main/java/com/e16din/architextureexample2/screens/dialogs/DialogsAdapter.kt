package com.e16din.architextureexample2.screens.dialogs

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.e16din.architextureexample2.data.DialogData
import com.e16din.architextureexample2.databinding.ItemDialogBinding

class DialogsAdapter(
  private var items: List<DialogData>,
  val onDialogClick: (item: DialogData) -> Unit
) : RecyclerView.Adapter<DialogsAdapter.DialogViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DialogViewHolder {
    val inflater = LayoutInflater.from(parent.context)
    val view = ItemDialogBinding.inflate(inflater)

    return DialogViewHolder(view)
  }

  override fun onBindViewHolder(holder: DialogViewHolder, position: Int) {
    val item = items[position]
    holder.updateUserImage(item.imageUrl)
    holder.updateUserName(item.name)

    holder.binding.root.setOnClickListener {
      onDialogClick.invoke(item)
    }
  }

  override fun getItemCount(): Int {
    return items.size
  }

  fun updateItems(dialogs: List<DialogData>) {
    items = dialogs
    notifyDataSetChanged()
  }

  class DialogViewHolder(val binding: ItemDialogBinding) : RecyclerView.ViewHolder(binding.root) {

    fun updateUserImage(url: String) {
      // todo: use Coil
    }

    fun updateUserName(name: String) {
      binding.nameLabel.text = name
    }
  }
}


