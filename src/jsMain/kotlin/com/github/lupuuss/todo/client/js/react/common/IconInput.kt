package com.github.lupuuss.todo.client.js.react.common

import kotlinx.css.*
import kotlinx.css.properties.LineHeight
import kotlinx.css.properties.Transforms
import kotlinx.css.properties.translateY
import kotlinx.html.INPUT
import kotlinx.html.classes
import react.RBuilder
import react.RComponent
import react.RProps
import react.ReactElement
import react.dom.div
import react.dom.i
import styled.css
import styled.styledDiv
import styled.styledInput

external interface IconInputProps : RProps {
    var iconName: String
    var iconStyle: String
    var iconSize: LinearDimension
    var iconSpacing: LinearDimension
    var iconColor: Color
    var attrs: (INPUT) -> Unit
}

class IconInput : RComponent<IconInputProps, dynamic>() {

    override fun RBuilder.render() {
        styledDiv {

            css { +IconInputStyles.container }

            styledDiv {

                css { +IconInputStyles.icon(props) }

                attrs { classes = setOf(props.iconName, props.iconStyle)}
            }

            styledInput {

                css { +IconInputStyles.input }

                attrs(props.attrs)
            }
        }
    }

}

fun RBuilder.iconInput(handler: IconInputProps.() -> Unit): ReactElement = child(IconInput::class) { this.attrs(handler) }