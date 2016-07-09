# Spring Cloud Contract

[![Join the chat at https://gitter.im/spring-cloud/spring-cloud-contract](https://badges.gitter.im/spring-cloud/spring-cloud-contract.svg)](https://gitter.im/spring-cloud/spring-cloud-contract?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

What you always need it confidence in pushing new features into a new application or service in a distributed system. This project provides support for Consumer Driven Contracts and service schemas in Spring applications, covering a range of options for writing tests, publishing them as assets, asserting that a contract is kept by producers and consumers, for HTTP and message-based interactions.

Feature ideas:

- [x] Move DSL and stub runner code over from [Accurest](https://github.com/Codearte/accurest)
- [ ] Spring Restdocs generators
- [ ] Wiremock support for Spring Boot apps
- [ ] Wiremock mock servers using `MockRestServiceServer` (from Spring Test)
- [ ] POJO-based contracts (packaging DTOs and stub declarations from the producer)
- [ ] Sample messages packaged and injectable into Spring Cloud Stream consumer tests
- [ ] `DiscoveryClient` support so backends can be stubbed or mocked transparently (declaratively)
- [ ] Actuator endpoints that verify the state of contracts supported by the host service
- [ ] Something to make PACT easier to use in Spring Boot apps?
- [ ] Support [Citrus](http://www.citrusframework.org/) users (somehow?)
- [ ] Schema registries for binary formats like protobuf, thrift, avro