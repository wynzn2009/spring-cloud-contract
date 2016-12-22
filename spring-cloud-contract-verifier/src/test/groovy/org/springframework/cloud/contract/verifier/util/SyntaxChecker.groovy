package org.springframework.cloud.contract.verifier.util

import groovy.transform.CompileStatic
import org.codehaus.groovy.control.CompilerConfiguration
import org.codehaus.groovy.control.customizers.ImportCustomizer
import org.mdkt.compiler.InMemoryJavaCompiler

/**
 * checking the syntax of produced scripts
 */
@CompileStatic
class SyntaxChecker {

	private static final String[] DEFAULT_IMPORTS = [
			"org.springframework.cloud.contract.spec.Contract",
			"com.jayway.restassured.response.ResponseOptions",
			"com.jayway.restassured.module.mockmvc.specification.*",
			"com.jayway.restassured.module.mockmvc.*",
			"org.junit.Test",
			"org.junit.Rule",
			"com.jayway.jsonpath.DocumentContext",
			"com.jayway.jsonpath.JsonPath",
			"javax.inject.Inject",
			"org.springframework.cloud.contract.verifier.messaging.internal.ContractVerifierObjectMapper",
			"org.springframework.cloud.contract.verifier.messaging.internal.ContractVerifierMessage",
			"org.springframework.cloud.contract.verifier.messaging.internal.ContractVerifierMessaging"
	]

	private static final String DEFAULT_IMPORTS_AS_STRING = DEFAULT_IMPORTS.collect {
		"import ${it};"
	}.join("\n")

	private static final String STATIC_IMPORTS = [
			"com.jayway.restassured.module.mockmvc.RestAssuredMockMvc.given",
			"com.jayway.restassured.module.mockmvc.RestAssuredMockMvc.when",
			"com.jayway.restassured.RestAssured.*",
			"org.springframework.cloud.contract.verifier.messaging.util.ContractVerifierMessagingUtil.headers",
			"com.toomuchcoding.jsonassert.JsonAssertion.assertThatJson",
			"org.assertj.core.api.Assertions.assertThat"
	].collect { "import static ${it};"}.join("\n")


	public static void tryToCompile(String builderName, String test) {
		if (builderName.toLowerCase().contains("spock")) {
			tryToCompileGroovy(test)
		} else {
			tryToCompileJava(test)
		}
	}

	public static void tryToCompileGroovy(String test) {
		def imports = new ImportCustomizer()
		CompilerConfiguration configuration = new CompilerConfiguration()
		configuration.addCompilationCustomizers(imports)
		new GroovyShell(SyntaxChecker.classLoader, configuration).parse(
				"${DEFAULT_IMPORTS_AS_STRING}\n${STATIC_IMPORTS}\n${test}")
	}

	public static Class tryToCompileJava(String test) {
		Random random = new Random()
		int first = Math.abs(random.nextInt())
		int hashCode = Math.abs(test.hashCode())
		StringBuffer sourceCode = new StringBuffer()
		String className = "TestClass_${first}_${hashCode}"
		String fqnClassName = "com.example.${className}"
		sourceCode.append("package com.example;\n")
		sourceCode.append("${DEFAULT_IMPORTS_AS_STRING}\n")
		sourceCode.append("${STATIC_IMPORTS}\n")
		sourceCode.append("public class ${className} {\n")
		sourceCode.append("   public void method() {")
		sourceCode.append("   ${test}")
		sourceCode.append("   }")
		sourceCode.append("}")
		return InMemoryJavaCompiler.compile(fqnClassName, sourceCode.toString())
	}

}
