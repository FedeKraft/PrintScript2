package factory

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import config.FormatterConfig
import formatter.Formatter
import parser.ASTProvider
import rules.Indentation
import rules.NewlineBeforePrintlnRule
import rules.NoSpaceAroundEqualsRule
import rules.SpaceAfterColonRule
import rules.SpaceBeforeColonRule
import java.io.InputStream

class FormatterFactory {

    fun createFormatter1_1(astProvider: ASTProvider, configInputStream: InputStream): Formatter {
        val mapper = jacksonObjectMapper()

        // Deserializar el archivo JSON a un objeto FormatterConfig
        val config = mapper.readValue(configInputStream, FormatterConfig::class.java)
        println("Archivo de configuración recibido y deserializado:\n$config")

        // Crear las reglas basadas en la configuración
        val rules = listOf(
            Indentation(config.indentation),
            NoSpaceAroundEqualsRule(config.spaceAroundEquals),
            SpaceBeforeColonRule(config.spaceBeforeColon),
            SpaceAfterColonRule(config.spaceAfterColon),
            NewlineBeforePrintlnRule(config.newlineBeforePrintln),
        )

        return Formatter(rules, astProvider)
    }
}
