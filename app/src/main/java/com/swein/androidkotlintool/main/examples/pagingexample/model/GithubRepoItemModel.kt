package com.swein.androidkotlintool.main.examples.pagingexample.model

data class GithubRepoItemModel(
    val id: Int,
    val nodeId: String,
    val name: String,
    val fullName: String,
    val description: String,
    val stars: Int
)