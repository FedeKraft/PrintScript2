package emitter

class PrintEmitter {
    private var count = 0
    fun print(value: String) {
        println(value)
        count++
    }
    fun getCount(): Int {
        return count
    }
}
