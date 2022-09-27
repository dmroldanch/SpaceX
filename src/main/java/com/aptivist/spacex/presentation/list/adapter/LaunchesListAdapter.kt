package com.aptivist.spacex.presentation.list.adapter

import android.content.Context
import android.graphics.Typeface
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aptivist.spacex.databinding.ViewLaunchListBinding
import com.aptivist.spacex.domain.IImageLoader
import com.aptivist.spacex.domain.models.SpaceXListItem
import com.squareup.picasso.Picasso
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject

class LaunchesListAdapter(private val imageLoader: IImageLoader,private val onLaunchSelected: (SpaceXListItem) -> Unit) :
    ListAdapter<SpaceXListItem, LaunchesListAdapter.LaunchesViewHolder>(DiffUtilCallback()) {

    inner class LaunchesViewHolder(
        private val itemBinding: ViewLaunchListBinding, private val context: Context
    ) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(spaceXListItem: SpaceXListItem) {
            itemBinding.apply {
                txtLaunchName.text = spaceXListItem.mission_name
                txtLaunchName.setTypeface(null, Typeface.BOLD)
                txtLaunchNumber.text = spaceXListItem.flight_number.toString()
                txtLaunchDescription.text = "Rocket : ${spaceXListItem.rocket?.rocket_name.toString()}"
                txtExpirationDate.text = spaceXListItem.launch_date_local.toString().toDate()
                try {
                    spaceXListItem?.links?.flickr_images?.get(0)
                        ?.let { imageLoader.loadImage(it, imgLaunchBackground) }
                }  catch (e: Exception) {

                }
                spaceXListItem?.links?.mission_patch_small?.let {  imageLoader.loadImage(it,imgLaunch)}


                itemBinding.root.setOnClickListener {
                    onLaunchSelected(spaceXListItem)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaunchesViewHolder {
        return LaunchesViewHolder(
            ViewLaunchListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), parent.context
        )
    }


    override fun onBindViewHolder(holder: LaunchesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun String.toDate() : String {
        try {
            val odt: OffsetDateTime =
                OffsetDateTime.parse(this)


            val dtf = DateTimeFormatter.ofPattern("MM/dd/uuuu", Locale.US)
            val formatted = dtf.format(odt)
            return formatted

        } catch (e: Exception) {
            Log.d("Date", e.message.toString())
            return ""
        }
    }
}

class DiffUtilCallback : DiffUtil.ItemCallback<SpaceXListItem>() {
    override fun areItemsTheSame(oldItem: SpaceXListItem, newItem: SpaceXListItem): Boolean {
        return oldItem.mission_id == newItem.mission_id
    }

    override fun areContentsTheSame(oldItem: SpaceXListItem, newItem: SpaceXListItem): Boolean {
        return oldItem.mission_id == newItem.mission_id
    }




}