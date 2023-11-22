import chisel3._

class Adder extends RawModule {
  val a = IO(Input(UInt(8.W)))
  val b = IO(Input(UInt(8.W)))
  val z = IO(Output(UInt(8.W)))
  z := a +% b
}

class AdderTB extends SimpleTestbench {
  val dut = Module(new Adder)
  dut.a := randomize()
  dut.b := randomize()
  dontTouch(dut.z)
}
