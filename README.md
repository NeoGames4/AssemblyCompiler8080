# AssemblyCompiler8080
A simple assembly compiler for the Intel 8080 based chip used at the electronics practical course (Elektronikpraktikum) at the University of Bonn.

View `latex-syntax-highlighting.tex` for simple assembly syntax highlighting in LaTeX.

![Static Badge](https://img.shields.io/badge/latest-1.1.0-green?link=https%3A%2F%2Fgithub.com%2FNeoGames4%2FAssemblyCompiler8080%2Freleases%2Ftag%2Fv1.1.0) ![Static Badge](https://img.shields.io/badge/status-active-green) ![Static Badge](https://img.shields.io/badge/requires-Java%20JDK%2FOpenJDK%208%2B-red) ![Static Badge](https://img.shields.io/badge/license-open%20source%2Fmpl--2.0-violet)

> ℹ︎ This program and the documentation refer to the EP manual from April, 2024. (SoSe 2024.)
> However, the instructions should not change very often. The program should still be up-to-date in the future. A careful look at the hex output is still advisable.
> Please do not hesitate to contact me via my e-mail if you have any questions or problems (regarding the compiler!): `mika.t (@) uni-bonn (dot) de`.

## Table of Contents
1. [What it does](#what-it-does)
2. [What it does not](#what-it-does-not)
3. [How to use from the command line](#how-to-use-from-the-command-line)
4. [How to use from an IDE of your choice](#how-to-use-from-an-ide-of-your-choice)
5. [Known instructions](#known-instructions)
6. [Known registers](#known-registers)
7. [Other supported features](#other-supported-features)
8. [Limitations & Requirements](#limitations--requirements)
9. [How to add/edit a instruction](#how-to-addedit-a-instruction)
10. [How to add/edit a register](#how-to-addedit-a-register)

## What it does
* This compiler translates assembly code into hex code as required by the course. (The compiler prints each instruction to a new line. Ignore all line breaks when you copy the code to the chip to assure correct line jumping.)
* It informs you if a instruction or a register is not known (to the compiler) or if they might be used incorrectly.
* Additionally, it is also possible to compile to binary (by setting the third argument to `binary` as described below).

## What it does not
* The compiler does not actually *execute* your code. To test the code’s behaviour I recommend https://eliben.org/js8080/ by [Eli Bendersky](https://github.com/eliben/js-8080-sim). (Please note that this online simulator does not support `IN` or `OUT`, but it shows the content of each register.)
* The compiler does not know every instruction specified in the table of the EP manual, as well as defined by the Intel 8080 assembly documentation. For a list of known instructions see below. (If you know some Java or C basics, it might be easy to add your own instructions though!)

Please also read limitations and requirements further below.

## How to use from the command line
For this approach the Java 8 JDK is required. Setting the third argument to `debug` is **highly recommended** as it increases the readability when copying the code to the computer used at the course.
1. Download the [latest release](https://github.com/NeoGames4/AssemblyCompiler8080/releases) or clone this repository (to build it yourself).
2. Navigate to the `.jar`-file from your terminal.
3. Run `java -jar [enter release file path here] [assembly source file path] [hex destination file] {hex/binary} {debug/standard}` (without the brackets, curly brackets are optional),  
for example `java -jar EPAssemblyCompiler.jar aufgabe1/source.asm aufgabe1/source_hex.txt hex debug`.
4. The compiled hex code should be written to the destination file.

## How to use from an IDE of your choice
1. Download or clone this repository.
2. Import the `EPAssembly` folder to your IDE (this process varies for each IDE). ([Eclipse](https://stackoverflow.com/a/8611662) | [IntelliJ IDEA](https://www.jetbrains.com/help/idea/import-project-or-module-wizard.html#import-project) | [VS Code](https://code.visualstudio.com/docs/java/java-project#:~:text=Import%20Java%20projects,gradle%20).)
3. Edit the run configuration to specify arguments for the `main`-method as above. ([Eclipse](https://help.eclipse.org/latest/index.jsp?topic=%2Forg.eclipse.jdt.doc.user%2Ftasks%2Ftasks-executionArgs.htm) | [IntelliJ IDEA](https://www.jetbrains.com/help/objc/add-environment-variables-and-program-arguments.html#:~:text=From%20the%20main%20menu%2C%20select,in%20the%20Program%20arguments%20field.) | [VS Code](https://code.visualstudio.com/docs/java/java-debugging#_configuration-options).)
4. Run the project. The compiled hex code should be written to the destination file.

## Known instructions
Please read the EP manual for more information about each instruction. Also take a look at allowed registers for the placeholders `N`, `E` and `O` right below. Instructions whose description has been marked with “⚠︎” have not yet been tested on the chip of the experiment.

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
    <td>CALL L<br></td>
    <td>1</td>
    <td>⚠︎ Jumps to the label L. (Sets the program counter to the address at L.) Puts the relative address of the following instruction on the stack to jump back when return is called.<br></td>
    <td>CALL LABEL<br>LABEL:<br>  RET<br>(Jumps to LABEL and returns immediately, staying in a never ending loop.)</td>
  </tr>
  <tr>
    <td>CZ L<br></td>
    <td>1</td>
    <td>⚠︎ Jumps to the label L and puts the relative address on the stack (waiting for return) if the Flag Z is 1.<br></td>
    <td>CZ LABEL<br>LABEL:<br>  RET<br>(Jumps to LABEL if the accumulator A is 0.)</td>
  </tr>
  <tr>
    <td>CNZ L<br></td>
    <td>1</td>
    <td>⚠︎ Jumps to the label L and puts the relative address on the stack (waiting for return) if the Flag Z is 0.<br></td>
    <td>CNZ LABEL<br>LABEL:<br>  RET<br>(Jumps to LABEL if the accumulator A is not 0.)</td>
  </tr>
  <tr>
    <td>RET<br></td>
    <td>0</td>
    <td>⚠︎ Returns the program counter to the relative address sitting on the stack.<br></td>
    <td>CALL LABEL<br>HLT<br>LABEL:<br>  RET<br>(Jumps ot LABEL and returns immediately to HLT.)</td>
  </tr>
  <tr>
    <td>RZ<br></td>
    <td>0</td>
    <td>⚠︎ Returns the program counter to the relative address sitting on the stack if the flag Z is 1.<br></td>
    <td>CALL LABEL<br>HLT<br>LABEL:<br>  RZ<br>(Jumps ot LABEL and returns immediately to HLT if the accumulator A is 0.)</td>
  </tr>
  <tr>
    <td>RNZ<br></td>
    <td>0</td>
    <td>⚠︎ Returns the program counter to the relative address sitting on the stack if the flag Z is 0.<br></td>
    <td>CALL LABEL<br>HLT<br>LABEL:<br>  RNZ<br>(Jumps ot LABEL and returns immediately to HLT if the accumulator A is not 0.)</td>
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
This lists contains all allowed registers for the instructions listed above.
* For **N**: A (accumulator), B, C, D, E, H and L.
* For **E**: A, B, C and ADC. (Input.)
* For **O**: X, R, DAC and ADC. (Output.)

To add or edit registers read further below.

## Other supported features
### Comments
The compiler supports/ignores (single line) comments indicated by a semicolon.
```asm
START:
    MVI C,10        ; This is a single line comment
    IN B            ; Text behind semicolons is going to be ignored
    MOV B,A

    CMP C
    JNZ START

EQUALS:
    OUT R
    JMP START
```

### Compile to binary
Per default the compiler compiles to hex. To compile to binary the third argument on execution must be set to lowercased `binary`.

### Debug mode
The `Compiler.java`-class contains a boolean named `DEBUG` which is `false` by default, but can be set by writing lowercased `debug` as the fourth argument. If true, the compiler will show the following extra information.
* The current line while compiling (to the console).
* The corresponding (original) line next to each hex code line (to the destination file, separated by an '@' character).

## Limitations & Requirements
* Requires Java 8 JDK (either installed on your computer or by your IDE).
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

## How to add/edit an instruction
1. Open `src/compiler/Instructions.java`.
2. This class contains a static Instruction[]-array named `instructions`. It includes every instruction known to the compiler. Each instruction is defined similar as follows.  
    ```java
    new Instruction("INSTRUCTION_TITLE", 1, "10101010") {	// The instruction title, the amount of required arguments and the binary representation.
        @Override
        // This method defines how this instruction should be translated to binary
        public ArrayList<String> run(String[] args, int line, Compiler compiler) throws CompileException {
            // This list will be returned, contains the 8-bit binary strings that represent this instruction
            // Please assure that each string you add consists of eight characters of only '0' and '1'
            ArrayList<String> r = new ArrayList<>();
            // Adds the binary representation set above ("10101010") to the output
            r.add(this.binaryRepr);

            // Example call of a register.
            // In this case, the register title (such as 'A' or 'B') is received from the first instruction argument args[0]
            // The line variable indicates where the compiler is currently working. It should be passed unchanged for error messages
            Register register = Compiler.getRegisterByTitle(args[0], line);
            // Registers may contain up to three register identifier: inputAddress, outputAddress and its associated binary representation ("ddd" or "sss").
            // Please check whether the required address is defined (is not null)
            if(register.inputAddress == null) {
                throw new CompileException(line, "Unknown input address for register " + register.title + ". The register might not be designed for this usage. "
                    + "Please reassure correct usage of this register and add the required information to its initialization in the Compiler.java class.");
            }
            // Adds the register address to the output
            r.add(register.inputAddress);

            // Return the binary representation of this instruction
            return r;
        }
    }
    ```  
    The `instructions`-array contains multiple examples. Please scroll down to `HLT` to see an instruction that requires no argument. See `INR` or `ANA` for instructions that make use of the "ddd" or "sss" register representation. `MOV` and `MVI` require two arguments. `MVI` and `ADI` accept an integer. Modify or add your instruction accordingly.
3. If you want to add a new instruction, paste its code into the array. Make sure to add a comma after the previous element/in front of the next element.

## How to add/edit a register
1. Open `src/compiler/Compiler.java` and scroll down to the initialization of the static `Register[]`-array `registers`.
2. Each register is defined as follows.
   ```java
   new Register("NAME", "111", "11111111", "00000000")
   ```
   The first argument defines the title of this register. The second argument defines its 3-bit representation ("ddd" or "sss"). The third argument describes its input address, the fourth argument describes its output address. The C register, for example, is represented by "001". (According to the EP manual, tab 8.8.) The input address 02_16 = 00000010_2 is described in section 8.3.3. There is no "output register" called C, which is why it is set to `null`. Read the other initializations of the other registers for more examples. Modify or add your register accordingly.
