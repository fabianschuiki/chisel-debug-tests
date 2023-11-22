import chisel3._
import chisel3.util._

class AggregatePorts extends RawModule {
  // Produces the following ports:
  // - input  foo.a
  // - input  foo.bar.b
  // - output foo.bar.c
  val foo = IO(new Bundle {
    val a = Input(UInt(8.W))
    val bar = new Bundle {
      val b = Input(UInt(8.W))
      val c = Output(UInt(8.W))
    }
  })

  // Produces the following ports:
  // - input cow[0]
  // - input cow[1]
  val cow = IO(Input(Vec(2, UInt(8.W))))

  foo.bar.c := 42.U
}

class AggregatePortsTB extends SimpleTestbench {
  val dut = Module(new AggregatePorts)
  dut.foo.a := randomize()
  dut.foo.bar.b := randomize()
  dut.cow(0) := randomize()
  dut.cow(1) := randomize()
}
