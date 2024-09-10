package reader

import java.io.File
import java.io.BufferedReader

class Reader(private val filePath: String) {

    private val reader: BufferedReader = File(filePath).bufferedReader()
    private var nextChar: Int = reader.read()

    fun read(): Char? {
        return if (nextChar != -1) {
            val char = nextChar.toChar()
            nextChar = reader.read()
            char
        }
        else {
            reader.close()
            null
        }
    }

    fun hasNextChar(): Boolean {
        return nextChar != -1
    }
}
