package ru.te3ka.boardgamerdiary.mygames

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import ru.te3ka.boardgamerdiary.R


class MyGamesPagerAdapter(fm: FragmentManager,
                          private val context: Context) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> MyCollectionFragment()
            1 -> WhishListFragment()
            2 -> WantToPlayFragment()
            else -> throw IllegalArgumentException("Invalid position")
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> context.getString(R.string.collection)
            1 -> context.getString(R.string.wishlist)
            2 -> context.getString(R.string.want_to_play)
            else -> null
        }
    }
}