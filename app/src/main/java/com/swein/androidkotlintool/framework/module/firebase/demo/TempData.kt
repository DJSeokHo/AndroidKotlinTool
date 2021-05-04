//package com.swein.androidkotlintool.framework.module.firebase.demo
//
//import com.swein.androidkotlintool.framework.module.firebase.demo.service.MemberModelService
//import com.swein.androidkotlintool.framework.module.firebase.demo.model.MemberModel
//import com.swein.androidkotlintool.framework.module.firebase.demo.model.ProductModel
//import com.swein.androidkotlintool.framework.module.firebase.demo.model.ShopModel
//import com.swein.androidkotlintool.framework.module.firebase.demo.service.ProductModelService
//import com.swein.androidkotlintool.framework.module.firebase.demo.service.ShopModelService
//import com.swein.androidkotlintool.framework.util.date.DateUtil
//import com.swein.androidkotlintool.framework.util.log.ILog
//import com.swein.androidkotlintool.framework.util.uuid.UUIDUtil
//
//fun createMemberTest() {
//
//    val memberModel = MemberModel()
//
//    memberModel.uuId = UUIDUtil.getUUIDString()
//    memberModel.loginSecretToken = ""
//    memberModel.memberId = ""
//    memberModel.email = ""
//    memberModel.providerId = "aaabbb111222333"
//    memberModel.provider = "KAKAO"
//    memberModel.phone = ""
//    memberModel.name = ""
//    memberModel.birth = ""
//    memberModel.gender = -1 // 1: male, 0: female
//    memberModel.nickname = "djseokho"
//    memberModel.profileImageUrl = ""
//    memberModel.pushToken = ""
//    memberModel.createDate = DateUtil.getCurrentDateTimeString()
//    memberModel.modifyDate = DateUtil.getCurrentDateTimeString()
//    memberModel.createBy = memberModel.uuId
//    memberModel.modifyBy = memberModel.uuId
//
//    MemberModelService.createMember(memberModel)
//}
//
//fun createShopTest() {
//
//    val shopModel = ShopModel()
//
//    shopModel.uuId = UUIDUtil.getUUIDString()
//    shopModel.ownerUuId = "d466db64b74345e7941ae85c8eeaef4c"
//    shopModel.businessNumber = "111-222-333-444"
//    shopModel.name = "가게이름"
//    shopModel.contact = "0211112222"
//    shopModel.area = "xxx동"
//    shopModel.detailAddress = "경기 성남 xxx동 111호"
//    shopModel.lat = 0.0
//    shopModel.lng = 0.0
//    shopModel.info = "소개소개"
//    shopModel.businessHour = "10:00 - 24:00"
//    shopModel.shopImageUrl = ""
//    shopModel.readyForSale = 0
//    shopModel.isOpen = 0
//    shopModel.createDate = DateUtil.getCurrentDateTimeString()
//    shopModel.modifyDate = DateUtil.getCurrentDateTimeString()
//    shopModel.createBy = "d466db64b74345e7941ae85c8eeaef4c"
//    shopModel.modifyBy = "d466db64b74345e7941ae85c8eeaef4c"
//
//    ShopModelService.createShop(shopModel)
//}
//
//fun createProductTest(index: Int) {
//    ILog.debug("???", "createProductTest $index")
//    val productModel = ProductModel()
//
//    productModel.uuId = UUIDUtil.getUUIDString()
//    productModel.shopUuId = "881492d4cdee4e399cd912884df867c2"
//
//    when {
//        index % 2 == 0 -> {
//            productModel.area = "분당구"
//        }
//        index % 3 == 0 -> {
//            productModel.area = "수정구"
//        }
//        else -> {
//            productModel.area = "중원구"
//        }
//    }
//
//    productModel.name = "제품 index $index"
//    productModel.imageUrl = ""
//
//    when (index) {
//        5 -> {
//            productModel.info = "고고고 제품"
//        }
//        6 -> {
//            productModel.info = "고고고 새로운 것"
//        }
//        7 -> {
//            productModel.info = "고고고것"
//        }
//        else -> {
//            productModel.info = "좋은 제품~~빨리 먹어"
//        }
//    }
//
//    productModel.price = 1000.0
//    productModel.saleTime = "18:00 - 23:00"
//    productModel.inventory = 3
//    productModel.pickerNumber = 2
//    productModel.createDate = DateUtil.getCurrentDateTimeString()
//    productModel.modifyDate = DateUtil.getCurrentDateTimeString()
//    productModel.createBy = "d466db64b74345e7941ae85c8eeaef4c"
//    productModel.modifyBy = "d466db64b74345e7941ae85c8eeaef4c"
//
//    ProductModelService.createProduct(productModel)
//}
//
//fun updateProductTest() {
//
//    val productModel = ProductModel()
//
//    productModel.uuId = "1d52fd912b664d8ebbd52196662c43f2"
//    productModel.shopUuId = "881492d4cdee4e399cd912884df867c2"
//    productModel.name = "제품 이kkk"
//    productModel.imageUrl = ""
//    productModel.info = "좋은 제품~~빨리 먹어kkk"
//    productModel.price = 1001.0
//    productModel.saleTime = "19:00 - 22:00"
//    productModel.inventory = 2
//    productModel.pickerNumber = 5
//    productModel.createDate = "2021-4-30 18:53:6"
//    productModel.modifyDate = DateUtil.getCurrentDateTimeString()
//    productModel.createBy = "d466db64b74345e7941ae85c8eeaef4c"
//    productModel.modifyBy = "d466db64b74345e7941ae85c8eeaef4c"
//
//    ProductModelService.updateProduct("m7kvT5GJbfHIlFEmAIgX", productModel)
//}
//
//fun deleteProductTest() {
//
//    ProductModelService.deleteProduct("mvs7moFvs8QNTj4H04k3")
//
//}
//
//fun selectProductList() {
//    ProductModelService.selectProductList()
//}
//
//fun selectProductList(conditionMap: MutableMap<String, Any>? = null) {
//    ProductModelService.selectProductList(conditionMap = conditionMap)
//}
//
//fun selectProduct(uuId: String) {
//    ProductModelService.selectProduct(uuId)
//}