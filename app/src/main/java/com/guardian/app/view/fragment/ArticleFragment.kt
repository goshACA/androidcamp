package com.guardian.app.view.fragment

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide

import com.guardian.app.R
import com.guardian.app.viewmodel.ArticleViewModel
import com.guardian.app.viewmodel.ViewModelFactory

class ArticleFragment : Fragment(), LifecycleOwner {

    private lateinit var viewModel: ArticleViewModel
    private lateinit var articleImgView: ImageView
    private lateinit var articleTitle: TextView
    private lateinit var articleText: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_article, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)

        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(activity!!.application)
        )[ArticleViewModel::class.java]

        initViews(view)

        val args = ArticleFragmentArgs.fromBundle(arguments!!)
        ViewCompat.setTransitionName(articleImgView, args.transitionName)
        Glide.with(context!!).load(args.transitionName).into(articleImgView)

        viewModel.articleLiveData.observe(this, Observer {
            articleTitle.text = it.title
            articleText.text = it.fields.text
        })

        viewModel.requestArticle(args.articleId, context!!)
    }

    private fun initViews(view: View) {
        articleImgView = view.findViewById(R.id.articleImg)
        articleText = view.findViewById(R.id.item_body)
        articleTitle = view.findViewById(R.id.item_title)
        articleText.movementMethod = ScrollingMovementMethod()
    }


}
