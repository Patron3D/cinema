package my.project.cinema.ui.season

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import my.project.cinema.databinding.FragmentSeasonsBinding
import my.project.cinema.utilit.Constants

@AndroidEntryPoint
class SeasonsFragment : Fragment() {

    private lateinit var binding: FragmentSeasonsBinding
    private val viewModel: SeasonViewModel by viewModels()
    private val adapterSeasonNumber = SeasonsNumberAdapter()
    private val adapterEpisode = SeasonInfoAdapter()
    private val args: SeasonsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSeasonsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getFilmDetails(arguments?.getInt(Constants.SEASON_ID).toString())
        arguments?.getInt(Constants.SEASON_ID)?.let { viewModel.getSeasonsNumber(it) }

        initClick()
        getSeriesTitle()
        getSeasonNumber()
        getSeriesAndSeasons()

        viewModel.getEpisodeInfo(arguments?.getInt(Constants.SEASON_ID)!!.toInt(), 0)

    }

    @SuppressLint("SetTextI18n")
    private fun initClick() {
        binding.ivBackStack.setOnClickListener {
            view?.findNavController()?.popBackStack()
        }

    }

    private fun getSeriesTitle() {
        viewModel.responseFilmId.observe(viewLifecycleOwner) {
            binding.tvTitleSeries.text = it.nameRu
        }
    }

    private fun getSeasonNumber() {
        binding.rvSeason.adapter = adapterSeasonNumber

        viewModel.responseSeasonsNumber.onEach {
            adapterSeasonNumber.setData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun getSeriesAndSeasons() {
        adapterSeasonNumber.onFilmClick {
            binding.tvSeriesAndSeasons.text = it
            val tempPosition = it.substringBefore(" ").toInt() - 1
            Log.d("episodeOfAdapter", "$tempPosition")
            viewModel.getEpisodeInfo(arguments?.getInt(Constants.SEASON_ID)!!.toInt(), tempPosition)
            getEpisodeInfo()
        }
    }

    private fun getEpisodeInfo() {
        binding.rvSeasonDate.adapter = adapterEpisode

        viewModel.responseEpisode.onEach {
            Log.d("episodeInfo", "$it")
            adapterEpisode.setData(it.episodes)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

}