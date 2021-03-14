package com.doublejj.edit.ui.modules.main.home

import android.opengl.Visibility
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentTransaction
import com.doublejj.edit.ApplicationClass.Companion.MENTOR_AUTH_CONFIRM
import com.doublejj.edit.ApplicationClass.Companion.USER_POSITION
import com.doublejj.edit.ApplicationClass.Companion.sSharedPreferences
import com.doublejj.edit.R
import com.doublejj.edit.databinding.HomeFragmentBinding
import com.doublejj.edit.ui.modules.main.MainActivity
import com.doublejj.edit.ui.modules.main.home.best_sympathy.BestSympathyFragment
import com.doublejj.edit.ui.modules.main.home.today_sentence.TodaySentenceFragment
import com.doublejj.edit.ui.modules.main.home.waiting_for_comment.WaitingForCommentFragment
import com.doublejj.edit.ui.utils.snackbar.CustomSnackbar
import com.google.android.material.snackbar.Snackbar

class HomeFragment : Fragment() {
    private val TAG: String = javaClass.simpleName.toString()
    private lateinit var binding: HomeFragmentBinding
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate<HomeFragmentBinding>(inflater, R.layout.home_fragment, container, false)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        binding.homeViewModel = viewModel
        binding.lifecycleOwner = this

        // MainActivty의 fragment 개수 관리 변수 증가
        (activity as MainActivity).increaseFragmentCount()

        /** toolbar buttons **/
        binding.tvLogo.setOnClickListener {
            // TODO : scroll to top
            Log.d(TAG, "logo clicked")
        }
        binding.ibRefresh.setOnClickListener {
            // TODO : refresh data
            Log.d(TAG, "refresh clicked")
        }

        // fragment안에서 새로운 fragment 전환
        binding.tvTitleTodaySentence.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .add(R.id.fl_home, TodaySentenceFragment())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit()
        }

        binding.tvTitleWaitedComment.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .add(R.id.fl_home, WaitingForCommentFragment())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit()
        }

        binding.tvTitleSympathyComment.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .add(R.id.fl_home, BestSympathyFragment())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit()
        }

        when (sSharedPreferences.getString(USER_POSITION, "MENTEE")) {
            "MENTEE" -> {
                binding.fabMentee.visibility = View.VISIBLE
            }
            "MENTOR" -> {
                binding.fabMentee.visibility = View.GONE
                // TODO : 첫 home 화면에서 아직 멘토 인증 안했다면 스낵바 띄우기 & 지우기
                if (!sSharedPreferences.getBoolean(MENTOR_AUTH_CONFIRM, false)) {
                    CustomSnackbar.make(binding.root, getString(R.string.snackbar_description_mentor), Snackbar.LENGTH_INDEFINITE).show()
                }
            }
        }

        binding.fabMentee.setOnClickListener {
            // TODO : 자소서 입력하기로 이동
        }
        
        // TODO : 오늘의 문장 0개일 때 카드 데이터 할당
        // 오늘의 문장 0개일 때 카드 처리
        val uploadedSentenceCountTest = 0
        if (uploadedSentenceCountTest <= 0) {
            binding.cvHomeTodaySentenceZero0.visibility = View.VISIBLE
            binding.cvHomeTodaySentenceZero1.visibility = View.VISIBLE
            binding.cvHomeTodaySentenceZero2.visibility = View.VISIBLE
            binding.cvHomeTodaySentenceZero3.visibility = View.VISIBLE
            binding.cvHomeTodaySentenceZero4.visibility = View.VISIBLE
            binding.cvHomeTodaySentence0.visibility = View.GONE
            binding.cvHomeTodaySentence1.visibility = View.GONE
            binding.cvHomeTodaySentence2.visibility = View.GONE
            binding.cvHomeTodaySentence3.visibility = View.GONE
            binding.cvHomeTodaySentence4.visibility = View.GONE
        }
        else {
            binding.cvHomeTodaySentenceZero0.visibility = View.GONE
            binding.cvHomeTodaySentenceZero1.visibility = View.GONE
            binding.cvHomeTodaySentenceZero2.visibility = View.GONE
            binding.cvHomeTodaySentenceZero3.visibility = View.GONE
            binding.cvHomeTodaySentenceZero4.visibility = View.GONE
            binding.cvHomeTodaySentence0.visibility = View.VISIBLE
            binding.cvHomeTodaySentence1.visibility = View.VISIBLE
            binding.cvHomeTodaySentence2.visibility = View.VISIBLE
            binding.cvHomeTodaySentence3.visibility = View.VISIBLE
            binding.cvHomeTodaySentence4.visibility = View.VISIBLE
        }
        
        return binding.root
    }

    override fun onDetach() {
        super.onDetach()
        // MainActivty의 fragment 개수 관리 변수 감소
        (activity as MainActivity).decreaseFragmentCount()
    }
}