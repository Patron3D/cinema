package my.project.cinema.ui.topten

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
import my.project.cinema.databinding.FragmentBestTenBinding
import my.project.cinema.utilit.Constants

@AndroidEntryPoint
class BestTenFragment : Fragment() {

    private lateinit var binding: FragmentBestTenBinding
    private val topViewModel: TopTenViewModel by viewModels()
    private val topAdapter = TopTenAdapter()
    private val args: BestTenFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBestTenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getInt(Constants.TOP_ID)?.let { topViewModel.getTopFilms(it) }

        topAdapter.onFilmClick {
            val action = BestTenFragmentDirections.actionTopTenFragmentToFilmPageFragment(it)
            findNavController().navigate(action)
        }

        getTopList()
        initClick()
    }

    private fun getTopList() {
        binding.recyclerViewTopTen.adapter = topAdapter

        topViewModel.responseBestListText.observe(viewLifecycleOwner) {
            Log.d("itStaffActor", "$it")
            topAdapter.setData(it)
        }
    }

    private fun initClick() {
        binding.toolbarTopTen.btnBackStack.setOnClickListener {
            view?.findNavController()?.popBackStack()
        }

    }

}