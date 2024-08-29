package org.example.rules

interface ConfigurableRule {
    fun setConfig(config: Map<String, Boolean>)
}
