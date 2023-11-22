/// The main function that will simply run all interesting Chisel generators in
/// this repository and produce a whole bunch of output files.
///
/// If you define additional test cases, add corresponding lines here to have
/// them executed.
object Main {
  def main(args: Array[String]): Unit = {
    Firtool.generateFiles(new SanityTB)
    Firtool.generateFiles(new AdderTB)
    Firtool.generateFiles(new ParametricAdderTB)
    Firtool.generateFiles(new DebugOnlyValuesTB)
    Firtool.generateFiles(new AggregatesTB)
    Firtool.generateFiles(new AggregatePortsTB)
    Firtool.generateFiles(new LFSRTB)
    Firtool.generateFiles(new InlinedModuleTB)
  }
}
