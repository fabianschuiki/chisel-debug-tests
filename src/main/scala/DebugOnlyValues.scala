import chisel3._
import chisel3.util._

class DebugOnlyValues extends RawModule {
  val a = IO(Input(UInt(19.W)))
  val b = IO(Input(UInt(19.W)))

  // Perform a few logical operations on the input ports. These don't contribute
  // to any output port. They will appear as expressions in the debug info, but
  // not in the generated Verilog.
  val x = a & b
  val y = a | b
  val z = a ^ b
  val w = Cat(a(3, 0), b(5, 0)) // effectively {a[3:0], b[5:0]}
}

class DebugOnlyValuesTB extends SimpleTestbench {
  val dut = Module(new DebugOnlyValues)
  dut.a := randomize()
  dut.b := randomize()
}
