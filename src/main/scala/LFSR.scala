import chisel3._
import chisel3.util._

// A simple 16-bit linear feedback shift register with the following polynomial:
//   x^16 + x^14 + x^13 + x^11 + 1
//
// See https://github.com/ucb-bar/chisel-tutorial/blob/release/src/main/scala/solutions/LFSR16.scala
class LFSR extends Module {
  val io = IO(new Bundle {
    val inc = Input(Bool())
    val out = Output(UInt(16.W))
  })

  val state = RegInit(1.U(16.W))
  when (io.inc) {
    val next_state = Cat(state(0)^state(2)^state(3)^state(5), state(15,1))
    state := next_state
  }
  io.out := state
}

class LFSRTB extends SimpleTestbench {
  // `clock` and `reset` are provided by `SimpleTestbench`
  withClockAndReset(clock, reset) {
    val dut = Module(new LFSR)
    dut.io.inc := randomize()
    dontTouch(dut.io.out)
  }
}
