package emitter

open class PrintEmitter {
    private var count = 0

    open fun print(value: Any) {
        if (value is Double) {
            if (value % 1 == 0.0) {
                print(value.toInt())
                count++
                return
            }
        }
        println(value)
        count++
    }

    open fun getCount(): Int = count
}
