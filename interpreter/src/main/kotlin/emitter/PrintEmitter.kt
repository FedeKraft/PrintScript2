package emitter

open class PrintEmitter {
    private var count = 0
    private val output = mutableListOf<String>()

    open fun print(value: Any): String {
        val outputValue = if (value is Double && value % 1 == 0.0) {
            value.toInt().toString()
        } else {
            value.toString()
        }
        output.add(outputValue)
        println(outputValue)
        count++
        return outputValue
    }

    open fun getCount(): Int = count

    fun getOutput(): String = output.joinToString(separator = "\n")
}
