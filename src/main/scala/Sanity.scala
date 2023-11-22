import chisel3._

class Sanity extends RawModule {
  val a = IO(Input(UInt(8.W)))
  val b = IO(Output(UInt(8.W)))
  b := a
}

class SanityTB extends SimpleTestbench {
  val dut = Module(new Sanity)
  dut.a := randomize()
  dontTouch(dut.b) // prevent unused ports from being deleted
}
