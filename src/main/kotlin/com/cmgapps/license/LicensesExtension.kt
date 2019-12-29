/*
 * Copyright (c) 2019. Christian Grach <christian.grach@cmgapps.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cmgapps.license

import com.cmgapps.license.model.Library
import com.cmgapps.license.reporter.LicensesReportsContainer
import groovy.lang.Closure
import org.gradle.api.Action
import org.gradle.util.ClosureBackedAction

typealias CustomReport = (List<Library>) -> String

open class LicensesExtension() {
    var reports: Action<in LicensesReportsContainer> = Action { }
        private set

    fun reports(closure: Closure<LicensesReportsContainer>) {
        reports(ClosureBackedAction<LicensesReportsContainer>(closure))
    }

    fun reports(configureAction: Action<in LicensesReportsContainer>) {
        reports = configureAction
    }

    var customReport: CustomReport? = null
        private set

    var additionalProjects = emptySet<String>()
        private set

    fun additionalProjects(vararg modules: String) {
        additionalProjects = setOf(*modules)
    }

    fun additionalProjects(modules: Collection<String>) {
        additionalProjects = when (modules) {
            is Set -> modules
            else -> modules.toSet()
        }
    }

    fun customReport(report: CustomReport) {
        this.customReport = report
    }
}

enum class OutputType {
    HTML,
    XML,
    JSON,
    TEXT,
    MD,
    CSV,
    CUSTOM
}
