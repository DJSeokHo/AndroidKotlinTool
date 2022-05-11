package com.swein.androidkotlintool.main.examples.pagingexample.modelservice

import com.swein.androidkotlintool.framework.utility.debug.ILog
import com.swein.androidkotlintool.framework.utility.okhttp.OKHttpWrapper
import com.swein.androidkotlintool.main.examples.pagingexample.model.GithubRepoItemModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject

object GithubRepoModelService {

    private const val TAG = "GithubRepoModelService"

    /**
     * example
     * page: 1
     * perPage : 5
     * q : Android
     * sort: starts
     */
    suspend fun searchRepositories(page: Int, perPage: Int, q: String, sort: String): MutableList<GithubRepoItemModel> = withContext(Dispatchers.IO) {
        val url = "https://api.github.com/search/repositories?page=$page&per_page=$perPage&q=$q&sort=$sort"

        val coroutineResponse = OKHttpWrapper.requestGet(url)

        val responseString: String = OKHttpWrapper.getStringResponse(coroutineResponse.response)
        ILog.debug(TAG, responseString)

        OKHttpWrapper.cancelCall(coroutineResponse.call)

        val jsonObject = JSONObject(responseString)

        val itemsArray =jsonObject.getJSONArray("items") as JSONArray

        val modelList = mutableListOf<GithubRepoItemModel>()
        var githubRepoItemModel: GithubRepoItemModel
        for (i in 0 until itemsArray.length() - 1) {
            githubRepoItemModel = GithubRepoItemModel(
                id = (itemsArray[i] as JSONObject).getInt("id"),
                nodeId = (itemsArray[i] as JSONObject).getString("node_id"),
                name = (itemsArray[i] as JSONObject).getString("name"),
                fullName = (itemsArray[i] as JSONObject).getString("full_name"),
                description = (itemsArray[i] as JSONObject).getString("description"),
                stars = (itemsArray[i] as JSONObject).getInt("stargazers_count")
            )

            modelList.add(githubRepoItemModel)
        }

        return@withContext modelList
    }

}