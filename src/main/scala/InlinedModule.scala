import chisel3._
import chisel3.util.experimental._

// This module will be inlined into `InlinedModuleTB`. It won't be visible in
// the output Verilog. It is tracked in the debug info though.
class InlinedModule extends RawModule with InlineInstance {
  val a = IO(Input(UInt(13.W)))
  val b = IO(Input(UInt(13.W)))
  val c = IO(Output(UInt(13.W)))
  c := a +% b
}

class InlinedModuleTB extends SimpleTestbench {
  val dut = Module(new InlinedModule)
  dut.a := randomize()
  dut.b := randomize()
  val result = WireInit(dut.c)
  dontTouch(result)
}
