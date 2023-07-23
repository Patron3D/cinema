package my.project.cinema.ui.seriesseasons

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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import my.project.cinema.R
import my.project.cinema.databinding.FragmentSeriesInfoBinding
import my.project.cinema.utilit.Constants

@AndroidEntryPoint
class SeriesInfoFragment : Fragment() {

    private lateinit var binding: FragmentSeriesInfoBinding
    private val viewModel: SeriesInfoViewModel by viewModels()
    private val actorAdapter = SeriesActorAdapter()
    private val directorAdapter = SeriesDirectorAdapter()
    private val screenAdapter = SeriesScreensAdapter()
    private val similarAdapter = SeriesSimilarAdapter()
    private val args: SeriesInfoFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSeriesInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val argsFilmId = args.seriesinfoId
        viewModel.getFilmDetails(argsFilmId.toString())
        viewModel.getFilmActor(argsFilmId!!.toInt())
        viewModel.getSimilarFilm(argsFilmId.toInt())
        viewModel.getSeriesInfo(argsFilmId.toInt())

        actorAdapter.onFilmClick {
            val action =
                SeriesInfoFragmentDirections.actionSeriesInfoFragmentToActorPageFragment(it)
            findNavController().navigate(action)
        }

        directorAdapter.onFilmClick {
            val action =
                SeriesInfoFragmentDirections.actionSeriesInfoFragmentToDirectorPageFragment(it)
            findNavController().navigate(action)
        }

        getFilmId()
        getSeriesInfo()
        getActor()
        getDirector()
        setImageScreen()
        getSimilarFilm()
        initClick()

    }

    private fun initClick() {
        binding.ivBackStack.setOnClickListener {
            view?.findNavController()?.popBackStack()
        }

        binding.btAllSeasons.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt(Constants.SEASON_ID, args.seriesinfoId!!.toInt())
            view?.findNavController()?.navigate(R.id.seasonsFragment, bundle)
        }

        binding.btAllActors.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt(Constants.ACTORS_ID, args.seriesinfoId!!.toInt())
            view?.findNavController()?.navigate(R.id.allActorFragment, bundle)
        }

        binding.btAllDirector.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt(Constants.DIRECTORS_ID, args.seriesinfoId!!.toInt())
            view?.findNavController()?.navigate(R.id.allDirectorFragment, bundle)
        }

        binding.btAllImage.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt(Constants.SCREEN_ID, args.seriesinfoId!!.toInt())
            view?.findNavController()?.navigate(R.id.fullScreenFragment, bundle)
        }

        binding.btAllSimilar.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt(Constants.SIMILAR_ID, args.seriesinfoId!!.toInt())
            view?.findNavController()?.navigate(R.id.similarFilmFragment, bundle)
        }
    }

    private fun getActor() {
        binding.rvActors.adapter = actorAdapter

        viewModel.responseActor.onEach {
            Log.d("itStaffActor", "$it")
            actorAdapter.setData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun getDirector() {
        binding.rvDirectors.adapter = directorAdapter

        viewModel.responseActor.onEach {
            Log.d("itStaffDirector", "$it")
            directorAdapter.setData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

    }

    @SuppressLint("SetTextI18n")
    private fun getSeriesInfo() {
        viewModel.responseSeries.observe(viewLifecycleOwner) {
            val total = it.total
            val countSeasons = resources.getQuantityString(R.plurals.count_season, total!!, total)
            binding.btAllSeasons.text = "$countSeasons >"

            val number = it.items[0].episodes.size
            val count = resources.getQuantityString(R.plurals.count_series, number, number)
            binding.tvNumberOfSeasons.text = count
        }
    }

    @SuppressLint("SetTextI18n")
    private fun getFilmId() {
        viewModel.responseFilmId.observe(viewLifecycleOwner) { list ->
            Log.d("itLogFilmId", "$list")
            if (list.coverUrl != null) {
                binding.tvTitleImage.visibility = View.INVISIBLE
                Glide.with(requireContext())
                    .load(list.coverUrl)
                    .into(binding.ivPoster)
                binding.ivPoster.visibility = View.VISIBLE
                binding.tvTitleImage.visibility = View.VISIBLE
                binding.tvTitleImage.text = list.nameRu ?: list.nameOriginal
            } else {
                binding.tvTitleImage.text = list.nameRu ?: list.nameOriginal
                Glide.with(requireContext())
                    .load(R.drawable.cover)
                    .into(binding.ivPoster)
                binding.ivPoster.visibility = View.VISIBLE
                binding.tvTitleImage.visibility = View.VISIBLE
            }
            binding.tvShortDescription.text = list.shortDescription ?: "О фильме"

            if (list.description.toString().length < 251) {
                binding.tvDescription.text = list.description
            } else {
                binding.tvDescription.text = list.description?.substring(0, 250) + "..."
            }


            binding.tvRating.text = ((list.ratingKinopoisk?.toString() ?: "") + "  ") + (list.nameRu
                ?: list.nameOriginal)
            binding.tvYear.text = (list.year.toString().padEnd(5, ',')) + "  " +
                    (list.genres?.joinToString(", ") { it?.genre.toString() })
            binding.tvCountry.text =
                (list.countries?.joinToString(", ") { it?.country.toString() }) + ",  " +
                        (list.filmLength?.div(60)) + "ч " + (list.filmLength?.rem(60)) + "мин" + ",  " +
                        (list.ratingAgeLimits?.drop(3) ?: "0") + "+"
        }
    }

    private fun setImageScreen() {
        binding.rvScreens.adapter = screenAdapter

        lifecycleScope.launch {
            viewModel.screens(args.seriesinfoId!!.toInt()).collectLatest {
                screenAdapter.submitData(it)
                Log.d("ScreenImage", "$it")
            }
        }
    }

    private fun getSimilarFilm() {
        binding.rvSimilar.adapter = similarAdapter

        viewModel.responseSimilar.onEach {
            Log.d("itStaffActor", "$it")
            similarAdapter.setData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

}