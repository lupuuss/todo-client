package com.github.lupuuss.todo.client.js.react.mytasks

import com.github.lupuuss.todo.client.js.react.Colors
import com.github.lupuuss.todo.client.js.react.NamedStylesheet
import kotlinx.css.*

object TaskItemStyles : NamedStylesheet() {


    val container by css {
        fontSize = 2.rem
        backgroundColor = Colors.primary
        borderRadius = 2.rem
        width = LinearDimension.fillAvailable
        display = Display.flex
        flexDirection = FlexDirection.row
        alignItems = Align.center
        justifyContent = JustifyContent.spaceEvenly
        padding(2.rem)

        child(":not(:first-child)") {
            marginLeft = 2.rem
        }
    }


    val actions by css {
        display = Display.flex
        flexDirection = FlexDirection.column
        child(":not(:first-child)") {
            marginTop = 1.rem
        }
    }

    val content by css {
        display = Display.flex
        flexDirection = FlexDirection.column
        flexShrink = 1.0
        flexGrow = 1.0
        flexWrap = FlexWrap.wrap
        minWidth = 0.px

        child(":not(:first-child)") {
            marginTop = 1.rem
        }
    }

    private val editableText by css {
        fontSize = 2.rem
        padding(1.rem)
        width = LinearDimension.fillAvailable
    }

    val nameText by css {
        +editableText
        whiteSpace = WhiteSpace.preWrap
        fontWeight = FontWeight.bold
    }

    val descriptionText by css {
        +editableText
        wordWrap = WordWrap.breakWord
        resize = Resize.none
        overflow = Overflow.hidden
    }

    val date by css {
        fontSize = 1.5.rem
        display = Display.inlineBlock
        whiteSpace = WhiteSpace.nowrap
    }
}