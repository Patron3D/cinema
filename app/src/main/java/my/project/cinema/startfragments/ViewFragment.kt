package my.project.cinema.startfragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import my.project.cinema.adapter.StartPagerAdapter
import my.project.cinema.databinding.FragmentViewBinding

@AndroidEntryPoint
class ViewFragment : Fragment() {

    private lateinit var binding: FragmentViewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initFragments()

    }

    private fun initFragments() {
        binding.viewPager2.adapter = StartPagerAdapter(requireActivity())
    }

}