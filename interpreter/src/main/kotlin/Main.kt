import interpreter.ExecutionContext

fun main() {
    val context = ExecutionContext()
    context.add("PI", 3.1416)
    println("Valor de PI: ${context.get("PI")}")
}
