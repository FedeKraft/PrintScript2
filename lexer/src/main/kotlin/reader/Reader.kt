package reader

import java.io.BufferedReader
import java.io.InputStream

class Reader(
    private val filePath: InputStream,
) {
    private val reader: BufferedReader = filePath.bufferedReader()
    private var nextChar: Int = reader.read()

    fun read(): Char? =
        if (nextChar != -1) {
            val char = nextChar.toChar()
            nextChar = reader.read()
            char
        } else {
            reader.close()
            null
        }

    fun hasNextChar(): Boolean = nextChar != -1
}
