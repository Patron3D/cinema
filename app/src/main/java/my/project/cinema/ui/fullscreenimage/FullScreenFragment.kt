package my.project.cinema.ui.fullscreenimage

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import my.project.cinema.databinding.FragmentFullScreenBinding
import my.project.cinema.utilit.Constants

@AndroidEntryPoint
class FullScreenFragment : Fragment() {

    private lateinit var binding: FragmentFullScreenBinding
    private val fullScreenViewModel: FullScreenViewModel by viewModels()
    private val screenAdapter = FullScreenAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFullScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initClick()
        setImageScreen()

    }

    private fun initClick() {
        binding.ivBackStack.setOnClickListener {
            view?.findNavController()?.popBackStack()
        }
    }

    private fun setImageScreen() {
        binding.rvScreen.adapter = screenAdapter

        lifecycleScope.launch {
            arguments?.getInt(Constants.SCREEN_ID)?.let { id ->
                fullScreenViewModel.screens(id).collectLatest {
                    screenAdapter.submitData(it)
                    Log.d("ScreenImage", "$it")
                }
            }
        }
    }

}