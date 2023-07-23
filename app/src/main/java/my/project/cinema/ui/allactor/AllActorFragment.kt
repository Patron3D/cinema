package my.project.cinema.ui.allactor

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
import my.project.cinema.databinding.FragmentAllActorBinding
import my.project.cinema.utilit.Constants

@AndroidEntryPoint
class AllActorFragment : Fragment() {

    private lateinit var binding: FragmentAllActorBinding
    private val allActorViewModel: AllActorViewModel by viewModels()
    private val actorAdapter = AllActorAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAllActorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getInt(Constants.ACTORS_ID)?.let { allActorViewModel.getFilmActor(it) }
        actorAdapter.onFilmClick {
            val action = AllActorFragmentDirections.actionAllActorFragmentToActorPageFragment(it)
            findNavController().navigate(action)
        }
        getActor()
        initClick()

    }

    private fun initClick() {
        binding.toolbarAllActors.btnBackStack.setOnClickListener {
            view?.findNavController()?.popBackStack()
        }

    }

    private fun getActor() {
        binding.recyclerViewAllActors.adapter = actorAdapter

        allActorViewModel.responseActor.onEach {
            Log.d("itStaffActor", "$it")
            actorAdapter.setData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

}