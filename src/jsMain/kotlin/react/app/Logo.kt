package react.app

import kotlinx.css.*
import react.Colors
import react.RBuilder
import react.RComponent
import react.RProps
import styled.css
import styled.styledDiv

external interface LogoProps : RProps {
    var value: String
}

class Logo(props: LogoProps) : RComponent<LogoProps, dynamic>(props) {

    override fun RBuilder.render() {
        styledDiv {
            css {
                fontSize = 2.rem
                color = Colors.contras
                padding(1.rem)
                float = Float.left
            }
            +props.value
        }
    }
}