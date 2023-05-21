package my.project.cinema.ui.filmpage

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
import my.project.cinema.databinding.FragmentFilmPageBinding
import my.project.cinema.utilit.Constants

@AndroidEntryPoint
class FilmPageFragment : Fragment() {

    private lateinit var binding: FragmentFilmPageBinding
    private val filmPageViewModel: FilmPageViewModel by viewModels()
    private val actorAdapter = FilmActorAdapter()
    private val directorAdapter = FilmDirectorAdapter()
    private val imageAdapter = FilmScreenAdapter()
    private val similarAdapter = FilmSimilarAdapter()
    private val args: FilmPageFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFilmPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val argsFilmId = args.imdbId

        filmPageViewModel.getFilmDetails(argsFilmId.toString())
        filmPageViewModel.getFilmActor(argsFilmId!!.toInt())
        filmPageViewModel.getSimilarFilm(argsFilmId.toInt())

        actorAdapter.onFilmClick {
            val action = FilmPageFragmentDirections.actionFilmPageFragmentToActorPageFragment(it)
            findNavController().navigate(action)
        }

        directorAdapter.onFilmClick {
            val action = FilmPageFragmentDirections.actionFilmPageFragmentToDirectorPageFragment(it)
            findNavController().navigate(action)
        }

        getFilmId()
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

        binding.btAllActors.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt(Constants.ACTORS_ID, args.imdbId!!.toInt())
            view?.findNavController()?.navigate(R.id.allActorFragment, bundle)
        }

        binding.btAllDirector.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt(Constants.DIRECTORS_ID, args.imdbId!!.toInt())
            view?.findNavController()?.navigate(R.id.allDirectorFragment, bundle)
        }

        binding.btAllImage.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt(Constants.SCREEN_ID, args.imdbId!!.toInt())
            view?.findNavController()?.navigate(R.id.fullScreenFragment, bundle)
        }

        binding.btAllSimilar.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt(Constants.SIMILAR_ID, args.imdbId!!.toInt())
            view?.findNavController()?.navigate(R.id.similarFilmFragment, bundle)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun getActor() {
        binding.rvActors.adapter = actorAdapter

        filmPageViewModel.responseActor.onEach {
            Log.d("itStaffActor", "$it")
            actorAdapter.setData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun getDirector() {
        binding.rvDirectors.adapter = directorAdapter

        filmPageViewModel.responseActor.onEach {
            Log.d("itStaffDirector", "$it")
            directorAdapter.setData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

    }

    @SuppressLint("SetTextI18n")
    private fun getFilmId() {
        filmPageViewModel.responseFilmId.observe(viewLifecycleOwner) { list ->
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
                        (list.ratingAgeLimits?.drop(3) ?: "") + "+"
        }
    }

    private fun setImageScreen() {
        binding.rvScreens.adapter = imageAdapter

        lifecycleScope.launch {
            filmPageViewModel.screens(args.imdbId!!.toInt()).collectLatest {
                imageAdapter.submitData(it)
                Log.d("ScreenImage", "$it")
            }
        }
    }

    private fun getSimilarFilm() {
        binding.rvSimilar.adapter = similarAdapter

        filmPageViewModel.responseSimilar.onEach {
            Log.d("itStaffActor", "$it")
            similarAdapter.setData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

}