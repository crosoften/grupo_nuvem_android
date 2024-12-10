package com.crosoften.emnuvem.data.model.response.faqs

data class Faq (
    val id: Int,
    val createdAt: String,
    val updatedAt: String,
    val question: String,
    val answer: String,
)
