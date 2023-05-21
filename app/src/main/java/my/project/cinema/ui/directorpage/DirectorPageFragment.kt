package my.project.cinema.ui.directorpage

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import my.project.cinema.R
import my.project.cinema.databinding.FragmentDirectorPageBinding
import my.project.cinema.utilit.Constants

@AndroidEntryPoint
class DirectorPageFragment : Fragment() {

    private lateinit var binding: FragmentDirectorPageBinding
    private val directorViewModel: DirectorViewModel by activityViewModels()
    private val directorAdapter = DirectorAdapter()
    private val args: DirectorPageFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDirectorPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        directorViewModel.getDirector(args.directordbId!!.toInt())
        directorViewModel.getDirectorFilms(args.directordbId!!.toInt())
        getDirector()
        getBestList()
        initClick()

    }

    private fun getDirector() {
        directorViewModel.responseActor.observe(viewLifecycleOwner) {
            val quantity = it.films?.size
            val count = resources.getQuantityString(R.plurals.count_films, quantity!!, quantity)
            binding.apply {
                if (it.nameEn != null) {
                    tvNameEn.visibility = View.VISIBLE
                    tvNameEn.text = it.nameEn
                }
                tvNameRu.text = it.nameRu
                tvProfession.text = it.profession
                Glide.with(ivDirector.context)
                    .load(it.posterUrl)
                    .into(ivDirector)
                tvQuantity.text = count
            }
        }
    }

    private fun getBestList() {
        binding.rvBestList.adapter = directorAdapter

        directorViewModel.responseBestListText.observe(viewLifecycleOwner) {
            Log.d("itStaffActor", "$it")
            directorAdapter.setData(it)
        }
    }

    private fun initClick() {
        binding.ivBackStack.setOnClickListener {
            view?.findNavController()?.popBackStack()
        }
        binding.btTopTenList.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt(Constants.TOP_ID, args.directordbId!!.toInt())
            view?.findNavController()?.navigate(R.id.topTenFragment, bundle)
        }
        binding.btAllFilmography.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt(Constants.FILM_ID, args.directordbId!!.toInt())
            view?.findNavController()?.navigate(R.id.filmographyDirectorFragment, bundle)
        }


        binding.ivDirector.setOnClickListener {
            val extras = FragmentNavigatorExtras(binding.ivDirector to "image_big")
            findNavController().navigate(
                R.id.action_directorPageFragment_to_fullImageDirectorFragment,
                null,
                null,
                extras
            )
        }
    }

}