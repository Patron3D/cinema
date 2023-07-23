package my.project.cinema.ui.premieres

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
import my.project.cinema.R
import my.project.cinema.databinding.FragmentPremiersBinding

@AndroidEntryPoint
class PremieresFragment : Fragment() {

    private lateinit var binding: FragmentPremiersBinding

    private val viewModel: PremieresViewModel by viewModels()
    private val premiersAdapter = PremiersAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPremiersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setPremieres()
        initToolbar()

        premiersAdapter.onFilmClick {
            val action = PremieresFragmentDirections.actionPremiersFragmentToFilmPageFragment(it)
            findNavController().navigate(action)
        }

    }

    private fun setPremieres() {
        binding.recyclerViewPremiers.adapter = premiersAdapter

        viewModel.responsePremier.onEach {
            Log.d("itLog", "$it")
            premiersAdapter.setData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

    }

    private fun initToolbar() {
        binding.toolbarPremieres.btnBackStack.setOnClickListener {
            view?.findNavController()?.popBackStack()
        }
        binding.toolbarPremieres.btnAbout.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_premiersFragment_to_aboutFragment)
        }
    }

}