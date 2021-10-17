package com.guardian.app.view.fragment

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.os.Handler
import android.view.*
import android.widget.ImageView
import android.widget.Switch
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.*
import com.guardian.app.R
import com.guardian.app.adapter.ArticleAdapter
import com.guardian.app.adapter.SwipeToDeleteCallback
import com.guardian.app.entity.RecyclerArticleItem
import com.guardian.app.service.NotificationJobIntentService
import com.guardian.app.utils.BitmapUtils
import com.guardian.app.utils.LocalDateUtils
import com.guardian.app.viewmodel.RecyclerViewModel
import kotlinx.android.synthetic.main.fragment_news.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class NewsFragment : Fragment(), LifecycleOwner {
    private var page: Int = 1

    private val  viewModel by viewModel<RecyclerViewModel>()

    private lateinit var articleAdapter: ArticleAdapter
    private lateinit var articleRecyclerView: RecyclerView
    private lateinit var onSuccessListener: OnSuccessListener

    private val handler = Handler()
    private lateinit var newDataCheckerRuunable: Runnable


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newDataCheckerRuunable = Runnable {
            viewModel.loadNewData(LocalDateUtils.getCurrentDateString(), context!!)
            handler.postDelayed(newDataCheckerRuunable, 30000)
        }


        articleRecyclerView = view.findViewById(R.id.recycler_data)
        /*onSuccessListener = object :
            OnSuccessListener {
            override fun onSuccess(data: ArrayList<RecyclerArticleItem>) {
                articleAdapter =
                    ArticleAdapter(context!!, data, object : ArticleAdapter.OnArticleClickListener {
                        override fun onClick(id: String, imgView: ImageView) {
                            viewModel.saveRecyclerPosition(articleAdapter.getPositionByItemId(id))
                            viewModel.saveIsClicked(true)
                            val extras = FragmentNavigatorExtras(
                                imgView to imgView.transitionName
                            )
                            val action =
                                NewsFragmentDirections.actionNewsFragmentToArticleFragment(
                                    id,
                                    imgView.transitionName
                                )
                            view.findNavController().navigate(action, extras)
                        }

                    }, object : ArticleAdapter.OnRemoveItemListener {
                        override fun onDeleteItem(item: RecyclerArticleItem) {
                            viewModel.deleteItem(item)
                        }


                        override fun onRestoreItem(item: RecyclerArticleItem) {
                            viewModel.restoreItem(item)
                        }

                    }, object : ArticleAdapter.OnLikeItemListener {
                        override fun onLikedItem(item: RecyclerArticleItem) {
                            viewModel.likeItem(item)
                        }

                        override fun onRemoveLikedItem(item: RecyclerArticleItem) {
                            viewModel.removeLikeFromItem(item)
                        }

                    })
                viewModel.articleListLiveData.observe(this@NewsFragment, Observer {
                    if (::articleAdapter.isInitialized)
                        articleAdapter.updateData(it)
                })
                initRecycler()
                startChecking()
            }
        }*/
        initRecyclerInitially()
        viewModel.recyclerLiveData.observe(this,
            Observer<PagedList<RecyclerArticleItem>> {
                articleAdapter.submitList(it)
            })
        //viewModel.getArticlesData(context!!, onSuccessListener)


    }

    private fun initRecyclerInitially(){
       articleAdapter =
           ArticleAdapter(context!!, ArrayList(), object : ArticleAdapter.OnArticleClickListener {
               override fun onClick(id: String, imgView: ImageView) {
                   viewModel.saveRecyclerPosition(articleAdapter.getPositionByItemId(id))
                   viewModel.saveIsClicked(true)
                   val extras = FragmentNavigatorExtras(
                       imgView to imgView.transitionName
                   )
                   val action =
                       NewsFragmentDirections.actionNewsFragmentToArticleFragment(
                           id,
                           imgView.transitionName
                       )
                   view?.findNavController()?.navigate(action, extras)
               }

           }, object : ArticleAdapter.OnRemoveItemListener {
               override fun onDeleteItem(item: RecyclerArticleItem) {
                   viewModel.deleteItem(item)
               }


               override fun onRestoreItem(item: RecyclerArticleItem) {
                   viewModel.restoreItem(item)
               }

           }, object : ArticleAdapter.OnLikeItemListener {
               override fun onLikedItem(item: RecyclerArticleItem) {
                   viewModel.likeItem(item)
               }

               override fun onRemoveLikedItem(item: RecyclerArticleItem) {
                   viewModel.removeLikeFromItem(item)
               }

           })
        articleRecyclerView.adapter = articleAdapter
        articleRecyclerView.layoutManager =
            LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL, false)
    }

    private fun startChecking() {
        newDataCheckerRuunable.run()
    }

    private fun stopChecking() {
        handler.removeCallbacks(newDataCheckerRuunable)
    }

    override fun onDestroyView() {
     //   val intent = Intent(context, NotificationJobIntentService::class.java)
      //  viewModel.startService(intent, context!!)
        super.onDestroyView()
    }


    private fun initRecycler() {
       // changeRecyclerViewType(viewModel.getDesignState())
       /* articleRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val visibleItemCount = recyclerView.childCount
                val totalItemCount = recyclerView.layoutManager?.itemCount
                val firstVisiblePosition =
                    (articleRecyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                if (totalItemCount != null && visibleItemCount + firstVisiblePosition >= totalItemCount && firstVisiblePosition >= 0) {
                    viewModel.getArticlesData(context!!, null, ++page)
                }
            }
        })*/
        /*ItemTouchHelper(
            SwipeToDeleteCallback(
                articleAdapter, btn_snackbar, Paint(),
                BitmapUtils.getBitmapFromVectorDrawable(R.drawable.ic_delete, context!!)
            )
        )
            .attachToRecyclerView(articleRecyclerView)
        articleRecyclerView.apply {
            postponeEnterTransition()
            viewTreeObserver
                .addOnPreDrawListener {
                    startPostponedEnterTransition()
                    true
                }
        }
        if (viewModel.getIsClicked()) {
            articleRecyclerView.scrollToPosition(viewModel.getRecyclerPosition())
            viewModel.saveIsClicked(false)
        }*/
    }

    private fun changeRecyclerViewType(isPinterest: Boolean) {
        if (isPinterest) {
            articleRecyclerView.layoutManager = GridLayoutManager(context!!, 2)
            articleRecyclerView.itemAnimator = DefaultItemAnimator()
        } else {
            articleRecyclerView.layoutManager =
                LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL, false)
        }
        articleRecyclerView.hasFixedSize()
        articleRecyclerView.adapter = articleAdapter
        articleAdapter.notifyDataSetChanged()
        viewModel.saveDesignState(isPinterest)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.recycler_switch_menu, menu)
        val mySwitch: Switch =
            menu.findItem(R.id.type_change).actionView.findViewById(R.id.switch_button)
        mySwitch.isChecked = viewModel.getDesignState()
        mySwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            changeRecyclerViewType(
                isChecked
            )
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onDetach() {
        super.onDetach()
        stopChecking()
    }

    interface OnSuccessListener {
        fun onSuccess(data: ArrayList<RecyclerArticleItem>)
    }
}
