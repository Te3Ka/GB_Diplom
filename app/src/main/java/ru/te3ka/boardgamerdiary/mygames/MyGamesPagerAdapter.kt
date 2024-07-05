package ru.te3ka.boardgamerdiary.mygames

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import ru.te3ka.boardgamerdiary.R
import ru.te3ka.boardgamerdiary.mygames.mycollection.MyCollectionFragment
import ru.te3ka.boardgamerdiary.mygames.wanttoplay.WantToPlayFragment
import ru.te3ka.boardgamerdiary.mygames.wishlist.WishListFragment

/**
 * Адаптер для ViewPager, который управляет отображением фрагментов в табах.
 *
 * @param fm FragmentManager для управления фрагментами.
 * @param context Контекст для получения строковых ресурсов.
 */
class MyGamesPagerAdapter(fm: FragmentManager,
                          private val context: Context) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    /**
     * Возвращает фрагмент для указанной позиции.
     *
     * @param position Позиция вкладки в ViewPager.
     * @return Фрагмент, соответствующий указанной позиции.
     */
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> MyCollectionFragment()
            1 -> WishListFragment()
            2 -> WantToPlayFragment()
            else -> throw IllegalArgumentException("Invalid position")
        }
    }

    /**
     * Возвращает общее количество вкладок.
     *
     * @return Количество вкладок в ViewPager.
     */
    override fun getCount(): Int {
        return 3
    }

    /**
     * Возвращает заголовок страницы для указанной позиции.
     *
     * @param position Позиция вкладки в ViewPager.
     * @return Заголовок страницы, соответствующий указанной позиции.
     */
    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> context.getString(R.string.collection)
            1 -> context.getString(R.string.wishlist)
            2 -> context.getString(R.string.want_to_play)
            else -> null
        }
    }
}