import com.swein.androidkotlintool.framework.module.firebase.demo.FirebaseDemoActivity
import com.swein.androidkotlintool.framework.module.firebase.demo.model.MemberSelfModel
import com.swein.androidkotlintool.framework.module.firebase.demo.model.ProductModel
import com.swein.androidkotlintool.framework.module.firebase.demo.model.ShopModel
import com.swein.androidkotlintool.framework.module.firebase.demo.service.MemberModelService
import com.swein.androidkotlintool.framework.module.firebase.demo.service.ProductModelService
import com.swein.androidkotlintool.framework.module.firebase.demo.service.ShopModelService
import com.swein.androidkotlintool.framework.util.log.ILog
import com.swein.androidkotlintool.framework.util.thread.ThreadUtil


private const val TAG = "TempData"

fun loginByKakaoExample() {

    MemberModelService.loginSNS("KAKAO", "kakao123", { list, documentIdList, documentSnapshot ->

        ILog.debug(TAG, "${list.size}")
        ILog.debug(TAG, "${list[0]}")
        ILog.debug(TAG, "${documentSnapshot?.id}")
        ILog.debug(TAG, documentIdList[0])

        MemberSelfModel.isLogin = true
        MemberSelfModel.parsingMemberModel(documentIdList[0], list[0])

        ILog.debug(TAG, "login by kakao success")

    }, {
        ILog.debug(TAG, it?.message)
    }, {
        ILog.debug(TAG, "need register")
    })
}

fun loginBySecretTokenKeyExample() {

    MemberModelService.loginSecretToken("aadsafdwqd", "test nick name", { list, documentIdList, documentSnapshot ->

        ILog.debug(TAG, "${list.size}")
        ILog.debug(TAG, "${list[0]}")
        ILog.debug(TAG, "${documentSnapshot?.id}")
        ILog.debug(TAG, documentIdList[0])

        MemberSelfModel.isLogin = true
        MemberSelfModel.parsingMemberModel(documentIdList[0], list[0])

        ILog.debug(TAG, "login by SecretTokenKey success")

    }, {
        ILog.debug(TAG, it?.message)
    }, {
        ILog.debug(TAG, "SecretTokenKey not exists")
    })
}

fun autoLoginWhenAppStartExample() {

    MemberModelService.requestMemberInfo(MemberSelfModel.uuId, { list, documentIdList, documentSnapshot ->

        ILog.debug(TAG, "${list.size}")
        ILog.debug(TAG, "${list[0]}")
        ILog.debug(TAG, "${documentSnapshot?.id}")
        ILog.debug(TAG, documentIdList[0])

        MemberSelfModel.isLogin = true
        MemberSelfModel.parsingMemberModel(documentIdList[0], list[0])

        ILog.debug(TAG, "auto login success")

    }, {
        ILog.debug(TAG, it?.message)
    }, {
        ILog.debug(TAG, "need login again")
    })
}

fun registerExample() {

    MemberModelService.checkIsMemberExist("KAKAO", "kakao123", { list, documentIdList, documentSnapshot ->

        ILog.debug(TAG, "${list.size}")
        ILog.debug(TAG, "${list[0]}")
        ILog.debug(TAG, "${documentSnapshot?.id}")
        ILog.debug(TAG, documentIdList[0])

        ILog.debug(TAG, "already exists, just login")

    }, {
        ILog.debug(TAG, "${it?.message}")
    }, {
        ILog.debug(TAG, "empty")

        MemberModelService.registerMember("KAKAO", "kakao123", "test nick name", "test@test.com", "aaaaaaaa",
            "/storage/emulated/0/DCIM/Screenshots/Screenshot_20210413-191931_RecyclerViewExample.jpg", { documentReference, map ->

                ILog.debug(TAG, documentReference?.id)
                ILog.debug(TAG, map.toString())

                MemberSelfModel.isLogin = true
                MemberSelfModel.parsingMemberModel(documentReference!!.id, map)

                ILog.debug(TAG, "register and login success")

            }, { e ->
                ILog.debug(TAG, e?.message)
                MemberSelfModel.clear()
            })

    })
}

fun registerBusinessExample() {
    ILog.debug(TAG, "registerBusinessExample")

    if (!MemberSelfModel.isLogin) {
        ILog.debug(TAG, "need login")
        return
    }

    ShopModelService.checkIsShopExist(MemberSelfModel.uuId, { list, documentIdList, documentSnapshot ->

        ILog.debug(TAG, "${list.size}")
        ILog.debug(TAG, "${list[0]}")
        ILog.debug(TAG, "${documentSnapshot?.id}")
        ILog.debug(TAG, documentIdList[0])

        MemberSelfModel.parsingShopModel(documentIdList[0], list[0])
        ILog.debug(TAG, "already exists, can not register shop again")

    }, {
        ILog.debug(TAG, it?.message)
    }, {
        ILog.debug(TAG, "empty")

        ShopModelService.registerBusiness(
            MemberSelfModel.uuId,
            "123-321-123",
            "shop owner name",
            "good good shop",
            "상호명요~~",
            "010-1234-4321",
            ShopModel.AREA_1,
            "this is detail address",
            37.0,
            127.0,
            "info info info",
            "10:00 - 23:00",
            "/storage/emulated/0/DCIM/Screenshots/Screenshot_20210413-191931_RecyclerViewExample.jpg",
            "/storage/emulated/0/DCIM/Screenshots/Screenshot_20210413-191931_RecyclerViewExample.jpg",
            { documentReference, map ->

                MemberSelfModel.parsingShopModel(documentReference!!.id, map)
                ILog.debug(TAG, map)
                ILog.debug(TAG, "register shop success and wait to review")

            }, { e ->
                ILog.debug(TAG, e?.message)
            })
    })
}

fun requestShopInfoExample() {

    if (!MemberSelfModel.isLogin) {
        ILog.debug(TAG, "need login")
        return
    }

    ShopModelService.requestShopInfo(MemberSelfModel.uuId, { list, documentIdList, documentSnapshot ->

        ILog.debug(TAG, "${list.size}")
        ILog.debug(TAG, "${list[0]}")
        ILog.debug(TAG, "${documentSnapshot?.id}")
        ILog.debug(TAG, documentIdList[0])

        MemberSelfModel.parsingShopModel(documentIdList[0], list[0])

    }, { e ->
        ILog.debug(TAG, e?.message)
    }, {
        ILog.debug(TAG, "empty")
    })
}

fun updateMemberProfileExample() {

    if (!MemberSelfModel.isLogin) {
        ILog.debug(TAG, "need login")
        return
    }

    MemberSelfModel.memberModel!!.nickname = "test 2 nick name"
    MemberSelfModel.memberModel!!.email = "test2@test.com"
    MemberSelfModel.memberModel!!.pushToken = "ccccccc"

    MemberModelService.updateMemberProfile(MemberSelfModel, { map ->
        MemberSelfModel.parsingMemberModel(map = map)
        ILog.debug(TAG, MemberSelfModel.memberModel!!.to().toString())
    }, { e ->
        ILog.debug(TAG, e?.message)
    })
}

fun modifyBusinessExample() {

    ILog.debug(TAG, "modifyBusinessExample")

    if (!MemberSelfModel.isLogin) {
        ILog.debug(TAG, "need login")
        return
    }

    ShopModelService.checkIsShopExist(MemberSelfModel.uuId, { list, documentIdList, documentSnapshot ->

        ILog.debug(TAG, "${list.size}")
        ILog.debug(TAG, "${list[0]}")
        ILog.debug(TAG, "${documentSnapshot?.id}")
        ILog.debug(TAG, documentIdList[0])

        ILog.debug(TAG, "already exists, you can modify")

        ShopModelService.modifyBusiness(
            MemberSelfModel,
            "/storage/emulated/0/DCIM/Screenshots/Screenshot_20210413-191931_RecyclerViewExample.jpg",
            "/storage/emulated/0/DCIM/Screenshots/Screenshot_20210413-191931_RecyclerViewExample.jpg",
            { map ->

                MemberSelfModel.parsingShopModel(map = map)
                ILog.debug(TAG, map)
                ILog.debug(TAG, "modify shop success and wait to review")

            }, { e ->
                ILog.debug(TAG, e?.message)
            })

    }, {
        ILog.debug(TAG, it?.message)
    }, {
        ILog.debug(TAG, "empty")
    })
}

fun uploadProductExample() {

    ILog.debug(TAG, "uploadProductExample")

    if (!MemberSelfModel.isLogin) {
        ILog.debug(TAG, "need login")
        return
    }

    if (MemberSelfModel.shopModel == null) {
        ILog.debug(TAG, "need register business")
        return
    }

    if (MemberSelfModel.shopModel!!.readyForSale != 1L) {
        ILog.debug(TAG, "need wait review")
        return
    }

    MemberSelfModel.shopModel?.let {
        ProductModelService.uploadProduct(it.area, "good product 333",
            "product info ~ 333", 1000.0, "18:00 - 24:00", 1,
            "/storage/emulated/0/DCIM/Screenshots/Screenshot_20210413-191931_RecyclerViewExample.jpg", it, { documentReference, map ->

                ILog.debug(TAG, map.toString())
                ILog.debug(TAG, documentReference)
                ILog.debug(TAG, "upload product success")

            }, { e ->
                ILog.debug(TAG, e?.message)
            })
    }

}

fun getProductListExample() {

    ILog.debug(TAG, "getProductListExample")

    if (!MemberSelfModel.isLogin) {
        ILog.debug(TAG, "need login")
        return
    }

    ProductModelService.requestProductList(null, limit = 10, ShopModel.AREA_1, { list, documentIdList, documentSnapshot ->

        ILog.debug(TAG, "${list.size}")
        ILog.debug(TAG, "${documentSnapshot?.id}")

        var productModel: ProductModel

        for (index in 0 until list.size) {

            productModel = ProductModel()
            productModel.documentId = documentIdList[index]
            productModel.parsing(list[index])

            ILog.debug(TAG, productModel.to().toString() + " " + productModel.documentId)
        }

    }, {
        ILog.debug(TAG, it?.message)
    }, {
        ILog.debug(TAG, "empty")
    })
}

fun getProductDetailExample() {

    ILog.debug(TAG, "getProductListExample")

    if (!MemberSelfModel.isLogin) {
        ILog.debug(TAG, "need login")
        return
    }

    ProductModelService.requestProductDetail("7fae17c2441b4270a103498305811ffa", { list, documentIdList, documentSnapshot ->

        ILog.debug(TAG, "${list.size}")
        ILog.debug(TAG, "${documentSnapshot?.id}")

        var productModel: ProductModel

        for (index in 0 until list.size) {

            productModel = ProductModel()
            productModel.documentId = documentIdList[index]
            productModel.parsing(list[index])

            ILog.debug(TAG, productModel.to().toString() + " " + productModel.documentId)

//            updateProductExample(productModel)
            requestPickupExample(productModel)
//            saleFinishedExample(productModel)
        }

    }, {
        ILog.debug(TAG, it?.message)
    }, {
        ILog.debug(TAG, "empty")
    })

}

fun updateProductExample(productModel: ProductModel) {

    ILog.debug(TAG, "updateProductExample")

    if (!MemberSelfModel.isLogin) {
        ILog.debug(TAG, "need login")
        return
    }

    ILog.debug(TAG, productModel.documentId)

    productModel.name = "new name hahaha"
    productModel.info = "new info ~~~"
    ProductModelService.updateProductModel(productModel, {

        productModel.parsing(it)
        ILog.debug(TAG, productModel.to().toString() + " " + productModel.documentId)

    }, {
        ILog.debug(TAG, it?.message)
    })

}

fun deleteProductExample(documentId: String) {

    ILog.debug(TAG, "deleteProduct")

    if (!MemberSelfModel.isLogin) {
        ILog.debug(TAG, "need login")
        return
    }

    ILog.debug(TAG, documentId)

    ProductModelService.deleteProduct(documentId, {

        ILog.debug(TAG, "delete success $documentId")

    }, {
        ILog.debug(TAG, it?.message)
    })
}


fun requestPickupExample(productModel: ProductModel) {

    ILog.debug(TAG, "requestPickupExample")

    if (!MemberSelfModel.isLogin) {
        ILog.debug(TAG, "need login")
        return
    }

    ILog.debug(TAG, productModel.documentId)

    ProductModelService.requestPickup(productModel, {

        productModel.parsing(it)
        ILog.debug(TAG, productModel.to().toString() + " " + productModel.documentId)

    }, {
        ILog.debug(TAG, it?.message)
    })

}

fun saleFinishedExample(productModel: ProductModel) {

    ILog.debug(TAG, "saleFinishedExample")

    if (!MemberSelfModel.isLogin) {
        ILog.debug(TAG, "need login")
        return
    }

    if (MemberSelfModel.uuId != productModel.shopModel?.ownerUuId) {
        ILog.debug(TAG, "only shop owner can do this")
        return
    }

    ILog.debug(TAG, productModel.documentId)

    ProductModelService.saleFinished(productModel, {

        productModel.parsing(it)
        ILog.debug(TAG, productModel.to().toString() + " " + productModel.documentId)

    }, {
        ILog.debug(TAG, it?.message)
    })

}