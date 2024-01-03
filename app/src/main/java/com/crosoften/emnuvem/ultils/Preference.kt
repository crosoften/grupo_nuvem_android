package com.crosoften.emnuvem.ultils

import android.content.Context
import android.content.SharedPreferences

class Preference {
    lateinit var preferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    lateinit var context: Context
    private var MODE_PRIVATE : Int = 0

    constructor(context: Context) {
        this.context = context
        preferences = context.getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE)
        editor = preferences.edit()

    }

    companion object {
        val PREFERENCES_NAME = "Preferences"
        val IS_LOGGIN = "isLoggin"
        val KEY_TOKEN = "token"
        val KEY_EMAIL = "email"
        val KEY_NAME = "name"
        val KEY_IMAGE = "image"
        val KEY_CPF = "cpf"
        val KEY_PHONE = "phone"
        val KEY_BIRTH = "birth"
        val KEY_ID_RESTAURANT = "restaurantId"
        val KEY_ID_ADDRESS = "addressId"
        val KEY_ID_ORDER = "orderId"
        val KEY_TYPE_USER = "typeUser"
        val KEY_ID_USER = "idUser"
        val KEY_IMAGE_REG = "image_key_reg"
        val KEY_IMAGE_REG_PET = "image_key_reg_pet"
        val URL_IMAGE_REG = "image_url_reg"
        val URL_IMAGE_REG_PET = "image_url_reg_pet"
    }

    fun checkLogin() : Boolean {
        return preferences.getBoolean(IS_LOGGIN, false)
    }
    fun checkUser() : Int {
        return preferences.getInt(KEY_TYPE_USER, 0)
    }

    fun getToken() : String {
        return preferences.getString(KEY_TOKEN, "").toString()
    }

    fun getName() : String {
        return preferences.getString(KEY_NAME, "").toString()
    }

    fun getImage() : String {
        return preferences.getString(URL_IMAGE_REG, "").toString()
    }

    fun getId() : Int {
        return preferences.getInt(KEY_ID_USER, 0)
    }

    fun getIdAddress() : Int {
        return preferences.getInt(KEY_ID_ADDRESS, 0)
    }

    fun getIdOrder() : Int {
        return preferences.getInt(KEY_ID_ORDER, 0)
    }

    fun saveIdRestaurant(id: Int){
        editor.putInt(KEY_ID_RESTAURANT, id)
        editor.commit()
        editor.apply()
    }

    fun saveIdUser(id: Int){
        editor.putInt(KEY_ID_USER, id)
        editor.commit()
        editor.apply()
    }
    fun saveToken(token: String){
        editor.putString(KEY_TOKEN, token)
        editor.commit()
        editor.apply()
    }
    fun saveIdAddress(id: Int){
        editor.putInt(KEY_ID_ADDRESS, id)
        editor.commit()
        editor.apply()
    }

    fun saveIdOrder(id: Int){
        editor.putInt(KEY_ID_ORDER, id)
        editor.commit()
        editor.apply()
    }

    fun has_image() : Boolean {
        if(!getImage().isEmpty()){
            return true
        }

        return false
    }

    fun logout() {
        editor.clear()
        editor.commit()
    }
}