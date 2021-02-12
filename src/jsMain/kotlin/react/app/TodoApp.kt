package react.app

import kotlinx.css.*
import react.Colors
import react.RBuilder
import react.RComponent
import styled.css
import styled.styledDiv

class TodoApp : RComponent<dynamic, dynamic>() {
    override fun RBuilder.render() {

        child(TopBar::class) {}

        styledDiv {
            css {
                width = LinearDimension.auto
                height = 100.pct
                backgroundColor = Colors.secondary
            }
        }
    }
}