package com.crosoften.emnuvem.ultils

open class Event<out T>(private val content: T) {
    private var hasBeenHandled = false

    /*

    Retorna o conteúdo e marca o evento como já manipulado.
    O conteúdo só pode ser acessado uma vez.*/
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null} else {
            hasBeenHandled = true
            content}}

    /*

    Retorna o conteúdo, mesmo que já tenha sido manipulado.*/
    fun peekContent(): T = content
}