package react

import styled.StyleSheet

abstract class NamedStylesheet(isStatic: Boolean = true) : StyleSheet("", isStatic = isStatic) {

    init {
        name = this::class.simpleName!!
    }
}