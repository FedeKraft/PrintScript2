package errorCollector

open class ErrorCollector {
    private val errors = mutableListOf<String>()

    fun reportError(error: String) {
        errors.add(error)
    }

    fun getErrors(): List<String> {
        return errors
    }

    fun hasErrors(): Boolean {
        return errors.isNotEmpty()
    }

    fun clearErrors() {
        errors.clear()
    }
}
