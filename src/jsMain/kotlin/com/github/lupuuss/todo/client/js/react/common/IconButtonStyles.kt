package com.github.lupuuss.todo.client.js.react.common

import com.github.lupuuss.todo.client.js.react.NamedStylesheet
import kotlinx.css.*
import kotlinx.css.properties.LineHeight

object IconButtonStyles : NamedStylesheet() {

    val button by css {
        width = LinearDimension.fitContent
        height = LinearDimension.fitContent

        borderRadius = 50.pct
    }

    fun icon(props: IconButtonProps): RuleSet = {

        fontSize = props.iconSize
        width = props.iconSize
        height = props.iconSize

        lineHeight = LineHeight(props.iconSize.toString())
        textAlign = TextAlign.center
        verticalAlign = VerticalAlign.middle
    }

}