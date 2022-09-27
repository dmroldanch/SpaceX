package com.aptivist.spacex.presentation.list

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aptivist.spacex.databinding.FragmentItemListBinding
import com.aptivist.spacex.domain.IImageLoader
import com.aptivist.spacex.domain.models.SpaceXListItem
import com.aptivist.spacex.presentation.list.LauchersViewModel
import com.aptivist.spacex.presentation.list.adapter.ImageAdapter
import com.aptivist.spacex.presentation.list.adapter.LaunchesListAdapter
import com.aptivist.spacex.utils.LoadingDialogFragment
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class ItemListFragment : Fragment() {

    private val launchesViewModel by viewModels<LauchersViewModel>()
    private val loadingDialogFragment: LoadingDialogFragment by lazy { LoadingDialogFragment.newInstance() }
    //private val adapter = LaunchesListAdapter(::onLaunchSelected)
    private val navController by lazy { findNavController() }
    @Inject
    lateinit var imageLoader : IImageLoader

    private val iconAdapter by lazy { ImageAdapter(imageLoader) }

    private val cardsAdapter by lazy { LaunchesListAdapter(imageLoader,::onLaunchSelected) }


    private var _binding: FragmentItemListBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentItemListBinding.inflate(inflater, container, false)
        binding.itemList.adapter = cardsAdapter
        binding.itemList.layoutManager = LinearLayoutManager(requireContext())

        binding.itemList.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (!recyclerView.canScrollVertically(1) && dy>0){
                        Toast.makeText(context,"onScrolled",Toast.LENGTH_SHORT).show()
                }
            }

        })

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            etListSearch?.doOnTextChanged { text, _, _, _ ->
                launchesViewModel.updateSearchText(text)
            }
            if(isLandscape()) {
                showData()
            }
        }



        setUpObservers()

    }



    private fun setUpObservers() {

        launchesViewModel.lauchesListFiltered.observe(viewLifecycleOwner) {
            cardsAdapter.submitList(it)
            //binding.emptyView.showOrGoneAnimatedFade( show = it.isEmpty())
        }

       /* launchesViewModel.error.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()){
                requireContext().showCustomDialog(
                    tittle = "Error",
                    description = it,
                    positiveButtonText = "Retry",
                    isCancelable = true,
                    positiveListener = { viewModel.getJobsList()}
                )
            }
        }*/

        launchesViewModel.loading.observe(viewLifecycleOwner){ isLoading ->
            if (isLoading)
                loadingDialogFragment.showDialog(requireActivity().supportFragmentManager)
            else loadingDialogFragment.dismiss()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onLaunchSelected(launch: SpaceXListItem) {
            launchesViewModel.selectLaunch(launch)
        if(!isLandscape()) {
            navController.navigate(
                ItemListFragmentDirections.itemListFragmentAction(
                    launch
                )
            )
        }else{
            showData()
        }

    }

    private fun showData() {
        with(binding){
            launchesViewModel.launchSelected?.let {
                vpLaunchesImages?.adapter = iconAdapter

                try {
                    it.links?.mission_patch_small?.let {
                        Picasso.get().load(it).into(imageLogo)
                    }
                } catch (e: Exception) {

                }

                itemDescription?.text = it.details
                itemSite?.text = "Site: ${it.launch_site?.site_name_long}"
                Log.d("Images List", it.links?.flickr_images.toString())
                iconAdapter.submitList(it.links?.flickr_images)
            }
        }
    }

    private fun isLandscape() = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE


}


