package com.aptivist.spacex.presentation.detail

import android.content.ClipData
import android.content.res.Configuration
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.DragEvent
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.CollapsingToolbarLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.aptivist.spacex.databinding.FragmentItemDetailBinding
import com.aptivist.spacex.domain.IImageLoader
import com.aptivist.spacex.presentation.list.ItemListFragmentArgs
import com.aptivist.spacex.presentation.list.LauchersViewModel
import com.aptivist.spacex.presentation.list.adapter.ImageAdapter
import com.aptivist.spacex.presentation.placeholder.PlaceholderContent
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class ItemDetailFragment : Fragment() {

    private val args: ItemDetailFragmentArgs by navArgs()

    private var item: PlaceholderContent.PlaceholderItem? = null

    lateinit var itemDetailImageView: ImageView
    @Inject lateinit var imageLoader : IImageLoader
    private val launchesViewModel by viewModels<LauchersViewModel>()

    private val iconAdapter by lazy { ImageAdapter(imageLoader) }


    private var toolbarLayout: CollapsingToolbarLayout? = null

    private lateinit var binding: FragmentItemDetailBinding

    // This property is only valid between onCreateView and
    // onDestroyView.


    private val dragListener = View.OnDragListener { v, event ->
        if (event.action == DragEvent.ACTION_DROP) {
            val clipDataItem: ClipData.Item = event.clipData.getItemAt(0)
            val dragData = clipDataItem.text
            item = PlaceholderContent.ITEM_MAP[dragData]
            updateContent()
        }
        true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            if (it.containsKey(ARG_ITEM_ID)) {
                // Load the placeholder content specified by the fragment
                // arguments. In a real-world scenario, use a Loader
                // to load content from a content provider.
                item = PlaceholderContent.ITEM_MAP[it.getString(ARG_ITEM_ID)]
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentItemDetailBinding.inflate(inflater, container, false)
        val rootView = binding.root

        toolbarLayout = binding.toolbarLayout
        itemDetailImageView = binding.detailImageToolbar!!

        updateContent()
        rootView.setOnDragListener(dragListener)


        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        if(!isLandscape()) {
            args?.let { launcher ->

                try {
                    launcher.launch.links?.flickr_images?.get(0)?.let {
                        imageLoader.loadImage(
                            it,
                            itemDetailImageView
                        )
                    }
                } catch (e: Exception) {
                    Log.d("Flickr Image", e.message.toString())
                }

                with(binding) {

                    itemName?.text = launcher.launch.mission_name.toString()
                    itemName?.setTypeface(null, Typeface.BOLD)
                    itemRocket?.text = "Rocket: ${launcher.launch.rocket?.rocket_name}"
                    vpLaunchesImages?.adapter = iconAdapter
                    try {
                        val odt: OffsetDateTime =
                            OffsetDateTime.parse(launcher.launch.launch_date_local)


                        val dtf = DateTimeFormatter.ofPattern("MM/dd/uuuu", Locale.US)
                        val formatted = dtf.format(odt)

                        itemDate?.text = formatted.toString()

                    } catch (e: Exception) {
                        Log.d("Date", e.message.toString())
                        itemDate?.text = ""
                    }

                    try {
                        launcher.launch.links?.mission_patch_small?.let {
                            Picasso.get().load(it).into(imageLogo)
                        }
                    } catch (e: Exception) {

                    }

                    itemNumber?.text = launcher.launch.flight_number.toString()
                    itemDescription?.text = launcher.launch.details
                    itemSite?.text = "Site: ${launcher.launch.launch_site?.site_name_long}"
                    Log.d("Images List", launcher.launch.links?.flickr_images.toString())
                    iconAdapter.submitList(launcher.launch.links?.flickr_images)
                }

            }
        }else {
            launchesViewModel.launchSelected?.let { launcher ->

                try {
                    launcher.links?.flickr_images?.get(0)?.let {
                        imageLoader.loadImage(
                            it,
                            itemDetailImageView
                        )
                    }
                } catch (e: Exception) {
                    Log.d("Flickr Image", e.message.toString())
                }

                with(binding) {

                    itemName?.text = launcher.mission_name.toString()
                    itemName?.setTypeface(null, Typeface.BOLD)
                    itemRocket?.text = "Rocket: ${launcher.rocket?.rocket_name}"
                    vpLaunchesImages?.adapter = iconAdapter
                    try {
                        val odt: OffsetDateTime =
                            OffsetDateTime.parse(launcher.launch_date_local)


                        val dtf = DateTimeFormatter.ofPattern("MM/dd/uuuu", Locale.US)
                        val formatted = dtf.format(odt)

                        itemDate?.text = formatted.toString()

                    } catch (e: Exception) {
                        Log.d("Date", e.message.toString())
                        itemDate?.text = ""
                    }

                    try {
                        launcher.links?.mission_patch_small?.let {
                            Picasso.get().load(it).into(imageLogo)
                        }
                    } catch (e: Exception) {

                    }

                    itemNumber?.text = launcher.flight_number.toString()
                    itemDescription?.text = launcher.details
                    itemSite?.text = "Site: ${launcher.launch_site?.site_name_long}"
                    Log.d("Images List", launcher.links?.flickr_images.toString())
                    iconAdapter.submitList(launcher.links?.flickr_images)
                }

            }
        }


    }


    private fun isLandscape() = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE




    private fun updateContent() {
        toolbarLayout?.title = item?.content
        Log.d("item.Demo",item?.content.toString())

        // Show the placeholder content as text in a TextView.
        item?.let {
            binding.itemName?.text = "it.details"
        }
    }

    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_ITEM_ID = "item_id"
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}