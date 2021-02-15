package com.github.lupuuss.todo.client.js.react.home

import react.RBuilder
import react.RComponent
import react.ReactElement
import react.dom.h1

class Home : RComponent<dynamic, dynamic>() {
    override fun RBuilder.render() {
        h1 {
            +"HOME PAGE"
        }
    }
}

fun RBuilder.home(): ReactElement {
    return child(Home::class) {}
}