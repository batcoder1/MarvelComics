package com.marvelapp.autentia.comics

import android.os.Parcel
import android.os.Parcelable

/**
 * Modelo Marvel
 * Created by erubio on 4/11/17.
 */

class ComicDataWrapper(
        var code: Int,
        var status: String,
        var copyright: String,
        var attributionText: String,
        var attributionHTML: String,
        var data: MarvelDataResponse

)

//class ComicDataContainer(val data: MarvelDataResponse)


class MarvelDataResponse(


        var offset: Int,
        var limit: Int,
        var total: Int,
        var count: Int,
        var results: ArrayList<Comic>

)


data class Comic(

        var id: Int,
        var title: String,
        var description: String?,
        var resourceURI: String,
        var urls: List<Url>,
        var series: SeriesSummary,
        var prices: List<ComicPrice>,
        var thumbnail: Image,
        var images: List<Image>,
        var characters: ResourceList
) : Parcelable {


    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Comic> = object : Parcelable.Creator<Comic> {
            override fun createFromParcel(source: Parcel): Comic = Comic(source)
            override fun newArray(size: Int): Array<Comic?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(source.readInt(), source.readString(), source.readString(), source.readString(), source.createTypedArrayList(Url.CREATOR),
            source.readTypedObject(SeriesSummary.CREATOR), source.createTypedArrayList(ComicPrice.CREATOR), source.readTypedObject(Image.CREATOR),
            source.createTypedArrayList(Image.CREATOR), source.readTypedObject(ResourceList.CREATOR))

    override fun describeContents() = 0
    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeInt(id)
        dest?.writeString(title)
        dest?.writeString(description)
        dest?.writeString(resourceURI)
        dest?.writeList(urls)
        dest?.writeTypedObject(series, 1)
        dest?.writeList(prices)
        dest?.writeList(images)
        dest?.writeTypedObject(thumbnail, 1)
        dest?.writeTypedObject(characters, 1)
    }


}


data class Url(var type: String, var url: String) : Parcelable {

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Url> = object : Parcelable.Creator<Url> {
            override fun createFromParcel(source: Parcel): Url = Url(source)
            override fun newArray(size: Int): Array<Url?> = arrayOfNulls(size)
        }
    }

    override fun describeContents() = 0

    constructor(source: Parcel) : this(source.readString(), source.readString())

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(type)
        dest?.writeString(url)

    }
}


data class SeriesSummary(var resourceURI: String, var name: String) : Parcelable {
    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<SeriesSummary> = object : Parcelable.Creator<SeriesSummary> {
            override fun createFromParcel(source: Parcel): SeriesSummary = SeriesSummary(source)
            override fun newArray(size: Int): Array<SeriesSummary?> = arrayOfNulls(size)
        }
    }

    override fun describeContents() = 0

    constructor(source: Parcel) : this(source.readString(), source.readString())

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(resourceURI)
        dest?.writeString(name)

    }
}


data class ComicPrice(var type: String, var price: Double) : Parcelable {
    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ComicPrice> = object : Parcelable.Creator<ComicPrice> {
            override fun createFromParcel(source: Parcel): ComicPrice = ComicPrice(source)
            override fun newArray(size: Int): Array<ComicPrice?> = arrayOfNulls(size)
        }
    }

    override fun describeContents() = 0

    constructor(source: Parcel) : this(source.readString(), source.readDouble())

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(type)
        dest?.writeDouble(price)

    }
}


data class Image(var path: String = "", var extension: String = "") : Parcelable {
    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Image> = object : Parcelable.Creator<Image> {
            override fun createFromParcel(source: Parcel): Image = Image(source)
            override fun newArray(size: Int): Array<Image?> = arrayOfNulls(size)
        }
    }

    override fun describeContents() = 0

    constructor(source: Parcel) : this(source.readString(), source.readString())

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(path)
        dest?.writeString(extension)

    }

}

data class ResourceList(var available: Int, var collectionURI: String, var items: List<Item>, var returned: Int) : Parcelable {
    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ResourceList> = object : Parcelable.Creator<ResourceList> {
            override fun createFromParcel(source: Parcel): ResourceList = ResourceList(source)
            override fun newArray(size: Int): Array<ResourceList?> = arrayOfNulls(size)
        }
    }

    override fun describeContents() = 0

    constructor(source: Parcel) : this(source.readInt(), source.readString(), source.createTypedArrayList(Item.CREATOR), source.readInt())

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeInt(available)
        dest?.writeString(collectionURI)
        dest?.writeList(items)
        dest?.writeInt(returned)

    }

}

data class Item(var resourceURI: String, var name: String, var type: String)

    : Parcelable {
    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Item> = object : Parcelable.Creator<Item> {
            override fun createFromParcel(source: Parcel): Item = Item(source)
            override fun newArray(size: Int): Array<Item?> = arrayOfNulls(size)
        }
    }

    override fun describeContents() = 0

    constructor(source: Parcel) : this(source.readString(), source.readString(), source.readString())

    override fun writeToParcel(dest: Parcel?, flags: Int) {

        dest?.writeString(resourceURI)
        dest?.writeString(name)
        dest?.writeString(type)


    }
}

class Params(var ts: String, var apikey: String, var hash: String)

//object Model {
//        data class Result(val query: Query)
//        data class Query(val searchinfo: SearchInfo)
//        data class SearchInfo(val totalhits: Int)
//}