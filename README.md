# java-bean-validation2
The main goal of this project is to show basic features of Java Bean Validation 2.0

_Reference_: https://beanvalidation.org/2.0/spec/

In this project we will cover only fields validation, note that JBV 2.0 is much more broader API,
for example it supports constructor parameters validation or method's return type validation.

# preface
Useful, self-expressive annotations:
* @Valid,
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
* @FutureOrPresent

# project description
We have class `User` with multiple fields covered with JBV annotations and in `UserValidationTest`
we test if it works (by filling with illegal values).

In AppRunner we present exemplary error messages.

# example
Consider User entity with fields under validation (only that fields, all others are irrelevant):
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