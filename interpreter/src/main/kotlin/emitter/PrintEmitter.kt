package emitter

open class PrintEmitter {
    private var count = 0

    open fun print(value: Any) {
        println(value)
        count++
    }

    open fun getCount(): Int {
        return count
    }
}
