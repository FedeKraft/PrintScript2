package config

import rules.LinterRule

object LinterConfig {
    private val rules = mutableListOf<LinterRule>()

    fun addRule(rule: LinterRule) {
        rules.add(rule)
    }

    fun activateRule(ruleClass: Class<out LinterRule>) {
        rules.find { it.javaClass == ruleClass }?.let { it.isActive = true }
    }

    fun deactivateRule(ruleClass: Class<out LinterRule>) {
        rules.find { it.javaClass == ruleClass }?.let { it.isActive = false }
    }

    fun getRules(): List<LinterRule> = rules
}
