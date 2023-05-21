package my.project.cinema.ui.series

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import my.project.cinema.R
import my.project.cinema.databinding.FragmentSeriesBinding

@AndroidEntryPoint
class SeriesFragment : Fragment() {

    private lateinit var binding: FragmentSeriesBinding

    private val viewModel: SeriesViewModel by viewModels()
    private val seriesAdapter = SeriesPagingAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSeriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setSeriesAll()
        initToolbar()

        seriesAdapter.onFilmClick {
            val action = SeriesFragmentDirections.actionSeriesFragmentToSeriesInfoFragment(it)
            findNavController().navigate(action)
        }

    }

    private fun setSeriesAll() {
        binding.recyclerViewSeries.adapter = seriesAdapter

        lifecycleScope.launch {
            viewModel.seriesAll.collectLatest {
                seriesAdapter.submitData(it)
            }
        }
    }

    private fun initToolbar() {
        binding.toolbarSeries.btnBackStack.setOnClickListener {
            view?.findNavController()?.popBackStack()
        }
        binding.toolbarSeries.btnAbout.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_seriesFragment_to_aboutFragment)
        }
    }

}