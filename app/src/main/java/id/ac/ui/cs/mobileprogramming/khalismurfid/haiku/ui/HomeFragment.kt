package id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.Observer
import id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.R
import id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.common.Common
import id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.database.entity.Poem
import id.ac.ui.cs.mobileprogramming.khalismurfid.haiku.databinding.FragmentHomeBinding
import kotlinx.android.synthetic.main.fragment_home.*

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
    private lateinit var mHandler: Handler
    private lateinit var mRunnable: Runnable

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentHomeBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_home, container, false
        )
        val activity = activity as MainActivity
        activity.poemViewModel.allPoems.observe(activity, Observer { poems ->
            // Update the cached copy of the words in the adapter.
            poems?.let {
                binding.pagerPoem.adapter = HomeFragmentAdapter(
                    activity.supportFragmentManager, poems
                )
            }
        })
        binding.fabCreatePoem.setOnClickListener{
            navigateToComposePoemPage()
        }
        binding.locationText.text = activity.location
        mHandler = Handler(Looper.getMainLooper())
        mRunnable = Runnable {
            time?.text = Common.timePassed.toString()
            mHandler.postDelayed(mRunnable, 1000)
        }
        mHandler.postDelayed(mRunnable, 1000)
        return binding.root
    }

    override fun onDestroyView() {
        mHandler.removeCallbacks(mRunnable)
        super.onDestroyView()
    }

    companion object {
        fun newInstance(): HomeFragment {
            val fragment = HomeFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    private fun navigateToComposePoemPage(){
        val intent = Intent(activity, ComposePoemActivity::class.java)
        intent.putExtra("location", (activity as MainActivity).location)
        startActivity(intent)
        activity?.overridePendingTransition(
            com.google.android.material.R.anim.abc_slide_in_bottom,
            com.google.android.material.R.anim.abc_slide_out_bottom
        );
    }

    private inner class HomeFragmentAdapter(fm: FragmentManager, private val poems: Array<Poem>) : FragmentStatePagerAdapter(
        fm
    ) {
        override fun getCount(): Int {
            return poems.size
        }
        override fun getItem(position: Int): Fragment {
            val fragment = PoemSlideFragment()
            val bundle = Bundle()
            bundle.putInt("poemId", poems[position].id)
            fragment.arguments = bundle
            return fragment
        }
    }
}
