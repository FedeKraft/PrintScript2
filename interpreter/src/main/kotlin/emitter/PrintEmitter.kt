package emitter

open class PrintEmitter {
    private var count = 0
    private val output = mutableListOf<String>()

    private val printedValues = mutableListOf<Any?>()

    fun print(value: Any?) {
        count++
        printedValues.add(value)
        println(value)
    }

    fun getPrintedValues(): List<Any?> = printedValues

    open fun getCount(): Int = count
}
