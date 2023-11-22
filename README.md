# Chisel Debug Tests

A collection of small standalone Chisel generators to end-to-end test CIRCT's
debug info emission.

- Use `sbt run` to run all generators and produce output files in `build/`.
- Set the `FIRTOOL_BIN` environment variable to specify a custom installation
  location of `firtool`; otherwise just looks for `firtool` in your `PATH`.
- The main function that executes all tests is in `src/main/scala/main.scala`.
