package com.utsman.frepho.ui.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.utsman.frepho.R
import com.utsman.frepho.base.*
import com.utsman.frepho.data.NetworkState
import com.utsman.frepho.data.Pexel
import com.utsman.frepho.data.Status
import com.utsman.frepho.ui.PhotoActivity
import kotlinx.android.synthetic.main.item_image.view.*
import kotlinx.android.synthetic.main.item_loader.view.*

class MainPagedAdapter : PagedListAdapter<Pexel, RecyclerView.ViewHolder>(FrephoDiffUtil()) {

    private var networkState: NetworkState? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_image -> MainViewHolder(layoutInflater(parent, R.layout.item_image))
            R.layout.item_loader -> NetworkViewHolder(layoutInflater(parent, R.layout.item_loader))
            else  -> throw IllegalArgumentException("not found view holder")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.item_image -> {
                val mainHolder = holder as MainViewHolder
                val pexel = getItem(position)
                if (pexel != null) mainHolder.bind(pexel)
            }
            R.layout.item_loader -> (holder as NetworkViewHolder).bind(networkState)
        }
    }

    private fun hasExtraRow() = networkState != null && networkState != NetworkState.LOADED

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount -1 ) {
            R.layout.item_loader
        } else {
            R.layout.item_image
        }
    }

    override fun getItemCount(): Int = super.getItemCount() + if (hasExtraRow()) 1 else 0

    fun setNetworkState(newNetworkState: NetworkState?) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }

}

class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(pexel: Pexel) = itemView.run {

        image_view_item.loadUrlBitmap(pexel.src.medium, pexel.src.small, object : RequestListener<Bitmap> {
            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Bitmap>?, isFirstResource: Boolean): Boolean {
                return false
            }

            override fun onResourceReady(resource: Bitmap?, model: Any?, target: Target<Bitmap>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {

                setOnClickListener {
                    val intent = Intent(context, PhotoActivity::class.java).apply {
                        putExtra("pexel", pexel)
                    }
                    context.startActivity(intent)

                    logi("gggggggg", pexel.src.medium)
                }

                return false
            }

        })
    }
}

class NetworkViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    @SuppressLint("SetTextI18n")
    fun bind(networkState: NetworkState?) = itemView.run {
        progress_circular.visibility = toVisibility(networkState?.status == Status.RUNNING)
        error_text_view.visibility = toVisibility(networkState?.status == Status.FAILED)

        error_text_view.text = "Network error: ${networkState?.msg}"
    }
}

class FrephoDiffUtil : DiffUtil.ItemCallback<Pexel>() {
    override fun areItemsTheSame(oldItem: Pexel, newItem: Pexel): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Pexel, newItem: Pexel): Boolean = oldItem == newItem
}