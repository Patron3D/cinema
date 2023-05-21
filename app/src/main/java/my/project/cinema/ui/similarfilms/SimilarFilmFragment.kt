package my.project.cinema.ui.similarfilms

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
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import my.project.cinema.databinding.FragmentSimilarFilmBinding
import my.project.cinema.utilit.Constants

@AndroidEntryPoint
class SimilarFilmFragment : Fragment() {

    private lateinit var binding: FragmentSimilarFilmBinding
    private val similarFilmsViewModel: SimilarFilmViewModel by viewModels()
    private val similarFilmsAdapter = SimilarFilmsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSimilarFilmBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbarSimilarFilms.btnBackStack.setOnClickListener {
            view.findNavController().popBackStack()
        }
        arguments?.getInt(Constants.SIMILAR_ID)?.let { similarFilmsViewModel.getSimilarFilm(it) }
        getSimilarFilm()

        similarFilmsAdapter.onFilmClick {
            val action =
                SimilarFilmFragmentDirections.actionSimilarFilmFragmentToFilmPageFragment(it)
            findNavController().navigate(action)
        }

    }

    private fun getSimilarFilm() {
        binding.recyclerViewSimilarFilms.adapter = similarFilmsAdapter

        similarFilmsViewModel.responseSimilar.onEach {
            Log.d("itStaffActor", "$it")
            similarFilmsAdapter.setData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

}