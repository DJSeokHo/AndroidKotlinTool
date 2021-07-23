package com.swein.androidkotlintool.main.examples.multiplebackstackforfragmentsinactivity.manager.childfragment

open class ChildBackStack {

    private var list = mutableListOf<ChildBackStackAbleFragment>()

    fun isEmpty(): Boolean = list.isEmpty()

    fun count(): Int = list.size

    override fun toString(): String {

        if (list.isEmpty()) {
            return ""
        }

        var string = "--- child top\n"
        for (fragment in list.reversed()) {
            string +=
                "${fragment.getChildFragmentInfo().childActionTag} " +
                        "${fragment.getChildFragmentInfo().childFragmentTag}\n"
        }
        string += " --- child bottom\n"
        return string
    }

    fun clear() {
        list.clear()
    }

    fun push(childBackStackAbleFragment: ChildBackStackAbleFragment) {
        list.add(childBackStackAbleFragment)
    }

    fun pop(): ChildBackStackAbleFragment? {

        if (list.isEmpty()) {
            return null
        }

        return list.removeAt(list.size - 1)
    }

    fun peek(): ChildBackStackAbleFragment? {

        if (list.isEmpty()) {
            return null
        }

        return list[list.size - 1]
    }

}