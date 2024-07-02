package com.crosoften.emnuvem.ultils

object Constants {
    /* Documentation - https://app.swaggerhub.com/apis/farmjob/api/1.0.0 */
    const val CONTENT_TYPE = "application/json;charset=UTF-8"
    const val TYPE_REQUEST = "bearer "
    const val BASE_CHAT = "http://191.252.192.226:5010"

    /* Homologação */ //private static final String BASE_APP = "http://181.215.89.98:5010/api/";
    /* Produção */
    private const val BASE_APP = "http://191.252.192.226:5010/api/"

    //private static final String BASE_APP = "http://48b67d24.ngrok.io/";
    private const val BASE_API = BASE_APP + "v1/"
    const val LOGIN = BASE_API + "login"
    const val LOGIN_FACEBOOK = BASE_API + "login/facebook"
    const val LOGOUT = BASE_API + "logout"
    const val REGISTER_PRODUCER = BASE_API + "register/producer"
    const val REGISTER_CONSULTANT = BASE_API + "register/consultant"
    const val REGISTER_BUSINESS = BASE_API + "register/business"
    const val FORGOT_PASS = BASE_API + "forgotpass"
    const val USERS = BASE_API + "users/"
    const val VERIFY_RESET_TOKEN = BASE_API + "verifyResetToken"
    const val RESET_PASS = BASE_API + "resetpass"
    const val SPECIALTIES = BASE_API + "specialties"
    const val USER_DATA = BASE_API + "user"
    const val POSTS = BASE_API + "feed"
    const val PUBLISH_POST = BASE_API + "posts"
    const val GROUPS = BASE_API + "groups"

    //public static final String PRODUCTS = BASE_API + "advertisements";
    const val MKT_PRODUCTS = BASE_API + "products"
    const val CONVERSATIONS = BASE_API + "conversations"
    const val CONTACT = BASE_API + "contact"
    const val CONSULTANTS = BASE_API + "consultants"
    const val STATES = BASE_API + "states"
    const val REVIEWS = BASE_API + "reviews"
    const val ADVERTISEMENTS = BASE_API + "advertisements"
    const val REFRESH_TOKEN = BASE_API + "refreshtoken"

    //HTTP
    const val HTTP_SUCCESS = 200
    const val BASE_URL = "https://06d3-167-250-186-189.ngrok-free.app/"
    const val BASE_NGROK = "https://b43e-2804-d45-9d0d-9e00-456e-d746-af1-1de6.ngrok-free.app/"

}