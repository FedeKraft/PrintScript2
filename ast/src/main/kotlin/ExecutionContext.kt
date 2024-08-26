data class ExecutionContext(val variables: Map<String, Any?> = emptyMap()) {

    fun addVariable(name: String, value: Any?): ExecutionContext {
        val updatedVariables = variables + (name to value)
        return ExecutionContext(updatedVariables)
    }

    fun getVariable(name: String): Any? {
        return variables[name]
    }

    fun hasVariable(name: String): Boolean {
        return variables.containsKey(name)
    }
}
