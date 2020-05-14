# CliArguments library
Constructor part for handling command line arguments. 

## Usage
### Command line syntax
You can register two types of arguments: flags and properties. __Flags__ are single 
letter arguments which represent boolean value. If given flag exists in an argument line, then
it has value of ```true```. If it does not exist, then it has value of ```false```. Flags can be
merged in one line argument. Here is the call to ```ls``` utility with two flags - 
```-a``` and ```-l``` - merged together:
```
ls -al
```
__Properties__ are name-value pairs, that user can specify in an argument line:
```
ls --tabsize=4
```
Command line can contain more arguments, than you registered. Parser would leave these arguments
unparsed. We will refer to these arguments as _plain arguments_. You can force parser to treat
the rest of command line as plain arguments if place ```--``` argument. All arguments to the right
from this argument wold be _plain_.

### API 
To use the API you should request ```ArgumentsFactory.class``` interface in your 
```ConstructorPart``` implementation:
```
  @Override
  public Set<Class<?>> getInterfacesNecessary() {
    return Set.of(ArgumentsFactory.class);
  }
```
```ArgumentsFactory``` allows you to create an instance of the parser:
```
final ArgumentsParser parser = argumentsFactory.createParser();
```
Parser holds the configuration of arguments given instance of parser could parse. You can create
as many instances of parser as you wish and configure them separately.

You can create a flag with expression:
```
Argument all = parser.addFlag("a", "all");
```
You can create a property with expression:
```
Argument tabSize = parser.addProperty("T", "tabsize");
```
The ```Argument``` object serves as key for retrieval of value:
```
ParseResult parseResult = parser.parse(strings);
final StringValue allValue = parseResult.getArgumentsParsed().get(all);
if (allValue.getInputValueType() == InputValueType.SPECIFIED) {
  // handle --all 
}
```

## Example
You can find an example of application built around the module in ```cli-arguemnts-helloworld```
artifact.

## Constructor Module
CliArguments is the module of [Constructor Framework](https://github.com/zkejidsconstructor).
You can simply place artifacts of this module on the path of application to use its API.

## Tests
Module API has set of tests. You can use ```cli-arguments-apitest``` artifact to test your
own implementation of API. Artifact contains set of ```*CheckList``` files. These files are
abstract JUnit5 test classes. You should extend the class ans provide your implementation
through the abstract method.

## Versioning
Artifact versions of the CliArguments Module follow the 
[Semantic Versioning 2.0.0](https://semver.org/spec/v2.0.0.html) specification.

## License
The module is provided under MIT License Copyright (c) 2020 Zkejid.