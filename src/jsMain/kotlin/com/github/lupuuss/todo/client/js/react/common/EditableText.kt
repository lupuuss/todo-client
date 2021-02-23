package com.github.lupuuss.todo.client.js.react.common

import kotlinx.browser.document
import kotlinx.css.*
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onInputFunction
import kotlinx.html.js.onLoadFunction
import org.w3c.dom.events.Event
import org.w3c.dom.get
import react.*
import react.dom.value
import styled.css

import styled.styledDiv
import styled.styledInput
import styled.styledTextArea

external interface EditableTextProps : RProps {
    var isEditable: Boolean
    var value: String
    var placeholder: String
    var onChange: (String) -> Unit
    var css: (CSSBuilder) -> Unit
    var textArea: Boolean
}

class EditableText(props: EditableTextProps) : RComponent<EditableTextProps, dynamic>(props) {

    override fun componentDidUpdate(prevProps: EditableTextProps, prevState: dynamic, snapshot: Any) {
        val areas = document.getElementsByTagName("textarea")

        for (i in 0 until areas.length) {
            val textArea = areas[i].asDynamic()
            textArea.style.height = "auto"
            textArea.style.height = textArea.scrollHeight + "px"
        }
    }

    override fun RBuilder.render() {

        if (props.isEditable) {

            if (props.textArea) {

                styledTextArea {
                    css { +props.css }
                    attrs {
                        value = props.value
                        placeholder = props.placeholder
                        onChangeFunction = this@EditableText::onValueChanged
                    }
                }
            } else {
                styledInput {
                    css { +props.css }
                    attrs {
                        value = props.value
                        placeholder = props.placeholder
                        onChangeFunction = this@EditableText::onValueChanged
                    }
                }
            }

        } else {
            styledDiv {
                css {
                    +props.css
                }
                +props.value
            }
        }
    }

    private fun onValueChanged(event: Event) {
        val target = event.target.asDynamic()

        val value = target.value as String

        props.onChange(value)
    }
}

fun RBuilder.editableText(props: EditableTextProps.() -> Unit) = child(EditableText::class) { attrs(props) }