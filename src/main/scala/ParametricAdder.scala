import chisel3._

class ParametricAdder(width: Int) extends RawModule {
  val a = IO(Input(UInt(width.W)))
  val b = IO(Input(UInt(width.W)))
  val z = IO(Output(UInt(width.W)))
  z := a +% b
}

class ParametricAdderTB extends SimpleTestbench {
  // Create three different `ParametricAdder` instances, with bit widths set to
  // 4, 7, and 14. Arrange the adders as `dut14(dut4(...), dut7(...))`.
  val dut4 = Module(new ParametricAdder(4))
  val dut7 = Module(new ParametricAdder(7))
  val dut14 = Module(new ParametricAdder(14))
  dut4.a := randomize()
  dut4.b := randomize()
  dut7.a := randomize()
  dut7.b := randomize()
  dut14.a := dut4.z
  dut14.b := dut7.z
  dontTouch(dut14.z)
}
