import chisel3._
import chisel3.util.experimental._

// This module and all its submodules will be fully inlined into
// `FlattenedModuleTB`. It won't be visible in the output Verilog. It is tracked
// in the debug info though.
class Foo extends RawModule {
  val a = IO(Input(UInt(13.W)))
  val b = IO(Input(UInt(13.W)))
  val c = IO(Output(UInt(13.W)))
  val bar = Module(new Bar)
  bar.x := a
  bar.y := b
  c := bar.z +% 42.U
}

class Bar extends RawModule {
  val x = IO(Input(UInt(13.W)))
  val y = IO(Input(UInt(13.W)))
  val z = IO(Output(UInt(13.W)))
  z := x +% y
}

class FlattenedModuleTB extends SimpleTestbench with FlattenInstance {
  val dut = Module(new Foo)
  dut.a := randomize()
  dut.b := randomize()
  val result = WireInit(dut.c)
  dontTouch(result)
}
