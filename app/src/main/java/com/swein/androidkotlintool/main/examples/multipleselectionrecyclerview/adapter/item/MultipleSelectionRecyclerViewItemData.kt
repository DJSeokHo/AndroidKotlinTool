package com.swein.androidkotlintool.main.examples.multipleselectionrecyclerview.adapter.item

data class MultipleSelectionRecyclerViewItemData(
    val id: String,
    val content: String
) {
    var isSelected: Boolean = false
}
