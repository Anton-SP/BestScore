package com.bestscore.featurecreatetemplate

import com.bestscore.core.templates.Parameter
import com.bestscore.core.templates.Template

class Validator {
    fun validate(template: Template, parameters: List<Parameter>): ValidationResult {
        if (template.name.isBlank()) {
            return ValidationResult(
                success = false,
                message = "Заполните имя игры"
            )
        }

        parameters.forEach { parameter ->
            if (parameter.parameterName.isBlank()) {
                return ValidationResult(
                    success = false,
                    message = "Заполните имена параметров"
                )
            }
        }

        return ValidationResult(
            success = true
        )
    }
}

data class ValidationResult(
    val success: Boolean,
    val message: String = ""
)