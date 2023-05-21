package my.project.cinema.ui.bestfilms

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
import my.project.cinema.databinding.FragmentBestFilmsBinding

@AndroidEntryPoint
class BestFilmsFragment : Fragment() {

    private lateinit var binding: FragmentBestFilmsBinding
    private val viewModel: BestFilmsViewModel by viewModels()
    private val bestFilmsAdapter = PagingBestFilmsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBestFilmsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setBestFilms()
        initToolbar()

        bestFilmsAdapter.onFilmClick {
            val action = BestFilmsFragmentDirections.actionBestFilmsFragmentToFilmPageFragment(it)
            findNavController().navigate(action)
        }
    }

    private fun setBestFilms() {
        binding.recyclerViewBestFilms.adapter = bestFilmsAdapter

        lifecycleScope.launch {
            viewModel.bestFilms.collectLatest {
                bestFilmsAdapter.submitData(it)
            }
        }
    }

    private fun initToolbar() {
        binding.toolbarBestFilms.btnBackStack.setOnClickListener {
            view?.findNavController()?.popBackStack()
        }
        binding.toolbarBestFilms.btnAbout.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_bestFilmsFragment_to_aboutFragment)
        }
    }

}