package com.swein.androidkotlintool.main.examples.multiplebackstackforfragmentsinactivity.manager.rootfragment

open class RootBackStack {

    private var list = mutableListOf<RootBackStackAbleFragment>()

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
        for (rootFragment in list) {
            rootFragment.getFragmentInfo().stack.clear()
        }
        list.clear()
    }

    fun push(rootBackStackAbleFragment: RootBackStackAbleFragment) {
        list.add(rootBackStackAbleFragment)
    }

    fun pop(): RootBackStackAbleFragment? {

        if (list.isEmpty()) {
            return null
        }

        return list.removeAt(list.size - 1)
    }

    fun peek(): RootBackStackAbleFragment? {

        if (list.isEmpty()) {
            return null
        }

        return list[list.size - 1]
    }

    fun findByFragmentTagAndActionTag(fragmentTag: String, actionTag: String): RootBackStackAbleFragment? {

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

    fun toTop(rootBackStackAbleFragment: RootBackStackAbleFragment) {

        if (list.isEmpty()) {
            return
        }

        val tempAction = list.removeAt(list.indexOf(rootBackStackAbleFragment))
        list.add(tempAction)
    }
}