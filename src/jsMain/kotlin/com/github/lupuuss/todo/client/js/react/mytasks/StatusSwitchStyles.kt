package com.github.lupuuss.todo.client.js.react.mytasks

import com.github.lupuuss.todo.api.core.task.Task
import com.github.lupuuss.todo.client.js.react.NamedStylesheet
import kotlinx.css.*
import kotlinx.css.properties.LineHeight

object StatusSwitchStyles : NamedStylesheet() {

    fun button(props: StatusSwitchProps): RuleSet = {

        val iconColorBackground = when(props.status) {
            Task.Status.NOT_STARTED -> Color("#909090")
            Task.Status.IN_PROGRESS -> Color("#EADF00")
            Task.Status.DONE -> Color("#64E74D")
        }

        backgroundColor = iconColorBackground
        borderRadius = 50.pct

        active { backgroundColor = iconColorBackground }
    }

    fun icon(props: StatusSwitchProps): RuleSet = {

        val iconColor = Color("#FFF")

        val iconClasses = when (props.status) {
            Task.Status.NOT_STARTED -> listOf("fas", "fa-check")
            Task.Status.IN_PROGRESS -> listOf("fas", "fa-hourglass-half")
            Task.Status.DONE -> listOf("fas", "fa-check")
        }.toMutableList()

        classes = iconClasses
        backgroundColor = Color.transparent
        color = iconColor

        width = props.size
        height = props.size
        fontSize = props.size
        lineHeight = LineHeight(props.size.toString())
        verticalAlign = VerticalAlign.middle
        textAlign = TextAlign.center
        margin(1.rem)
    }

}