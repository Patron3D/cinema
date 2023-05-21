package my.project.cinema.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import my.project.cinema.startfragments.FirstFragment
import my.project.cinema.startfragments.LoaderFragment
import my.project.cinema.startfragments.SecondFragment
import my.project.cinema.startfragments.ThirdFragment

class StartPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FirstFragment()
            1 -> SecondFragment()
            2 -> ThirdFragment()
            else -> LoaderFragment()
        }
    }

}