package my.project.cinema.ui.alldirectorpage

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
import my.project.cinema.databinding.FragmentDirectorBinding
import my.project.cinema.utilit.Constants

@AndroidEntryPoint
class AllDirectorFragment : Fragment() {

    private lateinit var binding: FragmentDirectorBinding
    private val allDirectorViewModel: AllDirectorViewModel by viewModels()
    private val directorAdapter = AllDirectorAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDirectorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getInt(Constants.DIRECTORS_ID)?.let { allDirectorViewModel.getFilmDirector(it) }
        directorAdapter.onFilmClick {
            val action = AllDirectorFragmentDirections.actionAllDirectorFragmentToDirectorPageFragment(it)
            findNavController().navigate(action)
        }
        getActor()
        initClick()

    }

    private fun initClick() {
        binding.toolbarAllDirectors.btnBackStack.setOnClickListener {
            view?.findNavController()?.popBackStack()
        }

    }

    private fun getActor() {
        binding.recyclerViewAllDirector.adapter = directorAdapter

        allDirectorViewModel.responseDirector.onEach {
            Log.d("itStaffActor", "$it")
            directorAdapter.setData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

}