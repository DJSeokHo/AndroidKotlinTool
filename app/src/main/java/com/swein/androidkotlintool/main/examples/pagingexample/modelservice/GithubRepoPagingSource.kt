package com.swein.androidkotlintool.main.examples.pagingexample.modelservice

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.swein.androidkotlintool.framework.util.log.ILog
import com.swein.androidkotlintool.main.examples.pagingexample.model.GithubRepoItemModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class GithubRepoPagingSource: PagingSource<Int, GithubRepoItemModel>() {

    companion object {
        private const val TAG = "GithubRepoPagingSource"
        private const val START_INDEX = 1
        const val PAGE_SIZE = 20
    }

    override fun getRefreshKey(state: PagingState<Int, GithubRepoItemModel>): Int? {
        // Try to find the page key of the closest page to anchorPosition, from
        // either the prevKey or the nextKey, but you need to handle nullability
        // here:
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey null -> anchorPage is the initial page, so
        //    just return null.
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GithubRepoItemModel> {

        return try {
            coroutineScope {

                val position = params.key ?: START_INDEX

                val response = async {
                    GithubRepoModelService.searchRepositories(position, params.loadSize, "Android", "stars")
                }

                val responseList = response.await()

                val nextKey = if (responseList.isEmpty()) {
                    null
                }
                else {
                    // initial load size = 3 * NETWORK_PAGE_SIZE
                    // ensure we're not requesting duplicating items, at the 2nd request
                    position + (params.loadSize / PAGE_SIZE)
                }

                val prevKey = if (position == START_INDEX) {
                    null
                }
                else {
                    position - 1
                }

                LoadResult.Page(responseList, prevKey, nextKey)
            }
        }
        catch (e: Exception) {
            ILog.debug(TAG, e.message)
            LoadResult.Error(e)
        }

    }

}