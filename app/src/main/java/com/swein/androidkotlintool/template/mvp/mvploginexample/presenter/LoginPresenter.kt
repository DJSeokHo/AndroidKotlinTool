package com.swein.androidkotlintool.template.mvp.mvploginexample.presenter

import com.swein.androidkotlintool.framework.util.thread.ThreadUtil
import com.swein.androidkotlintool.template.mvp.mvploginexample.model.IUser
import com.swein.androidkotlintool.template.mvp.mvploginexample.model.UserModel
import com.swein.androidkotlintool.template.mvp.mvploginexample.view.ILoginView

class LoginPresenter(var iLoginView: ILoginView): ILoginPresenter {

    private lateinit var iUser: IUser


    override fun clear() {
        iLoginView.onClear()
    }

    override fun login(id: String, password: String) {
        ThreadUtil.startThread {

            val iUser = UserModel()
            iUser.nickname = "I got nickname from server"
            iUser.age = 34

            Thread.sleep(3000)

            ThreadUtil.startUIThread(0) {

                iLoginView.onHideProgress()
                iLoginView.onLoginSuccessUpdateNicknameUI(iUser.nickname)
                iLoginView.onLoginSuccessUpdateAgeUI(iUser.age)
            }

        }
    }

    override fun showProgress() {
        iLoginView.onShowProgress()
    }

    override fun hideProgress() {
        iLoginView.onHideProgress()
    }

}