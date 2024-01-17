package com.saad.invitation.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.saad.invitation.R
import com.saad.invitation.adapters.ImageAdapter
import com.saad.invitation.databinding.FragmentHomeBinding
import com.saad.invitation.viewmodels.MainViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel by activityViewModel<MainViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.loadingAnim.visibility = View.VISIBLE
                binding.recyclerView.visibility = View.GONE

            } else {
                binding.loadingAnim.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE

            }
        }
        viewModel.designs.observe(viewLifecycleOwner) { design ->
            val adapter = ImageAdapter(design) { background, documentId ->
                onListItemClick(background, documentId)
            }
            binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
            binding.recyclerView.adapter = adapter

        }
        /*   viewModel.images.observe(requireActivity()) { imagelist ->
   
                *//* val adapter = ImageAdapter(imagelist) {
                  onListItemClick(it)
              }
              binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
              binding.recyclerView.adapter = adapter*//*

        }*/
    }

    private fun onListItemClick(item: String, id: String) {

        /*  val intent = Intent(this@MainActivity, DragActivity::class.java)
          intent.putExtra("document_background", item)
          intent.putExtra("document_key", id)*/
        val bundle = Bundle()
        bundle.apply {
            putString("document_id", id)
            putString("background", item)
        }

        findNavController().navigate(R.id.action_homeFragment_to_editorFragment, bundle)

    }
}

