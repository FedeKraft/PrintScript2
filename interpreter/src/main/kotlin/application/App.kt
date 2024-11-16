package application

import kotlin.system.exitProcess

class App {
    fun run() {
        println("Application is running")
        // Add your application logic here
    }
}

fun main() {
    val app = App()
    app.run()
    exitProcess(0)
}