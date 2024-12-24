package com.emnuvem.data.model.response.faqs

data class FaqsResponse(
    val faqs: List<Faq>,
    val countFaqs: Int,
)
