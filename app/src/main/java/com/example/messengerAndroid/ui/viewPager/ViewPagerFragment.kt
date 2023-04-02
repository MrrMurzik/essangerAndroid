package com.example.messengerAndroid.ui.viewPager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.messengerAndroid.databinding.FragmentProfileViewPagerBinding
import com.example.messengerAndroid.foundation.base.BaseFragment
import com.example.messengerAndroid.ui.viewPager.myContacts.MyContactsFragment
import com.example.messengerAndroid.ui.viewPager.myProfile.MyProfileFragment
import com.example.messengerAndroid.Constants.NUM_TABS
import com.google.android.material.tabs.TabLayoutMediator


class ViewPagerFragment : BaseFragment<FragmentProfileViewPagerBinding>(FragmentProfileViewPagerBinding::inflate) {

    lateinit var viewPager: ViewPager2



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPager = binding.pager
        viewPager.adapter = ScreenSlidePagerAdapter(this)

        TabLayoutMediator(binding.tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "My profile"
                else -> "My contacts"
            }
        }.attach()
    }



    private inner class ScreenSlidePagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int = NUM_TABS

        override fun createFragment(position: Int): Fragment {
            return when(position) {
                0 -> MyProfileFragment()
                else -> MyContactsFragment()
            }
        }
    }

}