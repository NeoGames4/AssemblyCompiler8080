# AssemblyCompiler8080
A simple assembly compiler for the Intel 8080 based chip used at the electronics practical course (Elektronikpraktikum) at the University of Bonn.

## Table of Contents
1. [What it does](#what-it-does)
2. [What it does not](#what-it-does-not)
3. [How to use from the command line](#how-to-use-from-the-command-line)
4. [How to use from an IDE of your choice](#how-to-use-from-an-ide-of-your-choice)
5. [Known commands](#known-commands)
6. [Known registers](#known-registers)
7. [Other supported features](#other-supported-features)
8. [Limitations & Requirements](#limitations--requirements)
9. [How to add/edit a command](#how-to-addedit-a-command)
10. [How to add/edit a register](#how-to-addedit-a-register)

## What it does
* This compiler translates assembly code into hex code as required by the course. (The compiler prints each command to a new line. Ignore all line breaks when you copy the code into the chip to assure correct line jumping.)
* Informs you if a command or a register is not known (to the compiler) or if they might be used incorrectly.
* Additional it is also possible to print out the binary representation.

## What it does not
* The compiler does not actually *execute* your code. To test the code’s behaviour I recommend https://eliben.org/js8080/ by [Eli Bendersky](https://github.com/eliben/js-8080-sim). (Please note that this online simulator does not support `IN` or `OUT`, but it shows the content of each register.)
* The compiler does not know every command specified in the table of the EP manual, as well as defined by the Intel 8080 assembly documentation. For a list of known commands see below. (If you known Java basics, it is relatively easy to add your own commands though!)

Please also read limitations and requirements further below.

## How to use from the command line
1. Download the latest release or clone this repository (to build it yourself).
2. Navigate to the `.jar`-file from your terminal.
3. Run `java -jar [enter release file path here] [assembly source file path] [hex destination file]` (without the brackets),  
for example `java -jar EPAssemblyCompiler.jar aufgabe1/source.asm aufgabe1/source_hex.txt`.
4. The compiled hex code should be written to the destination file.

## How to use from an IDE of your choice
1. Download or clone this repository.
2. Import the project to your IDE (this process varies for each IDE).
3. Edit the run configuration to specify arguments for the `main`-method as above (this process varies for each IDE).
4. Run the project. The compiled hex code should be written to the destination file.

## Known commands
Please read the EP manual for more information about each command. Also take a look at allowed registers for `N`, `E` and `O` below.
<table style="undefined;table-layout: fixed; width: 1061px"><colgroup>
<col style="width: 117px">
<col style="width: 60px">
<col style="width: 432px">
<col style="width: 452px">
</colgroup>
<thead>
  <tr>
    <th>Usage<br></th>
    <th>Args</th>
    <th>Description</th>
    <th>Example</th>
  </tr></thead>
<tbody>
  <tr>
    <td>MVI N, n</td>
    <td>2</td>
    <td>Moves a decimal number n ≤ 2^8 into the register N.<br></td>
    <td>MVI A,0<br>(Sets the accumulator register A to zero.)</td>
  </tr>
  <tr>
    <td>MOV M, N<br></td>
    <td>2</td>
    <td>Copies the content of a register N into register M.<br></td>
    <td>MOV B,A<br>(Sets B to the content of the accumulator A.)</td>
  </tr>
  <tr>
    <td>INR N<br></td>
    <td>1</td>
    <td>Increases the content of the register N by 1.<br></td>
    <td>INR A<br>(Increases the accumulator by one.)</td>
  </tr>
  <tr>
    <td>DCR N<br></td>
    <td>1</td>
    <td>Decreases the content of the register N by 1.<br></td>
    <td>DCR C<br>(Increases the register C by one.)</td>
  </tr>
  <tr>
    <td>CMP N<br></td>
    <td>1</td>
    <td>Compares the content of N with the content of the accumulator. Sets the flag Z = 1 if they are equals.<br></td>
    <td>CMP B<br>JZ EQUALS<br>(Jumps to label 'EQUALS' if B is equals to A.)</td>
  </tr>
  <tr>
    <td>ADI n<br></td>
    <td>1</td>
    <td>Adds the decimal number  n ≤ 2^8 to the accumulator A.</td>
    <td>ADI 13<br>(Adds 13 to the content of A.)</td>
  </tr>
  <tr>
    <td>DAD Y<br></td>
    <td>1</td>
    <td>Adds a double to another double. Doubles are saved in multiple registers. (Please read the EP manual for more details.)</td>
    <td>DAD B<br>(Adds the double contained in the registers B, C to H, L.)<br></td>
  </tr>
  <tr>
    <td>ANA N<br></td>
    <td>1</td>
    <td>Performs a bitwise AND operation between A and N and saves the result to A.</td>
    <td>MVI A,6     ; Sets A = 0000 0110<br>MVI B,10   ; Sets B = 0000 1010<br>ANA B<br>(Puts A = 0000 0010 into the accumulator A.)</td>
  </tr>
  <tr>
    <td>ORA N<br></td>
    <td>1</td>
    <td>Performs a bitwise OR operation between A and N and saves the result to A.<br></td>
    <td>MVI A,6<br>MVI B,10<br>ORA B<br>(Puts A = 0000 1110 into the accumulator A.)</td>
  </tr>
  <tr>
    <td>XRA N<br></td>
    <td>1</td>
    <td>Performs a bitwise XOR operation between A and N and saves the result to A.<br></td>
    <td>MVI A,6<br>MVI B,10<br>XRA B<br>(Puts A = 0000 1100 into the accumulator A.)</td>
  </tr>
  <tr>
    <td>JMP L<br></td>
    <td>1</td>
    <td>Jumps to the label L. (Sets the program counter to the address at L.)<br></td>
    <td>LABEL:<br>  JMP LABEL<br>(Jumps back to LABEL and stays in a never ending loop.)</td>
  </tr>
  <tr>
    <td>JZ L<br></td>
    <td>1</td>
    <td>Jumps to the label L if the Flag Z is 1.<br></td>
    <td>CMP B<br>JZ L<br>(Jumps to the label L if the accumulator A is equals to B.)</td>
  </tr>
  <tr>
    <td>JNZ L<br></td>
    <td>1</td>
    <td> Jumps to the label L if the Flag Z is 0.<br></td>
    <td>CMP B<br>JNZ L<br>(Jumps to the label L if the accumulator A is not equals to B.)</td>
  </tr>
  <tr>
    <td>IN E<br></td>
    <td>1</td>
    <td>Loads the content of the input register E to the accumulator A.<br></td>
    <td>IN B<br>MOV B,A<br>(Loads the input from the input register B to A and then A to the [output] register B. The Bs are not the same!)</td>
  </tr>
  <tr>
    <td>OUT O<br></td>
    <td>1</td>
    <td>Loads the content of the accumulator A to the output register O.<br></td>
    <td>MVI A,0<br>OUT DAC<br>(Puts a voltage of 0 to the digital-analog converter.)</td>
  </tr>
  <tr>
    <td>HLT</td>
    <td>0<br></td>
    <td>Stops the program.<br></td>
    <td>L:<br>  HLT<br>  JMP L<br>(Halts at the first iteration of the loop.)<br></td>
  </tr>
</tbody></table>

## Known registers
This lists contains all allowed registers for the commands listed above.
* For **N**: A (accumulator), B, C, D, E, H and L.
* For **E**: A, B, C and ADC. (Input.)
* For **O**: X, R, DAC and ADC. (Output.)

To add or edit registers read further below.

## Other supported features
### Comments
The compiler supports/ignores (single line) comments.
```asm
START:
    MVI C,10        ; This is a single line comment
    IN B            ; Text behind semicolons is going to be ignored
    MOV B,A

    CMP C,10
    JNZ START

EQUALS:
    OUT R
    JMP START
```

### Debug mode
The `Compiler.java`-class contains a boolean named `DEBUG`, which is `true` by default. If true, the compiler will show the following extra information.
* The current line (to the console while compiling).
* The corresponding (original) line next to each hex code line (to the destination file, separated by an '@' character).

## Limitations & Requirements
* Requires [Java 8](https://www.java.com/de/download/manual.jsp) (either by your computer or by your IDE).
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
