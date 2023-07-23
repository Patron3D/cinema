package my.project.cinema.ui.popular

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import my.project.cinema.R
import my.project.cinema.databinding.FragmentPopularBinding

@AndroidEntryPoint
class PopularFragment : Fragment() {

    private lateinit var binding: FragmentPopularBinding

    private val viewModel: PopularViewModel by viewModels()
    private val popularFilmsAdapter = PopularPagingAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPopularBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setPopularFilms()
        initToolbar()

        popularFilmsAdapter.onFilmClick {
            val action = PopularFragmentDirections.actionPopularFragmentToFilmPageFragment(it)
            findNavController().navigate(action)
        }

    }

    private fun setPopularFilms() {
        binding.recyclerViewPopular.adapter = popularFilmsAdapter

        lifecycleScope.launch {
            viewModel.bestFilms.collectLatest {
                popularFilmsAdapter.submitData(it)
            }
        }
    }

    private fun initToolbar() {
        binding.toolbarPopular.btnBackStack.setOnClickListener {
            view?.findNavController()?.popBackStack()
        }
        binding.toolbarPopular.btnAbout.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_popularFragment_to_aboutFragment)
        }
    }

}