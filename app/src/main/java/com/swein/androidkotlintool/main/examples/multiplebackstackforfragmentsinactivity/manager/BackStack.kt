package com.swein.androidkotlintool.main.examples.multiplebackstackforfragmentsinactivity.manager

class BackStack {

    private var list = mutableListOf<BackStackAbleFragment>()

    fun isEmpty(): Boolean = list.isEmpty()

    fun count(): Int = list.size

    override fun toString(): String {
        var string = "\ntop\n"
        for (fragment in list.reversed()) {
            string +=
                "${fragment.getFragmentInfo().actionTag} " +
                        "${fragment.getFragmentInfo().fragmentTag}\n"

            string += "${fragment.getFragmentInfo().stack}"
        }
        string += "bottom"
        return string
    }

    fun clear() {
        for (fragment in list) {
            fragment.getFragmentInfo().stack.clear()
        }
        list.clear()
    }

    fun push(backStackAbleFragment: BackStackAbleFragment) {
        list.add(backStackAbleFragment)
    }

    fun pop(): BackStackAbleFragment? {

        if (list.isEmpty()) {
            return null
        }

        return list.removeAt(list.size - 1)
    }

    fun peek(): BackStackAbleFragment? {

        if (list.isEmpty()) {
            return null
        }

        return list[list.size - 1]
    }

    fun findByFragmentTagAndActionTag(fragmentTag: String, actionTag: String): BackStackAbleFragment? {

        if (list.isEmpty()) {
            return null
        }

        for (item in list) {
            if (item.getFragmentInfo().fragmentTag == fragmentTag
                && item.getFragmentInfo().actionTag == actionTag
            ) {
                return item
            }
        }

        return null
    }

    fun toTop(backStackAbleFragment: BackStackAbleFragment) {

        if (list.isEmpty()) {
            return
        }

        val tempAction = list.removeAt(list.indexOf(backStackAbleFragment))
        list.add(tempAction)
    }

}