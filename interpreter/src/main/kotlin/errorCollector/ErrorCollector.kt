package errorCollector

open class ErrorCollector {
    private val errors = mutableListOf<String>()

    open fun reportError(error: String) {
        errors.add(error)
    }

    open fun getErrors(): List<String> = errors

    open fun hasErrors(): Boolean = errors.isNotEmpty()

    open fun clearErrors() {
        errors.clear()
    }
}
