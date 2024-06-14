# AssemblyCompiler8080
A simple assembly compiler for the Intel 8080 based chip used at the electronics practical course (Elektronikpraktikum) at the University of Bonn.

## What it does
* This compiler translates assembly code into hex code as required by the course. (The compiler prints each command to a new line. Ignore all line breaks when you copy the code into the chip to assure correct line jumping.)
* Informs you if a command or a register is not known (to the compiler) or if they might be used incorrectly.
* Additional it is also possible to print out the binary representation.

## What it does not
* The compiler does not actually *execute* your code. To test the code behaviour I recommend https://eliben.org/js8080/ by [Eli Bendersky](https://github.com/eliben/js-8080-sim).
* The compiler does not know every command specified in the table of the EP manual, as well as defined by the Intel 8080 assembly documentation. For a list of known commands see below. (If you known Java basics, it is relatively easy to add your own commands though!)

Please also read limitations and requirements further below.

## How to use from command line
1. Download the latest release or clone this repository (to build it yourself).
2. Navigate to the `.jar`-file from your terminal.
3. Run `java -jar [enter release file path here] [assembly source file path] [hex destination file]` (without the brackets),  
for example `java -jar EPAssemblyCompiler.jar aufgabe1/source.asm aufgabe1/source_hex.txt`.
4. The compiled hex code should be written to the destination file.

## How to use from an IDE of your choice
1. Download or clone this repository.
2. Import the project to your IDE (this process varies for each IDE).
3. Edit the run configuration to specify arguments for the `main`-method as above (this process varies for each IDE).
4. Run the project. The compiledhex code should be written to the destination file.

## Known commands

## Known registers

## Other supported features
### Comments
### Debug mode

## Limitations & Requirements
* Requires Java 8 (either by your computer or by your IDE).
* The compiler does not support instructions right behind a label declaration.

Instead of
```asm
A_LABEL:    MVI A,0
            JMP A_LABEL
```
write (as recommended anyways):
```asm
A_LABEL:
    MVI A,0
    JMP A_LABEL
```

## How to add/edit a command

## How to add/edit a register
