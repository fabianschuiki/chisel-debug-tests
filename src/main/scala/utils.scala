import chisel3._
import chisel3.util.HasBlackBoxInline
import circt.stage.ChiselStage

/// A simple base class for testbenches. Generates a clock and reset, and
/// provides a `randomize()` function that generates a random value every cycle.
class SimpleTestbench extends RawModule {
  // Instantiate the clock generator.
  val ckgen = Module(new ClockGenerator)
  val clock = ckgen.io.clock
  val reset = ckgen.io.reset

  /// Returns a Chisel value that will be a new random value at every clock
  /// tick.
  def randomize(): UInt = {
    val rng = Module(new Randomizer)
    rng.io.clock := clock
    rng.io.value
  }
}

/// A verbatim Verilog module that generates a clock and reset signal for a
/// brief period of time, and then ceases.
class ClockGenerator extends BlackBox with HasBlackBoxInline {
  val io = IO(new Bundle {
    val clock = Output(Clock())
    val reset = Output(Bool())
  })
  setInline(
    "ClockGenerator.sv",
    """
    |module ClockGenerator(output logic clock, output logic reset);
    |  int step = 0;
    |  initial repeat(200) #1 step++;
    |  assign clock = ~(step & 1);
    |  assign reset = step < 10;
    |endmodule
    """.trim.stripMargin
  )
}

/// A verbatim Verilog module that produces a new randomized value upon every
/// clock tick.
class Randomizer extends BlackBox with HasBlackBoxInline {
  val io = IO(new Bundle {
    val clock = Input(Clock())
    val value = Output(UInt(32.W))
  })
  setInline(
    "Randomizer.sv",
    """
    |module Randomizer(
    |  input logic clock,
    |  output logic [31:0] value
    |);
    |  always @(posedge clock) value = $urandom();
    |endmodule
    """.trim.stripMargin
  )
}

/// Helper to run a Chisel generator and firtool, and produce the corresponding
/// output files in `build/<module-name>`.
object Firtool {
  def generateFiles(generator: => RawModule): Unit = {
    var name: String = "Unknown"
    val chirrtl = ChiselStage.emitCHIRRTL({
      val module = generator
      name = module.name
      module
    })
    val binary: String = sys.env.get("FIRTOOL_BIN").getOrElse("firtool")
    val cmd = Seq(
      binary,
      "--format=fir",
      "--split-verilog",
      "-o", s"build/$name",
      "--output-final-mlir", s"build/$name/final.mlir",
      "-g",
      "--emit-hgldd",
      "--hgldd-only-existing-file-locs",
    )
    os.proc(cmd).call(stdin = chirrtl)
  }
}
