package com.swein.androidkotlintool.main.examples.pagingexample.viewmodel

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.swein.androidkotlintool.main.examples.pagingexample.model.GithubRepoItemModel
import com.swein.androidkotlintool.main.examples.pagingexample.modelservice.GithubRepoPagingSource
import kotlinx.coroutines.flow.Flow

class GithubRepoViewModel: ViewModel() {

    val pagingDataFlow: Flow<PagingData<GithubRepoItemModel>> = Pager(
        config = PagingConfig(GithubRepoPagingSource.PAGE_SIZE),
        pagingSourceFactory = {
            GithubRepoPagingSource()
        }
    ).flow //.cachedIn(viewModelScope) // cache

}