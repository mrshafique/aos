package com.aos.shafik.screens.dashboard

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aos.shafik.R
import com.aos.shafik.commons.GenericStateFlow
import com.aos.shafik.databinding.FragDashboardBinding
import com.aos.shafik.databinding.SingleAlbumBinding
import com.aos.shafik.room.entity.EntityAlbum
import com.aos.shafik.screens.MainActivity.Companion.pdMain
import com.bumptech.glide.Glide
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import java.io.File

/**
 * @Author: Shafik Shaikh
 * @Date: 09-10-2022
 */
@AndroidEntryPoint
class FragDashboard : Fragment() {
    private lateinit var binding: FragDashboardBinding
    private val viewModel: FragDashboardViewModel by viewModels()
    private lateinit var listAlbum: ArrayList<EntityAlbum>
    private var albumAdapter: AlbumAdapter? = null
    private lateinit var selectedEntityAlbum: EntityAlbum
    private var selectedPosition = -1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observer()
        binding.fragDashboardSv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.length > 2)
                    albumAdapter?.filter?.filter(query)
                else
                    albumAdapter?.filter?.filter("")
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.length > 2)
                    albumAdapter?.filter?.filter(newText)
                else
                    albumAdapter?.filter?.filter("")
                return false
            }

        })
    }

    /**
     * @Usage: collect all state flow objects which is initialized on FragDashboardViewModel class
     * @Author: Shafik Shaikh
     */
    private fun observer() {
        //LOCAL DB
        lifecycleScope.launchWhenStarted {
            viewModel.stateFlowList.collectLatest {
                when (it) {
                    is GenericStateFlow.Loading -> pdMain.show()
                    is GenericStateFlow.Success -> {
                        pdMain.dismiss()
                        if (it.data.isEmpty()) {
                            binding.fragDashboardRv.isVisible = false
                            binding.fragDashboardTv.isVisible = true
                            viewModel.getAlbumData()
                        } else {
                            listAlbum = it.data
                            displayAlbum()
                        }
                    }
                    else -> {}
                }
            }
        }

        //API
        lifecycleScope.launchWhenStarted {
            viewModel.stateFlowAlbum.collectLatest {
                when (it) {
                    is GenericStateFlow.Loading -> pdMain.show()
                    is GenericStateFlow.Success -> {
                        pdMain.dismiss()
                        viewModel.insertAlbumData(results = it.data.results)
                    }
                    is GenericStateFlow.Error -> {
                        pdMain.dismiss()
                        MaterialAlertDialogBuilder(requireActivity())
                            .setMessage(it.exception.message.toString())
                            .setPositiveButton(getString(R.string.retry))
                            { _, _ ->
                                kotlin.run {
                                    viewModel.getAlbumData()
                                }
                            }
                            .setNegativeButton(getString(R.string.exit))
                            { _, _ ->
                                kotlin.run {
                                    requireActivity().finish()
                                }
                            }
                            .show()
                    }
                    else -> {}
                }
            }
        }
    }

    /**
     * @Usage: create object of AlbumAdapter and set to RecyclerView
     * @Author: Shafik Shaikh
     */
    private fun displayAlbum() {
        binding.fragDashboardRv.isVisible = true
        binding.fragDashboardTv.isVisible = false
        binding.fragDashboardRv.apply {
            layoutManager = LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)
            albumAdapter = AlbumAdapter()
            adapter = albumAdapter
        }
    }

    /**
     * @Author: Shafik Shaikh
     * @Date: 09-10-2022
     */
    lateinit var listFilter: List<EntityAlbum>

    inner class AlbumAdapter : RecyclerView.Adapter<AlbumAdapter.AlbumHolder>(), Filterable {
        private var list = listAlbum

        init {
            listFilter = list
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumHolder {
            val singleBinding =
                SingleAlbumBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return AlbumHolder(singleBinding)
        }

        @SuppressLint("SetTextI18n")
        override fun onBindViewHolder(
            holder: AlbumHolder,
            @SuppressLint("RecyclerView") position: Int
        ) {
            val listItem = listFilter[position]
            holder.singleBinding.singleAlbumTvName.text = listItem.name
            holder.singleBinding.singleAlbumTvArtist.text = listItem.artist
            Glide.with(requireActivity())
                .load(listItem.image)
                .placeholder(R.drawable.loading_rotate)
                .error(R.drawable.error)
                .fitCenter()
                .centerCrop() //need fit() along with this
                .into(holder.singleBinding.singleAlbumIv)


            holder.itemView.setOnClickListener {
                selectedEntityAlbum = listItem
                selectedPosition = position

                ImagePicker.with(this@FragDashboard)
                    .crop()
                    .cameraOnly()
                    .saveDir(File(requireActivity().filesDir, "ImagePicker"))
                    .compress(1024) //final image size will be less than 1 MB
                    .maxResultSize(
                        300,
                        300
                    )
                    .createIntent { intent ->
                        startForProfileImageResult.launch(intent)
                    }
            }

            //open browser
            /*holder.itemView.setOnClickListener {
                val builder = CustomTabsIntent.Builder()
                val params = CustomTabColorSchemeParams.Builder()
                params.setToolbarColor(
                    ContextCompat.getColor(
                        requireActivity(),
                        R.color.purple_700
                    )
                )
                builder.setDefaultColorSchemeParams(params.build())
                builder.setShowTitle(true)
                builder.setShareState(CustomTabsIntent.SHARE_STATE_ON)
                builder.setInstantAppsEnabled(true)

                val customBuilder = builder.build()
                customBuilder.launchUrl(requireActivity(), Uri.parse(listItem.url))
            }*/
        }

        override fun getItemCount(): Int = listFilter.size

        inner class AlbumHolder(val singleBinding: SingleAlbumBinding) :
            RecyclerView.ViewHolder(singleBinding.root)

        override fun getFilter(): Filter {
            return object : Filter() {
                override fun performFiltering(constraint: CharSequence?): FilterResults {
                    val charString = constraint?.toString() ?: ""

                    listFilter = if (charString.isEmpty())
                        list
                    else {
                        val filteredList = ArrayList<EntityAlbum>()
                        for (filterItem in list) {
                            when {
                                filterItem.name.lowercase()
                                    .contains(charString) -> filteredList.add(
                                    filterItem
                                )
                                filterItem.artist.lowercase()
                                    .contains(charString) -> filteredList.add(
                                    filterItem
                                )
                            }
                        }
                        filteredList
                    }
                    return FilterResults().apply { values = listFilter }
                }

                @SuppressLint("NotifyDataSetChanged")
                override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                    listFilter = if (results?.values == null)
                        ArrayList()
                    else
                        results.values as List<EntityAlbum>
                    notifyDataSetChanged()
                }
            }
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data
            when (resultCode) {
                Activity.RESULT_OK -> {
                    val fileUri = data?.data!!
                    //update recycler view list item
                    val albumPosition = listAlbum.indexOfFirst {
                        it.url == selectedEntityAlbum.url
                    }
                    val updatedAlbum = listAlbum[albumPosition].apply {
                        image = fileUri.toString()
                    }
                    listAlbum[albumPosition] = updatedAlbum
                    listFilter[selectedPosition].image = fileUri.toString()
                    albumAdapter?.notifyDataSetChanged()

                    //update image in local db
                    selectedEntityAlbum.image = fileUri.toString()
                    viewModel.updateImage(selectedEntityAlbum = selectedEntityAlbum)
                }
                ImagePicker.RESULT_ERROR -> Toast.makeText(
                    requireActivity(),
                    ImagePicker.getError(data),
                    Toast.LENGTH_SHORT
                ).show()
                else ->
                    Toast.makeText(
                        requireActivity(),
                        getString(R.string.capture_image_cancelled),
                        Toast.LENGTH_SHORT
                    ).show()
            }
        }
}