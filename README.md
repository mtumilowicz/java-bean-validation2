[![Build Status](https://travis-ci.com/mtumilowicz/java-bean-validation2.svg?branch=master)](https://travis-ci.com/mtumilowicz/java-bean-validation2)

# java-bean-validation2
The main goal of this project is to show basic features of Java Bean Validation 2.0

_Reference_: https://beanvalidation.org/2.0/spec/  
_Reference_: https://www.baeldung.com/javax-validation

In this project we will cover only fields validation, note that JBV 2.0 is much more broader API,
for example it supports constructor's parameters validation or method's return type validation.

# preface
Useful, self-expressive annotations:
* @Past,
* @Future
* @Email, 
* @NotEmpty, 
* @NotBlank, 
* @Positive, 
* @PositiveOrZero, 
* @Negative, 
* @NegativeOrZero, 
* @PastOrPresent,
* @FutureOrPresent,
* @Pattern

If we want to validate relation object, for example:
```
class User {
    Address address;
}
```
we have to add `@Valid` over the relation:
```
class User {
    @Valid
    Address address;
}
```

# project description
We have class `User` with multiple fields covered with JBV annotations and in `UserValidationTest`
we test if it works (by filling with illegal values).

In `AppRunner` we present exemplary error messages.

## pom.xml
* validation api:
	```
	<dependency>
		<groupId>javax.validation</groupId>
		<artifactId>validation-api</artifactId>
		<version>2.0.1.Final</version>
	</dependency>
	```
* Hibernate Validator is the reference implementation of the validation API:
    ```
    <dependency>
        <groupId>org.hibernate.validator</groupId>
        <artifactId>hibernate-validator</artifactId>
        <version>${hibernate.validator}</version>
    </dependency>
    <dependency>
        <groupId>org.hibernate</groupId>
        <artifactId>hibernate-validator-annotation-processor</artifactId>
        <version>${hibernate.validator}</version>
    </dependency>    
    ```
    where: `<hibernate.validator>6.0.12.Final</hibernate.validator>`

* JSR 380 provides support for variable interpolation, 
allowing expressions inside the violation messages:
    ```
    <dependency>
        <groupId>javax.el</groupId>
        <artifactId>javax.el-api</artifactId>
        <version>${java.el.version}</version>
    </dependency>
    <dependency>
        <groupId>org.glassfish</groupId>
        <artifactId>javax.el</artifactId>
        <version>${java.el.version}</version>
    </dependency>
    ```
    where: `<java.el.version>3.0.0</java.el.version>`

# manual
Consider `User` entity with fields under validation (only that fields, all others are irrelevant):
```
@NotEmpty(message = "Emails should be not empty")
@ElementCollection
List<@NotNull(message = "Email should be not null") @Email(message = "Only valid email") String> emails;

@PositiveOrZero(message = "Rank should be >= 0")
int rank;
```
we construct invalid user:
```
User user = User.builder()
   .emails(Arrays.asList(null, "not valid email"))
   .rank(-1)
    .build();
```
then we try to save it to the database:
```
try {
    userRepository.save(user);
} catch (Exception ex) {
    System.out.println(ex.getLocalizedMessage());
}
```
output:
```
List of constraint violations:[
    ConstraintViolationImpl{interpolatedMessage='Only valid email', propertyPath=emails[1].<list element>, rootBeanClass=class com.example.javabeanvalidation2.User, messageTemplate='Only valid email'}
    ConstraintViolationImpl{interpolatedMessage='Rank should be >= 0', propertyPath=rank, rootBeanClass=class com.example.javabeanvalidation2.User, messageTemplate='{javax.validation.constraints.PositiveOrZero.message}'}
    ConstraintViolationImpl{interpolatedMessage='Email should be not null', propertyPath=emails[0].<list element>, rootBeanClass=class com.example.javabeanvalidation2.User, messageTemplate='Email should be not null'}
]]
```