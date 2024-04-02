package br.com.alura.helloapp.localData.room.converter

import java.security.MessageDigest

class HashConverter {

    companion object{
        fun String.toHash256(): String{
            val bytes = this.toByteArray(charset("UTF-8"))
            val md = MessageDigest.getInstance("SHA-256")
            val digest = md.digest(bytes)
            return digest.joinToString(""){
                "%02x".format(it)
            }
        }
    }

}