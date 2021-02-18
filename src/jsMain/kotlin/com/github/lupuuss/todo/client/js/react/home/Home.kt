package com.github.lupuuss.todo.client.js.react.home

import com.github.lupuuss.todo.client.js.react.mytasks.myTasks
import react.RBuilder
import react.RComponent
import react.ReactElement
import react.dom.h1

class Home : RComponent<dynamic, dynamic>() {
    override fun RBuilder.render() {
        myTasks()
    }
}

fun RBuilder.home(): ReactElement {
    return child(Home::class) {}
}