package my.project.cinema.ui.random

import android.os.Bundle
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
import kotlinx.coroutines.launch
import my.project.cinema.R
import my.project.cinema.databinding.FragmentRandomBinding
import my.project.cinema.utilit.Variables

@AndroidEntryPoint
class RandomFragment : Fragment() {

    private lateinit var binding: FragmentRandomBinding
    private val viewModel: RandomViewModel by viewModels()
    private val randomFilmsAdapter = RandomPagingAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRandomBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRandomFilms()
        initToolbar()

        binding.toolbarRandom.tvTitleGenre.text = Variables.titleGenre
        binding.toolbarRandom.tvTitleCountry.text = Variables.titleCountry

        randomFilmsAdapter.onFilmClick {
            val action = RandomFragmentDirections.actionRandomFragmentToFilmPageFragment(it)
            findNavController().navigate(action)
        }

    }

    private fun setRandomFilms() {
        binding.recyclerViewSeries.adapter = randomFilmsAdapter

        lifecycleScope.launch {
            viewModel.randomsFilms.collectLatest {
                randomFilmsAdapter.submitData(it)
            }
        }
    }

    private fun initToolbar() {
        binding.toolbarRandom.btnBackStack.setOnClickListener {
            view?.findNavController()?.popBackStack()
        }
        binding.toolbarRandom.btnAbout.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_randomFragment_to_aboutFragment)
        }
    }

}