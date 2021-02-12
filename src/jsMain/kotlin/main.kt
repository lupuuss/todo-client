import com.github.lupuuss.todo.client.core.TodoKodein
import react.dom.render
import kotlinx.browser.document
import kotlinx.browser.window
import react.app.TodoApp

fun main() {
    window.onload = {
        render(document.getElementById("root")) {
            child(TodoApp::class) {}
        }
    }
}
