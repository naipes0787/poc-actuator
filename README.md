# PoC Actuator

## About

This is a simple actuator project integrated with two Spring ways to add security (The legacy and the current supported
versions). The main idea is to exposed only the health and prometheus endpoints, the rest of them require a user
and password.

For practical purposes, the encrypted password to login is in this project (Which is not a good practice at all).

## How to use it?

In `src/test/resources` you can find the Postman collection to try it out, remember to delete the cookies if you
are experiencing some troubles with the authentication.

Besides that, the project it's using [ConditionalOnProperty](https://www.baeldung.com/spring-conditionalonproperty) to
set the legacy or the supported version. There are tests
for both versions. To change between one and the other you have to change a property from the application.properties:

- Legacy: `security.method=legacy`
- Current supported: `security.method=supported`

## Useful links

- [Spring Security without the WebSecurityConfigurerAdapter](https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter)
