package com.dicoding.gotrip.utils

fun countStar(rating: String): Int{
    var star = 0
    when(rating){
        "1" -> star = 1
        "2" -> star = 2
        "3" -> star = 3
        "4" -> star = 4
        "5" -> star = 5
    }
    return star
}