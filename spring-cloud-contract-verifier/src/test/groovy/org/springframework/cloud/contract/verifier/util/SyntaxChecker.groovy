package org.springframework.cloud.contract.verifier.util

import org.codehaus.groovy.control.CompilerConfiguration
import org.codehaus.groovy.control.customizers.ImportCustomizer

/**
 * Trait checking the syntax of produced scripts
 */
trait SyntaxChecker {

	String[] DEFAULT_IMPORTS = [
			"org.springframework.cloud.contract.spec.Contract",
			"com.jayway.restassured.response.ResponseOptions",
			"org.junit.Test",
			"org.junit.Rule",
			"com.jayway.jsonpath.DocumentContext",
			"com.jayway.jsonpath.JsonPath",
			"javax.inject.Inject",
			"org.springframework.cloud.contract.verifier.messaging.internal.ContractVerifierObjectMapper",
			"org.springframework.cloud.contract.verifier.messaging.internal.ContractVerifierMessage",
			"org.springframework.cloud.contract.verifier.messaging.internal.ContractVerifierMessaging"
	]

	String STATIC_IMPORTS = [
			"com.jayway.restassured.module.mockmvc.RestAssuredMockMvc.*",
			"com.jayway.restassured.RestAssured.*",
			"org.springframework.cloud.contract.verifier.messaging.util.ContractVerifierMessagingUtil.headers",
			"com.toomuchcoding.jsonassert.JsonAssertion.assertThatJson",
			"org.assertj.core.api.Assertions.assertThat"
	].collect { "import static ${it}"}.join("\n")

	CompilerConfiguration configuration = new CompilerConfiguration()

	void checkIfTestIsParsable(String test) {
		def imports = new ImportCustomizer()
		imports.addImports(DEFAULT_IMPORTS)
		configuration.addCompilationCustomizers(imports)
		new GroovyShell(getClass().classLoader, configuration).parse("${STATIC_IMPORTS}\n${test}")
	}

}
