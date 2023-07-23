package my.project.cinema.ui.directorpage

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import my.project.cinema.databinding.FragmentFullImageDirectorBinding

@AndroidEntryPoint
class FullImageDirectorFragment : Fragment() {

    private lateinit var binding: FragmentFullImageDirectorBinding
    private val directorViewModel: DirectorViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val animation = TransitionInflater.from(requireContext()).inflateTransition(
            android.R.transition.explode
        )
        sharedElementEnterTransition = animation
        sharedElementReturnTransition = animation
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFullImageDirectorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getDirector()

        binding.ivDirector.setOnClickListener {
            view.findNavController().popBackStack()
        }

    }

    private fun getDirector() {
        directorViewModel.responseActor.observe(viewLifecycleOwner) {
            binding.apply {
                Glide.with(ivDirector.context)
                    .load(it.posterUrl)
                    .into(ivDirector)
            }
        }
    }

}