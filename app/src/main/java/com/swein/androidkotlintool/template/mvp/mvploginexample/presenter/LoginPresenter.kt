package com.swein.androidkotlintool.template.mvp.mvploginexample.presenter

import com.swein.androidkotlintool.framework.util.thread.ThreadUtility
import com.swein.androidkotlintool.template.mvp.mvploginexample.model.UserModel
import com.swein.androidkotlintool.template.mvp.mvploginexample.presenter.controller.LoginController
import com.swein.androidkotlintool.template.mvp.mvploginexample.view.ILoginView

class LoginPresenter(var iLoginView: ILoginView): ILoginPresenter {

    override fun clear() {
        iLoginView.onClear()
    }

    override fun login(id: String, password: String) {

        LoginController.requestLogin(id, password, object : LoginController.LoginControllerDelegate {

            override fun onSuccess(response: String) {

                val iUser = UserModel()
                iUser.nickname = "I got nickname from server"
                iUser.age = 34

                ThreadUtility.startUIThread(0) {
                    iLoginView.onHideProgress()
                    iLoginView.onLoginSuccessUpdateNicknameUI(iUser.nickname)
                    iLoginView.onLoginSuccessUpdateAgeUI(iUser.age)
                }

            }

            override fun onFailed() {
                ThreadUtility.startUIThread(0) {

                    iLoginView.onHideProgress()

                }
            }
        })
    }


    override fun showProgress() {
        iLoginView.onShowProgress()
    }

    override fun hideProgress() {
        iLoginView.onHideProgress()
    }

}