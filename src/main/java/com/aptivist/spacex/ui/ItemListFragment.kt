package com.aptivist.maindetail

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.aptivist.spacex.databinding.FragmentItemListBinding
import com.aptivist.spacex.databinding.ItemListContentBinding
import com.aptivist.spacex.domain.models.SpaceXListItem
import com.aptivist.spacex.ui.LauchersViewModel
import com.aptivist.spacex.ui.list.adapter.LaunchesListAdapter
import com.aptivist.spacex.utils.LoadingDialogFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ItemListFragment : Fragment() {

    private val launchesViewModel by viewModels<LauchersViewModel>()
    private val loadingDialogFragment: LoadingDialogFragment by lazy { LoadingDialogFragment.newInstance() }
    private val adapter = LaunchesListAdapter(::onLaunchSelected)
    private val navController by lazy { findNavController() }


    private var _binding: FragmentItemListBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentItemListBinding.inflate(inflater, container, false)
        binding.itemList.adapter = adapter
        binding.itemList.layoutManager = LinearLayoutManager(requireContext())
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etListSearch?.doOnTextChanged { text, _, _, _ ->
            launchesViewModel.updateSearchText(text)
        }

        setUpObservers()
        launchesViewModel.getLaunchList()

    }



    private fun setUpObservers() {

        launchesViewModel.lauchesListFiltered.observe(viewLifecycleOwner) {
            adapter.submitList(it)
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
        navController.navigate(ItemListFragmentDirections.itemListFragmentAction(launch))
    }
}