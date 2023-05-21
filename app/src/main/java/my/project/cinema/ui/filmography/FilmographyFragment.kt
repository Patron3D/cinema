package my.project.cinema.ui.filmography

import android.annotation.SuppressLint
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
import my.project.cinema.databinding.FragmentFilmographyBinding
import my.project.cinema.utilit.Constants

@AndroidEntryPoint
class FilmographyFragment : Fragment() {

    private lateinit var binding: FragmentFilmographyBinding
    private val viewModel: FilmographyViewModel by viewModels()
    private val actorAdapter = ActorAdapter()
    private val voiceAdapter = VoiceAdapter()
    private val selfAdapter = SelfAdapter()
    private val args: FilmographyFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFilmographyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getInt(Constants.FILMOGRAPHY_ID)?.let { viewModel.getActor(it) }
        arguments?.getInt(Constants.FILMOGRAPHY_ID)?.let { viewModel.getActorFilms(it) }

        getActor()
        initClick()

        actorAdapter.onFilmClick {
            val action =
                FilmographyFragmentDirections.actionFilmographyFragmentToFilmPageFragment(it)
            findNavController().navigate(action)
        }
        selfAdapter.onFilmClick {
            val action =
                FilmographyFragmentDirections.actionFilmographyFragmentToFilmPageFragment(it)
            findNavController().navigate(action)
        }
        voiceAdapter.onFilmClick {
            val action =
                FilmographyFragmentDirections.actionFilmographyFragmentToFilmPageFragment(it)
            findNavController().navigate(action)
        }

    }

    @SuppressLint("SetTextI18n")
    private fun getActor() {
        viewModel.responseActor.observe(viewLifecycleOwner) {

            var actorMale = 0
            var selfMale = 0
            var voiceMale = 0
            var actorFemale = 0
            var selfFemale = 0
            var voiceFemale = 0

            binding.apply {
                if (it.nameRu != null) {
                    tvName.text = it.nameRu
                } else {
                    tvName.text = it.nameEn
                }

                if (it.sex == MALE) {

                    it.films!!.onEach { list ->
                        if (list.professionKey == ACTOR) {
                            actorMale++
                        } else if (list.professionKey == VOICE_MALE) {
                            voiceMale++
                        } else if (list.professionKey == HIMSELF) {
                            selfMale++
                        }
                    }
                }

                binding.chipFilms1.text = "Актер  $actorMale"
                binding.chipFilms2.text = "Актер дубляжа  $voiceMale"
                binding.chipFilms3.text = "Актер: играет самого себя  $selfMale"

                if (it.sex == FEMALE) {

                    it.films!!.onEach { list ->
                        if (list.professionKey == ACTOR) {
                            actorFemale++
                        } else if (list.professionKey == VOICE_FEMALE) {
                            voiceFemale++
                        } else if (list.professionKey == HERSELF) {
                            selfFemale++
                        }
                    }

                    binding.chipFilms1.text = "Актриса  $actorFemale"
                    binding.chipFilms2.text = "Актриса дубляжа  $voiceFemale"
                    binding.chipFilms3.text = "Актриса: играет саму себя  $selfFemale"
                }
            }
        }
    }

    private fun initClick() {
        binding.ivBackStack.setOnClickListener {
            view?.findNavController()?.popBackStack()
        }

        binding.chipFilms1.setOnClickListener {
            binding.rvFilms.adapter = actorAdapter

            viewModel.responseFilmsList.observe(viewLifecycleOwner) {
                Log.d("itStaffActor", "$it")
                actorAdapter.setData(it)
            }
        }

        binding.chipFilms2.setOnClickListener {
            binding.rvFilms.adapter = voiceAdapter

            viewModel.responseFilmsList.observe(viewLifecycleOwner) {
                Log.d("itStaffActor", "$it")
                voiceAdapter.setData(it)
            }
        }

        binding.chipFilms3.setOnClickListener {
            binding.rvFilms.adapter = selfAdapter

            viewModel.responseFilmsList.observe(viewLifecycleOwner) {
                Log.d("itStaffActor", "$it")
                selfAdapter.setData(it)
            }
        }
    }

    companion object {
        const val MALE = "MALE"
        const val FEMALE = "FEMALE"
        const val ACTOR = "ACTOR"
        const val VOICE_MALE = "VOICE_MALE"
        const val VOICE_FEMALE = "VOICE_FEMALE"
        const val HIMSELF = "HIMSELF"
        const val HERSELF = "HERSELF"

    }

}