# Contributing

## Pushing code

If you're going to work on a functionality that will require several days of work, please do not push code that does not run on its own. To cope with this, either create a new branch or comment your code before committing.
**Only push code that won't make others' project crash.**

## Syntax conventions

- exclusively use English in your code (**including comments**)
- classes and interfaces should always have a leading uppercase
- exclusively use *camelCase* style:
```java
int myVariable = MyClass.someLengthyName(someOtherVariable);
```
- avoid shortcuts, and self-describing type in variables' name:
```java
int iCounter; // not correct

String fullDirName; // not correct
String fullDirectoryName; // correct

String helloString; // not correct
String helloMessage; // correct
```
