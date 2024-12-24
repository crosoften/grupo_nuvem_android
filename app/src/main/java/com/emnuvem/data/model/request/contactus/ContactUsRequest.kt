package com.emnuvem.data.model.request.contactus

data class ContactUsRequest(
    val name: String,
    val phone: String,
    val email: String,
    val message: String
)
