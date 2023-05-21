package my.project.cinema.ui.actorpage

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
import my.project.cinema.databinding.FragmentActorPageBinding
import my.project.cinema.utilit.Constants

@AndroidEntryPoint
class ActorPageFragment : Fragment() {

    private lateinit var binding: FragmentActorPageBinding
    private val actorViewModel: ActorViewModel by activityViewModels()
    private val actorAdapter = ActorAdapter()
    private val args: ActorPageFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentActorPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        args.actordbId?.let { actorViewModel.getActor(it.toInt()) }
        args.actordbId?.let { actorViewModel.getActorFilms(it.toInt()) }

        getActor()
        getBestList()
        initClick()
    }

    private fun getActor() {
        actorViewModel.responseActor.observe(viewLifecycleOwner) {
            val quantity = it.films?.size
            val count = resources.getQuantityString(R.plurals.count_films, quantity!!, quantity)
            binding.apply {
                if (it.nameEn != null){
                    tvNameEn.visibility = View.VISIBLE
                    tvNameEn.text = it.nameEn
                }
                tvNameRu.text = it.nameRu
                tvProfession.text = it.profession
                Glide.with(ivActor.context)
                    .load(it.posterUrl)
                    .into(ivActor)
                tvQuantity.text = count
            }
        }
    }

    private fun getBestList() {
        binding.rvBestList.adapter = actorAdapter

        actorViewModel.responseBestListText.observe(viewLifecycleOwner) {
            Log.d("itStaffActor", "$it")
            actorAdapter.setData(it)
        }
    }

    private fun initClick() {
        binding.ivBackStack.setOnClickListener {
            view?.findNavController()?.popBackStack()
        }

        binding.btAllFilmography.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt(Constants.FILMOGRAPHY_ID, args.actordbId!!.toInt())
            view?.findNavController()?.navigate(R.id.filmographyFragment, bundle)
        }

        binding.btTopTenList.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt(Constants.TOP_ID, args.actordbId!!.toInt())
            view?.findNavController()?.navigate(R.id.topTenFragment, bundle)
        }

        binding.ivActor.setOnClickListener {
            val extras = FragmentNavigatorExtras(binding.ivActor to "image_big")
            findNavController().navigate(
                R.id.action_actorPageFragment_to_fullImageFragment,
                null,
                null,
                extras
            )
        }
    }

}