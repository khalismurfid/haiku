package id.ac.ui.cs.mobileprogramming.khalismurfid.haiku

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.databinding.FragmentHomeBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding: FragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.pagerPoem.adapter = activity?.supportFragmentManager?.let {
            HomeFragmentAdapter(
                it
            )
        }
        binding.fabCreatePoem.setOnClickListener{
            navigateToComposePoemPage()
        }
        return binding.root
    }

    companion object {
        fun newInstance(): HomeFragment{
            val fragment = HomeFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    private fun navigateToComposePoemPage(){
        val intent = Intent(activity, ComposePoemActivity::class.java)
        startActivity(intent)
        activity?.overridePendingTransition(com.google.android.material.R.anim.abc_slide_in_bottom, com.google.android.material.R.anim.abc_slide_out_bottom);
    }

    private inner class HomeFragmentAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
        override fun getCount(): Int = NUM_PAGES
        override fun getItem(position: Int): Fragment = PoemSlideFragment()
    }
}
