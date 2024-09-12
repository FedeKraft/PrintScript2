package errorCollector

open class ErrorCollector {
    private val errors = mutableListOf<String>()

    open fun reportError(error: String) {
        errors.add(error)
    }

    open fun getErrors(): List<String> {
        return errors
    }

    open fun hasErrors(): Boolean {
        return errors.isNotEmpty()
    }

    open fun clearErrors() {
        errors.clear()
    }
}
