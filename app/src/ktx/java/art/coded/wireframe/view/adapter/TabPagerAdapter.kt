package art.coded.wireframe.view.adapter

import art.coded.wireframe.R
import androidx.fragment.app.FragmentPagerAdapter
import art.coded.wireframe.view.fragment.TabFragment
import androidx.annotation.StringRes
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

private val LOG_TAG = TabPagerAdapter::class.java.simpleName
@StringRes private val TAB_TITLES = intArrayOf(R.string.tab_text_1, R.string.tab_text_2)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class TabPagerAdapter(private val mContext: Context, fm: FragmentManager?) : FragmentPagerAdapter(
    fm!!
) {
    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a TabFragment (defined as a static inner class below).
        return TabFragment.newInstance(position + 1)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mContext.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        // Show 2 total pages.
        return 2
    }
}