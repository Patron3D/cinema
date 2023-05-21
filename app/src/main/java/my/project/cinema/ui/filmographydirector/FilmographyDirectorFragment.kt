package my.project.cinema.ui.filmographydirector

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import my.project.cinema.databinding.FragmentFilmographyDirectorBinding
import my.project.cinema.utilit.Constants

@AndroidEntryPoint
class FilmographyDirectorFragment : Fragment() {

    private lateinit var binding: FragmentFilmographyDirectorBinding
    private val viewModel: FilmographyDirectorViewModel by viewModels()
    private val adapter = FilmographyDirectorAdapter()
    private val args: FilmographyDirectorFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFilmographyDirectorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getInt(Constants.FILM_ID)?.let { viewModel.getDirectorFilms(it) }

        adapter.onFilmClick {
            val action =
                FilmographyDirectorFragmentDirections.actionFilmographyDirectorFragmentToFilmPageFragment(
                    it
                )
            findNavController().navigate(action)
        }

        initClick()
        getFilms()
    }

    private fun initClick() {
        binding.ivBackStack.setOnClickListener {
            view?.findNavController()?.popBackStack()
        }
    }

    private fun getFilms() {
        binding.rvFilmographyDirector.adapter = adapter

        viewModel.responseFilms.observe(viewLifecycleOwner) {
            Log.d("itStaffActor", "$it")
            adapter.setData(it)
        }
    }

}