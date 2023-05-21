package my.project.cinema.ui.home

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
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import my.project.cinema.R
import my.project.cinema.databinding.FragmentHomeBinding
import my.project.cinema.ui.home.homepopular.HomePopularAdapter
import my.project.cinema.ui.home.homepopular.HomePopularViewModel
import my.project.cinema.ui.home.homepremieres.HomePremieresAdapter
import my.project.cinema.ui.home.homerandom.HomeRandomAdapter
import my.project.cinema.ui.home.homerandom.HomeRandomViewModel
import my.project.cinema.ui.home.homeseries.HomeSeriesAdapter
import my.project.cinema.ui.home.homeseries.HomeSeriesViewModel
import my.project.cinema.ui.home.hometop.HomeTopAdapter
import my.project.cinema.ui.home.hometop.HomeTopViewModel
import my.project.cinema.utilit.Variables
import my.project.cinema.ui.premieres.PremieresViewModel

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val premieresViewModel: PremieresViewModel by viewModels()
    private val homePremieresAdapter = HomePremieresAdapter()

    private val popularViewModel: HomePopularViewModel by viewModels()
    private val homePopularAdapter = HomePopularAdapter()

    private val topViewModel: HomeTopViewModel by viewModels()
    private val homeTopAdapter = HomeTopAdapter()

    private val seriesViewModel: HomeSeriesViewModel by viewModels()
    private val homeSeriesAdapter = HomeSeriesAdapter()

    private val randomViewModel: HomeRandomViewModel by viewModels()
    private val homeRandomAdapter = HomeRandomAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setPremieres()
        setPopular()
        setTop()
        setSeriesAll()
        setRandomFilms()

        homePremieresAdapter.onFilmClick {
            val action = HomeFragmentDirections.actionHomeFragmentToFilmPageFragment(it)
            findNavController().navigate(action)
        }

        homeTopAdapter.onFilmClick {
            val action = HomeFragmentDirections.actionHomeFragmentToFilmPageFragment(it)
            findNavController().navigate(action)
        }

        homePopularAdapter.onFilmClick {
            val action = HomeFragmentDirections.actionHomeFragmentToFilmPageFragment(it)
            findNavController().navigate(action)
        }

        homeSeriesAdapter.onFilmClick {
            val action = HomeFragmentDirections.actionHomeFragmentToSeriesInfoFragment(it)
            findNavController().navigate(action)
        }

        homeRandomAdapter.onFilmClick {
            val action = HomeFragmentDirections.actionHomeFragmentToFilmPageFragment(it)
            findNavController().navigate(action)
        }

        binding.tvTitleRandomGenre.text = Variables.titleGenre
        binding.tvTitleRandomCountry.text = Variables.titleCountry

        binding.btAllPremieres.setOnClickListener {
            view.findNavController().navigate(R.id.action_homeFragment_to_premiersFragment)
        }
        binding.btAllPopular.setOnClickListener {
            view.findNavController().navigate(R.id.action_homeFragment_to_popularFragment)
        }
        binding.btAllTop.setOnClickListener {
            view.findNavController().navigate(R.id.action_homeFragment_to_bestFilmsFragment)
        }
        binding.btAllSeries.setOnClickListener {
            view.findNavController().navigate(R.id.action_homeFragment_to_seriesFragment)
        }
        binding.btAllRandom.setOnClickListener {
            view.findNavController().navigate(R.id.action_homeFragment_to_randomFragment)
        }

    }

    private fun setPremieres() {
        binding.rvHomePremiers.apply {
            adapter = homePremieresAdapter
            setHasFixedSize(true)
        }


        premieresViewModel.responsePremier.onEach {
            Log.d("itLog", "$it")
            homePremieresAdapter.setData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun setPopular() {
        binding.rvHomePopular.apply {
            adapter = homePopularAdapter
            setHasFixedSize(true)
        }

        lifecycleScope.launch {
            popularViewModel.popularFilms.collectLatest {
                homePopularAdapter.submitData(it)
            }
        }
    }

    private fun setTop() {
        binding.rvHomeTop.apply {
            adapter = homeTopAdapter
            setHasFixedSize(true)
        }

        lifecycleScope.launch {
            topViewModel.topFilms.collectLatest {
                homeTopAdapter.submitData(it)
            }
        }
    }

    private fun setSeriesAll() {
        binding.rvHomeSeries.adapter = homeSeriesAdapter

        lifecycleScope.launch {
            seriesViewModel.seriesAll.collectLatest {
                homeSeriesAdapter.submitData(it)
            }
        }
    }

    private fun setRandomFilms() {
        binding.rvHomeRandom.adapter = homeRandomAdapter

        lifecycleScope.launch {
            randomViewModel.randomsFilms.collectLatest {
                homeRandomAdapter.submitData(it)
            }
        }
    }

}