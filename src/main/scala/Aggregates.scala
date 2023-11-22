import chisel3._
import chisel3.util._

class Aggregates extends RawModule {
  val a = IO(Input(UInt(4.W)))
  val b = IO(Input(UInt(4.W)))

  // Generate a few aggregate values (bundles and vectors) that will only appear
  // in the debug info. These generate structs and arrays.
  val someBundle = Wire(new SomeBundle)
  someBundle.foo := a & b
  someBundle.bar := a | b

  val someVec = Wire(Vec(3, UInt(4.W)))
  someVec(0) := a
  someVec(1) := b
  someVec(2) := a ^ b

  val everything = Wire(new Bundle {
    val innerBundle = new SomeBundle
    val innerVec = Vec(3, UInt(4.W))
  })
  everything.innerBundle := someBundle
  everything.innerVec := someVec

  // The above will generate the following separate variables:
  // - someBundle: {foo: a & b, bar: a | b}
  // - someVec: [a, b, a ^ b]
  // - everything: {
  //     innerBundle: {foo: a & b, bar: a | b},
  //     innerVec: [a, b, a ^ b],
  //   }
}

class SomeBundle extends Bundle {
  val foo = UInt(4.W)
  val bar = UInt(4.W)
}

class AggregatesTB extends SimpleTestbench {
  val dut = Module(new Aggregates)
  dut.a := randomize()
  dut.b := randomize()
}
