package io.mockk.performance

import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import org.openjdk.jmh.annotations.State
import org.openjdk.jmh.annotations.TearDown
import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.BenchmarkMode
import org.openjdk.jmh.annotations.Level
import org.openjdk.jmh.annotations.Mode
import org.openjdk.jmh.annotations.Param
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.Setup
import org.openjdk.jmh.infra.Blackhole

@State(Scope.Thread)
@BenchmarkMode(Mode.Throughput)
open class InjectMockKsBenchmark {
    @Param("5", "20")
    var size: Int = 10

    @Param("independent", "wide", "linear", "diamond")
    lateinit var shape: String

    private lateinit var factory: () -> Any

    @Setup(Level.Trial)
    fun setup() {
        factory = {
            when (shape) {
                "independent" -> {
                    when (size) {
                        20 -> IndependentTarget20()
                        5 -> IndependentTarget5()
                        else -> error("Unsupported size: $size")
                    }
                }
                "wide" -> {
                    when (size) {
                        20 -> WideTarget20()
                        5 -> WideTarget5()
                        else -> error("Unsupported size: $size")
                    }
                }
                "linear" -> {
                    when (size) {
                        20 -> LinearTarget20()
                        5 -> LinearTarget5()
                        else -> error("Unsupported size: $size")
                    }
                }
                "diamond" -> {
                    when (size) {
                        20 -> DiamondTarget20()
                        5 -> DiamondTarget5()
                        else -> error("Unsupported size: $size")
                    }
                }
                else -> error("Unsupported shape: $shape")
            }
        }
    }

    @TearDown(Level.Invocation)
    fun tearDown() = unmockkAll()

    @Benchmark
    fun initWithoutDependencyOrder(blackhole: Blackhole) {
        val target = factory()
        MockKAnnotations.init(target)
        blackhole.consume(target)
    }

    @Benchmark
    fun initWithDependencyOrder(blackhole: Blackhole) {
        val target = factory()
        MockKAnnotations.init(target, useDependencyOrder = true)
        blackhole.consume(target)
    }

    interface Repository {
        fun getData(): String
    }

    // ===== Independent =====
    class Independent01(val repository: Repository)
    class Independent02(val repository: Repository)
    class Independent03(val repository: Repository)
    class Independent04(val repository: Repository)
    class Independent05(val repository: Repository)
    class Independent06(val repository: Repository)
    class Independent07(val repository: Repository)
    class Independent08(val repository: Repository)
    class Independent09(val repository: Repository)
    class Independent10(val repository: Repository)
    class Independent20(val repository: Repository)

    class IndependentTarget5 {
        @MockK
        lateinit var repository: Repository

        @InjectMockKs lateinit var n01: Independent01
        @InjectMockKs lateinit var n02: Independent02
        @InjectMockKs lateinit var n03: Independent03
        @InjectMockKs lateinit var n04: Independent04
        @InjectMockKs lateinit var n05: Independent05
    }

    class IndependentTarget20 {
        @MockK
        lateinit var repository: Repository

        @InjectMockKs lateinit var n01: Independent01
        @InjectMockKs lateinit var n02: Independent02
        @InjectMockKs lateinit var n03: Independent03
        @InjectMockKs lateinit var n04: Independent04
        @InjectMockKs lateinit var n05: Independent05
        @InjectMockKs lateinit var n06: Independent06
        @InjectMockKs lateinit var n07: Independent07
        @InjectMockKs lateinit var n08: Independent08
        @InjectMockKs lateinit var n09: Independent09
        @InjectMockKs lateinit var n10: Independent10
        @InjectMockKs lateinit var n20: Independent20
    }

    // ===== Wide =====
    class Wide01(val repository: Repository)
    class Wide02(val repository: Repository)
    class Wide03(val repository: Repository)
    class Wide04(val repository: Repository)
    class Wide05(val repository: Repository)
    class Wide06(val repository: Repository)
    class Wide07(val repository: Repository)
    class Wide08(val repository: Repository)
    class Wide09(val repository: Repository)
    class Wide10(val repository: Repository)
    class Wide11(val repository: Repository)
    class Wide12(val repository: Repository)
    class Wide13(val repository: Repository)
    class Wide14(val repository: Repository)
    class Wide15(val repository: Repository)
    class Wide16(val repository: Repository)
    class Wide17(val repository: Repository)
    class Wide18(val repository: Repository)
    class Wide19(val repository: Repository)
    class Wide20(val repository: Repository)

    class WideRoot5(
        val n01: Wide01,
        val n02: Wide02,
        val n03: Wide03,
        val n04: Wide04,
    )

    class WideRoot20(
        val n01: Wide01,
        val n02: Wide02,
        val n03: Wide03,
        val n04: Wide04,
        val n05: Wide05,
        val n06: Wide06,
        val n07: Wide07,
        val n08: Wide08,
        val n09: Wide09,
        val n10: Wide10,
        val n11: Wide11,
        val n12: Wide12,
        val n13: Wide13,
        val n14: Wide14,
        val n15: Wide15,
        val n16: Wide16,
        val n17: Wide17,
        val n18: Wide18,
        val n19: Wide19,
    )

    class WideTarget5 {
        @MockK
        lateinit var repository: Repository

        @InjectMockKs lateinit var n01: Wide01
        @InjectMockKs lateinit var n02: Wide02
        @InjectMockKs lateinit var n03: Wide03
        @InjectMockKs lateinit var n04: Wide04
        @InjectMockKs lateinit var root: WideRoot5
    }

    class WideTarget20 {
        @MockK
        lateinit var repository: Repository

        @InjectMockKs lateinit var n01: Wide01
        @InjectMockKs lateinit var n02: Wide02
        @InjectMockKs lateinit var n03: Wide03
        @InjectMockKs lateinit var n04: Wide04
        @InjectMockKs lateinit var n05: Wide05
        @InjectMockKs lateinit var n06: Wide06
        @InjectMockKs lateinit var n07: Wide07
        @InjectMockKs lateinit var n08: Wide08
        @InjectMockKs lateinit var n09: Wide09
        @InjectMockKs lateinit var n10: Wide10
        @InjectMockKs lateinit var n11: Wide11
        @InjectMockKs lateinit var n12: Wide12
        @InjectMockKs lateinit var n13: Wide13
        @InjectMockKs lateinit var n14: Wide14
        @InjectMockKs lateinit var n15: Wide15
        @InjectMockKs lateinit var n16: Wide16
        @InjectMockKs lateinit var n17: Wide17
        @InjectMockKs lateinit var n18: Wide18
        @InjectMockKs lateinit var n19: Wide19
        @InjectMockKs lateinit var root: WideRoot20
    }

    // ===== Linear =====
    class Linear01(val repository: Repository)
    class Linear02(val n01: Linear01)
    class Linear03(val n02: Linear02)
    class Linear04(val n03: Linear03)
    class Linear05(val n04: Linear04)
    class Linear06(val n05: Linear05)
    class Linear07(val n06: Linear06)
    class Linear08(val n07: Linear07)
    class Linear09(val n08: Linear08)
    class Linear10(val n09: Linear09)
    class Linear11(val n10: Linear10)
    class Linear12(val n11: Linear11)
    class Linear13(val n12: Linear12)
    class Linear14(val n13: Linear13)
    class Linear15(val n14: Linear14)
    class Linear16(val n15: Linear15)
    class Linear17(val n16: Linear16)
    class Linear18(val n17: Linear17)
    class Linear19(val n18: Linear18)
    class Linear20(val n19: Linear19)
    class Linear21(val n20: Linear20)
    class Linear22(val n21: Linear21)
    class Linear23(val n22: Linear22)
    class Linear24(val n23: Linear23)
    class Linear25(val n24: Linear24)
    class Linear26(val n25: Linear25)
    class Linear27(val n26: Linear26)
    class Linear28(val n27: Linear27)
    class Linear29(val n28: Linear28)
    class Linear30(val n29: Linear29)

    class LinearTarget5 {
        @MockK
        lateinit var repository: Repository

        @InjectMockKs lateinit var n01: Linear01
        @InjectMockKs lateinit var n02: Linear02
        @InjectMockKs lateinit var n03: Linear03
        @InjectMockKs lateinit var n04: Linear04
        @InjectMockKs lateinit var n05: Linear05
    }

    class LinearTarget20 {
        @MockK
        lateinit var repository: Repository

        @InjectMockKs lateinit var n01: Linear01
        @InjectMockKs lateinit var n02: Linear02
        @InjectMockKs lateinit var n03: Linear03
        @InjectMockKs lateinit var n04: Linear04
        @InjectMockKs lateinit var n05: Linear05
        @InjectMockKs lateinit var n06: Linear06
        @InjectMockKs lateinit var n07: Linear07
        @InjectMockKs lateinit var n08: Linear08
        @InjectMockKs lateinit var n09: Linear09
        @InjectMockKs lateinit var n10: Linear10
        @InjectMockKs lateinit var n11: Linear11
        @InjectMockKs lateinit var n12: Linear12
        @InjectMockKs lateinit var n13: Linear13
        @InjectMockKs lateinit var n14: Linear14
        @InjectMockKs lateinit var n15: Linear15
        @InjectMockKs lateinit var n16: Linear16
        @InjectMockKs lateinit var n17: Linear17
        @InjectMockKs lateinit var n18: Linear18
        @InjectMockKs lateinit var n19: Linear19
        @InjectMockKs lateinit var n20: Linear20
    }

    // ===== Diamond =====
    class Diamond01(val repository: Repository)
    class Diamond02(val n01: Diamond01)
    class Diamond03(val n01: Diamond01)
    class Diamond04(val n02: Diamond02, val n03: Diamond03)
    class Diamond05(val n04: Diamond04)
    class Diamond06(val n04: Diamond04)
    class Diamond07(val n05: Diamond05, val n06: Diamond06)
    class Diamond08(val n07: Diamond07)
    class Diamond09(val n07: Diamond07)
    class Diamond10(val n08: Diamond08, val n09: Diamond09)
    class Diamond11(val n10: Diamond10)
    class Diamond12(val n10: Diamond10)
    class Diamond13(val n11: Diamond11, val n12: Diamond12)
    class Diamond14(val n13: Diamond13)
    class Diamond15(val n13: Diamond13)
    class Diamond16(val n14: Diamond14, val n15: Diamond15)
    class Diamond17(val n16: Diamond16)
    class Diamond18(val n16: Diamond16)
    class Diamond19(val n17: Diamond17, val n18: Diamond18)
    class Diamond20(val n19: Diamond19)
    class Diamond21(val n19: Diamond19)
    class Diamond22(val n20: Diamond20, val n21: Diamond21)
    class Diamond23(val n22: Diamond22)
    class Diamond24(val n22: Diamond22)
    class Diamond25(val n23: Diamond23, val n24: Diamond24)
    class Diamond26(val n25: Diamond25)
    class Diamond27(val n25: Diamond25)
    class Diamond28(val n26: Diamond26, val n27: Diamond27)
    class Diamond29(val n28: Diamond28)
    class Diamond30(val n28: Diamond28)

    class DiamondTarget5 {
        @MockK
        lateinit var repository: Repository

        @InjectMockKs lateinit var n01: Diamond01
        @InjectMockKs lateinit var n02: Diamond02
        @InjectMockKs lateinit var n03: Diamond03
        @InjectMockKs lateinit var n04: Diamond04
        @InjectMockKs lateinit var n05: Diamond05
    }

    class DiamondTarget20 {
        @MockK
        lateinit var repository: Repository

        @InjectMockKs lateinit var n01: Diamond01
        @InjectMockKs lateinit var n02: Diamond02
        @InjectMockKs lateinit var n03: Diamond03
        @InjectMockKs lateinit var n04: Diamond04
        @InjectMockKs lateinit var n05: Diamond05
        @InjectMockKs lateinit var n06: Diamond06
        @InjectMockKs lateinit var n07: Diamond07
        @InjectMockKs lateinit var n08: Diamond08
        @InjectMockKs lateinit var n09: Diamond09
        @InjectMockKs lateinit var n10: Diamond10
        @InjectMockKs lateinit var n11: Diamond11
        @InjectMockKs lateinit var n12: Diamond12
        @InjectMockKs lateinit var n13: Diamond13
        @InjectMockKs lateinit var n14: Diamond14
        @InjectMockKs lateinit var n15: Diamond15
        @InjectMockKs lateinit var n16: Diamond16
        @InjectMockKs lateinit var n17: Diamond17
        @InjectMockKs lateinit var n18: Diamond18
        @InjectMockKs lateinit var n19: Diamond19
        @InjectMockKs lateinit var n20: Diamond20
    }
}
