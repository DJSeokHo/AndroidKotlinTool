package com.swein.androidkotlintool.framework.module.room.example.model

import com.swein.androidkotlintool.framework.module.room.example.model.entity.IdeaInfoEntity

class IdeaModel {

    var uuId: String = ""
    var name: String = ""
    var title: String = ""
    var info: String = ""
    var date: String = ""

    fun parsing(ideaInfoEntity: IdeaInfoEntity) {
        this.uuId = ideaInfoEntity.uuId
        this.name = ideaInfoEntity.name
        this.title = ideaInfoEntity.title
        this.info = ideaInfoEntity.info
        this.date = ideaInfoEntity.date
    }

    fun toDataClass(): IdeaInfoEntity {
        return IdeaInfoEntity(uuId, name, title, info, date)
    }

}