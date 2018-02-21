# CONTroL

CONTroL, which stands for CONtext-variability-based software Testing Library, is a tool that aims to support the execution of test cases in mobile dynamically adaptive systems, focusing on the validation of the adaptation process of those systems. Currently, this tool is only available for the Android platform.

## Prerequisites
1. Android Studio 3.0.1 or higher.
2. Android API 14 or higher.

## Instructions
1. Clone the control-library project and make the compile process using Android Studio.
2. Open the project from the system that will be tested and import the compiled control-library as an Android module.
3. Configure the gradle file of the system under test as explained in the "gradle_example" file in "samples" folder.
4. Fill the control_test_sequence.json and control_setup.json files following the examples in "samples" folder.
5. Insert the @ControlContext, @ControlContextGroup, @ControlFeature and @ControlStatusAdapted in the methods from the source code of the system under test.
6. In the android device used to test the application create folder named "/control" in the root of the SD Card. After that move the "control_test_sequence.json" and "control_setup.json" files to this folder.
7. Execute the application.
8. After all tests finishes, move the "control_report.json" file from "/control" folder for a computer.
9. Execute the control-report tool to generate the final report file.

See the paper about CONTroL for further reference. Link:

## Developers
* Ismayle de Sousa Santos
* Erick Barros dos Santos

## License