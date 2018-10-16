Simple tool to process sales notification messages.

Dependencies:
SBT build tool is used for building, packaging and testing the application.
    If you do not have SBT installed, you can set it up using
    https://www.scala-sbt.org/1.x/docs/Installing-sbt-on-Mac.html

Test:
At the root level of the project, run the command "sbt test" to run the project test suite.

Package:
At the root level of the project, run the command "sbt package" to create the Runtime jar for the project in the target folder.

Run:
At the location of the jar file, run the following command:
"java -jar <jar_file_name>"

Assumptions:
1. Application supports only pounds(P)
2. Report will be printed to command line
3. The external company will be pushing events in chunks to the system to process, application processes 1 chunk at a time
4. Both sales and adjustment reports need to be printed once more at the end of processing of a chunk