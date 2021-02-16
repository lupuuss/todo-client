package com.github.lupuuss.todo.client.js.react.common

import com.github.lupuuss.todo.client.js.react.NamedStylesheet
import kotlinx.css.*
import kotlinx.css.properties.LineHeight
import kotlinx.css.properties.Transforms
import kotlinx.css.properties.translateY

object IconInputStyles : NamedStylesheet() {

    val container by css {
        position = Position.relative
    }

    val input by css {
        width = 100.pct
    }

    fun icon(props: IconInputProps): RuleSet = {
        color = props.iconColor
        width = props.iconSize
        height = props.iconSize
        fontSize = props.iconSize
        marginRight = props.iconSpacing
        position = Position.absolute
        right = 100.pct
        top = 50.pct
        textAlign = TextAlign.center
        verticalAlign = VerticalAlign.middle
        lineHeight = LineHeight(props.iconSize.toString())
        transform = Transforms().apply {
            translateY((-50).pct)
        }
    }
}