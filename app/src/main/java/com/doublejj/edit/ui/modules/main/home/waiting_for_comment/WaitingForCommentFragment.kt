package com.doublejj.edit.ui.modules.main.home.waiting_for_comment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.doublejj.edit.R
import com.doublejj.edit.data.models.sentence.SentenceData
import com.doublejj.edit.databinding.WaitingForCommentFragmentBinding
import com.doublejj.edit.ui.modules.main.MainActivity
import com.doublejj.edit.ui.modules.main.home.today_sentence.SentenceAdapter

class WaitingForCommentFragment : Fragment() {
    private lateinit var binding: WaitingForCommentFragmentBinding
    private lateinit var viewModel: WaitingForCommentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.waiting_for_comment_fragment, container, false)
        viewModel = ViewModelProvider(this).get(WaitingForCommentViewModel::class.java)

        binding.waitingForCommentViewModel = viewModel
        binding.lifecycleOwner = this
        (activity as MainActivity).increaseFragmentCount()

        /** set adapter **/
        setAdapter()

        /** toolbar buttons **/
        binding.ibBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        binding.ibRefresh.setOnClickListener {
            // TODO : refresh data
        }

        return binding.root
    }

    fun setAdapter() {
        var sentenceDataList = mutableListOf<SentenceData>()

        // TODO : 테스트 코드 지우기
        sentenceDataList.add(
            SentenceData(
            "purple/suprise",
            0L,
            "제인",
            "개발",
            "직무 관련 경험",
            "실습은 요양원에서 했는데 현장에서 일을 해보니 생각했던 것보다 사회복지사가 신경써야 할 부분들이 많았지만 어르신들과 보내는 시간이 너무 즐거웠습니다.",
            20L,
            sympathy = true
        )
        )
        sentenceDataList.add(
            SentenceData(
            "blue/wink",
            1L,
            "그린",
            "개발",
            "직무 관련 경험",
            "어쩌구 저쩌구",
            10L,
            sympathy = true
        )
        )
        sentenceDataList.add(
            SentenceData(
            "lightPurple/relief",
            2L,
            "조이",
            "디자인",
            "직무 관련 경험",
            "어쩌구 저쩌구",
            30L,
            sympathy = false
        )
        )

        binding.rvSentence.layoutManager = LinearLayoutManager(context)
        binding.rvSentence.adapter = SentenceAdapter(requireContext(), sentenceDataList, requireActivity().supportFragmentManager)
    }

    override fun onDetach() {
        super.onDetach()
        (activity as MainActivity).decreaseFragmentCount()
    }
}